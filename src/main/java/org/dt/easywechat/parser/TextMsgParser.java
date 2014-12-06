package org.dt.easywechat.parser;

import org.dt.easywechat.model.TextMsgModel;
import org.dt.easywechat.model.WechatMsgModel;
import org.dt.easywechat.util.JsoupUtil;
import org.jsoup.nodes.Document;

public class TextMsgParser implements MessageParser{

	@Override
	public WechatMsgModel parse(Document doc, WechatMsgModel model) {
		TextMsgModel txt = (TextMsgModel) model;
		txt.setContent(JsoupUtil.getTagContent(doc, WechatMsgTags.CONTENT));
		return txt;
	}

}
