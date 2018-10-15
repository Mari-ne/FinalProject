package com.epam.totalizator.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class to hash strings (use md5)
 *
 */
public class Hasher {
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
}
