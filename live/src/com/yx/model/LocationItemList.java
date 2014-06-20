package com.yx.model;

import java.io.Serializable;
import java.util.List;

public class LocationItemList implements Serializable{


	private static final long serialVersionUID = 1765454534930318013L;
	private List<LocationItem> locationItems;
	private String zzz;

	public List<LocationItem> getLocationItems() {
		return locationItems;
	}

	public void setLocationItems(List<LocationItem> locationItems) {
		this.locationItems = locationItems;
	}

	public String getZzz() {
		return zzz;
	}

	public void setZzz(String zzz) {
		this.zzz = zzz;
	}
	
}
