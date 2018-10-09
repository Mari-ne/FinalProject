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

import com.epam.totalizator.entity.Sport;
import com.epam.totalizator.entity.SportTeam;
import com.epam.totalizator.pool.ConnectionPool;
import com.epam.totalizator.util.ProjectException;

public class SportTeamDao extends AbstractDao<Integer, SportTeam> {

	private static final String SQL_SELECT_ALL_TEAM = "select id, sport_id, wins, loses\r\n"
			+ "from sport_team";
	private static final String SQL_SELECT_TEAM_BY_ID = "select id, sport_id, wins, loses\r\n"
			+ "from sport_team where id = ?";
	private static final String SQL_SELECT_TEAM_BY_SPORT_ID = "select id, sport_id, wins, loses\r\n"
			+ "from sport_team where sport_id = ?";
	
	private static final String SQL_SELECT_TEAM_BY_SPORT_NAME = "select t.id, t.sport_id, t.wins, t.loses\r\n" + 
			"from sport_team as t inner join language_m2m_sport as l on c.sport_id = l.sport_id\r\n" + 
			"where l.sport = ?";
	private static final String SQL_SELECT_TEAM_BY_NAME = "select t.id, t.sport_id, t.wins, t.loses\r\n" +
			"from sport_team as t inner join language_has_sport_team as l on t.id = l.sport_team_id\r\n" +
			"where l.name = ?";
	
	private static final String SQL_SELECT_ALL_WITH_LOCAL_NAME = "select t.id, t.sport_id, l2.sport, l1.name, t.wins, t.loses\r\n"
			+ "from sport_team as t inner join language_has_sport_team as l1 on t.id = l1.sport_team_id\r\n"
			+ " 					inner join language_m2m_sport as l2 on l2.sport_id = t.sport_id\r\n"	
			+ "where l1.language_id = ? and l2.language_id = ?";
	
	private static final String SQL_SELECT_SPORT = "select sport_id, sport\r\n"
			+ "from language_m2m_sport\r\n"
			+ "where language_id = ?";
	
	private static final String SQL_INSERT_TEAM = "insert into sport_team(sport_id)\r\n"
			+ "values(?)";
	private static final String SQL_INSERT_LOCAL_NAME = "insert into language_has_sport_team(language_id, sport_team_id, name)\r\n"
			+ "values ('EN', ?, ?), ('JP', ?, ?), ('RU', ?, ?)";
	
	private static final String ID = "id";
	private static final String SPORT_ID = "sport_id";
	private static final String SPORT = "sport";
	private static final String NAME = "name";
	private static final String WINS = "wins";
	private static final String LOSES = "loses";
	
	private static final Logger LOGGER = Logger.getRootLogger();
	
	@Override
	public List<SportTeam> findAll() throws ProjectException{
		List<SportTeam> teams = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_ALL_TEAM);
			result = stat.executeQuery();
			while(result.next()) {
				SportTeam team = new SportTeam();
				team.setId(result.getInt(ID));
				team.setSportId(result.getInt(SPORT_ID));
				team.setWins(result.getInt(WINS));
				team.setLoses(result.getInt(LOSES));
				teams.add(team);
			}
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return teams;
	}

	@Override
	public Optional<SportTeam> findById(Integer id) throws ProjectException{
		SportTeam team = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_TEAM_BY_ID);
			stat.setInt(1, id.intValue());
			result = stat.executeQuery();
			while(result.next()) {
				team = new SportTeam();
				team.setId(result.getInt(ID));
				team.setSportId(result.getInt(SPORT_ID));
				team.setWins(result.getInt(WINS));
				team.setLoses(result.getInt(LOSES));
			}
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return Optional.ofNullable(team);
	}

	public List<SportTeam> findBySportId(int sport_id) throws ProjectException{
		List<SportTeam> teams = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_TEAM_BY_SPORT_ID);
			stat.setInt(1, sport_id);
			result = stat.executeQuery();
			while(result.next()) {
				SportTeam team = new SportTeam();
				team.setId(result.getInt(ID));
				team.setSportId(result.getInt(SPORT_ID));
				team.setWins(result.getInt(WINS));
				team.setLoses(result.getInt(LOSES));
				teams.add(team);
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return teams;
	}
	
	public List<SportTeam> findBySportName(String name) throws ProjectException{
		List<SportTeam> teams = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_TEAM_BY_SPORT_NAME);
			stat.setString(1, name);
			result = stat.executeQuery();
			while(result.next()) {
				SportTeam team = new SportTeam();
				team.setId(result.getInt(ID));
				team.setSportId(result.getInt(SPORT_ID));
				team.setWins(result.getInt(WINS));
				team.setLoses(result.getInt(LOSES));
				teams.add(team);
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return teams;
	}
	
	public List<SportTeam> findAllWithName(String lang) throws ProjectException{
		List<SportTeam> teams = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_ALL_WITH_LOCAL_NAME);
			stat.setString(1, lang);
			stat.setString(2, lang);
			result = stat.executeQuery();
			while(result.next()) {
				SportTeam team = new SportTeam();
				team.setId(result.getInt(ID));
				team.setSportId(result.getInt(SPORT_ID));
				team.setSport(result.getString(SPORT));
				team.setName(result.getString(NAME));
				team.setWins(result.getInt(WINS));
				team.setLoses(result.getInt(LOSES));
				teams.add(team);
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return teams;
	}
	
	public List<SportTeam> findByTeamName(String name) throws ProjectException{
		List<SportTeam> teams = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_TEAM_BY_NAME);
			stat.setString(1, name);
			result = stat.executeQuery();
			while(result.next()) {
				SportTeam team = new SportTeam();
				team.setId(result.getInt(ID));
				team.setSportId(result.getInt(SPORT_ID));
				team.setWins(result.getInt(WINS));
				team.setLoses(result.getInt(LOSES));
				teams.add(team);
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return teams;
	}
	
	public List<Sport> findSport(String lang) throws ProjectException{
		List<Sport> sports = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_SPORT);
			stat.setString(1, lang);
			result = stat.executeQuery();
			while(result.next()) {
				Sport sport = new Sport();
				sport.setId(result.getInt(SPORT_ID));
				sport.setName(result.getString(SPORT));
				sports.add(sport);
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return sports;
	}
	
	@Override
	public boolean delete(Integer id) throws ProjectException{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(SportTeam entity) throws ProjectException{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean create(SportTeam entity) throws ProjectException{
		PreparedStatement stat = null;
		ResultSet res = null;
		boolean result = false;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_INSERT_TEAM, Statement.RETURN_GENERATED_KEYS);
			stat.setInt(1, entity.getSportId());
			if(stat.executeUpdate() == 1) {
				result = true;
			}
			res = stat.getGeneratedKeys();
			while(res.next()) {
				entity.setId(res.getInt(1));
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, res);			
		}
		return result;
	}

	public boolean createLocalNames(Integer id, String[] names) throws ProjectException{
		PreparedStatement stat = null;
		boolean result = false;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_INSERT_LOCAL_NAME);
			stat.setInt(1, id);
			stat.setString(2, names[0]);
			stat.setInt(3, id);
			stat.setString(4, names[1]);
			stat.setInt(5, id);
			stat.setString(6, names[2]);
			if(stat.executeUpdate() == 3) {
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
	public SportTeam update(SportTeam entity) throws ProjectException{
		throw new UnsupportedOperationException();
	}

}
