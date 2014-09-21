package org.easywechat.session;

import java.util.HashMap;
import java.util.Map;

public class SessionChain {

	private String id;
	private SessionModel enter;
	private Map<String, SessionModel> sessions;
	
	public SessionChain(){
		sessions = new HashMap<String, SessionModel>();
	}
	
	public SessionChain(String id){
		this();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public SessionModel getEnter() {
		return enter;
	}

	public void setEnter(SessionModel enter) {
		this.enter = enter;
	}

	public Map<String, SessionModel> getSessions() {
		return sessions;
	}

	public void setSessions(Map<String, SessionModel> sessions) {
		this.sessions = sessions;
	}
	
	public void addSession(String sessionId, SessionModel model) {
		this.sessions.put(sessionId, model);
	}
	
	public SessionModel getSession(String sessionId){
		if(sessions != null) {
			return sessions.get(sessionId);
		}
		return null;
	}
}
