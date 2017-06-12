package com.QRCloud.dao;

import java.security.MessageDigest;

public class DaoUtil {
	public static String MD5Encode(String password){
		byte[] thedigest = null;
		String ret = null;
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			thedigest = md.digest();
			ret = byteArrayToHexString(thedigest);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public static String byteArrayToHexString(byte[] b) { 
		StringBuffer resultSb = new StringBuffer(); 
		for (int i = 0; i < b.length; i++) { 		
			resultSb.append(Integer.toString((b[i] & 0xFF) + 0x100, 16).substring(1));
		} 
		return resultSb.toString(); 
	}
}
