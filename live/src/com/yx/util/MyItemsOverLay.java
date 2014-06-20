package com.yx.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.OverlayItem;
import com.baidu.mapapi.Projection;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;
import com.yx.model.LocationItem;

public class MyItemsOverLay extends ItemizedOverlay<OverlayItem>{
      
	private List<OverlayItem>overlayItemList = new ArrayList<OverlayItem>();
	private Context context;
	String temp1 = null;
	private List<LocationItem> locationItemList = new ArrayList<LocationItem>();
	private LocationItem MyItem = null;
	private ParseUser currentUser = null;
	
	public MyItemsOverLay(Drawable marker,Context context, List<LocationItem> locationItemList ){
		super(boundCenterBottom(marker));
		this.context=context;
		this.locationItemList = locationItemList;
	}
	
	public MyItemsOverLay(Drawable defaultMarker) {
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
		// Projection接口用于屏幕像素点坐标系统和地球表面经纬度点坐标系统之间的变换 
        Projection projection = mapView.getProjection();  
        // 遍历所有的OverlayItem  
        for (int index = this.size() - 1; index >= 0; index--) {  
            // 得到给定索引的item  
            OverlayItem overLayItem = getItem(index);  
  
            // 把经纬度变换到相对于MapView左上角的屏幕像素坐标  
            Point point = projection.toPixels(overLayItem.getPoint(), null);  
            Paint paintText = new Paint();
            String familyName = "宋体";
            paintText.setColor(Color.rgb(255, 154, 0));
            paintText.setFakeBoldText(true);
            Typeface font = Typeface.create(familyName, Typeface.BOLD);
            paintText.setTextSize(17);  
            paintText.setTypeface(font);
            
            char[] ss =overLayItem.getTitle().toCharArray();
            String temp = new String(ss, 0, 2);
            if(temp.equals("长春")){
            	if(temp.length()>=7){
            	temp1 = new String(ss, 2, 5);
            	}else{
            		temp1 = new String(ss,0,ss.length);
            	}
            }else{
            	if(temp.length()>=5){
            	     temp1 = new String(ss, 0, 5);
            	}else{
            		temp1 = new String(ss,0,ss.length);
            	}
            }
            
			canvas.drawText(temp1, point.x-45, point.y-22, paintText);
        }  
	}

	@Override
	protected boolean onTap(int i) {
		// TODO Auto-generated method stub
		Parse.initialize(context, "IlQ8wBml4IkepGG0tuphk2VDIAfTuvcXX9OJOJfh", "NMcva8057XBA0mdRwlLKvLGXxL9uzQABVFAaVbyO");
		ParseAnalytics.trackAppOpened(null);
		setFocus(overlayItemList.get(i));  
		MyItem = locationItemList.get(i);
		System.out.println("liznegkun--->" + MyItem.getName());
        Toast.makeText(this.context, overlayItemList.get(i).getSnippet(), Toast.LENGTH_SHORT).show();  
//        for(int ii = 0;ii<locationItemList.size();ii++){
//        	MyItem = locationItemList.get(ii);
//        	if(locationItemList.get(ii).getId().equals(overlayItemList.get(i).getSnippet())){
//        		
//        		currentUser = BmobUser.getCurrentUser();
//				if (currentUser != null) {
//					BmobQuery query = new BmobQuery("favorite");
//					query.whereEqualTo("BusinessID", MyItem.getId());
//					query.whereEqualTo("username", currentUser.getUsername().toString());
//					System.out.println("zhangbin--->" + MyItem.getId() + "  " + currentUser.getUsername().toString());
//					query.findInBackground(new FindCallback() {
//
//						@Override
//						public void done(List<BmobObject> arg0, BmobException e) {
//							// TODO Auto-generated method stub
//							if (e == null) {
//								if(arg0.size() == 0){
//									System.out.println("nearBy--->" + "not is");
//									Intent intent2 = new Intent();
//									intent2.putExtra("MyItem", MyItem);
//									intent2.putExtra("username", currentUser.getUsername().toString());
//									intent2.putExtra("isFavor", "not");
//									intent2.putExtra("isLogin", "is");
//									intent2.setClass(context, ItemActivity.class);
//									context.startActivity(intent2);
//								}else{
//								System.out.println("gaosiwei--->" + "isis");
//								Intent intent = new Intent();
//								intent.putExtra("MyItem", MyItem);
//								intent.putExtra("username", currentUser.getUsername().toString());
//								intent.putExtra("isFavor", "is");
//								intent.putExtra("isLogin", "is");
//								intent.setClass(context, ItemActivity.class);
//								context.startActivity(intent);
//								}
//								System.out.println("yangxu成功--->" + arg0.size());
//							} else {
//								Intent intent2 = new Intent();
//								intent2.putExtra("MyItem", MyItem);
//								intent2.putExtra("username", currentUser.getUsername().toString());
//								intent2.putExtra("isFavor", "not");
//								intent2.putExtra("isLogin", "is");
//								intent2.setClass(context, ItemActivity.class);
//								context.startActivity(intent2);
//								Log.d("失败", "查询错误: " + e.getMessage());
//							}
//
//						}
//					});
//				 
//				} else {
//					Intent intent3 = new Intent();
//					intent3.putExtra("isFavor", "not");
//					intent3.putExtra("isLogin", "not");
//					intent3.putExtra("MyItem", MyItem);
//					intent3.setClass(context, ItemActivity.class);
//					context.startActivity(intent3);
//				}
//        		
//        		
//        		
//        		
//        	}
//        }
        return true; 
	}

	
	
	
}
