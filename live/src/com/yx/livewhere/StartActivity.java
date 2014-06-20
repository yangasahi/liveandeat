package com.yx.livewhere;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.yx.util.MyApplication;

public class StartActivity extends Activity{

	private Thread myThread = null;
	private LocationClient mLocClient = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AdManager.getInstance(this).init("0b582d4cbfb5a99b", "b37350057a3a0f7d", false);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ﾃｻﾓﾐｱ��
		setContentView(R.layout.start);
		SpotManager.getInstance(this).loadSpotAds();
		SpotManager.getInstance(this).setSpotTimeout(4000);
		SpotManager.getInstance(this).showSpotAds(this);
		SpotManager.getInstance(this).showSpotAds(this, new SpotDialogListener() {
		    @Override
		    public void onShowSuccess() {
		        Log.i("Youmi", "onShowSuccess");
		    }

		    @Override
		    public void onShowFailed() {
		        Log.i("Youmi", "onShowFailed");
		    }
		});
		MyApplication app = (MyApplication) this.getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(getApplication());
			app.mBMapManager.init(app.strKey,
					new MyApplication.MyGeneralListener());
		}
		app.mBMapManager.start();

		mLocClient = ((MyApplication) getApplication()).mLocationClient;
		// ((MyApplication)getApplication()).locateTextView = locateTextView;
		mLocClient.start();

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setAddrType("all"); // 设置地址信息，仅设置为“all”时有地址信息，默认无地址信息
		option.setScanSpan(5000); // 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
		mLocClient.setLocOption(option);
		mLocClient.requestLocation();
		myThread = new Thread(new Runnable() {
			boolean flag = true;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(flag){
					try {
						Thread.sleep(7000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				Intent intent = new Intent();
				intent.setClass(StartActivity.this, MainingActivity.class);
				StartActivity.this.startActivity(intent);
				StartActivity.this.finish();
				}
				flag = false;
			}
		});
		myThread.start();
	}

}
