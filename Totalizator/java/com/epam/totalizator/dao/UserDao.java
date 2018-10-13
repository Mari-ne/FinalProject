package com.epam.totalizator.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.epam.totalizator.entity.User;
import com.epam.totalizator.pool.ConnectionPool;
import com.epam.totalizator.exception.ProjectException;

/**
 * Dao class to work with 'user' table from database.
 *
 */
public class UserDao extends AbstractDao<String, User> {

	private static final String SQL_SELECT_ALL_USER = "select login, password, `e-mail`, role\r\n"
			+ "from user";
	private static final String SQL_SELECT_BY_LOGIN = "select login, password, `e-mail`, role\r\n"
			+ "from user where login = ?";
	private static final String SQL_SELECT_BY_ROLE = "select login, password, `e-mail`, role\r\n"
			+ "from user where role = ?";
	
	private static final String SQL_SELECT_BY_LOGIN_PASS = "select login, password, `e-mail`, role\r\n"
			+ "from user where login = ? and password = ?";
	
	private static final String SQL_INSERT_NEW_USER = "insert into user(login, password, `e-mail`, role)\r\n"
			+ "values(?, ?, ?, ?)";
	
	private static final String SQL_SELECT_CARDS_BY_LOGIN = "select card_number as card\r\n"
			+ "from card_info\r\n"
			+ "where user_login = ? and is_used = 1";
	
	private static final String SQL_UPDATE_USER = "update user \r\n"
			+ "set password = ?, `e-mail` = ?\r\n"
			+ "where login = ?";
	
	private static final String SQL_INSERT_CARD = "insert into card_info(user_login, card_number)\r\n"
			+ "values(?, ?)";
	
	private static final String SQL_REMOVE_CARDS = "update card_info\r\n"
			+ "set is_used = 0\r\n"
			+ "where user_login = ? and card_number = ?";
	
	private static final String SQL_REVOKE_CARD = "update card_info\r\n"
			+ "set is_used = 1\r\n"
			+ "where user_login = ? and card_number = ?";
	
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String E_MAIL = "e-mail";
	private static final String ROLE = "role";
	
	private static final Logger LOGGER = Logger.getRootLogger();
	
	@Override
	public List<User> findAll()  throws ProjectException{
		List<User> users = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_ALL_USER);
			result = stat.executeQuery();
			while(result.next()) {
				User user = new User();
				user.setLogin(result.getString(LOGIN));
				user.setPassword(result.getString(PASSWORD));
				user.setEmail(result.getString(E_MAIL));
				user.setRole(result.getString(ROLE));
				users.add(user);
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return users;
	}

	@Override
	public Optional<User> findById(String id) throws ProjectException{
		User user = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_BY_LOGIN);
			stat.setString(1, id);
			result = stat.executeQuery();
			while(result.next()) {
				user = new User();
				user.setLogin(result.getString(LOGIN));
				user.setPassword(result.getString(PASSWORD));
				user.setEmail(result.getString(E_MAIL));
				user.setRole(result.getString(ROLE));
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return Optional.ofNullable(user);
	}
	
	public List<User> findByRole(String role) throws ProjectException{
		List<User> users = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_BY_ROLE);
			stat.setString(1, role);
			result = stat.executeQuery();
			while(result.next()) {
				User user = new User();
				user.setLogin(result.getString(LOGIN));
				user.setPassword(result.getString(PASSWORD));
				user.setEmail(result.getString(E_MAIL));
				user.setRole(result.getString(ROLE));
				users.add(user);
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return users;
	}

	public Optional<User> findByLoginPass(String login, String pass) throws ProjectException{
		User user = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_BY_LOGIN_PASS);
			stat.setString(1, login);
			stat.setString(2, pass);
			result = stat.executeQuery();
			while(result.next()) {
				user = new User();
				user.setLogin(result.getString(LOGIN));
				user.setPassword(result.getString(PASSWORD));
				user.setEmail(result.getString(E_MAIL));
				user.setRole(result.getString(ROLE));
				if(user.getRole().equals("User")) {
					user.setCards(findCards(login));
				}
			}
			
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return Optional.ofNullable(user);
	}
	
	public List<String> findCards(String login) throws ProjectException{
		List<String> cards = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_SELECT_CARDS_BY_LOGIN);
			stat.setString(1, login);
			result = stat.executeQuery();
			while(result.next()) {
				cards.add(result.getString("card"));
			}
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat, result);			
		}
		return cards;
	}
	
	@Override
	public boolean delete(String id) throws ProjectException{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(User entity) throws ProjectException{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean create(User entity) throws ProjectException{
		PreparedStatement stat = null;
		boolean result = false;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_INSERT_NEW_USER);
			stat.setString(1, entity.getLogin());
			stat.setString(2, entity.getPassword());
			stat.setString(3, entity.getEmail());
			stat.setString(4, entity.getRole());
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

	public void createCards(String card, String login) throws ProjectException{
		PreparedStatement stat = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_REVOKE_CARD);
			stat.setString(1, login);
			stat.setString(2, card);
			if(stat.executeUpdate() == 0) {
				//this card was't created
				closeStatement(stat);
				stat = con.prepareStatement(SQL_INSERT_CARD);
				stat.setString(1, login);
				stat.setString(2, card);
				stat.executeUpdate();
			}			
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat);			
		}
	}
	
	@Override
	public User update(User entity) throws ProjectException{
		PreparedStatement stat = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_UPDATE_USER);
			stat.setString(1, entity.getPassword());
			stat.setString(2, entity.getEmail());
			stat.setString(3, entity.getLogin());
			stat.executeUpdate();
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat);
		}
		return entity;
	}

	public void deleteCards(String card, String login) throws ProjectException{
		PreparedStatement stat = null;
		try(Connection con = ConnectionPool.getInstance().takeConnection()){
			stat = con.prepareStatement(SQL_REMOVE_CARDS);
			stat.setString(1, login);
			stat.setString(2, card);
			stat.executeUpdate();	
		} catch(SQLException e) {
			throw new ProjectException(e);
		} finally {
			closeStatement(stat);			
		}
	}
	
}
