package com.epam.totalizator.servlet;

import java.util.Locale;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public SessionListener() {}

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  { 
    	org.apache.log4j.Logger.getRootLogger().debug("New session was created");
    	
    	se.getSession().setAttribute("lang", new Locale("ru"));
    	se.getSession().setAttribute("user", null);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
    	org.apache.log4j.Logger.getRootLogger().debug("Session was destroyed");
    	se.getSession().removeAttribute("lang");
    	se.getSession().removeAttribute("user");
    }
	
}
