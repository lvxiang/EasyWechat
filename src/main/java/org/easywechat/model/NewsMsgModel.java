package org.easywechat.model;

import java.util.ArrayList;
import java.util.List;

public class NewsMsgModel extends WechatMsgModel{

	private int articleCount = 0;
	private List<Article> articles;
	
	public NewsMsgModel(){
		this.type = MessageType.NEWS;
		this.articles = new ArrayList<Article>();
	}
	
	public void addArticle(Article article){
	    this.articles.add(article);
	}
	
	public void addArticle(String title, String desc, String picUrl, String url) {
	    this.addArticle(new Article(title, desc, picUrl, url));
	}
	
	public class Article {
		
		private String title;
		private String description;
		private String picUrl;
		private String url;
		
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getPicUrl() {
			return picUrl;
		}
		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
	}
	
}
