package org.dt.easywechat.util;

import java.util.regex.Pattern;


public class StringUtil {
	
	static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', 
	                                 '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	static final Pattern BLANK_PATTERN = Pattern.compile("^\\s+$");
	static final Pattern PHONE_PATTERN = Pattern.compile("^[1-9][0-9]{4,14}$");
	
	public static String byte2Hex(byte[] arr){
		if(arr != null){
			long length = arr.length << 1;
			if(length > 0 && length < Integer.MAX_VALUE){
				char[] result = new char[(arr.length << 1)];
				for(int i = 0; i < arr.length; i++) {
					result[(i << 1)] = HEX_CHARS[arr[i] >>> 4 & 0x0f];
					result[(i << 1) + 1] = HEX_CHARS[arr[i] & 0x0f];
				}
				return new String(result);
			}
		}
		return null;
	}
	
	public static boolean isBlank(String str){
		if(str == null || str.length() == 0)
			return true;
		return BLANK_PATTERN.matcher(str).matches();
	}
	
	public static boolean isValidPhone(String phoneNum){
		return phoneNum != null && PHONE_PATTERN.matcher(phoneNum).matches();
	}
	
}
