package org.dt.easywechat.model;

public class EventMsgModel extends WechatMsgModel{

	private String event;
	private EventType eventType;
	
	private String eventKey;
	private String ticket;
	
	private String longitude;
	private String latitude;
	private String pricision;
	
	public static enum EventType{
		SUBSCRIBE, UNSUBSCRIBE, SCAN, SCAN_SUBSCRIBE,
		LOCATION, CLICK, VIEW
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getPricision() {
		return pricision;
	}

	public void setPrecision(String pricision) {
		this.pricision = pricision;
	}
}
