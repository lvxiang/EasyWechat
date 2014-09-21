package org.easywechat.parser;

import org.easywechat.model.WechatMsgModel;
import org.jsoup.nodes.Document;

public interface MessageParser {

	public WechatMsgModel parse(Document doc, WechatMsgModel model);
	
}
