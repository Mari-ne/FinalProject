package com.epam.totalizator.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import com.mysql.jdbc.Driver;

import com.epam.totalizator.pool.db.DbParameter;
import com.epam.totalizator.util.MessageManager;
import com.epam.totalizator.exception.ProjectException;

import org.apache.log4j.Logger;

/**
 * Pool of connections class.
 * 		Firstly, open 3 connection.
 * 		If there is a need in more connection they will be created (to 10 connection).
 * 		
 */
public class ConnectionPool {
	
	private static ConnectionPool instance;
	
	private LinkedBlockingQueue<Connection> connections;	
	private String url;
	private String user;
	private String password;
	private int maxPoolSize = 10;
	private int minPoolSize = 3;
		
	public AtomicInteger counter = new AtomicInteger(0); //count, how many connections is busy right now

	private static final ReentrantLock lock = new ReentrantLock();	
	private static final Logger LOGGER = Logger.getRootLogger();
	
	private ConnectionPool() {
		url = DbParameter.DB_URL.getValue();
		user = DbParameter.DB_USER.getValue();
		password = DbParameter.DB_PASSWORD.getValue();
		try {
			maxPoolSize = Integer.parseInt(DbParameter.DB_MAXPOOLSIZE.getValue());
			minPoolSize = Integer.parseInt(DbParameter.DB_MINPOOLSIZE.getValue());
		} catch(NumberFormatException e) {
			LOGGER.error(e);
		}
	}
	
	private void initialize(){
		connections = new LinkedBlockingQueue<>(maxPoolSize);
		try {
			DriverManager.registerDriver(new Driver());
			for(int i = 0; i < minPoolSize; i ++) {
				try {
					ProxyConnection connect = new ProxyConnection(DriverManager.getConnection(url, user, password));
					connections.add(connect);
				} catch(SQLException ex) {
					LOGGER.error(ex);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		if(connections.isEmpty()) {
			LOGGER.fatal("There is no connections in pool");
			throw new RuntimeException(MessageManager.getMessage("exc.pool"));
		}
	}
	
	public static ConnectionPool getInstance() {
		try{
			lock.lock();
			if(instance == null) {
				instance = new ConnectionPool();
				instance.initialize();
			}
		} finally {
			lock.unlock();			
		}
		return instance;
	}
	
	public Connection takeConnection() throws ProjectException {
		Connection con = null;		
		try{
			lock.lock();
			con = connections.poll();
			if(con == null && counter.get() < maxPoolSize) {
				try {
					con = new ProxyConnection(DriverManager.getConnection(url, user, password));
				} catch (SQLException ex) {
					throw new ProjectException(ex);
				}
			}
			counter.incrementAndGet();
		} finally {
			lock.unlock();			
		}
		return con;
	}
	
	public void releaseConnection(Connection con) {
		try {
			if(con != null)
				con.close();
		} catch(SQLException e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	public boolean offer(Connection con) {
		counter.decrementAndGet();
		return connections.offer(con);
	}
	
	public void closePool() throws SQLException {
		for(int i = 0; i < connections.size(); i ++) {
			try {
				((ProxyConnection)connections.take()).realyClose();
			} catch (InterruptedException e) {
				LOGGER.error(e.getMessage());
			}
		}
		Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();
		
		while(drivers.hasMoreElements()) {
			DriverManager.deregisterDriver(drivers.nextElement());
		}
		instance = null;
	}
}
