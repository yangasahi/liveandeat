package com.yx.xml;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.yx.model.LocationItem;

public class LocationContentHandler extends DefaultHandler{
	private List<LocationItem> infos = null;
    private LocationItem LocationInfo = null;
    private String tagName = null;
    
    public LocationContentHandler(List<LocationItem> infos) {
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
		if(tagName.equals("biz")){
			LocationInfo = new LocationItem();
		}
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		String temp = new String(ch,start,length);
		if(tagName.equals("id")){
			LocationInfo.setId(temp);
		}else if(tagName.equals("name")){
			LocationInfo.setName(temp);
		}else if(tagName.equals("addr")){
		    LocationInfo.setAddr(temp);
		}else if(tagName.equals("tel")){
			LocationInfo.setTel(temp);
		}else if(tagName.equals("cate")){
			LocationInfo.setCate(temp);
		}else if(tagName.equals("rate")){
			LocationInfo.setRate(temp);
		}else if(tagName.equals("cost")){
			LocationInfo.setCost(temp);
		}else if(tagName.equals("desc")){
			LocationInfo.setDist(temp);
		}else if(tagName.equals("lng")){
//			LocationInfo.setLng(Double.parseDouble(temp));
		}else if(tagName.equals("lat")){
//			LocationInfo.setLat(Double.parseDouble(temp));
		}
		
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		if(qName.equals("biz")){
		     infos.add(LocationInfo);
		}
		tagName = "";
	}

	
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	

	public List<LocationItem> getInfos() {
		return infos;
	}

	public void setInfos(List<LocationItem> infos) {
		this.infos = infos;
	}

	
}
