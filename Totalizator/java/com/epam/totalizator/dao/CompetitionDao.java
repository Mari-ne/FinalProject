package com.epam.totalizator.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.epam.totalizator.pool.ConnectionPool;
import com.epam.totalizator.util.MessageManager;
import com.epam.totalizator.exception.ProjectException;
import com.epam.totalizator.entity.Competition;

public class CompetitionDao extends AbstractDao<Integer, Competition> {

	private static final String SQL_SELECT_ALL_COMPETITION = "select id, sport_id, team1_id, team2_id, start, finish, state, result\r\n"
			+ "from competition";
	private static final String SQL_SELECT_COMPETITION_BY_SPORT_NAME = "select c.id, c.sport_id, c.team1_id, c.team2_id, c.start, c.finish, c.state, c.result\r\n" + 
			"from competition as c inner join language_m2m_sport as l on c.sport_id = l.sport_id\r\n" + 
			"where l.sport = ?"; 
	private static final String SQL_SELECT_COMPETITION_BY_SPORT_ID = "select id, sport_id, team1_id, team2_id, start, finish, state, result\r\n"
			+ "from competition where sport_id = ?"; 
	private static final String SQL_SELECT_COMPETITION_BY_ID = "select id, sport_id, team1_id, team2_id, start, finish, state, result\r\n"
			+ "from competition where id = ?";
	private static final String SQL_SELECT_COMPETITION_BY_TEAM_ID = "select id, sport_id, team1_id, team2_id, start, finish, state, result\r\n"
			+ "from competition where team1_id = ? or team2_id = ?";
	private static final String SQL_SELECT_COMPETITON_BY_TEAM_NAME = "select c.id, c.sport_id, c.team1_id, c.team2_id, c.start, c.finish, c.state, c.result\r\n" +
			"from competition as c inner join language_has_sport_team as l1 on c.team1_id = l1.sport_team_id\r\n" +
			" 					  inner join language_has_sport_team as l2 on c.team2_id = l2.sport_team_id\r\n" +
			"where l1.name = ? or l2.name = ?";
	private static final String SQL_SELECT_COMPETITION_BY_COMPETITORS_ID = "select id, sport_id, team1_id, team2_id, start, finish, state, result\r\n"
			+ "from competition\r\n"
			+ "where (team1_id = ? and team2_id = ?) or (team1_id = ? or team2_id = ?)";
	private static final String SQL_SELECT_COMPETITION_BY_COMPETITORS_NAME = "select c.id, c.sport_id, c.team1_id, c.team2_id, c.start, c.finish, c.state, c.result\r\n" +
			"from competition as c inner join language_has_sport_team as l1 on c.team1_id = l1.sport_team_id\r\n" +
			" 					  inner join language_has_sport_team as l2 on c.team2_id = l2.sport_team_id\r\n" +
			"where (l1.name = ? and l2.name = ?) or (l1.name = ? and l2.name = ?)";
	
	private static final String SQL_SELECT_LAST_MOUNTH_WITH_LOCAL_NAME = "select c.id, c.sport_id, l3.sport, c.team1_id, l1.name as team1, c.team2_id, l2.name as team2, c.start, c.finish, c.state, c.result\r\n"
			+ "from competition as c inner join language_has_sport_team as l1 on c.team1_id = l1.sport_team_id\r\n"
			+ " 					inner join language_has_sport_team as l2 on c.team2_id = l2.sport_team_id\r\n"
			+ " 					inner join language_m2m_sport as l3 on l3.sport_id = c.sport_id\r\n"	
			+ "where l1.language_id = ? and l2.language_id = ? and l3.language_id = ? and datediff(start, current_date()) < 30\r\n"
			+ "order by c.start desc limit 15";
	
	private static final String SQL_INSERT_COMPETITION = "insert into competition(sport_id, team1_id, team2_id, start, finish)\r\n"
			+ "values(?, ?, ?, ?, ?)";
	
	private static final String SQL_SELECT_BETTABLE_WITH_NAMES = "select c.id, c.team1_id, c.team2_id, l1.name as team1, l2.name as team2, l3.sport\r\n"
			+ "from competition as c inner join language_has_sport_team as l1 on c.team1_id = l1.sport_team_id\r\n"
			+ " 					inner join language_has_sport_team as l2 on c.team2_id = l2.sport_team_id\r\n"
			+ " 					inner join language_m2m_sport as l3 on l3.sport_id = c.sport_id\r\n"	
			+ "where l1.language_id = ? and l2.language_id = ? and l3.language_id = ? and c.state = 'Acceptence of bets'";
	
