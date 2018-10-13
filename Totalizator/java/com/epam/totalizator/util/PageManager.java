package com.epam.totalizator.util;

import java.util.ResourceBundle;

/**
 * Service class, to manage pages.
 *
 */
public class PageManager {

	private static ResourceBundle bundle = ResourceBundle.getBundle("page");
	
	public static String getPage(String key) {
		return bundle.getString(key);
	}
}
