package com.epam.totalizator.command;

import java.util.Locale;
import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import com.epam.totalizator.servlet.SessionRequest;
import com.epam.totalizator.util.MessageManager;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.util.ProjectException;

public class LanguageCommand extends AbstractCommand {

	@Override
	public Optional<String> execute(SessionRequest req) throws ProjectException {
		String page = null;
		try {
			String lang = req.getParametr("lang")[0];
			req.setSessionAttribute("lang", new Locale(lang, lang.toUpperCase()));
			MessageManager.localeChange((Locale)req.getSessionAttribute("lang"));
			PageManager.localeChange((Locale)req.getSessionAttribute("lang"));
			page = PageManager.getPage(req.getParametr("page")[0]);
		} catch (InvalidAttributesException e) {
			throw new ProjectException(e);
		}
		return Optional.ofNullable(page);
	}

}
