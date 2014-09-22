/*
 * Copyright 2014 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package org.easywechat;

import org.easywechat.model.TextMsgModel;
import org.easywechat.model.WechatMsgModel;
import org.easywechat.model.WechatMsgModel.MessageType;
import org.easywechat.util.WechatUtil;

/**
 * 类WechatModelBuilder.java的实现描述：TODO 类实现描述 
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
    
    public TextMsgBuilder createNewsMsg(){
        
    }
    
    public class TextMsgBuilder{

        private WechatMsgModel model;
        
        private TextMsgBuilder(WechatMsgModel model){
            this.model = model;
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
    
    public class NewsMsgBuilder{
        
        private WechatMsgModel model;
        
        private NewsMsgBuilder(WechatMsgModel model){
            this.model = model;
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
        
        public NewsMsgBuilder addArticle(){
            
            return this;
        }
        
        public NewsMsgBuilder addArticle(String title, String description, String picUrl, String url){
            
            return this;
        }
    }
}
