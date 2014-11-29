package com.locationmatching.util;

import org.apache.commons.net.util.Base64;

/**
 * Class that contains utility functions used by the rest of the application.
 * 
 * @author Greg Geller
 * @version 1.0
 * @since 1.0
 * 
 * 11/27/2014
 *
 */
public class Utils {
	
	/**
	 * Encrypt the passed in string
	 * 
	 * @param unencrypted
	 * @return Encrypted String
	 */
	public static String encrypt(String unencrypted) {
		return new String(Base64.encodeBase64(unencrypted.getBytes(), false, true));
	}
	
	/**
	 * Decrypt the encrypted String
	 * 
	 * @param encrypted
	 * 
	 * @return Decrypted String
	 */
	public static String decrypt(String encrypted) {
		return new String(Base64.decodeBase64(encrypted.getBytes()));
	}
}
