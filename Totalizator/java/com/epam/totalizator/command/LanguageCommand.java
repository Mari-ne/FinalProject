package com.epam.totalizator.command;

import java.util.Locale;
import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;

import org.apache.log4j.Logger;

import com.epam.totalizator.servlet.SessionRequestContainer;
import com.epam.totalizator.util.MessageManager;
import com.epam.totalizator.util.PageManager;
import com.epam.totalizator.exception.ProjectException;

/**
 * Class command to change language of representation.
 *
 */
public class LanguageCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getRootLogger();
	private static final String PARAM_LANG = "lang";
	private static final String PARAM_PAGE = "page";
	
	@Override
	public Optional<String> execute(SessionRequestContainer req) throws ProjectException {
		String page = null;
		try {
			String lang = req.getParametr(PARAM_LANG)[0];
			req.setSessionAttribute(PARAM_LANG, lang);
			MessageManager.localeChange(new Locale(lang));
			page = PageManager.getPage(req.getParametr(PARAM_PAGE)[0]);
		} catch (InvalidAttributesException e) {
			LOGGER.warn(e);
		}
		return Optional.ofNullable(page);
	}

}
