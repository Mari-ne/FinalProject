package com.epam.totalizator.filter;

import java.io.IOException;
import java.util.List;

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

import com.epam.totalizator.entity.TeamVs;
import com.epam.totalizator.service.TeamService;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.exception.ProjectException;

/**
 * Servlet Filter implementation class StatisticPageFilter
 */
@WebFilter(urlPatterns = { "/jsp/statistic.jsp"},
dispatcherTypes = {
		DispatcherType.FORWARD,
		DispatcherType.REQUEST
})
public class StatisticPageFilter implements Filter {

	private static final Logger LOGGER = Logger.getRootLogger();
	private static final String PARAM_LANG = "lang";
	private static final String PARAM_LIST = "list";
	
    /**
     * Default constructor. 
     */
    public StatisticPageFilter() {}

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
			List<TeamVs> matches = TeamService.getStatistic(((String)req.getSession().getAttribute(PARAM_LANG)));
			request.setAttribute(PARAM_LIST, matches);
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
