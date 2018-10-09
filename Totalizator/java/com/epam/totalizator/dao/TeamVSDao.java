package com.epam.totalizator.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.epam.totalizator.entity.TeamVs;
import com.epam.totalizator.pool.ConnectionPool;
import com.epam.totalizator.util.Key;
import com.epam.totalizator.util.ProjectException;

public class TeamVSDao extends AbstractDao<Key<Integer, Integer>, TeamVs> {

	private static final String SQL_SELECT_ALL_TEAM_VS = "select team1_id, team2_id, team1_wins, team2_wins, quantity\r\n"
			+ "from team_vs";
	private static final String SQL_SELECT_VS_BY_IDS = "select  team1_id, team2_id, team1_wins, team2_wins, quantity\r\n"
			+ "from team_vs\r\n" 
			+ "where (team1_id = ? and team2_id = ?) or (team1_id = ? and team2_id = ?)";
	private static final String SQL_SELECT_VS_FOR_ONE = "select * from team_vs where team1_id = ? or team2_id = ?";
	private static final String SQL_SELECT_VS_BY_TEAM_NAME = "select vs.team1_id, vs.team2_id, vs.team1_wins, vs.team2_wins, vs.quantity\r\n" +
				"from team_vs as vs inner join language_has_sport_team as l1 on vs.team1_id = l1.sport_team_id\r\n" + 
				" 					inner join language_has_sport_team as l2 on vs.team2_id = l2.sport_team_id\r\n" +
				"where l1.name = ? or l2.name = ?";
	private static final String SQL_SELECT_VS_BY_TEAMS_NAME = "select vs.team1_id, vs.team2_id, vs.team1_wins, vs.team2_wins, vs.quantity\r\n" +
			"from team_vs as vs inner join language_has_sport_team as l1 on vs.team1_id = l1.sport_team_id\r\n" + 
			" 					inner join language_has_sport_team as l2 on vs.team2_id = l2.sport_team_id\r\n" +
			"where (l1.name = ? and l2.name = ?) or (l1.name = ? and l2.name = ?)";
	private static final String SQL_SELECT_VS_BY_SPORT_ID = "select vs.team1_id, vs.team2_id, vs.team1_wins, vs.team2_wins, vs.quantity\r\n" +
			"from team_vs as vs inner join sport_team as t on vs.team1_id = t.id\r\n" +
			"where t.sport_id = ?";
	private static final String SQL_SELECT_VS_BY_SPORT_NAME = "select vs.team1_id, vs.team2_id, vs.team1_wins, vs.team2_wins, vs.quantity\r\n" +
			"from team_vs as vs inner join sport_team as t on vs.team1_id = t.id\r\n" +
			" 					inner join language_m2m_sport as l on t.sport_id = l.sport_id\r\n" +
			"where l.sport = ?";
	
	private static final String SQL_SELECT_ALL_WITH_LOCAL_NAME = "select vs.team1_id, vs.team2_id, l1.name as team1, l2.name as team2, vs.team1_wins, vs.team2_wins, vs.quantity\r\n"
			+ "from team_vs as vs inner join language_has_sport_team as l1 on vs.team1_id = l1.sport_team_id\r\n"
			+ " 					inner join language_has_sport_team as l2 on vs.team2_id = l2.sport_team_id\r\n"
			+ "where l1.language_id = ? and l2.language_id = ?";
	
	private static final String SQL_INSERT_VS = "insert into team_vs(team1_id, team2_id)\r\n"
			+ "values (?,?)";
	
	private static final String TEAM1_ID = "team1_id";
	private static final String TEAM2_ID = "team2_id";
	private static final String TEAM1 = "team1";
	private static final String TEAM2 = "team2";
	private static final String TEAM1_WINS = "team1_wins";
	private static final String TEAM2_WINS = "team2_wins";
	private static final String QUANTITY = "quantity";
	
	private static final Logger LOGGER = Logger.getRootLogger();
	
