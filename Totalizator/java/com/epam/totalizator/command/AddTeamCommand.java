package com.epam.totalizator.command;

import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import org.apache.log4j.Logger;

import com.epam.totalizator.service.TeamService;
import com.epam.totalizator.servlet.SessionRequestContainer;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.exception.ProjectException;

/**
 * Class-command to add new team to database.
 *
 */
public class AddTeamCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getRootLogger();
	private static final String PARAM_SPORT = "sport";
	private static final String PARAM_NAME_EN = "nameEN";
	private static final String PARAM_NAME_JP = "nameJP";
	private static final String PARAM_NAME_RU = "nameRU";
	
	@Override
	public Optional<String> execute(SessionRequestContainer req) throws ProjectException {
		String page = null;
		try {
			int sport = Integer.parseInt(req.getParametr(PARAM_SPORT)[0]);
			String[] names = new String[3];
			names[0] = req.getParametr(PARAM_NAME_EN)[0];
			names[1] = req.getParametr(PARAM_NAME_JP)[0];
			names[2] = req.getParametr(PARAM_NAME_RU)[0];
			if(TeamService.addTeam(sport, names)) {
				page = PageManager.getPage("path.teams");
			}
		} catch (NumberFormatException | InvalidAttributesException e) {
			LOGGER.warn(e);
		}
		return Optional.ofNullable(page);
	}

}
