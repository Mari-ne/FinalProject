package com.epam.totalizator.command;

import java.sql.Timestamp;
import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import org.apache.log4j.Logger;

import com.epam.totalizator.service.CompetitionService;
import com.epam.totalizator.servlet.SessionRequest;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.exception.ProjectException;

public class AddCompetitionCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getRootLogger();
	private static final String PARAMETR_SPORT = "sport";
	private static final String PARAMETR_TEAM1 = "team1";
	private static final String PARAMETR_TEAM2 = "team2";
	private static final String PARAMETR_START = "start";
	private static final String PARAMETR_FINISH = "finish";
	
	@Override
	public Optional<String> execute(SessionRequest req) throws ProjectException {
		String page = null;
		try {
			int sport = Integer.parseInt(req.getParametr(PARAMETR_SPORT)[0]);
			int team1 = Integer.parseInt(req.getParametr(PARAMETR_TEAM1)[0]);
			int team2 = Integer.parseInt(req.getParametr(PARAMETR_TEAM2)[0]);
			Timestamp start = Timestamp.valueOf(req.getParametr(PARAMETR_START)[0].replace("T"," ") + ":00");
			Timestamp finish = Timestamp.valueOf(req.getParametr(PARAMETR_FINISH)[0].replace("T"," ") + ":00");
			if(CompetitionService.addCompetition(sport, team1, team2, start, finish)) {
				page = PageManager.getPage("path.main");
			}
		} catch (NumberFormatException | InvalidAttributesException e) {
			LOGGER.warn(e);
		}
		return Optional.ofNullable(page);
	}

}
