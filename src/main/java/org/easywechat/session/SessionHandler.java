package org.easywechat.session;

import java.util.Map;

import org.easywechat.model.WechatMsgModel;

public interface SessionHandler {

	public String handle(String openid, WechatMsgModel model, Map<Object, Object> data);
	
}
