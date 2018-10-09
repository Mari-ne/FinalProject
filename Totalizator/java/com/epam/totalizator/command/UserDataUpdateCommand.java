package com.epam.totalizator.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import com.epam.totalizator.entity.User;
import com.epam.totalizator.service.UserService;
import com.epam.totalizator.servlet.SessionRequest;
import com.epam.totalizator.util.MessageManager;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.util.ProjectException;

public class UserDataUpdateCommand extends AbstractCommand {
	private static final String PARAM_MESSAGE = "message";

	@Override
	public Optional<String> execute(SessionRequest req) throws ProjectException {
		String page = null;
		try {
			User user = (User) req.getSessionAttribute("user");
			String email = req.getParametr("email")[0];
			UserService.Error err;
			if(user.getRole().equals("User")) {
				String[] card = req.getParametr("card");
				List <String> cards = new ArrayList<>();
				for(int i = 0; i < card.length - 1; i ++) {
					cards.add(card[i]);
				}
				err = UserService.userUpdate(user, email, cards);
			} else {
				err = UserService.userUpdate(user, email);
			}
			if(!err.equals(UserService.Error.NONE)) {
				req.addAttribute(PARAM_MESSAGE, MessageManager.getMessage(err.getValue()));
				org.apache.log4j.Logger.getRootLogger().info(MessageManager.getMessage(err.getValue()) + "\n");
				page = PageManager.getPage("path.personalUpdate");
			}else {
				req.setSessionAttribute("user", user);
				page = PageManager.getPage("path.personalData");
			}
			
			page = PageManager.getPage("path.personalData");
		} catch (InvalidAttributesException e) {
			org.apache.log4j.Logger.getRootLogger().warn(e.getMessage());
		}
		return Optional.ofNullable(page);
	}

}
