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

import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.exception.ProjectException;
import com.epam.totalizator.service.UserService;

/**
 * Servlet Filter implementation class UserPageFilter
 */
@WebFilter(urlPatterns = { "/jsp/user.jsp"},
dispatcherTypes = {
		DispatcherType.FORWARD,
		DispatcherType.REQUEST
})
public class UserPageFilter implements Filter {

	private static final Logger LOGGER = Logger.getRootLogger();
	private static final String PARAM_USERS = "users";
	private static final String PARAM_BOOKAMAKERS = "bookmakers";
	
    /**
     * Default constructor. 
     */
    public UserPageFilter() {}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {}

	/**
	 * Add to request data about users and bookmakers.
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		try {
			request.setAttribute(PARAM_USERS, UserService.getUsers());
			request.setAttribute(PARAM_BOOKAMAKERS, UserService.getBookmakers());
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
