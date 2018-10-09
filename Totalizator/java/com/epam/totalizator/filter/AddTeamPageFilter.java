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

import com.epam.totalizator.entity.Sport;
import com.epam.totalizator.service.TeamService;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.util.ProjectException;

/**
 * Servlet Filter implementation class AddTeamPageFilter
 */
@WebFilter(urlPatterns = { "/jsp/addTeam_ru_RU.jsp", "/jsp/addTeam_en_EN.jsp", "/jsp/addTeam_jp_JP.jsp" },
dispatcherTypes = {
		DispatcherType.FORWARD,
		DispatcherType.REQUEST
})
public class AddTeamPageFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AddTeamPageFilter() {}

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
			List<Sport> sport = TeamService.getSports((Locale)req.getSession().getAttribute("lang"));
			request.setAttribute("sport", sport);
		} catch (ProjectException e) {
			org.apache.log4j.Logger.getRootLogger().error(e.getMessage());
			dispatcher = req.getRequestDispatcher(PageManager.getPage("path.error"));
		}		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {}

}
