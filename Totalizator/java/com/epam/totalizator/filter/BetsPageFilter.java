package com.epam.totalizator.filter;

import java.io.IOException;
import java.util.ArrayList;
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

import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.exception.ProjectException;
import com.epam.totalizator.service.CompetitionService;
import com.epam.totalizator.entity.Competition;
import com.epam.totalizator.entity.Forecast;

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
	private static final String PARAM_LANG = "lang";
	private static final String PARAM_USER = "user";
	
    /**
     * Default constructor. 
     */
    public BetsPageFilter() {}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {}

	/**
	 * Add to request data about user's bets on now 'bettable' competition.
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		try {
			String lang = (String) req.getSession().getAttribute(PARAM_LANG);
			List<List<Forecast>> list = new ArrayList<>();
			for(Competition comp:CompetitionService.getBettable((lang))) {
				List<Forecast> forecast = CompetitionService.getForecastById(comp.getId(), lang);
				list.add(forecast);
			}
			if(!list.isEmpty()) {
				List<String> user = new ArrayList<>();			
				for(Forecast forc : list.get(0)) {
					user.add(forc.getUserLogin());
				}
				req.setAttribute(PARAM_LIST, list);
				req.setAttribute(PARAM_USER, user);
			}else {
				req.setAttribute(PARAM_USER, null);				
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
