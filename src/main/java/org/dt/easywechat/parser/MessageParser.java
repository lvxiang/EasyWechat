package org.dt.easywechat.parser;

import org.dt.easywechat.model.WechatMsgModel;
import org.jsoup.nodes.Document;

public interface MessageParser {

	public WechatMsgModel parse(Document doc, WechatMsgModel model);
	
}
