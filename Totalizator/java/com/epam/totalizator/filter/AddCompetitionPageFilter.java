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

import com.epam.totalizator.entity.Sport;
import com.epam.totalizator.entity.SportTeam;
import com.epam.totalizator.service.CompetitionService;
import com.epam.totalizator.service.TeamService;
import com.epam.totalizator.util.MessageManager;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.util.ServiceThread;
import com.epam.totalizator.exception.ProjectException;

/**
 * Servlet Filter implementation class AddCompetitionPageFilter
 */
@WebFilter(urlPatterns = { "/jsp/addCompetition.jsp"},
dispatcherTypes = {
		DispatcherType.FORWARD,
		DispatcherType.REQUEST
})
public class AddCompetitionPageFilter implements Filter {

	private static final Logger LOGGER = Logger.getRootLogger();
	private static final String PARAM_LANG = "lang";
	private static final String PARAM_TEAMS = "teams";
	private static final String PARAM_SPORT = "sport";
	private static final String PARAM_ERROR = "error";
    /**
     * Default constructor. 
     */
    public AddCompetitionPageFilter() {}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {}

	/**
	 * Add to request data about teams and sports to choose from.
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		try {
			if(CompetitionService.getBettable("ru").size() == ServiceThread.number.get()) {
				throw new ProjectException(MessageManager.getMessage("exc.addCompetition.enough"));
			}
			List<SportTeam> teams = TeamService.getTeams((String)req.getSession().getAttribute(PARAM_LANG));
			List<Sport> sport = TeamService.getSports((String)req.getSession().getAttribute(PARAM_LANG));
			request.setAttribute(PARAM_TEAMS, teams);
			request.setAttribute(PARAM_SPORT, sport);
		} catch (ProjectException e) {
			LOGGER.error(e);
			req.getSession().setAttribute(PARAM_ERROR, e.getMessage());
			resp.sendRedirect(req.getContextPath() + PageManager.getPage("path.error"));
		}		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {}

}
