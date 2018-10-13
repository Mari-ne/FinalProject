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

import com.epam.totalizator.util.MessageManager;

/**
 * Servlet Filter implementation class ErrorFilter
 */
@WebFilter(urlPatterns = { "/jsp/*"},
dispatcherTypes = {
		DispatcherType.FORWARD,
		DispatcherType.REQUEST
})
public class ErrorFilter implements Filter {

	private static final String PARAM_MESSAGE = "message";
	private static final String PARAM_ERROR = "error";
	
    /**
     * Default constructor. 
     */
    public ErrorFilter() { }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {}

	/**
	 * Add error message to request.
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String param = (String)req.getSession().getAttribute(PARAM_MESSAGE);
		if(param != null) {
			req.setAttribute(PARAM_MESSAGE, MessageManager.getMessage(param));
			req.getSession().removeAttribute(PARAM_MESSAGE);
		}
		param = (String)req.getSession().getAttribute(PARAM_ERROR);
		if(param != null) {
			req.setAttribute(PARAM_MESSAGE, param);
			req.getSession().removeAttribute(PARAM_ERROR);
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {}

}
