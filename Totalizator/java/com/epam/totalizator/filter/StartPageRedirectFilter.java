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
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class StartPageRedirectFilter
 */
@WebFilter( urlPatterns = { "/" },
			dispatcherTypes = {
					DispatcherType.FORWARD,
					DispatcherType.REQUEST
			},
			initParams = { 
					@WebInitParam(name = "start_path", value = "/index.jsp") })
public class StartPageRedirectFilter implements Filter {

	private String indexPath;
	
	public void init(FilterConfig fConfig) throws ServletException {
		indexPath = fConfig.getInitParameter("start_path");
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
		chain.doFilter(request, response);
	}
	
	public void destroy() {
	}

}
