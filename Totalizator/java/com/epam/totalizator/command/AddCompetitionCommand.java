package com.epam.totalizator.command;

import java.sql.Timestamp;
import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import org.apache.log4j.Logger;

import com.epam.totalizator.service.CompetitionService;
import com.epam.totalizator.servlet.SessionRequest;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.util.ProjectException;

public class AddCompetitionCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getRootLogger();
	
	@Override
	public Optional<String> execute(SessionRequest req) throws ProjectException {
		String page = null;
		try {
			int sport = Integer.parseInt(req.getParametr("sport")[0]);
			int team1 = Integer.parseInt(req.getParametr("team1")[0]);
			int team2 = Integer.parseInt(req.getParametr("team2")[0]);
			Timestamp start = Timestamp.valueOf(req.getParametr("start")[0].replace("T"," ") + ":00");
			Timestamp finish = Timestamp.valueOf(req.getParametr("finish")[0].replace("T"," ") + ":00");
			if(CompetitionService.addCompetition(sport, team1, team2, start, finish)) {
				page = PageManager.getPage("path.main");
			}
		} catch (NumberFormatException | InvalidAttributesException e) {
			LOGGER.warn(e.getMessage());
		}
		return Optional.ofNullable(page);
	}

}
