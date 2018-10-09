package com.epam.totalizator.command;

import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import org.apache.log4j.Logger;

import com.epam.totalizator.service.ResultService;
import com.epam.totalizator.servlet.SessionRequest;
import com.epam.totalizator.util.ProjectException;

public class SetPoolCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getRootLogger();
	
	@Override
	public Optional<String> execute(SessionRequest req) throws ProjectException {
		String page = null;
		try {
			String[] parts = req.getParametr("pool");
			ResultService.changePool(parts);
		} catch (InvalidAttributesException e) {
			LOGGER.warn(e.getMessage());
		}
		return Optional.ofNullable(page);
	}

}
