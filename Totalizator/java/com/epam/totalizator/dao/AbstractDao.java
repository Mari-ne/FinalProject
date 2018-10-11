package com.epam.totalizator.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.epam.totalizator.entity.Entity;
import com.epam.totalizator.exception.ProjectException;

public abstract class AbstractDao <K, T extends Entity> {
	
	public abstract List<T> findAll() throws ProjectException;
	public abstract Optional<T> findById(K id) throws ProjectException;
	public abstract boolean delete(K id) throws ProjectException;
	public abstract boolean delete(T entity) throws ProjectException;
	public abstract boolean create(T entity) throws ProjectException;
	public abstract T update(T entity) throws ProjectException;
	
	private final static Logger LOGGER = Logger.getRootLogger();
	
	protected void closeStatement(Statement stat, ResultSet result){
		try {
			if(result != null)
				result.close();
		} catch(SQLException e) {
			LOGGER.error(e.getMessage());
		}
		try {
			if(stat != null)
				stat.close();
		} catch(SQLException e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	protected void closeStatement(Statement stat){
		try {
			if(stat != null)
				stat.close();
		} catch(SQLException e) {
			LOGGER.error(e.getMessage());
		}
	}
}
