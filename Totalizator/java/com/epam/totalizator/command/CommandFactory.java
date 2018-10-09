package com.epam.totalizator.command;

import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import com.epam.totalizator.servlet.SessionRequest;

public class CommandFactory {
	public Optional<AbstractCommand> defineCommand(SessionRequest req){
		String command = null;
		try {
			command = req.getParametr("command")[0];
		} catch (InvalidAttributesException e1) {
			return Optional.empty();
		}
		org.apache.log4j.Logger.getRootLogger().debug("Recived command " + command);
		AbstractCommand com = null;
		try {
			com = CommandEnum.valueOf(command.toUpperCase()).getCommand();
		}catch (IllegalArgumentException e) {
			//LOGGER.error(e);
		}
		return Optional.ofNullable(com);
	}
}
