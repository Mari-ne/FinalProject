package com.epam.totalizator.command;

import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import org.apache.log4j.Logger;

import com.epam.totalizator.exception.ProjectException;
import com.epam.totalizator.service.UserService;
import com.epam.totalizator.servlet.SessionRequestContainer;
import com.epam.totalizator.util.PageManager;

public class AddUserCommand extends AbstractCommand {

	private static final String PARAM_LOGIN = "login";
	private static final String PARAM_EMAIL = "email";
	private static final String PARAM_ROLE = "role";
	private static final String PARAM_MESSAGE = "message";
	private static final Logger LOGGER = Logger.getRootLogger();
	
	@Override
	public Optional<String> execute(SessionRequestContainer req) throws ProjectException {
		String login;
		String email;
		String role;
		String page = null;
		try {
			login = req.getParametr(PARAM_LOGIN)[0];
			email = req.getParametr(PARAM_EMAIL)[0];
			role = req.getParametr(PARAM_ROLE)[0];
			UserService.Error err = UserService.addUser(login, email, role);
			if(!err.equals(UserService.Error.NONE)) {
				req.setSessionAttribute(PARAM_MESSAGE, err.getValue());
				page = PageManager.getPage("path.addUser");
			}else {
				page = PageManager.getPage("path.user");
			}
		} catch (InvalidAttributesException e) {
			LOGGER.warn(e);
		}				
		return Optional.ofNullable(page);
	}

}
