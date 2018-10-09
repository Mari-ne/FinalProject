package com.epam.totalizator.filter;

import java.io.IOException;
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

import com.epam.totalizator.entity.User;
import com.epam.totalizator.service.UserService;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.util.ProjectException;

/**
 * Servlet Filter implementation class PersonalDataPageFilter
 */
@WebFilter(urlPatterns = { "/jsp/personalData_ru_RU.jsp", "/jsp/personalData_en_EN.jsp", "/jsp/personalData_jp_JP.jsp" },
dispatcherTypes = {
		DispatcherType.FORWARD,
		DispatcherType.REQUEST
})
public class PersonalDataPageFilter implements Filter {

    /**
     * Default constructor. 
     */
    public PersonalDataPageFilter() {}

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
			if(user.getRole().equals("User")) {
				String login = user.getLogin();
				req.setAttribute("perRes", UserService.getPersonalResult(login).get());
				req.setAttribute("forecast", UserService.getForecasts(login, (Locale)req.getSession().getAttribute("lang")));
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
