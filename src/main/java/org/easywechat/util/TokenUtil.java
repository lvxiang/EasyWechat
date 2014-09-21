package org.easywechat.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TokenUtil {
	
	static final char[] ALPHABET = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
		'l', 'm', 'n', 'o', 'o', 'p', 'q', 'r', 's', 't', 'u',
		'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
		'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
		'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
	};
	
	static final Calendar initdate = Calendar.getInstance();
	static{
		initdate.set(Calendar.YEAR, 2013);
		initdate.set(Calendar.MONTH, Calendar.JULY);
		initdate.set(Calendar.DAY_OF_MONTH, 1);
		initdate.set(Calendar.HOUR_OF_DAY, 0);
		initdate.set(Calendar.MINUTE, 0);
		initdate.set(Calendar.SECOND, 0);
		initdate.set(Calendar.MILLISECOND, 0);
	}
	
	private static final int MAX_TOKEN_LENGTH = 128;
	
	public static String randomStr(int length){
		if(length > 0 && length < MAX_TOKEN_LENGTH){
			Random       random = new Random();
			StringBuffer buf    = new StringBuffer();
			for(int i = 0; i < length; i ++)
				buf.append(ALPHABET[random.nextInt(ALPHABET.length)]);
			return buf.toString();
		}
		throw new IllegalArgumentException("length must be between 1 and " + MAX_TOKEN_LENGTH);
	}

	public synchronized static String createInitialId(){
		return String.valueOf(new Date().getTime() - initdate.getTimeInMillis());
	}
	
	public static String createSessionToken(String username, String ts, String seed){
		String[] arr = new String[]{username, ts, seed};
		Arrays.sort(arr);
		return CryptUtil.MD5(Arrays.toString(arr));
	}
}
