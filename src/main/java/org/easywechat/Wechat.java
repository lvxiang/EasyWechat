package org.easywechat;

import java.io.InputStream;

import org.easywechat.session.SessionManager;

public class Wechat {

	private static SessionManager sessionManager;
	
	public static void initSession(String filename, int expire){
		if(filename != null) {
			if(sessionManager == null) {
				InputStream in = Wechat.class.getClassLoader().getResourceAsStream(filename);
				sessionManager = SessionManager.newInstance(in);
				
			} else {
				
			}
		}
	}
	
}
