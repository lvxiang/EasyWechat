package org.easywechat;

import java.io.InputStream;
import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.easywechat.model.WechatMsgModel;
import org.easywechat.parser.WechatMsgParser;
import org.easywechat.session.SessionManager;

public class Wechat {

	public static final String KEY_RESULT_MSG = "$MSG$";
	
	private static VelocityEngine ve;
	private static SessionManager sessionManager;
	
	public static void initSession(String filename, int expire){
		if(filename != null) {
			if(sessionManager == null) {
				InputStream in = Wechat.class.getClassLoader().getResourceAsStream(filename);
				sessionManager = SessionManager.newInstance(in, expire);
				
			} else {
				throw new IllegalStateException("A session manager already exists!");
			}
		}
	}
	
	public static SessionManager getSessionManager(){
		return sessionManager;
	}
	
	public static WechatMsgModel parseMsg(String msg){
		return WechatMsgParser.parseMsg(msg);
	}
	
	public static String writeMsg(WechatMsgModel model, String encoding){
		if(ve == null) {
			ve = new VelocityEngine();
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			ve.init();
		}
		
		VelocityContext context = new VelocityContext();
		context.put("model", model);
		
		StringWriter w = new StringWriter();
		ve.mergeTemplate("META-INF/velocity/base.vm", encoding, context, w);
		return w.getBuffer().toString();
	}
	
	/***
	 * 根据收到的文本消息和需要返回的文本消息，创建标准的文本消息回复，
	 * 时间使用系统时间
	 * @param received 收到的文本消息
	 * @param content 需要回复的文本内容
	 * @return
	 * @see genStdTextModel(TextMsgModel received, String content, long time)
	 */
	public static WechatMsgModel genStdTextModel(WechatMsgModel received, String content){
		return genStdTextModel(received, content, System.currentTimeMillis());
	}
	
	/**
	 * 根据收到的文本消息和需要返回的文本消息，创建标准的文本消息回复，
	 * 时间使用系统时间
	 * @param received 收到的文本消息
	 * @param content 需要回复的文本内容
	 * @param time 指定创建时间
	 * @return
	 */
	public static WechatMsgModel genStdTextModel(WechatMsgModel received, String content, long time){
		return WechatModelBuilder
				.createBuilder()
				.createTextMsg()
				.setFromUserName(received.getToUserName())
				.setToUserName(received.getFromUserName())
				.setCreateTime(String.valueOf(time))
				.setContent(content)
				.build();
	}
	
}