package com.epam.totalizator.command;

import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import com.epam.totalizator.service.TeamService;
import com.epam.totalizator.servlet.SessionRequest;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.util.ProjectException;

public class AddTeamCommand extends AbstractCommand {

	@Override
	public Optional<String> execute(SessionRequest req) throws ProjectException {
		String page = null;
		try {
			int sport = Integer.parseInt(req.getParametr("sport")[0]);
			String[] names = new String[3];
			names[0] = req.getParametr("nameEN")[0];
			names[1] = req.getParametr("nameJP")[0];
			names[2] = req.getParametr("nameRU")[0];
			if(TeamService.addTeam(sport, names)) {
				page = PageManager.getPage("path.teams");
			}
		} catch (NumberFormatException | InvalidAttributesException e) {
			org.apache.log4j.Logger.getRootLogger().warn(e.getMessage());
		}
		return Optional.ofNullable(page);
	}

}
