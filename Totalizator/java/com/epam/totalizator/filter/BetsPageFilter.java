package com.epam.totalizator.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.totalizator.dao.PersonalResultDao;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.exception.ProjectException;

/**
 * Servlet Filter implementation class BetsPageFilter
 */
@WebFilter(urlPatterns = { "/jsp/bets.jsp"},
dispatcherTypes = {
		DispatcherType.FORWARD,
		DispatcherType.REQUEST
})
public class BetsPageFilter implements Filter {

	private static final Logger LOGGER = Logger.getRootLogger();
	private static final String PARAM_LIST = "list";
	
    /**
     * Default constructor. 
     */
    public BetsPageFilter() {}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		try {
			PersonalResultDao dao = new PersonalResultDao();
			request.setAttribute(PARAM_LIST, dao.findAllWithBets());
		} catch (ProjectException e) {
			LOGGER.error(e);
			resp.sendRedirect(req.getContextPath() + PageManager.getPage("path.error"));
		}		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {}

}
