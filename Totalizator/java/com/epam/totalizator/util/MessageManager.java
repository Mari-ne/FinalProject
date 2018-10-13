package com.epam.totalizator.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Service class, to manage messages.
 *
 */
public class MessageManager {
	private static ResourceBundle bundle = ResourceBundle.getBundle("message", new Locale("en"));
	
	public static String getMessage(String key) {
		return bundle.getString(key);
	}
	
	/**
	 * Change locale of resource bundle.
	 * @param loc New locale, need to open bundle with
	 */
	public static void localeChange(Locale loc) {
		if(!bundle.getLocale().equals(loc)) {
			bundle = ResourceBundle.getBundle("message", loc);
		}
	}
}
