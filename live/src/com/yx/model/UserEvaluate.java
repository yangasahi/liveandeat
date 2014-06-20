package com.yx.model;

import java.io.Serializable;

public class UserEvaluate implements Serializable{

	private static final long serialVersionUID = 1L;

	private String uid;
	private String uname;
	private String avatar_url;
	private String space_url;
	private String pubtime;
	private String score;
	private String cost;
	private String content;
	
	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	public String getPubtime() {
		return pubtime;
	}
	public void setPubtime(String pubtime) {
		this.pubtime = pubtime;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getSpace_url() {
		return space_url;
	}
	public void setSpace_url(String space_url) {
		this.space_url = space_url;
	}
	
	
	
}
