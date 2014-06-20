package com.yx.livewhere;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yx.model.LocationItem;
import com.yx.model.UserEvaluate;

public class JsonUtils {

	public List<LocationItem> parseItemFromJson(String jsonData) {
		List<LocationItem> list = new ArrayList<LocationItem>();
		Type listType = new TypeToken<LinkedList<LocationItem>>() {
		}.getType();
		Gson gson = new Gson();
		LinkedList<LocationItem> items = gson.fromJson(jsonData, listType);
		for (Iterator iterator = items.iterator(); iterator.hasNext();) {
			LocationItem item = (LocationItem) iterator.next();
			list.add(item);
		}
		return list;
	}
	public List<UserEvaluate> parseUserFromJson(String jsonData) {
		List<UserEvaluate> list = new ArrayList<UserEvaluate>();
		Type listType = new TypeToken<LinkedList<UserEvaluate>>() {
		}.getType();
		Gson gson = new Gson();
		LinkedList<UserEvaluate> items = gson.fromJson(jsonData, listType);
		for (Iterator iterator = items.iterator(); iterator.hasNext();) {
			UserEvaluate item = (UserEvaluate) iterator.next();
			list.add(item);
		}
		return list;
	}
}
