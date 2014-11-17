package org.easywechat.session.test;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.easywechat.session.SessionParser;
import org.easywechat.session.SessionTemplate;
import org.junit.Test;


public class SessionParserTest {

	@Test
	public void test(){
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("multiple-chain-template.xml");
		if(in != null) {
			try {
				SessionTemplate template = SessionParser.parse(in, "UTF-8");
				Assert.assertNotNull(template);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