	private static final String SQL_SELECT_BETTABLE = "select id, team1_id, team2_id, start, finish\r\n"
			+ "from competition\r\n"	
			+ "where state = 'Acceptence of bets'\r\n"
			+ "order by start";
	
	private static final String SQL_UPDATE_COMPETITION = "update competition\r\n"
			+ "set start = ?, finish = ?, state = ?, result = ?\r\n"
			+ "where id = ?";
	
	private static final String SQL_SELECT_EXPECTED = "select id, team1_id, sport_id, team2_id, start, finish\r\n"
			+ "from competition\r\n"	
			+ "where state = 'Completion of bets' and timestampdiff(second, current_timestamp(), finish) <= 0";
	
	private static final String SQL_REMOVE_COMPETITION = "update competition\r\n"
			+ "set is_active = 0\r\n"
			+ "where id = ?";
	
	private static final String ID = "id";
	private static final String SPORT_ID = "sport_id";
	private static final String TEAM1_ID = "team1_id";
	private static final String TEAM2_ID = "team2_id";
	private static final String SPORT = "sport";
	private static final String TEAM1 = "team1";
	private static final String TEAM2 = "team2";
	private static final String START = "start";
	private static final String FINISH = "finish";
	private static final String STATE = "state";
	private static final String RESULT = "result";
	
	private static final Logger LOGGER = Logger.getRootLogger();
	
