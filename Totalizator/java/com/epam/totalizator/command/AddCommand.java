package com.epam.totalizator.command;

import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import org.apache.log4j.Logger;

import com.epam.totalizator.servlet.SessionRequest;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.util.ProjectException;

public class AddCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getRootLogger();
	
	@Override
	public Optional<String> execute(SessionRequest req) throws ProjectException {
		String page = null;
		try {
			String type = req.getParametr("type")[0];
			page = PageManager.getPage("path.add" + type);
		} catch (InvalidAttributesException e) {
			LOGGER.warn(e.getMessage());
		}
		return Optional.ofNullable(page);
	}

}
