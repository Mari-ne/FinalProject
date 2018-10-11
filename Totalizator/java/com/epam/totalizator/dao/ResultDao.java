package com.epam.totalizator.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.epam.totalizator.pool.ConnectionPool;
import com.epam.totalizator.exception.ProjectException;
import com.epam.totalizator.entity.Result;

public class ResultDao extends AbstractDao<Integer, Result> {

	private static final String SQL_SELECT_ALL = "select correct, betters, bets, pool_part, pool, coefficient\r\n"
			+ "from result";
	private static final String SQL_SELECT_BY_CORRECT = "select correct, betters, bets, pool_part, pool, coefficient\r\n"
			+ "from result where correct = ?";
	
	private static final String SQL_UPDATE_RESULT = "update result\r\n"
			+ "set betters = ?, bets = ?, pool = ?, coefficient = ?\r\n"
			+ "where correct = ?";
	
	private static final String SQL_UPDATE_POOL_PART = "update result\r\n"
			+ "set pool_part = ?, pool = ?\r\n"
			+ "where correct = ?";
	
	private static final String CORRECT = "correct";
	private static final String BETTERS = "betters";
	private static final String BETS = "bets";
	private static final String POOL_PART = "pool_part";
	private static final String POOL = "pool";
	private static final String COEFFICIENT = "coefficient";
	
	private static final Logger LOGGER = Logger.getRootLogger();

	@Override
	public List<Result> findAll() throws ProjectException{
		List<Result> results = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet res = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_ALL);
			res = stat.executeQuery();
			while(res.next()) {
				Result result = new Result();
				result.setCorrect(res.getInt(CORRECT));
				result.setBetters(res.getInt(BETTERS));
				result.setBets(BigDecimal.valueOf(res.getDouble(BETS)));
				result.setPoolPart(res.getInt(POOL_PART));
				result.setPool(BigDecimal.valueOf(res.getDouble(POOL)));
				result.setCoefficient(BigDecimal.valueOf(res.getDouble(COEFFICIENT)));
				results.add(result);
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, res);			
		}
		return results;
	}

	@Override
	public Optional<Result> findById(Integer id) throws ProjectException{
		Result result = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_BY_CORRECT);
			stat.setInt(1, id);
			res = stat.executeQuery();
			while(res.next()) {
				result = new Result();
				result.setCorrect(res.getInt(CORRECT));
				result.setBetters(res.getInt(BETTERS));
				result.setBets(BigDecimal.valueOf(res.getDouble(BETS)));
				result.setPoolPart(res.getInt(POOL_PART));
				result.setPool(BigDecimal.valueOf(res.getDouble(POOL)));
				result.setCoefficient(BigDecimal.valueOf(res.getDouble(COEFFICIENT)));
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, res);			
		}
		return Optional.ofNullable(result);
	}

	@Override
	public boolean delete(Integer id) throws ProjectException{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(Result entity) throws ProjectException{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean create(Result entity) throws ProjectException{
		throw new UnsupportedOperationException();
	}

	@Override
	public Result update(Result entity) throws ProjectException{
		PreparedStatement stat = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_UPDATE_RESULT);
			stat.setInt(1, entity.getBetters());
			stat.setBigDecimal(2, entity.getBets());
			stat.setBigDecimal(3, entity.getPool());
			stat.setBigDecimal(4, entity.getCoefficient());
			stat.setInt(5, entity.getCorrect());
			stat.executeUpdate();
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat);			
		}
		return entity;
	}
	
	public Result updatePoolPart(Result entity) throws ProjectException{
		PreparedStatement stat = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_UPDATE_POOL_PART);
			stat.setInt(1, entity.getPoolPart());
			stat.setBigDecimal(2, entity.getPool());
			stat.setInt(3, entity.getCorrect());
			stat.executeUpdate();
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat);			
		}
		return entity;
	}
}
