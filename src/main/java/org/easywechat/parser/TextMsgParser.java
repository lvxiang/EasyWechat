package org.easywechat.parser;

import org.easywechat.model.TextMsgModel;
import org.easywechat.model.WechatMsgModel;
import org.easywechat.util.JsoupUtil;
import org.jsoup.nodes.Document;

public class TextMsgParser implements MessageParser{

	@Override
	public WechatMsgModel parse(Document doc, WechatMsgModel model) {
		TextMsgModel txt = (TextMsgModel) model;
		txt.setContent(JsoupUtil.getTagContent(doc, WechatMsgTags.CONTENT));
		return txt;
	}

}
