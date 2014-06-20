package com.yx.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.MyLocationOverlay;
import com.baidu.mapapi.Projection;

public class Mylay extends MyLocationOverlay{


	
	public Mylay(Context arg0, MapView arg1,GeoPoint pt) {
		super(arg0, arg1);
	}




	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// TODO Auto-generated method stub
		super.draw(canvas, mapView, shadow);
		
		shadow = true;
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setDither(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeWidth(5);
		Projection projection = mapView.getProjection();
		
		Point p1 = new Point();
		GeoPoint sad = new GeoPoint((int)(43.826691*1e6),
				(int)(125.282532*1e6));
		projection.toPixels(sad, p1);
		canvas.drawCircle(p1.x, p1.y, projection.metersToEquatorPixels(1000000), paint);
	}


	@Override
	protected void drawMyLocation(Canvas canvas, MapView mapView, Location lastFix,
			GeoPoint myLocation, long when) {
		// TODO Auto-generated method stub
//		 String filepath = "/sdcard/TryMeBack/iconmarka.png";
//		Bitmap	bitmap = BitmapFactory.decodeFile(filepath);
//		try {  
//            Projection projection = mapView.getProjection();  
//            Point point = new Point();  
//            projection.toPixels(myLocation, point);  
//  
//            int x = point.x - bitmap.getWidth() / 2;  
//            int y = point.y - bitmap.getHeight();  
//            canvas.drawBitmap(bitmap, x, y, new Paint());  
//  
//        } catch (Exception e) {  
            super.drawMyLocation(canvas, mapView, lastFix, myLocation, when);  
    //    }  
	}

}