	@Override
	public List<TeamVs> findAll() throws ProjectException{
		List<TeamVs> matches = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_ALL_TEAM_VS);
			result = stat.executeQuery();
			while(result.next()) {
				TeamVs match = new TeamVs();
				match.setTeam1Id(result.getInt(TEAM1_ID));
				match.setTeam2Id(result.getInt(TEAM2_ID));
				match.setTeam1Wins(result.getInt(TEAM1_WINS));
				match.setTeam2Wins(result.getInt(TEAM2_WINS));
				match.setQuantity(result.getInt(QUANTITY));
				matches.add(match);
			}
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return matches;
	}
	
	public List<TeamVs> findByOneID(Integer id) throws ProjectException{
		List<TeamVs> matches = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_VS_FOR_ONE);
			stat.setInt(1, id);
			stat.setInt(2, id);
			result = stat.executeQuery();
			while(result.next()) {
				TeamVs match = new TeamVs();
				match.setTeam1Id(result.getInt(TEAM1_ID));
				match.setTeam2Id(result.getInt(TEAM2_ID));
				match.setTeam1Wins(result.getInt(TEAM1_WINS));
				match.setTeam2Wins(result.getInt(TEAM2_WINS));
				match.setQuantity(result.getInt(QUANTITY));
				matches.add(match);
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		}  finally {
			closeStatement(stat, result);			
		}
		return matches;
	}
	
	public List<TeamVs> findByTeamName(String name) throws ProjectException{
		List<TeamVs> matches = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_VS_BY_TEAM_NAME);
			stat.setString(1, name);
			stat.setString(2, name);
			result = stat.executeQuery();
			while(result.next()) {
				TeamVs match = new TeamVs();
				match.setTeam1Id(result.getInt(TEAM1_ID));
				match.setTeam2Id(result.getInt(TEAM2_ID));
				match.setTeam1Wins(result.getInt(TEAM1_WINS));
				match.setTeam2Wins(result.getInt(TEAM2_WINS));
				match.setQuantity(result.getInt(QUANTITY));
				matches.add(match);
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return matches;
	}
	
	public Optional<TeamVs> findByTeamsName(String name1, String name2) throws ProjectException{
		TeamVs match = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_VS_BY_TEAMS_NAME);
			stat.setString(1, name1);
			stat.setString(2, name2);
			stat.setString(3, name2);
			stat.setString(4, name1);
			result = stat.executeQuery();
			while(result.next()) {
				match = new TeamVs();
				match.setTeam1Id(result.getInt(TEAM1_ID));
				match.setTeam2Id(result.getInt(TEAM2_ID));
				match.setTeam1Wins(result.getInt(TEAM1_WINS));
				match.setTeam2Wins(result.getInt(TEAM2_WINS));
				match.setQuantity(result.getInt(QUANTITY));
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return Optional.ofNullable(match);
	}
	
	public List<TeamVs> findBySportId(int id) throws ProjectException{
		List<TeamVs> matches = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_VS_BY_SPORT_ID);
			stat.setInt(1, id);
			result = stat.executeQuery();
			while(result.next()) {
				TeamVs match = new TeamVs();
				match.setTeam1Id(result.getInt(TEAM1_ID));
				match.setTeam2Id(result.getInt(TEAM2_ID));
				match.setTeam1Wins(result.getInt(TEAM1_WINS));
				match.setTeam2Wins(result.getInt(TEAM2_WINS));
				match.setQuantity(result.getInt(QUANTITY));
				matches.add(match);
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return matches;
	}
	
	public List<TeamVs> findBySportName(String name) throws ProjectException{
		List<TeamVs> matches = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_VS_BY_SPORT_NAME);
			stat.setString(1, name);
			result = stat.executeQuery();
			while(result.next()) {
				TeamVs match = new TeamVs();
				match.setTeam1Id(result.getInt(TEAM1_ID));
				match.setTeam2Id(result.getInt(TEAM2_ID));
				match.setTeam1Wins(result.getInt(TEAM1_WINS));
				match.setTeam2Wins(result.getInt(TEAM2_WINS));
				match.setQuantity(result.getInt(QUANTITY));
				matches.add(match);
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return matches;
	}
	
	@Override
	public Optional<TeamVs> findById(Key<Integer, Integer> id) throws ProjectException{
		TeamVs match = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_VS_BY_IDS);
			stat.setInt(1, id.getKey1());
			stat.setInt(2, id.getKey2());
			stat.setInt(3, id.getKey2());
			stat.setInt(4, id.getKey1());
			result = stat.executeQuery();
			while(result.next()) {
				match = new TeamVs();
				match.setTeam1Id(result.getInt(TEAM1_ID));
				match.setTeam2Id(result.getInt(TEAM2_ID));
				match.setTeam1Wins(result.getInt(TEAM1_WINS));
				match.setTeam2Wins(result.getInt(TEAM2_WINS));
				match.setQuantity(result.getInt(QUANTITY));
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return Optional.ofNullable(match);
	}
	
	public List<TeamVs> findAllWithNames(String lang) throws ProjectException{
		List<TeamVs> matches = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_ALL_WITH_LOCAL_NAME);
			stat.setString(1, lang);
			stat.setString(2, lang);
			result = stat.executeQuery();
			while(result.next()) {
				TeamVs match = new TeamVs();
				match.setTeam1Id(result.getInt(TEAM1_ID));
				match.setTeam1(result.getString(TEAM1));
				match.setTeam2Id(result.getInt(TEAM2_ID));
				match.setTeam2(result.getString(TEAM2));
				match.setTeam1Wins(result.getInt(TEAM1_WINS));
				match.setTeam2Wins(result.getInt(TEAM2_WINS));
				match.setQuantity(result.getInt(QUANTITY));
				matches.add(match);
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return matches;
	}
	
	@Override
	public boolean delete(Key<Integer, Integer> id) throws ProjectException{
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean delete(TeamVs entity) throws ProjectException{
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean create(TeamVs entity) throws ProjectException{
		PreparedStatement stat = null;
		boolean result = false;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_INSERT_VS);
			stat.setInt(1, entity.getTeam1Id());
			stat.setInt(2, entity.getTeam2Id());
			if(stat.executeUpdate() == 1) {
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
	public TeamVs update(TeamVs entity) throws ProjectException{
		throw new UnsupportedOperationException();
	}
	
	

}
