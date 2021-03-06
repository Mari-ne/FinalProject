package com.epam.totalizator.command;

import java.util.Optional;

import com.epam.totalizator.servlet.SessionRequestContainer;
import com.epam.totalizator.util.PageManager;

/**
 * Class-command to log out from account.
 *
 */
public class LogOutCommand extends AbstractCommand {

	@Override
	public Optional<String> execute(SessionRequestContainer req) {
		String page = PageManager.getPage("path.index");
		req.invalidateSession();
		return Optional.of(page);
	}

}
