package com.yx.livewhere;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.yx.util.MyApplication;

public class MyActivity  extends MapActivity{
	private ImageButton loginButton,registButton;//ｿﾘﾖﾆｵﾗｲｿTabﾌ�ﾗｪ
	MapView mMapView = null;
	private boolean logOut = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ﾃｻﾓﾐｱ��
		setContentView(R.layout.me);
		
		
		MyApplication app = (MyApplication) this.getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(getApplication());
			app.mBMapManager.init(app.strKey,
					new MyApplication.MyGeneralListener());
		}
		app.mBMapManager.start();
		// ﾈ郢�ﾊｹﾓﾃｵﾘﾍｼSDK｣ｬﾇ��ﾊｼｻｯｵﾘﾍｼActivity
		super.initMapActivity(app.mBMapManager);

		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.setBuiltInZoomControls(true);
		// ﾉ靹ﾃﾔﾚﾋ�ｷﾅｶｯｻｭｹ�ｳﾌﾖﾐﾒｲﾏﾔﾊｾoverlay,ﾄｬﾈﾏﾎｪｲｻｻ贍ﾆ
		mMapView.setDrawOverlayWhenZooming(true);
		mMapView.setBuiltInZoomControls(true);
		mMapView.setLongClickable(true);
		mMapView.getController().setZoom(4);
		mMapView.invalidate();
		
		loginButton = (ImageButton)findViewById(R.id.login_button);
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MyActivity.this, LoginActivity.class);
				MyActivity.this.startActivity(intent);
				finish();
			}
		});
		registButton = (ImageButton)findViewById(R.id.regist_button);
		registButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    Intent intent = new Intent();
			    intent.setClass(MyActivity.this, RegistActivity.class);
			    MyActivity.this.startActivity(intent);
			    MyActivity.this.finish();
			}
		});
		
	      
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK  && event.getRepeatCount() == 0) {
			
				MyActivity.this.finish();
			
		}
		return true;
	}
	


	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
