package com.epam.totalizator.command;

import java.util.Optional;

import com.epam.totalizator.servlet.SessionRequest;
import com.epam.totalizator.util.PageManager;

public class LogOutCommand extends AbstractCommand {

	@Override
	public Optional<String> execute(SessionRequest req) {
		String page = PageManager.getPage("path.index");
		req.invalidateSession();
		return Optional.of(page);
	}

}
