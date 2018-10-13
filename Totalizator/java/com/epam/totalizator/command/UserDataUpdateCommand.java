package com.epam.totalizator.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import org.apache.log4j.Logger;

import com.epam.totalizator.entity.User;
import com.epam.totalizator.service.UserService;
import com.epam.totalizator.servlet.SessionRequestContainer;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.exception.ProjectException;

/**
 * Class-command to process command of user data update.
 * 		Update email and cards (if user's role 'User').
 *
 */
public class UserDataUpdateCommand extends AbstractCommand {
	
	private static final Logger LOGGER = Logger.getRootLogger();
	private static final String PARAM_MESSAGE = "message";
	private static final String PARAM_USER = "user";
	private static final String PARAM_EMAIL = "email";
	private static final String PARAM_CARD = "card";

	@Override
	public Optional<String> execute(SessionRequestContainer req) throws ProjectException {
		String page = null;
		try {
			User user = (User) req.getSessionAttribute(PARAM_USER);
			String email = req.getParametr(PARAM_EMAIL)[0];
			UserService.Error err;
			if(user.getRole().equals("User")) {
				String[] card = req.getParametr(PARAM_CARD);
				List <String> cards = new ArrayList<>();
				for(int i = 0; i < card.length - 1; i ++) {
					cards.add(card[i]);
				}
				err = UserService.userUpdate(user, email, cards);
			} else {
				err = UserService.userUpdate(user, email);
			}
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
