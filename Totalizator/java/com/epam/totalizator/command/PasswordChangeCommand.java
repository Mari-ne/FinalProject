package com.epam.totalizator.command;

import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import com.epam.totalizator.entity.User;
import com.epam.totalizator.service.UserService;
import com.epam.totalizator.servlet.SessionRequest;
import com.epam.totalizator.util.MessageManager;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.util.ProjectException;

public class PasswordChangeCommand extends AbstractCommand {

	private static final String PARAM_MESSAGE = "message";
	
	@Override
	public Optional<String> execute(SessionRequest req) throws ProjectException {
		String page = null;
		try {
			User user = (User) req.getSessionAttribute("user");
			String newPass = req.getParametr("new")[0];
			String oldPass = req.getParametr("old")[0];
			UserService.Error err = UserService.passwordChange(user, oldPass, newPass);			
			if(!err.equals(UserService.Error.NONE)) {
				req.addAttribute(PARAM_MESSAGE, MessageManager.getMessage(err.getValue()));
				page = PageManager.getPage("path.personalUpdate");
			}else {
				req.setSessionAttribute("user", user);
				page = PageManager.getPage("path.personalData");
			}
			
			page = PageManager.getPage("path.personalData");
		} catch (InvalidAttributesException e) {
			
		}
		return Optional.ofNullable(page);
	}

}
