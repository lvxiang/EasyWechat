package org.easywechat.session.test;

import org.easywechat.session.SessionManager;
import org.junit.Test;

public class SessionManagerTest {

	@Test
	public void test(){
		SessionManager sm = SessionManager.newInstance("multiple-chain-template.xml", 1);
	}
	
}
