package com.epam.totalizator.command;

import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import org.apache.log4j.Logger;

import com.epam.totalizator.entity.User;
import com.epam.totalizator.service.UserService;
import com.epam.totalizator.servlet.SessionRequest;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.exception.ProjectException;
import com.epam.totalizator.util.Validator;

public class LoginCommand extends AbstractCommand {

	private static final String PARAM_LOGIN = "login";
	private static final String PARAM_PASSWORD = "password";
	private static final String PARAM_USER = "user";
	
	private static final Logger LOGGER = Logger.getRootLogger();
	
	@Override
	public Optional<String> execute(SessionRequest req) throws ProjectException{
		String login;
		String password;
		String page = null;
		try {
			login = req.getParametr(PARAM_LOGIN)[0];
			password = req.getParametr(PARAM_PASSWORD)[0];
			password = Validator.passwordHasher(password);
			Optional<User> user = UserService.isAutorized(login, password);
			if(user.isPresent()) {
				req.setSessionAttribute(PARAM_USER, user.get());
				page = PageManager.getPage("path.personalData");
			}
		} catch (InvalidAttributesException e) {
			LOGGER.warn(e);
		}
		return Optional.ofNullable(page);
	}

}
