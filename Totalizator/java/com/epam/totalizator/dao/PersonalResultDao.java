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

import com.epam.totalizator.entity.PersonalResult;
import com.epam.totalizator.pool.ConnectionPool;
import com.epam.totalizator.exception.ProjectException;

/**
 * Dao class to work with 'personal_result' table from databse.
 *
 */
public class PersonalResultDao extends AbstractDao<String, PersonalResult> {

	private static final String SQL_SELECT_ALL_PERSONAL_RESULT = "select user_login, last_bet, last_gain, all_bet, all_gain\r\n"
			+ "from personal_result";
	private static final String SQL_SELECT_BY_LOGIN = "select user_login, last_bet, last_gain, all_bet, all_gain\r\n"
			+ "from personal_result where user_login = ?";
	private static final String SQL_UPDATE_LAST_BET = "update personal_result\r\n"
			+ "set last_bet = ?\r\n"
			+ "where user_login = ?";
	private static final String SQL_UPDATE_LAST_GAIN = "update personal_result\r\n"
			+ "set last_gain = ?\r\n"
			+ "where user_login = ?";
	private static final String SQL_SELECT_ALL_WITH_BETS = "select user_login, last_bet, last_gain, all_bet, all_gain\r\n"
			+ "from personal_result\r\n"
			+ "where last_bet is not null";
	
	private static final String LOGIN = "user_login";
	private static final String LAST_BET = "last_bet";
	private static final String LAST_GAIN = "last_gain";
	private static final String ALL_BET = "all_bet";
	private static final String ALL_GAIN = "all_gain";
	
	private static final Logger LOGGER = Logger.getRootLogger();
	
	@Override
	public List<PersonalResult> findAll() throws ProjectException{
		List<PersonalResult> results = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet res = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_ALL_PERSONAL_RESULT);
			res = stat.executeQuery();
			while(res.next()) {
				PersonalResult result = new PersonalResult();
				result.setUserLogin(res.getString(LOGIN));
				result.setLastBet(BigDecimal.valueOf(res.getDouble(LAST_BET)));
				result.setLastGain(BigDecimal.valueOf(res.getDouble(LAST_GAIN)));
				result.setAllBet(BigDecimal.valueOf(res.getDouble(ALL_BET)));
				result.setAllGain(BigDecimal.valueOf(res.getDouble(ALL_GAIN)));
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
	public Optional<PersonalResult> findById(String id) throws ProjectException{
		PersonalResult result = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_BY_LOGIN);
			stat.setString(1, id);
			res = stat.executeQuery();
			while(res.next()) {
				result = new PersonalResult();
				result.setUserLogin(res.getString(LOGIN));
				result.setLastBet(BigDecimal.valueOf(res.getDouble(LAST_BET)));
				result.setLastGain(BigDecimal.valueOf(res.getDouble(LAST_GAIN)));
				result.setAllBet(BigDecimal.valueOf(res.getDouble(ALL_BET)));
				result.setAllGain(BigDecimal.valueOf(res.getDouble(ALL_GAIN)));
			}
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, res);			
		}
		return Optional.ofNullable(result);
	}

	public List<PersonalResult> findAllWithBets() throws ProjectException{
		List<PersonalResult> results = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet res = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_ALL_WITH_BETS);
			res = stat.executeQuery();
			while(res.next()) {
				PersonalResult result = new PersonalResult();
				result.setUserLogin(res.getString(LOGIN));
				result.setLastBet(BigDecimal.valueOf(res.getDouble(LAST_BET)));
				result.setLastGain(BigDecimal.valueOf(res.getDouble(LAST_GAIN)));
				result.setAllBet(BigDecimal.valueOf(res.getDouble(ALL_BET)));
				result.setAllGain(BigDecimal.valueOf(res.getDouble(ALL_GAIN)));
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
	public boolean delete(String id) throws ProjectException{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(PersonalResult entity) throws ProjectException{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean create(PersonalResult entity) throws ProjectException{
		throw new UnsupportedOperationException();
	}

	@Override
	public PersonalResult update(PersonalResult entity) throws ProjectException{
		PreparedStatement stat = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_UPDATE_LAST_BET);
			stat.setBigDecimal(1, entity.getLastBet());
			stat.setString(2, entity.getUserLogin());
			stat.executeUpdate();
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat);			
		}
		return entity;
	}

	public PersonalResult updateGain(PersonalResult entity) throws ProjectException{
		PreparedStatement stat = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_UPDATE_LAST_GAIN);
			stat.setBigDecimal(1, entity.getLastGain());
			stat.setString(2, entity.getUserLogin());
			stat.executeUpdate();
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat);			
		}
		return entity;
	}
}
