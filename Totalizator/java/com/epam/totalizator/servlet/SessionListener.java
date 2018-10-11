package com.epam.totalizator.servlet;

import java.util.Locale;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.epam.totalizator.util.ServiceThread;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {

	private static final Logger LOGGER = Logger.getRootLogger();
	
    /**
     * Default constructor. 
     */
    public SessionListener() {}

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  { 
    	LOGGER.debug("New session was created");
    	
    	se.getSession().setAttribute("lang", "en");
    	LOGGER.info(se.getSession().getAttribute("lang") + "\n");
    	se.getSession().setAttribute("user", null);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
    	LOGGER.debug("Session was destroyed");
    	se.getSession().removeAttribute("lang");
    	se.getSession().removeAttribute("user");
    }
	
}
