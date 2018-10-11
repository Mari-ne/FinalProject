package com.epam.totalizator.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.epam.totalizator.entity.Competition;
import com.epam.totalizator.service.CompetitionService;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.exception.ProjectException;

/**
 * Servlet Filter implementation class MainPageFillter
 */
@WebFilter(urlPatterns = { "/jsp/main.jsp" },
dispatcherTypes = {
		DispatcherType.FORWARD,
		DispatcherType.REQUEST
})
public class MainPageFillter implements Filter {

	private static final Logger LOGGER= Logger.getRootLogger();
	private static final String PARAM_LANG = "lang";
	private static final String PARAM_LIST = "list"; 
	
    /**
     * Default constructor. 
     */
    public MainPageFillter() {}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		RequestDispatcher dispatcher = null;
		try {
			List<Competition> competitions = CompetitionService.constructMainTable((String)req.getSession().getAttribute(PARAM_LANG));
			request.setAttribute(PARAM_LIST, competitions);
		} catch (ProjectException e) {
			LOGGER.error(e);
			dispatcher = req.getRequestDispatcher(PageManager.getPage("path.error"));
		}		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {}

}
