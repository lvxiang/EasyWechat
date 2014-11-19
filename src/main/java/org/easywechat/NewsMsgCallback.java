package org.easywechat;

import org.easywechat.WechatModelBuilder.NewsMsgBuilder;

public abstract class NewsMsgCallback<T> {

	/***
	 * 
	 * @param pos
	 * @param builder
	 * @param obj
	 */
	public abstract void execute(int pos, NewsMsgBuilder builder, T obj);
	
}
