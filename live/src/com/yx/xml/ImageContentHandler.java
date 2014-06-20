package com.yx.xml;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.yx.model.ImageItem;
import com.yx.model.LocationItem;

public class ImageContentHandler extends DefaultHandler{
	private List<ImageItem> infos = null;
    private ImageItem ImageInfo = null;
    private String tagName = null;
    private String total = null;
	private StringBuilder sb = new StringBuilder();
    
    public ImageContentHandler(List<ImageItem> infos) {
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
		if(tagName.equals("pic")){
			ImageInfo = new ImageItem();
		}
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		if(tagName.equals("avatar_url")){
			sb.delete(0, sb.length());
			for(int i=0;i<length;i++){
				sb.append(ch[i]);
			}
			ImageInfo.setAvatar_url(sb.toString());
		}else if(tagName.equals("url")){
			sb.delete(0, sb.length());
			for(int i=0;i<length;i++){
				sb.append(ch[i]);
			}
			ImageInfo.setUrl(sb.toString());
		}else if(tagName.equals("thumbnail_url")){
			sb.delete(0, sb.length());
			for(int i=0;i<length;i++){
				sb.append(ch[i]);
			}
			ImageInfo.setThumbnail_url(sb.toString());
		}
		String temp = new String(ch, start, length);
		if (tagName.equals("total")) {
			total = temp;
		} else if (tagName.equals("uid")) {
			ImageInfo.setTotal(total);
			ImageInfo.setUid(temp);
		} else if (tagName.equals("uname")) {
			ImageInfo.setUname(temp);
		} else if (tagName.equals("pubtime")) {
			ImageInfo.setPubtime(temp);
		} else if (tagName.equals("title")) {
			ImageInfo.setTitle(temp);
		} 
		
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		if(qName.equals("pic")){
		     infos.add(ImageInfo);
		}
		tagName = "";
	}

	
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	

	public List<ImageItem> getInfos() {
		return infos;
	}

	public void setInfos(List<ImageItem> infos) {
		this.infos = infos;
	}

}
