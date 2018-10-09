package com.epam.totalizator.util;

import java.util.Locale;

import java.util.ResourceBundle;

public class PageManager {

	private static ResourceBundle bundle = ResourceBundle.getBundle("page", new Locale("ru", "RU"));
	
	public static String getPage(String key) {
		return bundle.getString(key);
	}
	
	public static void localeChange(Locale loc) {
		if(!bundle.getLocale().equals(loc)) {
			bundle = ResourceBundle.getBundle("page", loc);
		}
	}
}
