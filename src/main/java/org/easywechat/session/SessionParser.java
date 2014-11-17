package org.easywechat.session;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SessionParser {
	private static final String TAG_SESSION_CHAIN = "session-chain";
	private static final String TAG_SESSION       = "session";
	private static final String TAG_TYPE          = "type";
	private static final String TAG_MATCH         = "match";
	private static final String TAG_CLASSNAME     = "classname";
	private static final String TAG_FUNCTION      = "function";
	@SuppressWarnings("unused")
	private static final String TAG_CONDITIONAL   = "conditional";
	private static final String TAG_CONDITION     = "condition";
	private static final String TAG_VALUE         = "value";
	private static final String TAG_TARGET        = "target";
	private static final String TAG_RETURN        = "return";
	private static final String TAG_MISMATCH      = "mismatch";
	
	private static final String ATTR_ID           = "id";
	private static final String ATTR_CLASS        = "class";
	private static final String ATTR_ENTER        = "enter";

	public static SessionTemplate parse(InputStream in, String charset) throws IOException{
		if(in != null) {
			Document doc = Jsoup.parse(in, charset, "/");
			return parseDoc(doc);
		} else
			throw new NullPointerException();
	}
	
	public static SessionTemplate parse(String content) {
		if(content != null) {
			Document doc = Jsoup.parse(content); 
			return parseDoc(doc);
		} else {
			throw new NullPointerException();	
		}
	}
	
	private static SessionTemplate parseDoc(Document doc){
		if(doc != null){
			SessionTemplate template = new SessionTemplate();
			Elements sessionchains = doc.getElementsByTag(TAG_SESSION_CHAIN);
			Iterator<Element> iter = sessionchains.iterator();
			while(iter.hasNext()) {
				SessionChain chain = parseChain(iter.next());
				template.addChain(chain.getId(), chain);
			}
			return template;
		}
		return null;
	}

	private static SessionChain parseChain(Element chain){
		if(chain != null) {
			SessionChain sessionChain = new SessionChain();
			String id = chain.attr(ATTR_ID);
			if(StringUtil.isBlank(id))
				throw new IllegalArgumentException();
			sessionChain.setId(id);
			Elements sessions = chain.getElementsByTag(TAG_SESSION);
			Iterator<Element> iter = sessions.iterator();
			while(iter.hasNext()) {
				SessionModel sm = parseSession(iter.next());
				sm.setChain(sessionChain);
				sessionChain.addSession(sm.getId(), sm);
				if(sm.isEnter()) {
					if(sessionChain.getEnter() == null)
						sessionChain.setEnter(sm);
					else
						throw new IllegalArgumentException("Multiple enters in one session chain!");
				}
			}
			return sessionChain;
		}
		return null;
	}
	
	private static SessionModel parseSession(Element session){
		if(session != null) {
			SessionModel model = new SessionModel();
			String id    = session.attr(ATTR_ID);
			if(StringUtil.isBlank(id)) {
				throw new IllegalArgumentException("model must have id!");
			} else {
				model.setId(id);
				String sessionClass = session.attr(ATTR_CLASS);
				if(ATTR_ENTER.equals(sessionClass)) {
					model.setEnter(true);
				}
				
				String type      = getContentByTag(session, TAG_TYPE);
				String match     = getContentByTag(session, TAG_MATCH);
				String className = getContentByTag(session, TAG_CLASSNAME);
				String function  = getContentByTag(session, TAG_FUNCTION);
				if(StringUtil.isBlank(match)) {
					throw new IllegalArgumentException("Model must have a match condition!");
				}
				model.setType(type);
				model.setMatch(match);
				model.setClassName(className);
				model.setFunction(function);
				
				Elements conditions = session.getElementsByTag(TAG_CONDITION);
				Iterator<Element> iter = conditions.iterator();
				while(iter.hasNext()) {
					Element elem  = iter.next();
					String value  = getContentByTag(elem, TAG_VALUE);
					String target = getContentByTag(elem, TAG_TARGET);
					String result = getContentByTag(elem, TAG_RETURN);
					model.addCondition(value, target, result);
				}
				
				Element mismatch = session.getElementsByTag(TAG_MISMATCH).first();
				if(mismatch != null) {
					String target = getContentByTag(mismatch, TAG_TARGET);
					String result = getContentByTag(mismatch, TAG_RETURN);
					model.setMismatch(target, result);
				}
				return model;
			}
		}
		return null;
	}
	
	private static String getContentByTag(Element parent, String tag){
		if(parent != null && tag != null) {
			Elements elems = parent.getElementsByTag(tag);
			Iterator<Element> iter = elems.iterator();
			while(iter.hasNext()) {
				Element elem = iter.next();
				return elem.html();
			}
			return null;
		}
		throw new NullPointerException();
	}
}
