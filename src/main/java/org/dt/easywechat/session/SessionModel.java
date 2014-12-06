package org.dt.easywechat.session;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.dt.easywechat.Wechat;
import org.dt.easywechat.model.EventMsgModel;
import org.dt.easywechat.model.TextMsgModel;
import org.dt.easywechat.model.WechatMsgModel;

public class SessionModel implements Cloneable{
	private String id;
	private String type;
	private String match;
	private String className;
	private String function;
	private Map<String, Condition> conditions;
	private Condition mismatch;
	private Pattern pattern;
	private boolean enter;
	private boolean needResult;
	private SessionChain chain;
	private Map<Object, Object> data;
	
	private long lastUpdate;
	
	public SessionModel(){
		this.conditions = new HashMap<String, Condition>();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
		pattern = Pattern.compile(match);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public void addCondition(String value, String target, String result){
		
		this.conditions.put(value, new Condition(value, target, result));
	}
	
	public Condition getCondition(String condition){
		return this.conditions.get(condition);
	}
	
	public Condition getMismatch() {
		return mismatch;
	}

	public void setMismatch(String target, String result) {
		this.mismatch = new Condition(null, target, result);
	}

	public synchronized void update(long time){
		this.lastUpdate = time;
	}
	
	public synchronized long getLastUpdate(){
		return this.lastUpdate;
	}
	
	public boolean isEnter() {
		return enter;
	}

	public void setEnter(boolean enter) {
		this.enter = enter;
	}

	public boolean isNeedResult() {
		return needResult;
	}

	public void setNeedResult(boolean needResult) {
		this.needResult = needResult;
	}

	public SessionChain getChain() {
		return chain;
	}

	public void setChain(SessionChain chain) {
		this.chain = chain;
	}

	protected Map<Object, Object> getData(){
		return data;
	}

	protected void setData(Map<Object, Object> data) {
		this.data = data;
	}
	
	@Override
	public SessionModel clone(){
		try {
			return (SessionModel) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 判断是否符合当前SessionModel定义的规则，目前只支持text和event类型的消息
	 * @param model
	 * @return
	 */
	public boolean matches(WechatMsgModel model){
		if(this.type.equals(model.getTypeText())) {
			switch(model.getType()) {
			case TEXT: 
				return pattern.matcher(((TextMsgModel) model).getContent()).matches();
			case EVENT: 
				return pattern.matcher(((EventMsgModel) model).getEvent()).matches();
			default:
				break;
			}
		}
		return false;
	}
	
	public class Condition{
		private String value;
		private String target;
		private String result;
		private boolean needResult;
		
		public Condition(String value, String target, String result){
			this.setValue(value);
			this.setTarget(target);
			this.setResult(result);
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getTarget() {
			return target;
		}

		public void setTarget(String target) {
			this.target = target;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
			if(result != null && result.equals(Wechat.KEY_RESULT_MSG))
				this.needResult = true;
		}

		public boolean isNeedResult() {
			return needResult;
		}

		public void setNeedResult(boolean needResult) {
			this.needResult = needResult;
		}
	}
}