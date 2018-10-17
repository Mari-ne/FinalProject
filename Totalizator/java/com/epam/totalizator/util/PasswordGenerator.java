package com.epam.totalizator.util;

import java.util.Random;

/**
 * Class to generate passwords.
 *
 */
public class PasswordGenerator {

	private static final int MIN_LENGTH = 8;
	private static final int MAX_LENGTH = 20;
	
	public static String generate() {
		String pass = null;
		do {
			pass  = "";
	        Random r = new Random();
	        int length = MIN_LENGTH + r.nextInt(MAX_LENGTH - MIN_LENGTH + 1);
	        for (int i = 0; i < length; ++i) {
	            char next = 0;
	            int range = 10;
	            switch(r.nextInt(3)) {
	                case 0: {next = '0'; range = 10;} break;
	                case 1: {next = 'a'; range = 26;} break;
	                case 2: {next = 'A'; range = 26;} break;
	            }
	            pass += (char)((r.nextInt(range)) + next);
	        }
		} while(!Validator.isAcceptablePassword(pass));
        return pass;
	}
}
