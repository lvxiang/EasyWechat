package org.easywechat.model;

public class WechatMsgModel {
	
	public static enum MessageType {
		TEXT, NEWS, EVENT, LOCATION,
		UNKNOWN
	}
	
	public static final String TYPE_TEXT     = "text";
	public static final String TYPE_LOCATION = "location";
	public static final String TYPE_EVENT    = "event";
	public static final String TYPE_NEWS     = "news";
	
	public static final String EVENT_SUBSCRIBE   = "subscribe";
	public static final String EVENT_UNSUBSCRIBE = "unsubscribe";
	public static final String EVENT_SCAN        = "scan";
	public static final String EVENT_LOCATION    = "location";
	public static final String EVENT_CLICK       = "click";
	public static final String EVENT_VIEW        = "view";
	
	protected String toUserName;
	protected String fromUserName;
	protected String createTime;
	protected String msgId;
	protected MessageType type;
	
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	public String getTypeText(){
		switch(type) {
			case TEXT: return TYPE_TEXT;
			case NEWS: return TYPE_NEWS;
			case EVENT: return TYPE_EVENT;
		default:
			break;
		}
		return null;
	}
}
