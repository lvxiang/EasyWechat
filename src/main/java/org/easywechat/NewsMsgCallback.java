package org.easywechat;

import org.easywechat.WechatModelBuilder.NewsMsgBuilder;

public abstract class NewsMsgCallback<T> {

	public abstract void execute(NewsMsgBuilder builder, T obj);
	
}
