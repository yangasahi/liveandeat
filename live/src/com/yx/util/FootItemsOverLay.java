package com.yx.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.OverlayItem;
import com.parse.ParseObject;

public class FootItemsOverLay extends ItemizedOverlay<OverlayItem>{

	private Context context;
	private List<ParseObject> footprintItemList = new ArrayList<ParseObject>();
	private List<OverlayItem>overlayItemList = new ArrayList<OverlayItem>();
	
	public FootItemsOverLay(Drawable marker,Context context ,List<ParseObject> footprintItemList){
		super(boundCenterBottom(marker));
		this.context=context;
		this.footprintItemList = footprintItemList;
	}
	public FootItemsOverLay(Drawable marker,Context context ){
		super(boundCenterBottom(marker));
		this.context=context;
	}
	
	public FootItemsOverLay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return overlayItemList.get(i);
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return overlayItemList.size();
	}
	
	public void addOverlay(OverlayItem overlayItem){
		overlayItemList.add(overlayItem);
		this.populate();
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// TODO Auto-generated method stub
		super.draw(canvas, mapView, shadow);
	}

	@Override
	protected boolean onTap(int i) {
		// TODO Auto-generated method stub
		 Toast.makeText(this.context, overlayItemList.get(i).getSnippet(), Toast.LENGTH_SHORT).show();  
		 return true; 
	}
	
	
}
