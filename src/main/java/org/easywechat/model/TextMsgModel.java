package org.easywechat.model;

public class TextMsgModel extends WechatMsgModel{

	private String content;

	public TextMsgModel(){
		this.type = MessageType.TEXT;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
