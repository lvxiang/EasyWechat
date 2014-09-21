package org.easywechat.parser;

import org.easywechat.model.EventMsgModel;
import org.easywechat.model.EventMsgModel.EventType;
import org.easywechat.model.WechatMsgModel;
import org.easywechat.util.JsoupUtil;
import org.jsoup.nodes.Document;

public class EventMsgParser implements MessageParser{

	@Override
	public WechatMsgModel parse(Document doc, WechatMsgModel model) {
		EventMsgModel eve = (EventMsgModel) model;
		String event = JsoupUtil.getTagContent(doc, WechatMsgTags.EVENT);
		eve.setEvent(event);
		if(WechatMsgModel.EVENT_SUBSCRIBE.equals(event)) {
			String eventKey = JsoupUtil.getTagContent(doc, WechatMsgTags.EVENT_KEY);
			if(eventKey == null) {
				eve.setEventType(EventType.SUBSCRIBE);
			} else {
				String ticket = JsoupUtil.getTagContent(doc, WechatMsgTags.TICKET);
				eve.setEventKey(eventKey);
				eve.setTicket(ticket);
				eve.setEventType(EventType.SCAN_SUBSCRIBE);
			}
			return eve;
		}
		
		if(WechatMsgModel.EVENT_UNSUBSCRIBE.equals(event)) {
			eve.setEventType(EventType.UNSUBSCRIBE);
			return eve;
		}
		
		if(WechatMsgModel.EVENT_SCAN.equals(event)) {
			eve.setEventType(EventType.SCAN);
			String eventKey = JsoupUtil.getTagContent(doc, WechatMsgTags.EVENT_KEY);
			String ticket = JsoupUtil.getTagContent(doc, WechatMsgTags.TICKET);
			eve.setEventKey(eventKey);
			eve.setTicket(ticket);
			return eve;
		}
		
		if(WechatMsgModel.EVENT_LOCATION.equals(event)) {
			eve.setEventType(EventType.LOCATION);
			String longitude = JsoupUtil.getTagContent(doc, WechatMsgTags.LATITUDE);
			String latitude  = JsoupUtil.getTagContent(doc, WechatMsgTags.LATITUDE);
			String precision = JsoupUtil.getTagContent(doc, WechatMsgTags.PRECISION);
			eve.setLatitude(latitude);
			eve.setLongitude(longitude);
			eve.setPrecision(precision);
			return eve;
		}
		
		if(WechatMsgModel.EVENT_CLICK.equals(event)) {
			eve.setEventType(EventType.CLICK);
			String eventKey = JsoupUtil.getTagContent(doc, WechatMsgTags.EVENT_KEY);
			eve.setEventKey(eventKey);
			return eve;
		}
		
		if(WechatMsgModel.EVENT_VIEW.equals(event)) {
			eve.setEventType(EventType.VIEW);
			String eventKey = JsoupUtil.getTagContent(doc, WechatMsgTags.EVENT_KEY);
			eve.setEventKey(eventKey);
			return eve;
		}
		return null;
	}

}
