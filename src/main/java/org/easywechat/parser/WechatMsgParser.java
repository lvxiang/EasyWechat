package org.easywechat.parser;

import org.easywechat.model.EventMsgModel;
import org.easywechat.model.TextMsgModel;
import org.easywechat.model.WechatMsgModel;
import org.easywechat.model.WechatMsgModel.MessageType;
import org.easywechat.util.JsoupUtil;
import org.easywechat.util.WechatUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WechatMsgParser {

	public static WechatMsgModel parseMsg(String content){
		Document doc = Jsoup.parse(content);
		String fromUserName = JsoupUtil.getTagContent(doc, WechatMsgTags.FROM_USER_NAME);
		String toUserName   = JsoupUtil.getTagContent(doc, WechatMsgTags.TO_USER_NAME);
		String createTime   = JsoupUtil.getTagContent(doc, WechatMsgTags.CREATE_TIME);
		String type         = JsoupUtil.getTagContent(doc, WechatMsgTags.MSG_TYPE);
		String msgId        = JsoupUtil.getTagContent(doc, WechatMsgTags.MSG_ID);
		WechatMsgModel msg  = null; 
		MessageParser parser = null;
		switch(WechatUtil.convertFromStringToType(type)) {
			case TEXT: {
				msg = new TextMsgModel();
				msg.setType(MessageType.TEXT);
				parser = new TextMsgParser();
				break;
			}
			case EVENT: {
				msg = new EventMsgModel();
				msg.setType(MessageType.EVENT);
				parser = new EventMsgParser();
				break;
			}
			case LOCATION: break;
			case NEWS: break;
			case UNKNOWN: break;
		}
		if(msg != null) {
			msg.setFromUserName(fromUserName);
			msg.setToUserName(toUserName);
			msg.setCreateTime(createTime);
			msg.setMsgId(msgId);
			return parser.parse(doc, msg);
		}
		return null;
	}
	
}
