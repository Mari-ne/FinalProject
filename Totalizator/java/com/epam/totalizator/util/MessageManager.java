package com.epam.totalizator.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {
	private static ResourceBundle bundle = ResourceBundle.getBundle("message", new Locale("ru", "RU"));
	
	public static String getMessage(String key) {
		return bundle.getString(key);
	}
	
	public static void localeChange(Locale loc) {
		if(!bundle.getLocale().equals(loc)) {
			bundle = ResourceBundle.getBundle("message", loc);
		}
	}
}
