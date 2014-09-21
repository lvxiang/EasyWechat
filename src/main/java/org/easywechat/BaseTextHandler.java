package org.easywechat;

import org.easywechat.model.TextMsgModel;
import org.easywechat.model.WechatMsgModel;
import org.easywechat.util.JsoupUtil;
import org.jsoup.nodes.Document;

/***
 * 基本的文本消息处理类，会将用户发来的内容返回给用户
 * @author lvxiang
 *
 */
public class BaseTextHandler extends BaseHandler{

	@Override
	protected WechatMsgModel handle(Document doc) {
		if(doc != null && this.baseModel != null) {
			TextMsgModel msg = new TextMsgModel();
			msg.setFromUserName(baseModel.getToUserName());
			msg.setToUserName(baseModel.getFromUserName());
			msg.setCreateTime(String.valueOf(System.currentTimeMillis()));
			msg.setContent(JsoupUtil.getTagContent(doc, WechatTags.CONTENT));
			return msg;
		} 
		return null;
	}
	
}
