package com.song.test.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class EncUtil {
	
	private StandardPBEStringEncryptor jasypt;
	private String key = "changsick";
	private String algorithm = "PBEWithMD5AndDES";

	public EncUtil() {
		jasypt = new StandardPBEStringEncryptor();
		jasypt.setPassword(key);
		
	}
	
	public String setEnc(String pw) {
		return jasypt.encrypt(pw);
	}
	
	public String setdec(String pw) {
		return jasypt.decrypt(pw);
	}
	
}
