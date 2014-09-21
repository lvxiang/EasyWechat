package org.easywechat.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptUtil {

	public static String MD5(String s) {
		if(s != null){
			try {
				byte[] btInput = s.getBytes();
				// 获得MD5摘要算法的 MessageDigest 对象
				MessageDigest mdInst = MessageDigest.getInstance("MD5");
				// 使用指定的字节更新摘要
				mdInst.update(btInput);
				// 获得密文
				byte[] md = mdInst.digest();
				// 把密文转换成十六进制的字符串形式
				return StringUtil.byte2Hex(md);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

}
