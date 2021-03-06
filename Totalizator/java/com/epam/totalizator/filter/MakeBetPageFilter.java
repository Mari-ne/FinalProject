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

import com.epam.totalizator.entity.Competition;
import com.epam.totalizator.entity.User;
import com.epam.totalizator.service.CompetitionService;
import com.epam.totalizator.util.MessageManager;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.exception.ProjectException;
import com.epam.totalizator.util.ServiceThread;

/**
 * Servlet Filter implementation class MakeBetPageFilter
 */
@WebFilter(urlPatterns = { "/jsp/makeBet.jsp"},
dispatcherTypes = {
		DispatcherType.FORWARD,
		DispatcherType.REQUEST
})
public class MakeBetPageFilter implements Filter {

	private static final Logger LOGGER = Logger.getRootLogger();
	private static final String PARAM_LANG = "lang";
	private static final String PARAM_USER = "user";
	private static final String PARAM_LIST = "list";
	private static final String PARAM_ERROR = "error";
	
    /**
     * Default constructor. 
     */
    public MakeBetPageFilter() {}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {}

	/**
	 * Add to request data about 'bettable' competitions. 
	 * 		If user hasn't registered cards or there is not enough amount of competitions, he will be redirect to error page.
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		try {
			User user = (User)req.getSession().getAttribute(PARAM_USER);
			if(CompetitionService.hasMadeBet(user.getLogin())) {
				throw new ProjectException(MessageManager.getMessage("exc.makeBet.second"));
			}
			if(user.getCards() != null) {
				List<Competition> comp = CompetitionService.getBettable((String)req.getSession().getAttribute(PARAM_LANG));
				if(comp.isEmpty() || comp.size() != ServiceThread.number.get()) {
					throw new ProjectException(MessageManager.getMessage("exc.makeBet.competition"));
				}else {
					request.setAttribute(PARAM_LIST, comp);				
				}
			} else {
				throw new ProjectException(MessageManager.getMessage("exc.makeBet.card"));
			}
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
