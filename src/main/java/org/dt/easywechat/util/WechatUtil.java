package org.dt.easywechat.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.dt.easywechat.model.WechatMsgModel;
import org.dt.easywechat.model.WechatMsgModel.MessageType;

public class WechatUtil {

	/***
	 * 验证微信消息来源是否合法，根据官方文档中的描述进行消息验证
	 * @return
	 */
	public static boolean validateMsg(String token, String timestamp, String nonce, String signature){
		List<String> array = new ArrayList<String>();
		array.add(token);
		array.add(timestamp);
		array.add(nonce);
		Collections.sort(array);
		
		StringBuilder builder = new StringBuilder();
		builder.append(array.get(0));
		builder.append(array.get(1));
		builder.append(array.get(2));
		String str = builder.toString();
		
		String sig = DigestUtils.sha1Hex(str);
		return sig != null && sig.equals(signature);
	}
	
	public static MessageType convertFromStringToType(String type){
		if(WechatMsgModel.TYPE_TEXT.equals(type))
			return MessageType.TEXT;
		if(WechatMsgModel.TYPE_EVENT.equals(type))
			return MessageType.EVENT;
		if(WechatMsgModel.TYPE_LOCATION.equals(type))
			return MessageType.LOCATION;
		if(WechatMsgModel.TYPE_NEWS.equals(type))
			return MessageType.NEWS;
		return MessageType.UNKNOWN;
	}
}
