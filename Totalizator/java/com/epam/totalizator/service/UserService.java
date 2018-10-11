package com.epam.totalizator.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epam.totalizator.dao.ForecastDao;
import com.epam.totalizator.dao.PersonalResultDao;
import com.epam.totalizator.dao.UserDao;
import com.epam.totalizator.entity.Forecast;
import com.epam.totalizator.entity.PersonalResult;
import com.epam.totalizator.entity.User;
import com.epam.totalizator.exception.ProjectException;
import com.epam.totalizator.util.Validator;

public class UserService {

	public static enum Error{
		USED_LOGIN("error.login.used"),
		INCORRECT_PASSWORD("error.password.invalid"),
		INCORRECT_LOGIN("error.login.invalid"),
		INCORRECT_EMAIL("error.email.invalid"),
		INCORRECT_CARD("error.card.invalid"),
		OTHER_PASSWORD("error.password.other"),
		UNEXPECTED(""),
		NONE("");
		
		private String value;
		
		private Error(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	private static UserDao userDao = new UserDao();
	private static PersonalResultDao perResDao = new PersonalResultDao();
	private static ForecastDao forecastDao = new ForecastDao();
	
	public static Optional<User> isAutorized(String login, String password) throws ProjectException {
		return userDao.findByLoginPass(login, password);
	}
	
	public static Error register(String login, String password, String email, String role)
			throws ProjectException {
		if(!Validator.isAcceptableLogin(login)) {
			return Error.INCORRECT_LOGIN;
		}
		if(!Validator.isAcceptablePassword(password)) {
			return Error.INCORRECT_PASSWORD;
		}
		if(!Validator.isAcceptableEmail(email)) {
			return Error.INCORRECT_EMAIL;
		}
		
		Error result = Error.UNEXPECTED;
		if(userDao.findById(login).isPresent()) {
			result = Error.USED_LOGIN;
		} else {
			password = Validator.passwordHasher(password);
			User user = new User(login, password, email, role, null);
			if(userDao.create(user)) {
				result = Error.NONE;
			}
		}		
		return result;
	}

	public static Optional<PersonalResult> getPersonalResult(String login) throws ProjectException{
		return perResDao.findById(login);
	}
	
	public static List<Forecast> getForecasts(String login, String lang) throws ProjectException{
		return forecastDao.findByLoginWithNames(login, lang.toUpperCase());
	}
	
	public static Error userUpdate(User user, String newEmail, List<String> newCards) throws ProjectException {
		if(!Validator.isAcceptableEmail(newEmail)) {
			return Error.INCORRECT_EMAIL;
		}
		for(String card:newCards) {
			if(!Validator.isAcceptableCard(card)) {
				return Error.INCORRECT_CARD;
			}
		}
		user.setEmail(newEmail);
		userDao.update(user);
		List<String> oldCards = user.getCards();
		if(oldCards == null) {
			oldCards = new ArrayList<>();
		}
		List<String> toDelete = new ArrayList<>();
		List<String> toInsert = new ArrayList<>();
		for(String card : oldCards) {
			if(!newCards.contains(card)) {
				toDelete.add(card);
			}
		}
		for(String card : newCards) {
			if(!oldCards.contains(card)) {
				toInsert.add(card);
			}
		}
		for(String card : toInsert) {
			userDao.createCards(card, user.getLogin());			
		}
		for(String card : toDelete) {
			userDao.deleteCards(card, user.getLogin());			
		}
		user.setCards(newCards);
		return Error.NONE;
	}
	
	public static Error userUpdate(User user, String newEmail) throws ProjectException {
		if(!Validator.isAcceptableEmail(newEmail)) {
			return Error.INCORRECT_EMAIL;
		}
		user.setEmail(newEmail);
		userDao.update(user);
		return Error.NONE;
	}
	
	public static boolean makeBet(String login, String[] ids, String[] forecast, BigDecimal bet, String card) throws ProjectException{
		boolean result = true;
		for(int i = 0; i < ids.length; i ++) {
			int id = Integer.parseInt(ids[i]);
			Forecast entity = new Forecast(login, id, forecast[i]);
			result = result & forecastDao.create(entity);
		}
		PersonalResult res = new PersonalResult();
		res.setUserLogin(login);
		res.setLastBet(bet);
		perResDao.update(res);
		return result;
	}
	
	public static Error passwordChange(User user, String oldPass, String newPass) throws ProjectException{
		String hash = Validator.passwordHasher(oldPass);
		if(!hash.equals(user.getPassword())) {
			return Error.OTHER_PASSWORD;
		}
		if(!Validator.isAcceptablePassword(newPass)) {
			return Error.INCORRECT_PASSWORD;
		}
		hash = Validator.passwordHasher(newPass);
		user.setPassword(hash);
		userDao.update(user);
		return Error.NONE;
	}
}
