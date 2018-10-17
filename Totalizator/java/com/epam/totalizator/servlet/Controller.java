package com.epam.totalizator.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.totalizator.command.AbstractCommand;
import com.epam.totalizator.command.CommandFactory;
import com.epam.totalizator.pool.ConnectionPool;
import com.epam.totalizator.util.MessageManager;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.exception.ProjectException;

/**
 * Class-controller. Work with all requests (urlPatterns = '/').
 *
 */
@WebServlet(urlPatterns = {"/"})
public class Controller extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getRootLogger();
	private static final String PARAM_ERROR = "error";
	
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
    	processing(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
    	processing(req, resp);
    }
    
    private void processing(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException{
    	Optional<String> page = Optional.empty();
    	SessionRequestContainer request = new SessionRequestContainer(req);
    	CommandFactory factory = new CommandFactory();
    	Optional<AbstractCommand> command = factory.defineCommand(request);
    	if(command.isPresent()) {
	    	try {
				page = command.get().execute(request);
		    	if(!page.isPresent()) {
		    		page = Optional.of(PageManager.getPage("path.error"));
		    	}	    	
			} catch (ProjectException e) {
				LOGGER.error(e);
				request.setSessionAttribute(PARAM_ERROR, MessageManager.getMessage(e.getMessage()));
				page = Optional.of(PageManager.getPage("path.error"));
			}
			request.createRequest(req);
			resp.sendRedirect(req.getContextPath() + page.get());
    	}	
    }
    
    /**
     * Close pool of connections.
     */
    @Override
    public void destroy() {
    	try {
			ConnectionPool.getInstance().closePool();
		} catch (SQLException e) {
			LOGGER.error(e);
		}
    	super.destroy();
    }
}
