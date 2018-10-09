package com.epam.totalizator.filter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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

import com.epam.totalizator.entity.SportTeam;
import com.epam.totalizator.service.TeamService;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.util.ProjectException;

/**
 * Servlet Filter implementation class TeamPageFilter
 */
@WebFilter(urlPatterns = { "/jsp/teams_ru_RU.jsp", "/jsp/teams_en_EN.jsp", "/jsp/teams_jp_JP.jsp" },
dispatcherTypes = {
		DispatcherType.FORWARD,
		DispatcherType.REQUEST
})
public class TeamPageFilter implements Filter {

    /**
     * Default constructor. 
     */
    public TeamPageFilter() {}

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
			List<SportTeam> matches = TeamService.getTeams(((Locale)req.getSession().getAttribute("lang")));
			request.setAttribute("list", matches);
		} catch (ProjectException e) {
			dispatcher = req.getRequestDispatcher(PageManager.getPage("path.error"));
		}		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {}

}
