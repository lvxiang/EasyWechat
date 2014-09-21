package org.easywechat.handler;

import org.easywechat.MessageHandler;
import org.easywechat.model.WechatMsgModel;

public class MessageHandlerFactory {

	public static MessageHandler getHandler(String type){
		if(WechatMsgModel.TYPE_TEXT.equals(type))
			return new TextMsgHandler();
		if(WechatMsgModel.TYPE_EVENT.equals(type))
			return new EventMsgHandler();
		throw new IllegalArgumentException("Unsupported Message Type: " + type);
	}
	
}