	@Override
	public List<Competition> findAll() throws ProjectException{
		List<Competition> competitions = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try (Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = null;
			stat = con.prepareStatement(SQL_SELECT_ALL_COMPETITION);
			result = stat.executeQuery();
			while(result.next()) {
				Competition comp = new Competition();
				comp.setId(result.getInt(ID));
				comp.setSportId(result.getInt(SPORT_ID));
				comp.setTeam1Id(result.getInt(TEAM1_ID));
				comp.setTeam2Id(result.getInt(TEAM2_ID));
				comp.setStart(result.getTimestamp(START));
				comp.setFinish(result.getTimestamp(FINISH));
				comp.setState(result.getString(STATE));
				comp.setResult(result.getString(RESULT));
				competitions.add(comp);
			}
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return competitions;
	}

	public List<Competition> findBySportName(String name) throws ProjectException{
		List<Competition> competitions = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try (Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = null;
			stat = con.prepareStatement(SQL_SELECT_COMPETITION_BY_SPORT_NAME);
			stat.setString(1, name);
			result = stat.executeQuery();
			while(result.next()) {
				Competition comp = new Competition();
				comp.setId(result.getInt(ID));
				comp.setSportId(result.getInt(SPORT_ID));
				comp.setTeam1Id(result.getInt(TEAM1_ID));
				comp.setTeam2Id(result.getInt(TEAM2_ID));
				comp.setStart(result.getTimestamp(START));
				comp.setFinish(result.getTimestamp(FINISH));
				comp.setState(result.getString(STATE));
				comp.setResult(result.getString(RESULT));
				competitions.add(comp);
			}
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return competitions;
	}
	
	public List<Competition> findBySportId(int id) throws ProjectException{
		List<Competition> competitions = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try (Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = null;
			stat = con.prepareStatement(SQL_SELECT_COMPETITION_BY_SPORT_ID);
			stat.setInt(1, id);
			result = stat.executeQuery();
			while(result.next()) {
				Competition comp = new Competition();
				comp.setId(result.getInt(ID));
				comp.setSportId(result.getInt(SPORT_ID));
				comp.setTeam1Id(result.getInt(TEAM1_ID));
				comp.setTeam2Id(result.getInt(TEAM2_ID));
				comp.setStart(result.getTimestamp(START));
				comp.setFinish(result.getTimestamp(FINISH));
				comp.setState(result.getString(STATE));
				comp.setResult(result.getString(RESULT));
				competitions.add(comp);
			}
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return competitions;
	}
	
	public List<Competition> findByTeamId(int id) throws ProjectException{
		List<Competition> competitions = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try (Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = null;
			stat = con.prepareStatement(SQL_SELECT_COMPETITION_BY_TEAM_ID);
			stat.setInt(1, id);
			stat.setInt(2, id);
			result = stat.executeQuery();
			while(result.next()) {
				Competition comp = new Competition();
				comp.setId(result.getInt(ID));
				comp.setSportId(result.getInt(SPORT_ID));
				comp.setTeam1Id(result.getInt(TEAM1_ID));
				comp.setTeam2Id(result.getInt(TEAM2_ID));
				comp.setStart(result.getTimestamp(START));
				comp.setFinish(result.getTimestamp(FINISH));
				comp.setState(result.getString(STATE));
				comp.setResult(result.getString(RESULT));
				competitions.add(comp);
			}
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return competitions;
	}
	
	public List<Competition> findByTeamName(String name) throws ProjectException{
		List<Competition> competitions = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try (Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = null;
			stat = con.prepareStatement(SQL_SELECT_COMPETITON_BY_TEAM_NAME);
			stat.setString(1, name);
			stat.setString(2, name);
			result = stat.executeQuery();
			while(result.next()) {
				Competition comp = new Competition();
				comp.setId(result.getInt(ID));
				comp.setSportId(result.getInt(SPORT_ID));
				comp.setTeam1Id(result.getInt(TEAM1_ID));
				comp.setTeam2Id(result.getInt(TEAM2_ID));
				comp.setStart(result.getTimestamp(START));
				comp.setFinish(result.getTimestamp(FINISH));
				comp.setState(result.getString(STATE));
				comp.setResult(result.getString(RESULT));
				competitions.add(comp);
			}
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return competitions;
	}
	
	public List<Competition> findByCompetitorsId(int id1, int id2) throws ProjectException{
		List<Competition> competitions = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try (Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = null;
			stat = con.prepareStatement(SQL_SELECT_COMPETITION_BY_COMPETITORS_ID);
			stat.setInt(1, id1);
			stat.setInt(2, id2);
			stat.setInt(3, id2);
			stat.setInt(4, id1);
			result = stat.executeQuery();
			while(result.next()) {
				Competition comp = new Competition();
				comp.setId(result.getInt(ID));
				comp.setSportId(result.getInt(SPORT_ID));
				comp.setTeam1Id(result.getInt(TEAM1_ID));
				comp.setTeam2Id(result.getInt(TEAM2_ID));
				comp.setStart(result.getTimestamp(START));
				comp.setFinish(result.getTimestamp(FINISH));
				comp.setState(result.getString(STATE));
				comp.setResult(result.getString(RESULT));
				competitions.add(comp);
			}
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return competitions;
	}
	
	public List<Competition> findByCompetitorsName(String name1, String name2) throws ProjectException{
		List<Competition> competitions = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try (Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = null;
			stat = con.prepareStatement(SQL_SELECT_COMPETITION_BY_COMPETITORS_NAME);
			stat.setString(1, name1);
			stat.setString(2, name2);
			stat.setString(3, name2);
			stat.setString(4, name1);
			result = stat.executeQuery();
			while(result.next()) {
				Competition comp = new Competition();
				comp.setId(result.getInt(ID));
				comp.setSportId(result.getInt(SPORT_ID));
				comp.setTeam1Id(result.getInt(TEAM1_ID));
				comp.setTeam2Id(result.getInt(TEAM2_ID));
				comp.setStart(result.getTimestamp(START));
				comp.setFinish(result.getTimestamp(FINISH));
				comp.setState(result.getString(STATE));
				comp.setResult(result.getString(RESULT));
				competitions.add(comp);
			}
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return competitions;
	}
	
	@Override
	public Optional<Competition> findById(Integer id) throws ProjectException{
		Competition comp = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		try (Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = null;
			stat = con.prepareStatement(SQL_SELECT_COMPETITION_BY_ID);
			stat.setInt(1, id.intValue());
			result = stat.executeQuery();
			while(result.next()) {
				comp = new Competition();
				comp.setId(result.getInt(ID));
				comp.setSportId(result.getInt(SPORT_ID));
				comp.setTeam1Id(result.getInt(TEAM1_ID));
				comp.setTeam2Id(result.getInt(TEAM2_ID));
				comp.setStart(result.getTimestamp(START));
				comp.setFinish(result.getTimestamp(FINISH));
				comp.setState(result.getString(STATE));
				comp.setResult(result.getString(RESULT));
			}
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return Optional.ofNullable(comp);
	}

	public List<Competition> findForLastMounth(String lang) throws ProjectException{
		List<Competition> competitions = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try (Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_LAST_MOUNTH_WITH_LOCAL_NAME);
			stat.setString(1, lang);
			stat.setString(2, lang);
			stat.setString(3, lang);
			result = stat.executeQuery();
			while(result.next()) {
				Competition comp = new Competition();
				comp.setId(result.getInt(ID));
				comp.setSportId(result.getInt(SPORT_ID));
				comp.setSport(result.getString(SPORT));
				comp.setTeam1Id(result.getInt(TEAM1_ID));
				comp.setTeam1(result.getString(TEAM1));
				comp.setTeam2Id(result.getInt(TEAM2_ID));
				comp.setTeam2(result.getString(TEAM2));
				comp.setStart(result.getTimestamp(START));
				comp.setFinish(result.getTimestamp(FINISH));
				comp.setState(result.getString(STATE));
				switch(comp.getState()) {
					case "Acceptance of bets":{
						comp.setState(MessageManager.getMessage("competition.state.accept"));
						break;
					}
					case "Completion of bets":{
						comp.setState(MessageManager.getMessage("competition.state.wait"));
						break;
					}
					case "Completed":{
						comp.setState(MessageManager.getMessage("competition.state.completed"));
						break;
					}
				}
				comp.setResult(result.getString(RESULT));				
				competitions.add(comp);
			}
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return competitions;
	}
	
	public List<Competition> findBettableWithName(String lang) throws ProjectException{
		List<Competition> competitions = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try (Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = null;
			stat = con.prepareStatement(SQL_SELECT_BETTABLE_WITH_NAMES);
			stat.setString(1, lang);
			stat.setString(2, lang);
			stat.setString(3, lang);
			result = stat.executeQuery();
			while(result.next()) {
				Competition comp = new Competition();
				comp.setId(result.getInt(ID));
				comp.setSport(result.getString(SPORT));
				comp.setTeam1(result.getString(TEAM1));
				comp.setTeam2(result.getString(TEAM2));
				comp.setTeam1Id(result.getInt(TEAM1_ID));
				comp.setTeam2Id(result.getInt(TEAM2_ID));
				competitions.add(comp);
			}
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return competitions;
	}
	
	public List<Competition> findBettable() throws ProjectException{
		List<Competition> competitions = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try (Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = null;
			stat = con.prepareStatement(SQL_SELECT_BETTABLE);
			result = stat.executeQuery();
			while(result.next()) {
				Competition comp = new Competition();
				comp.setId(result.getInt(ID));
				comp.setTeam1Id(result.getInt(TEAM1_ID));
				comp.setTeam2Id(result.getInt(TEAM2_ID));
				comp.setStart(result.getTimestamp(START));
				comp.setFinish(result.getTimestamp(FINISH));
				competitions.add(comp);
			}
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return competitions;
	}
	
	public List<Competition> findExpected() throws ProjectException{
		List<Competition> competitions = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try (Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = null;
			stat = con.prepareStatement(SQL_SELECT_EXPECTED);
			result = stat.executeQuery();
			while(result.next()) {
				Competition comp = new Competition();
				comp.setId(result.getInt(ID));
				comp.setTeam1Id(result.getInt(TEAM1_ID));
				comp.setTeam2Id(result.getInt(TEAM2_ID));
				comp.setSportId(result.getInt(SPORT_ID));
				comp.setStart(result.getTimestamp(START));
				comp.setFinish(result.getTimestamp(FINISH));
				competitions.add(comp);
			}
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return competitions;
	}
	
	@Override
	public boolean delete(Integer id) throws ProjectException{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(Competition entity) throws ProjectException{
		PreparedStatement stat = null;
		boolean result = false;
		try (Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_REMOVE_COMPETITION);
			stat.setInt(1, entity.getId());
			if(stat.executeUpdate() == 1) {
				result = true;
			}
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat);			
		}
		return result;
	}

	@Override
	public boolean create(Competition entity) throws ProjectException{
		PreparedStatement stat = null;
		ResultSet res = null;
		boolean result = false;
		try (Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_INSERT_COMPETITION);
			stat.setInt(1, entity.getSportId());
			stat.setInt(2, entity.getTeam1Id());
			stat.setInt(3, entity.getTeam2Id());
			stat.setTimestamp(4, entity.getStart());
			stat.setTimestamp(5, entity.getFinish());
			if(stat.executeUpdate() == 1) {
				result = true;
			}
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, res);			
		}
		return result;
	}

	@Override
	public Competition update(Competition entity) throws ProjectException{
		PreparedStatement stat = null;
		boolean result = false;
		try (Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_UPDATE_COMPETITION);
			stat.setTimestamp(1, entity.getStart());
			stat.setTimestamp(2, entity.getFinish());
			stat.setString(3, entity.getState());
			stat.setString(4, entity.getResult());
			stat.setInt(5, entity.getId());
			stat.executeUpdate();
		}catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat);			
		}
		return entity;
	}

}
