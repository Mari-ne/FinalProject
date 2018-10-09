package com.epam.totalizator.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.totalizator.command.AbstractCommand;
import com.epam.totalizator.command.CommandFactory;
import com.epam.totalizator.pool.ConnectionPool;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.util.ProjectException;
import com.epam.totalizator.util.ServiceThread;

@WebServlet(urlPatterns = {"/"})
public class Controller extends HttpServlet {

	@Override
	public void init() throws ServletException {
		ServiceThread thread = new ServiceThread();
		thread.setDaemon(true);
		thread.start();
		super.init();
	}
	
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
    	SessionRequest request = new SessionRequest(req);
    	CommandFactory factory = new CommandFactory();
    	Optional<AbstractCommand> command = factory.defineCommand(request);
		RequestDispatcher dispatcher = null;
    	if(command.isPresent()) {
	    	try {
				page = command.get().execute(request);
		    	if(page.isPresent()) {
		    		dispatcher = getServletContext().getRequestDispatcher(page.get());
		    	} else {
		    		dispatcher = getServletContext().getRequestDispatcher(PageManager.getPage("path.error"));
		    	}	    	
			} catch (ProjectException e) {
				dispatcher = getServletContext().getRequestDispatcher(PageManager.getPage("path.error"));
			}
			request.createRequest(req);
			resp.sendRedirect(req.getContextPath() + page.get());
			//dispatcher.forward(req, resp);
    	}	
    }
    
    @Override
    public void destroy() {
    	try {
			ConnectionPool.getInstance().closePool();
		} catch (SQLException e) {
			
		}
    	super.destroy();
    }
}
