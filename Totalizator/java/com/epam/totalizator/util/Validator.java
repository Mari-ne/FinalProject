package com.epam.totalizator.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
	
	/**
	 * String hashing.
	 * 
	 * @param password String, that need to be hashed
	 * @return Hashed password
	 */
	public static String passwordHasher(String password) {
		byte[] digest = new byte[0];
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
	        md.update(password.getBytes("UTF-8"));
			digest = md.digest();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			org.apache.log4j.Logger.getRootLogger().warn(e.getMessage());
		}
		
		BigInteger bigInt = new BigInteger(1, digest);
		String hashPass = bigInt.toString(16);

	    while( hashPass.length() < 32 ){
	    	hashPass = "0" + hashPass;
	    }
	    
		return hashPass;
	}
	
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
