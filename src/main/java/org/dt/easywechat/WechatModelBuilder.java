/*
 * Copyright 2014 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package org.dt.easywechat;

import java.util.Collection;

import org.dt.easywechat.model.NewsMsgModel;
import org.dt.easywechat.model.TextMsgModel;
import org.dt.easywechat.model.WechatMsgModel;
import org.dt.easywechat.model.NewsMsgModel.Article;

/**
 * 绫籛echatModelBuilder.java鐨勫疄鐜版弿杩帮細TODO 绫诲疄鐜版弿杩�
 * @author lvxiang Sep 22, 2014 7:50:24 PM
 */
public class WechatModelBuilder {

    
    
    private WechatModelBuilder(){}
    
    public static WechatModelBuilder createBuilder(){
        return new WechatModelBuilder();
    }
    
    public TextMsgBuilder createTextMsg(){
        return new TextMsgBuilder(new TextMsgModel());
    }
    
    public NewsMsgBuilder createNewsMsg(){
        return new NewsMsgBuilder(new NewsMsgModel());
    }
    
    private abstract class AbstractMsgBuilder{
    	protected WechatMsgModel model;
    	
    	protected AbstractMsgBuilder(WechatMsgModel model){
    		this.model = model;
    	}
    	
    	public WechatMsgModel build(){
    		return model;
    	}
    }
    
    public class TextMsgBuilder extends AbstractMsgBuilder{

        
        protected TextMsgBuilder(WechatMsgModel model) {
			super(model);
			// TODO Auto-generated constructor stub
		}

		public TextMsgBuilder setFromUserName(String str){
            model.setFromUserName(str);
            return this;
        }
        
        public TextMsgBuilder setToUserName(String toUserName){
            model.setToUserName(toUserName);
            return this;
        }
        
        public TextMsgBuilder setCreateTime(String createTime){
            model.setCreateTime(createTime);
            return this;
        }
        
        public TextMsgBuilder setContent(String content){
            ((TextMsgModel) model).setContent(content);
            return this;
        }
        
    }
    
    public class NewsMsgBuilder extends AbstractMsgBuilder{
        
        protected NewsMsgBuilder(WechatMsgModel model) {
			super(model);
		}
        
        public NewsMsgBuilder setFromUserName(String str){
            model.setFromUserName(str);
            return this;
        }
        
        public NewsMsgBuilder setToUserName(String toUserName){
            model.setToUserName(toUserName);
            return this;
        }
        
        public NewsMsgBuilder setCreateTime(String createTime){
            model.setCreateTime(createTime);
            return this;
        }
        
        public NewsMsgBuilder addArticle(Article article){
            ((NewsMsgModel) model).addArticle(article);
            return this;
        }
        
        public NewsMsgBuilder addArticle(String title, String description, String picUrl, String url){
            ((NewsMsgModel) model).addArticle(title, description, picUrl, url);
            return this;
        }
        
        public <T> NewsMsgBuilder each(Collection<T> coll, NewsMsgCallback<? super T> callback) {
        	int i = 0;
        	for(T t: coll) {
        		callback.execute(i, this, t);
        		i ++;
        	}
        	return this;
        }
    }
    

}
