package com.epam.totalizator.command;

import java.util.Optional;

import com.epam.totalizator.servlet.SessionRequest;
import com.epam.totalizator.exception.ProjectException;

public abstract class AbstractCommand {

	public abstract Optional<String> execute(SessionRequest req) throws ProjectException;
}
