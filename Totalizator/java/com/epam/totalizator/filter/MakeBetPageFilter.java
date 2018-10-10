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

import com.epam.totalizator.entity.Competition;
import com.epam.totalizator.entity.User;
import com.epam.totalizator.service.CompetitionService;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.util.ProjectException;
import com.epam.totalizator.util.ServiceThread;

/**
 * Servlet Filter implementation class MakeBetPageFilter
 */
@WebFilter(urlPatterns = { "/jsp/makeBet_ru_RU.jsp", "/jsp/makeBet_en_EN.jsp", "/jsp/makeBet_jp_JP.jsp" },
dispatcherTypes = {
		DispatcherType.FORWARD,
		DispatcherType.REQUEST
})
public class MakeBetPageFilter implements Filter {

    /**
     * Default constructor. 
     */
    public MakeBetPageFilter() {}

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
			User user = (User)req.getSession().getAttribute("user");
			if(user.getCards() != null) {
				List<Competition> comp = CompetitionService.getBettable((Locale)req.getSession().getAttribute("lang"));
				if(comp.isEmpty() || comp.size() != ServiceThread.number.get()) {
					dispatcher = req.getRequestDispatcher(PageManager.getPage("path.error"));
				}else {
					request.setAttribute("list", comp);				
				}
			} else {
				dispatcher = req.getRequestDispatcher(PageManager.getPage("path.error"));
			}
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
