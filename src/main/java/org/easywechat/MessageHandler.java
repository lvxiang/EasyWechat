package org.easywechat;

import org.easywechat.model.WechatMsgModel;
import org.jsoup.nodes.Document;

public interface MessageHandler {
	
	public WechatMsgModel handleMessage(Document doc);
	
}
