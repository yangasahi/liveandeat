package com.yx.livewhere;

import java.util.List;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.Overlay;
import com.baidu.mapapi.OverlayItem;
import com.baidu.mapapi.Projection;
import com.yx.model.LocationItem;
import com.yx.util.MyApplication;
import com.yx.util.MyItemsOverLay;
import com.yx.util.Mylay;

public class MymapActivity extends MapActivity {

	private GeoPoint geoPoint = null;

	MapView mMapView = null;

	LocationListener mLocationListener = null;// onResume时注册此listener，onPause时需要Remove
	Mylay mLocationOverlay = null; // 定位图层
	MKSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用

	MKPoiResult mRes = null; // poi检索结果
	private ImageButton Nearbybutton = null;

//	private LocationItemList ItemInfos;
	private List<LocationItem>LocationItems;


	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题
		setContentView(R.layout.map);
		Intent intent = getIntent();
		LocationItems = (List<LocationItem>) intent.getSerializableExtra("items");
		Nearbybutton = (ImageButton) findViewById(R.id.nearButton);

		Nearbybutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MymapActivity.this.finish();
				overridePendingTransition(R.anim.out, R.anim.out_to_right);
			}
		});

		final Drawable marker = MymapActivity.this.getResources().getDrawable(
				R.drawable.itembg2);// 创建图标资源（用于显示在overlayItem所标记的位置）
		// 为maker定义位置和边界
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());

		MyApplication app = (MyApplication) this.getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(getApplication());
			app.mBMapManager.init(app.strKey,
					new MyApplication.MyGeneralListener());
		}
		app.mBMapManager.start();
		// 如果使用地图SDK，请初始化地图Activity
		super.initMapActivity(app.mBMapManager);

		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.setBuiltInZoomControls(true);
		// 设置在缩放动画过程中也显示overlay,默认为不绘制
		mMapView.setDrawOverlayWhenZooming(true);
		mMapView.setBuiltInZoomControls(true);
		mMapView.setLongClickable(true);
		mMapView.getController().setZoom(16);

		// 添加定位图层
		mLocationOverlay = new Mylay(this, mMapView, geoPoint);
		mMapView.getOverlays().add(mLocationOverlay);
		MyItemsOverLay overlays = new MyItemsOverLay(marker,MymapActivity.this,LocationItems);
		for(int i = 0;i<LocationItems.size();i++){
			LocationItem locationItem = LocationItems.get(i);
			GeoPoint ptitem = new GeoPoint((int)(Double.parseDouble(locationItem.getLat())* 1E6), (int)(Double.parseDouble(locationItem.getLng())*1E6));
		OverlayItem overlayItem = new OverlayItem(ptitem, locationItem.getName(), locationItem.getId());
		overlays.addOverlay(overlayItem);
		}
		mMapView.getOverlays().add(overlays);
		// mMapView.getOverlays().add(new zidingyi());
		 mMapView.invalidate();
		// 注册定位事件
		mLocationListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {
					GeoPoint pt = new GeoPoint(
							(int) (location.getLatitude() * 1e6),
							(int) (location.getLongitude() * 1e6));
					mMapView.getController().animateTo(pt);
				}
			}
		};
	}

	@Override
	protected void onPause() {
		MyApplication app = (MyApplication) this.getApplication();
		app.mBMapManager.getLocationManager().removeUpdates(mLocationListener);
		mLocationOverlay.disableMyLocation();
		mLocationOverlay.disableCompass(); // 关闭指南针
		app.mBMapManager.stop();
		super.onPause();
	}



	@Override
	protected void onResume() {
		MyApplication app = (MyApplication) this.getApplication();
		// 注册定位事件，定位后将地图移动到定位点
		app.mBMapManager.getLocationManager().requestLocationUpdates(
				mLocationListener);
		mLocationOverlay.enableMyLocation();
		mLocationOverlay.enableCompass(); // 打开指南针
		app.mBMapManager.start();
		super.onResume();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	public class zidingyi extends Overlay {

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			// TODO Auto-generated method stub
			super.draw(canvas, mapView, shadow);
			Paint paint = new Paint();
			paint.setColor(Color.BLUE);
			paint.setDither(true);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeCap(Paint.Cap.ROUND);
			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeWidth(5);
			Projection projection = mapView.getProjection();

			Point p1 = new Point();
			GeoPoint sad = new GeoPoint((int) (43.826691 * 1e6),
					(int) (125.282532 * 1e6));
			projection.toPixels(sad, p1);
			canvas.drawCircle(p1.x, p1.y,
					projection.metersToEquatorPixels(1000000), paint);
		}

	}

}