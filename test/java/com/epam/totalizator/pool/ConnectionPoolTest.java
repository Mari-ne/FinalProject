package com.epam.totalizator.pool;

import java.sql.Connection;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.totalizator.exception.ProjectException;

public class ConnectionPoolTest {

	private Connection con;
	
	@Test
	public void getInstance() {
		ConnectionPool pool = ConnectionPool.getInstance();
		Assert.assertEquals(pool, ConnectionPool.getInstance());
	}
	
	@Test
	public void takeConnection() throws ProjectException, SQLException {
		Connection con = ConnectionPool.getInstance().takeConnection();
		Assert.assertEquals(ConnectionPool.getInstance().counter.get(), 1);
		ConnectionPool.getInstance().releaseConnection(con);
	}	
	
	@Test
	public void releaseConnection() {
		Assert.assertEquals(ConnectionPool.getInstance().counter.get(), 0);
	}
	
	@AfterClass
	public void close() throws SQLException {
		ConnectionPool.getInstance().closePool();
	}
}
