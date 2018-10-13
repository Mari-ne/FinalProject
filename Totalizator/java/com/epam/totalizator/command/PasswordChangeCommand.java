package com.epam.totalizator.command;

import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import org.apache.log4j.Logger;

import com.epam.totalizator.entity.User;
import com.epam.totalizator.service.UserService;
import com.epam.totalizator.servlet.SessionRequestContainer;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.exception.ProjectException;

/**
 * Class-command to change password.
 *
 */
public class PasswordChangeCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getRootLogger();
	private static final String PARAM_USER = "user";
	private static final String PARAM_OLD = "old";
	private static final String PARAM_NEW = "new";
	private static final String PARAM_MESSAGE = "message";
	
	@Override
	public Optional<String> execute(SessionRequestContainer req) throws ProjectException {
		String page = null;
		try {
			User user = (User) req.getSessionAttribute(PARAM_USER);
			String newPass = req.getParametr(PARAM_NEW)[0];
			String oldPass = req.getParametr(PARAM_OLD)[0];
			UserService.Error err = UserService.passwordChange(user, oldPass, newPass);			
			if(!err.equals(UserService.Error.NONE)) {
				req.setSessionAttribute(PARAM_MESSAGE, err.getValue());
				page = PageManager.getPage("path.personalUpdate");
			}else {
				req.setSessionAttribute(PARAM_USER, user);
				page = PageManager.getPage("path.personalData");
			}
		} catch (InvalidAttributesException e) {
			LOGGER.warn(e);
		}
		return Optional.ofNullable(page);
	}

}
