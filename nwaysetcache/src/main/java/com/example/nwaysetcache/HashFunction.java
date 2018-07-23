package com.example.nwaysetcache;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HashFunction {

	/**
	 * Hashing method defines hash algorithm for key mapping to cache address
	 * location. Default hash map implemented here is MD5 hash algorithm..
	 * 
	 * @param key
	 *            Key to be hashed.
	 * @return Hashed integer Key.
	 */
	public static <K> int getHash(K key) {
		byte[] bytesOfKey = null;
		try {
			bytesOfKey = key.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(NSetCache.class.getName()).log(Level.SEVERE, null, ex);
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(NSetCache.class.getName()).log(Level.SEVERE, null, ex);
		}
		byte[] hashBytes = md.digest(bytesOfKey);
		return Math.abs(ByteBuffer.wrap(hashBytes).getInt());
	}
}
