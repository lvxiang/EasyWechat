package org.easywechat;

import java.io.InputStream;

import org.easywechat.model.WechatMsgModel;
import org.easywechat.parser.WechatMsgParser;
import org.easywechat.session.SessionManager;

public class Wechat {

	public static final String KEY_RESULT_MSG = "$MSG$";
	
	private static SessionManager sessionManager;
	
	public static void initSession(String filename, int expire){
		if(filename != null) {
			if(sessionManager == null) {
				InputStream in = Wechat.class.getClassLoader().getResourceAsStream(filename);
				sessionManager = SessionManager.newInstance(in, expire);
				
			} else {
				throw new IllegalStateException("A session manager already exists!");
			}
		}
	}
	
	public static SessionManager getSessionManager(){
		return sessionManager;
	}
	
	public static WechatMsgModel parseMsg(String msg){
		return WechatMsgParser.parseMsg(msg);
	}
	
}