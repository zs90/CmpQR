package com.QRCloud.dao;

import java.security.MessageDigest;

/**
 * 一些用于DAO层的实用工具类
 * 
 * @author Shane
 * @version 1.0
 */
public class DaoUtil {
	/**
	 * MD5编码方法，将字符串password进行MD5编码
	 * 
	 * @param password
	 *            需要编码的字符串，也就是密码
	 * @return 编码后的字符串
	 */
	public static String MD5Encode(String password) {
		byte[] thedigest = null;
		String ret = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			thedigest = md.digest();
			ret = byteArrayToHexString(thedigest);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	/**
	 * 将字节串转化为十六进制字符串
	 * 
	 * @param b
	 *            待转换的字节串
	 * @return 转化后的十六进制字符串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(Integer.toString((b[i] & 0xFF) + 0x100, 16).substring(1));
		}
		return resultSb.toString();
	}
}
