package com.epam.totalizator.command;

import java.math.BigDecimal;
import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import org.apache.log4j.Logger;

import com.epam.totalizator.entity.User;
import com.epam.totalizator.service.UserService;
import com.epam.totalizator.servlet.SessionRequest;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.exception.ProjectException;

public class AddForecastCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getRootLogger();
	private static final String PARAM_USER = "user";
	private static final String PARAM_ID = "id";
	private static final String PARAM_FORECAST = "forecast";
	private static final String PARAM_CARD = "card";
	private static final String PARAM_BET = "bet";
	
	@Override
	public Optional<String> execute(SessionRequest req) throws ProjectException {
		String page = null;
		try {
			User user = (User)req.getSessionAttribute(PARAM_USER);
			String[] ids = req.getParametr(PARAM_ID);
			String[] forecasts = req.getParametr(PARAM_FORECAST);
			String card = req.getParametr(PARAM_CARD)[0];
			BigDecimal bet = BigDecimal.valueOf(Double.parseDouble(req.getParametr(PARAM_BET)[0]));
			if(UserService.makeBet(user.getLogin(), ids, forecasts, bet, card)) {
				page = PageManager.getPage("path.personalData");
			}
		} catch (NumberFormatException | InvalidAttributesException e) {
			LOGGER.warn(e);
		}
		return Optional.ofNullable(page);
	}

}
