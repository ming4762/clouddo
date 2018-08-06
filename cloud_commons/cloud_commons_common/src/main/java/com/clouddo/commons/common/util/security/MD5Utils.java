package com.clouddo.commons.common.util.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.io.IOException;
import java.io.InputStream;

public class MD5Utils {
	private static final String SALT = "1qazxsw2";

	private static final String ALGORITH_NAME = "md5";

	private static final int HASH_ITERATIONS = 2;

	public static String encrypt(String pswd) {
		String newPassword = new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(SALT), HASH_ITERATIONS).toHex();
		return newPassword;
	}

	public static String encrypt(String username, String pswd) {
		String newPassword = new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(username + SALT),
				HASH_ITERATIONS).toHex();
		return newPassword;
	}

	/**
	 * 进行MD5加密
	 * @param value
	 * @return
	 */
	public static String md5(String value) {
		return new SimpleHash(ALGORITH_NAME, value, null, 1).toHex();
	}

	/**
	 * 进行MD5加密
	 * @param inputStream
	 * @return
	 */
	public static String md5(InputStream inputStream) throws IOException {
		return DigestUtils.md5Hex(inputStream);
	}


}
