package com.epam.totalizator.command;

import java.math.BigDecimal;
import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import com.epam.totalizator.entity.User;
import com.epam.totalizator.service.UserService;
import com.epam.totalizator.servlet.SessionRequest;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.util.ProjectException;

public class AddForecastCommand extends AbstractCommand {

	@Override
	public Optional<String> execute(SessionRequest req) throws ProjectException {
		String page = null;
		try {
			User user = (User)req.getSessionAttribute("user");
			String[] ids = req.getParametr("id");
			String[] forecasts = req.getParametr("forecast");
			String card = req.getParametr("card")[0];
			BigDecimal bet = BigDecimal.valueOf(Double.parseDouble(req.getParametr("bet")[0]));
			if(UserService.makeBet(user.getLogin(), ids, forecasts, bet)) {
				page = PageManager.getPage("path.personalData");
			}
		} catch (NumberFormatException | InvalidAttributesException e) {
			org.apache.log4j.Logger.getRootLogger().warn(e.getMessage());
		}
		return Optional.ofNullable(page);
	}
}
