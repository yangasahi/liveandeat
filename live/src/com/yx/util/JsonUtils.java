package com.yx.util;

import java.io.StringReader;

import com.google.gson.stream.JsonReader;

public class JsonUtils {
	public void parseJson(String jsonData){
		try{
			//�����Ҫ����JSON���ݣ���ҪҪ����һ��JsonReader����
			JsonReader reader = new JsonReader(new StringReader(jsonData));
			reader.beginArray();
			while(reader.hasNext()){
				reader.beginObject();
				while(reader.hasNext()){
					String tagName = reader.nextName();
					if(tagName.equals("name")){
						System.out.println("name--->" + reader.nextString());
					}
					else if(tagName.equals("age")){
						System.out.println("age--->" + reader.nextInt());
					}
				}
				reader.endObject();
			}
			reader.endArray();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
