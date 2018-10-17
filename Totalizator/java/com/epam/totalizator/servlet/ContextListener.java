package com.epam.totalizator.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.epam.totalizator.util.ServiceThread;

/**
 * Application Lifecycle Listener implementation class ContextListener
 */
public class ContextListener implements ServletContextListener {	
    
	ServiceThread thread;

	/**
	 * End thread {@linkplain ServiceThread}
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  {
    	if(thread.isAlive())
    		thread.interrupt();
    }

	/**
	 * Start thread {@linkplain ServiceThread}
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	thread = new ServiceThread();
		thread.start();
    }
	
}
