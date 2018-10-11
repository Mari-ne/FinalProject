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

import com.epam.totalizator.entity.User;
import com.epam.totalizator.service.UserService;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.exception.ProjectException;

/**
 * Servlet Filter implementation class PersonalDataPageFilter
 */
@WebFilter(urlPatterns = { "/jsp/personalData.jsp"},
dispatcherTypes = {
		DispatcherType.FORWARD,
		DispatcherType.REQUEST
})
public class PersonalDataPageFilter implements Filter {

	private static final Logger LOGGER = Logger.getRootLogger();
	private static final String PARAM_USER = "user";
	private static final String PARAM_PERRES = "perRes";
	private static final String PARAM_LANG = "lang";
	private static final String PARAM_FORECAST = "forecast";
	
    /**
     * Default constructor. 
     */
    public PersonalDataPageFilter() {}

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
			User user = (User)req.getSession().getAttribute(PARAM_USER);
			if(user.getRole().equals("User")) {
				String login = user.getLogin();
				req.setAttribute(PARAM_PERRES, UserService.getPersonalResult(login).get());
				req.setAttribute(PARAM_FORECAST, UserService.getForecasts(login, (String)req.getSession().getAttribute(PARAM_LANG)));
			}
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
