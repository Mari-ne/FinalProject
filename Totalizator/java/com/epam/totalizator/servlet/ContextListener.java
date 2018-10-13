package com.epam.totalizator.servlet;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.epam.totalizator.util.ServiceThread;

/**
 * Application Lifecycle Listener implementation class ContextListener
 */
public class ContextListener implements ServletContextListener {

	private static final Logger LOGGER = Logger.getRootLogger();
	
    /**
     * Default constructor. 
     */
    public ContextListener() {}

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  {}

	/**
	 * Start daemon thread {@linkplain ServiceThread}
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	ServiceThread thread = new ServiceThread();
		thread.setDaemon(true);
		thread.start();
    }
	
}
