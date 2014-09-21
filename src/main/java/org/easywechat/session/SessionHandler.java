package org.easywechat.session;

import org.easywechat.model.WechatMsgModel;

public interface SessionHandler {

	public String handle(String openid, WechatMsgModel model);
	
}
