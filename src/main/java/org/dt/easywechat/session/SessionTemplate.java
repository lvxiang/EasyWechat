package org.dt.easywechat.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SessionTemplate {
	
	private Map<String, SessionChain> chains;
	
	public SessionTemplate(){
		this.chains = new HashMap<String, SessionChain>();
	}

	public SessionChain getChain(String chainId){
		return chains.get(chainId);
	}
	
	public void addChain(String chainId, SessionChain chain){
		chains.put(chainId, chain);
	}
	
	public void removeChain(String chainId) {
		chains.remove(chainId);
	}
	
	public Set<String> getChainIds(){
		return chains.keySet();
	}
}
