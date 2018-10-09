package com.epam.totalizator.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.epam.totalizator.pool.ConnectionPool;
import com.epam.totalizator.util.Key;
import com.epam.totalizator.util.ProjectException;
import com.epam.totalizator.entity.Forecast;

public class ForecastDao extends AbstractDao<Key<String, Integer>, Forecast> {

	private static final String SQL_SELECT_ALL = "select user_login, competition_id, result\r\n"
			+ "from competition_m2m_user";
	private static final String SQL_SELECT_BY_ID = "select user_login, competition_id, result\r\n"
			+ "from competition_m2m_user where user_login = ? and competition_id = ?";
	private static final String SQL_SELECT_BY_LOGIN = "select user_login, competition_id, result\r\n"
			+ "from competition_m2m_user where user_login = ?";
	private static final String SQL_SELECT_BY_COMPETITION_ID = "select user_login, competition_id, result\r\n"
			+ "from competition_m2m_user where competition_id = ?";
	
	private static final String SQL_SELECT_BY_LOGIN_WITH_NAMES = "select cu.competition_id, \r\n"
			+ "case cu.result when '1' then l1.name when '2' then l2.name else cu.result end as result\r\n"
			+ "from competition_m2m_user as cu inner join competition as c on cu.competition_id = c.id\r\n"
			+ "									inner join language_has_sport_team as l1 on c.team1_id = l1.sport_team_id\r\n"
			+ "									inner join language_has_sport_team as l2 on c.team2_id = l2.sport_team_id\r\n"
			+ "where l1.language_id = ? and l2.language_id = ? and cu.user_login = ?";
	
	private static final String SQL_INSER_FORECAST = "insert into competition_m2m_user (login, competition_id, result)\r\n"
			+ "values (?, ?, ?)";
	
	private static final String SQL_SELECT_ACTUAl_FORECAST = "select user_login, competition_id, result\r\n" + 
			"from competition_m2m_user\r\n" + 
			"where competition_id in (select id\r\n" + 
			"							from competition\r\n" + 
			"                            where state = 'Completion of bets')";
	
	private static final String LOGIN = "user_login";
	private static final String COMPETITION_ID = "competition_id";
	private static final String RESULT = "result";
	
	private static final Logger LOGGER = Logger.getRootLogger();
	
	@Override
	public List<Forecast> findAll() throws ProjectException{
		List<Forecast> forecasts = new ArrayList<>();
		Statement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.createStatement();
			result = stat.executeQuery(SQL_SELECT_ALL);
			while(result.next()) {
				Forecast forecast = new Forecast();
				forecast.setUserLogin(result.getString(LOGIN));
				forecast.setCompetitionId(result.getInt(COMPETITION_ID));
				forecast.setResult(result.getString(RESULT));
				forecasts.add(forecast);
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return forecasts;
	}

	@Override
	public Optional<Forecast> findById(Key<String, Integer> id) throws ProjectException{
		Forecast forecast = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_BY_ID);
			stat.setString(1, id.getKey1());
			stat.setInt(2, id.getKey2());
			result = stat.executeQuery();
			while(result.next()) {
				forecast = new Forecast();
				forecast.setUserLogin(result.getString(LOGIN));
				forecast.setCompetitionId(result.getInt(COMPETITION_ID));
				forecast.setResult(result.getString(RESULT));
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return Optional.ofNullable(forecast);
	}

	public List<Forecast> findByLogin(String login) throws ProjectException{
		List<Forecast> forecasts = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_BY_LOGIN);
			stat.setString(1, login);
			result = stat.executeQuery();
			while(result.next()) {
				Forecast forecast = new Forecast();
				forecast.setUserLogin(result.getString(LOGIN));
				forecast.setCompetitionId(result.getInt(COMPETITION_ID));
				forecast.setResult(result.getString(RESULT));
				forecasts.add(forecast);
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return forecasts;
	}
	
	public List<Forecast> findByLoginWithNames(String login, String lang) throws ProjectException{
		List<Forecast> forecasts = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_BY_LOGIN_WITH_NAMES);
			stat.setString(1, lang);
			stat.setString(2, lang);
			stat.setString(3, login);
			result = stat.executeQuery();
			while(result.next()) {
				Forecast forecast = new Forecast();
				forecast.setCompetitionId(result.getInt(COMPETITION_ID));
				if(result.getString(RESULT).charAt(0) == 'x') {
					switch(lang) {
					case "EN":
						forecast.setResultFull("Draw");
						break;
					case "JP":
						forecast.setResultFull("引き分け");
						break;
					case "RU":
						forecast.setResultFull("Ничья");
						break;
					}
				}else {
					forecast.setResultFull(result.getString(RESULT));
				}
				forecasts.add(forecast);
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return forecasts;
	}
	
	public List<Forecast> findByCompetitionID(int id) throws ProjectException{
		List<Forecast> forecasts = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_BY_COMPETITION_ID);
			stat.setInt(1, id);
			result = stat.executeQuery();
			while(result.next()) {
				Forecast forecast = new Forecast();
				forecast.setUserLogin(result.getString(LOGIN));
				forecast.setCompetitionId(result.getInt(COMPETITION_ID));
				forecast.setResult(result.getString(RESULT));
				forecasts.add(forecast);
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return forecasts;
	}
	
	public List<Forecast> findActualForecast() throws ProjectException{
		List<Forecast> forecasts = new ArrayList<>();
		Statement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.createStatement();
			result = stat.executeQuery(SQL_SELECT_ACTUAl_FORECAST);
			while(result.next()) {
				Forecast forecast = new Forecast();
				forecast.setUserLogin(result.getString(LOGIN));
				forecast.setCompetitionId(result.getInt(COMPETITION_ID));
				forecast.setResult(result.getString(RESULT));
				forecasts.add(forecast);
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return forecasts;
	}
	
	@Override
	public boolean delete(Key<String, Integer> id) throws ProjectException{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(Forecast entity) throws ProjectException{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean create(Forecast entity) throws ProjectException{
		PreparedStatement stat = null;
		boolean result = false;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_INSER_FORECAST);
			stat.setString(1, entity.getUserLogin());
			stat.setInt(2, entity.getCompetitionId());
			stat.setString(3, entity.getResult());
			if(stat.executeLargeUpdate() == 1) {
				result = true;
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat);			
		}
		return result;
	}

	@Override
	public Forecast update(Forecast entity) throws ProjectException{
		throw new UnsupportedOperationException();
	}

}
