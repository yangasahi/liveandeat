package com.yx.xml;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.yx.model.UserEvaluate;

public class UserEvaluateHandler extends DefaultHandler {
	private List<UserEvaluate> infos = null;
	private UserEvaluate evaluateInfo = null;
	private String tagName = null;
	private String total = null;
	private StringBuilder sb = new StringBuilder();

	public UserEvaluateHandler(List<UserEvaluate> infos) {
		super();
		this.infos = infos;
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		this.tagName = localName;
		if (tagName.equals("comment")) {
			evaluateInfo = new UserEvaluate();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		if(tagName.equals("avatar_url")){
	//		sb.delete(0, sb.length());
			for(int i=start;i<length;i++){
				sb.append(ch[i]);
			}
			evaluateInfo.setAvatar_url(sb.toString());
		}else{
		String temp = new String(ch, start, length);
		if (tagName.equals("total")) {
			total = temp;
		} else if (tagName.equals("uid")) {
//			evaluateInfo.setTotal(total);
			evaluateInfo.setUid(temp);
		} else if (tagName.equals("uname")) {
			evaluateInfo.setUname(temp);
		} else if (tagName.equals("pubtime")) {
			evaluateInfo.setPubtime(temp);
		} else if (tagName.equals("score")) {
			evaluateInfo.setScore(temp);
		} else if (tagName.equals("cost")) {
			evaluateInfo.setCost(temp);
		} else if (tagName.equals("content")) {
			evaluateInfo.setContent(temp);
		}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		if (qName.equals("comment")) {
			infos.add(evaluateInfo);
		}
		tagName = "";
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub

	}

	public List<UserEvaluate> getInfos() {
		return infos;
	}

	public void setInfos(List<UserEvaluate> infos) {
		this.infos = infos;
	}
}
