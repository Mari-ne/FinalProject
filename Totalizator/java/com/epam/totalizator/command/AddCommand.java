package com.epam.totalizator.command;

import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import org.apache.log4j.Logger;

import com.epam.totalizator.servlet.SessionRequestContainer;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.exception.ProjectException;

/**
 * Class-command to choose what will be added.
 */
public class AddCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getRootLogger();
	private static final String PARAMETR_TYPE = "type";
	
	@Override
	public Optional<String> execute(SessionRequestContainer req) throws ProjectException {
		String page = null;
		try {
			String type = req.getParametr(PARAMETR_TYPE)[0];
			page = PageManager.getPage("path.add" + type);
		} catch (InvalidAttributesException e) {
			LOGGER.warn(e.getMessage());
		}
		return Optional.ofNullable(page);
	}

}
