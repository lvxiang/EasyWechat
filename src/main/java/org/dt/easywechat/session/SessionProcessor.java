package org.dt.easywechat.session;


import java.util.HashMap;

import org.dt.easywechat.Wechat;
import org.dt.easywechat.model.EventMsgModel;
import org.dt.easywechat.model.TextMsgModel;
import org.dt.easywechat.model.WechatMsgModel;
import org.dt.easywechat.session.SessionModel.Condition;
import org.jsoup.helper.StringUtil;

public class SessionProcessor {

	/***
	 * 从template中找到符合条件的session入口，并执行其方法
	 * @param openid
	 * @param template
	 * @param model
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static SessionProcessResult process(String openid, SessionTemplate template, WechatMsgModel model) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		if(template != null && model != null) {
			for(String id: template.getChainIds()) {
				SessionChain chain = template.getChain(id);
				SessionModel entry = chain.getEnter();
				if(entry != null && entry.matches(model)) {
					entry.setData(new HashMap<Object, Object>());
					switch(model.getType()) {
					case TEXT: 
						return executeTextMsg(openid, chain, entry, model);
					case EVENT: 
						return executeEventMsg(openid, chain, entry, model);
					default:
						break;
					}
				}
			}
		}
		return null;
	}
	
	public static SessionProcessResult process(String openid, SessionTemplate template, 
			SessionModel session, WechatMsgModel model) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		if(template != null && session != null && model != null) {
			if(session.matches(model)) {
				switch(model.getType()) {
				case TEXT: 
					return executeTextMsg(openid, session.getChain(), session, model);
				case EVENT: 
					return executeEventMsg(openid, session.getChain(), session, model);
				default:
					break;
				}
			} else {
				// 走mismatch流程
				if(session.getMismatch() != null)
					return new SessionProcessResult(session.getMismatch().getResult(), null);
			}
		}
		return null;
	}
	
	private static SessionProcessResult executeTextMsg(String openid, SessionChain chain, 
			SessionModel session, WechatMsgModel model) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		String className = session.getClassName();
		String result    = "";
		if(!StringUtil.isBlank(className)) {
			SessionHandler handler = (SessionHandler) Class.forName(className).newInstance();
			result = handler.handle(openid, model, session.getData());
		} else {
			result = ((TextMsgModel) model).getContent();
		}
		
		Condition cond = session.getCondition(result);
		if(cond != null) {
			if(cond.isNeedResult()) 
				cond.setResult((String) session.getData().get(Wechat.KEY_RESULT_MSG));
			
			SessionModel next = chain.getSession(cond.getTarget());
			if(next != null) next.setData(session.getData());
			return new SessionProcessResult(cond.getResult(), next);
		}
		return null;
	}
	
	private static SessionProcessResult executeEventMsg(String openid, SessionChain chain, 
			SessionModel session, WechatMsgModel model) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		String className = session.getClassName();
		String result    = "";
		if(className != null) {
			SessionHandler handler = (SessionHandler) Class.forName(className).newInstance();
			result = handler.handle(openid, model, session.getData());
		} else {
			result = ((EventMsgModel) model).getEvent();
		}
		Condition cond = session.getCondition(result);
		if(cond != null) {
			if(cond.isNeedResult()) 
				cond.setResult((String) session.getData().get(Wechat.KEY_RESULT_MSG));
			SessionModel next = chain.getSession(cond.getTarget());
			if(next != null) next.setData(session.getData());
			return new SessionProcessResult(cond.getResult(), next);
		}
		return null;
	}
	
	public static class SessionProcessResult{
		String  result = null;
		SessionModel next = null;
		
		public SessionProcessResult(String result, SessionModel model){
			this.setResult(result);
			this.setNext(model);
		}
		
		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		public SessionModel getNext() {
			return next;
		}

		public void setNext(SessionModel next) {
			if(next != null)
				this.next = next.clone();
		}
	}
}
