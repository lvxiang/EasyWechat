package org.easywechat;

import org.easywechat.model.WechatMsgModel;
import org.easywechat.util.JsoupUtil;
import org.jsoup.nodes.Document;

public abstract class BaseHandler implements MessageHandler{
	
	protected WechatMsgModel baseModel;
	
	public WechatMsgModel handleMessage(Document doc) {
		baseHandle(doc);
		return handle(doc);
	}
	
	protected void baseHandle(Document doc){
		if(doc != null) {
			baseModel = new WechatMsgModel();
			baseModel.setFromUserName(JsoupUtil.getTagContent(doc, WechatTags.FROM_USER_NAME));
			baseModel.setToUserName(JsoupUtil.getTagContent(doc, WechatTags.TO_USER_NAME));
			baseModel.setCreateTime(JsoupUtil.getTagContent(doc, WechatTags.CREATE_TIME));
			baseModel.setMsgId(JsoupUtil.getTagContent(doc, WechatTags.MSG_ID));
		}
	}
	
	protected abstract WechatMsgModel handle(Document doc);
	
	
}
