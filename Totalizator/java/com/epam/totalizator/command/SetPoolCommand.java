package com.epam.totalizator.command;

import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import org.apache.log4j.Logger;

import com.epam.totalizator.service.ResultService;
import com.epam.totalizator.servlet.SessionRequestContainer;
import com.epam.totalizator.exception.ProjectException;

/**
 * Class-command to update pool.
 * 		Called if bookmaker changed pool parts for priza pool.
 */
public class SetPoolCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getRootLogger();
	private static final String PARAM_POOL = "pool";
	
	@Override
	public Optional<String> execute(SessionRequestContainer req) throws ProjectException {
		String page = null;
		try {
			String[] parts = req.getParametr(PARAM_POOL);
			ResultService.changePool(parts);
		} catch (InvalidAttributesException e) {
			LOGGER.warn(e);
		}
		return Optional.ofNullable(page);
	}

}
