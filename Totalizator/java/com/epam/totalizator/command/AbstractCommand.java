package com.epam.totalizator.command;

import java.util.Optional;

import com.epam.totalizator.servlet.SessionRequestContainer;
import com.epam.totalizator.exception.ProjectException;

public abstract class AbstractCommand {

	public abstract Optional<String> execute(SessionRequestContainer req) throws ProjectException;
}
