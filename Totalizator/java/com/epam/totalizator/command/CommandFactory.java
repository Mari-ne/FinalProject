package com.epam.totalizator.command;

import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import org.apache.log4j.Logger;

import com.epam.totalizator.servlet.SessionRequestContainer;

public class CommandFactory {
	
	private static final Logger LOGGER = Logger.getRootLogger();
	
	public Optional<AbstractCommand> defineCommand(SessionRequestContainer req){
		String command = null;
		try {
			command = req.getParametr("command")[0];
		} catch (InvalidAttributesException e1) {
			return Optional.empty();
		}
		LOGGER.debug("Recived command " + command);
		AbstractCommand com = null;
		try {
			com = CommandEnum.valueOf(command.toUpperCase()).getCommand();
		}catch (IllegalArgumentException e) {
			LOGGER.error(e);
		}
		return Optional.ofNullable(com);
	}
}
