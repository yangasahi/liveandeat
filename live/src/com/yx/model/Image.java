package com.yx.model;

import java.io.Serializable;

public class Image implements Serializable{

	private static final long serialVersionUID = 1L;

	private String result_num;//���صļ�¼����
	private String uid;//�û�id
	private String uname;//�û���
	private String avatar_url;//�û�ͷ���ַ
	private String pubtime;//����ʱ��
	private String url;//ͼƬ��url
	public String getResult_num() {
		return result_num;
	}
	public void setResult_num(String result_num) {
		this.result_num = result_num;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
