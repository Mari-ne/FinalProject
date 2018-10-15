package com.epam.totalizator.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service class, to validate inputed data, such as passowords {@linkplain Validator#isAcceptablePassword(String)},
 * login {@linkplain Validator#isAcceptableLogin(String)}, email {@linkplain Validator#isAcceptableEmail(String)} and
 * card {@linkplain Validator#isAcceptableCard(String)}.
 *
 */
public class Validator {

	private static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])\\S{8,20}";
	private static final String LOGIN_PATTERN = "[\\w-]{4,20}";
	private static final String EMAIL_PATTERN = "\\w+@\\w{2,6}\\.\\w{2,3}";
	private static final String CARD_PATTERN = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";
	
	public static boolean isAcceptablePassword(String password) {
		Pattern passPattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher match = passPattern.matcher(password);
		return match.matches();
	}
	
	public static boolean isAcceptableLogin(String login) {
		Pattern logPattern = Pattern.compile(LOGIN_PATTERN);
		Matcher match = logPattern.matcher(login);
		return match.matches();
	}
	
	public static boolean isAcceptableEmail(String email) {
		Pattern mailPattern = Pattern.compile(EMAIL_PATTERN);
		Matcher match = mailPattern.matcher(email);
		return match.matches();
	}
	
	public static boolean isAcceptableCard(String card) {
		Pattern cardPattern = Pattern.compile(CARD_PATTERN);
		Matcher match = cardPattern.matcher(card);
		return match.matches();
	}
}
