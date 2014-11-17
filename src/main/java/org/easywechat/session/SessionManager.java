package org.easywechat.session;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.easywechat.model.WechatMsgModel;
import org.easywechat.session.SessionProcessor.SessionProcessResult;

public class SessionManager {

	private static final int    DEFAULT_EXPIRE  = 300; // 会话默认5分钟过期
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	private final Map<String, SessionModel> sessions = 
			new HashMap<String, SessionModel>();
	
	private SessionTemplate template;
	
	private Thread runner;
	
	private int expire = DEFAULT_EXPIRE;
	
	public static SessionManager newInstance(String filename) {
		return newInstance(filename, DEFAULT_CHARSET, DEFAULT_EXPIRE);
	}
	
	public static SessionManager newInstance(String filename, String charset){
		return newInstance(filename, charset, DEFAULT_EXPIRE);
	}
	
	public static SessionManager newInstance(String filename, String charset, int expire){
		InputStream in = SessionManager.class.getClassLoader().getResourceAsStream(filename);
		return newInstance(in, charset, expire);
	}
	
	public static SessionManager newInstance(InputStream in){
		return newInstance(in, DEFAULT_CHARSET, DEFAULT_EXPIRE);
	}
	
	public static SessionManager newInstance(InputStream in, String charset) {
		return newInstance(in, charset, DEFAULT_EXPIRE);
	}
	
	public static SessionManager newInstance(InputStream in, String charset, int expire){
		SessionManager sm = new SessionManager();
		sm.init(in, charset, expire);
		return sm;
	}
	
	private SessionManager(){}
	
	public final int getExpire() {
		return expire;
	}

	public final void setExpire(int expire) {
		this.expire = expire;
	}
	
	private final void init(InputStream input, String charset, int expire) {
		this.setExpire(expire);
		try {
			template = SessionParser.parse(input, charset);
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		runner = new Thread(new TimerRunner());
		runner.start();
	}
	
	public final SessionFilterResult filter(String openid, WechatMsgModel model){
		SessionModel session = this.sessions.get(openid);
		if(session != null) {
			try {
				SessionProcessResult result = SessionProcessor.process(openid, template, session, model);
				if(result != null) {
					if(result.getNext() != null) {
						result.getNext().update(System.currentTimeMillis());
						this.sessions.put(openid, result.getNext());
					} else {
						this.sessions.remove(openid);
					}
					return new SessionFilterResult(true, result.getResult());
				} else {
					// 未命中
					return new SessionFilterResult(false, null);
				}
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				SessionProcessResult result = SessionProcessor.process(openid, template, model);
				if(result != null) {
					if(result.getNext() != null) {
						result.getNext().update(System.currentTimeMillis());
						this.sessions.put(openid, result.getNext());
					}
					return new SessionFilterResult(true, result.getResult());
				}
				return new SessionFilterResult(false, null);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;
	}
	
	public final boolean sessionExists(String openid){
		return this.sessions.containsKey(openid);
	}
	
	public final SessionModel getSession(String openid){
		return this.sessions.get(openid);
	}
	
	public final SessionModel removeSession(String openid){
		return this.sessions.remove(openid);
	}
	
	private class TimerRunner implements Runnable{

		public void run() {
			// TODO Auto-generated method stub
			while(true) {
				try {
					Thread.sleep(expire * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(String key: sessions.keySet()) {
					long currentTime = System.currentTimeMillis();
					SessionModel model = sessions.get(key);
					if(model != null && currentTime - model.getLastUpdate() > expire * 1000) {
						sessions.remove(key);
					}
				}
			}
		}
	}
	
	public class SessionFilterResult{
		boolean match = false;
		String  result = null;
		
		public SessionFilterResult(boolean match, String result) {
			this.match = match;
			this.result = result;
		}

		public boolean isMatch() {
			return match;
		}

		public void setMatch(boolean match) {
			this.match = match;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}
	}
}
