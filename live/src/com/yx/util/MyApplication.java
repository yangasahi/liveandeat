package com.yx.util;

import android.app.Application;
import android.content.Context;
import android.os.Vibrator;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;

public class MyApplication extends Application {

	static MyApplication mDemoApp;
	private static MyApplication mInstance = null;
    public boolean m_bKeyRight = true;
    public BMapManager mBMapManager = null;
    public static final String strKey = "BE4AAD31F37798D690B9F31F306A408E955D2601";
	
	public LocationClient mLocationClient = null;
	private String mData;  
	public MyLocationListenner myListener = new MyLocationListenner();
	public TextView locateTextView;
	public NotifyLister mNotifyer=null;
	public Vibrator mVibrator01;
	public double jingdu;
	public double weidu;
	public String city;
	
	@Override
	public void onCreate() {
		mLocationClient = new LocationClient( getApplicationContext() );
		mLocationClient.registerLocationListener( myListener );
		
		mDemoApp = this;
		mBMapManager = new BMapManager(this);
		boolean isSuccess = mBMapManager.init(this.strKey , new MyGeneralListener());
		// ｳ�ﾊｼｻｯｵﾘﾍｼsdkｳﾉｹｦ｣ｬﾉ靹ﾃｶｨﾎｻｼ猯�ﾊｱｼ�
		if (isSuccess) {
			mBMapManager.getLocationManager().setNotifyInternal(60, 50);
		}
		else {
		    // ｵﾘﾍｼsdkｳ�ﾊｼｻｯﾊｧｰﾜ｣ｬｲｻﾄﾜﾊｹﾓﾃsdk
		}
		
		super.onCreate(); 
		mInstance = this;
		initEngineManager(this);
	}
	
	@Override
	//ｽｨﾒ鰆ﾚﾄ�appｵﾄﾍﾋｳ�ﾖｮﾇｰｵ�ﾓﾃmapadpiｵﾄdestroy()ｺｯﾊ�｣ｬｱﾜﾃ籔ﾘｸｴｳ�ﾊｼｻｯｴ�ﾀｴｵﾄﾊｱｼ蔆�ｺﾄ
	public void onTerminate() {
		// TODO Auto-generated method stub
	    if (mBMapManager != null) {
            mBMapManager.destroy();
            mBMapManager = null;
        }
		super.onTerminate();
	}
	
	public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(strKey,new MyGeneralListener())) {
//            Toast.makeText(MyApplication.getInstance().getApplicationContext(), 
//                    "BMapManager  ｳ�ﾊｼｻｯｴ��!", Toast.LENGTH_LONG).show();
        }
	}
	public static MyApplication getInstance() {
		return mInstance;
	}
	// ｳ｣ﾓﾃﾊﾂｼ�ｼ猯�｣ｬﾓﾃﾀｴｴｦﾀ�ｨｳ｣ｵﾄﾍ�ﾂ邏��｣ｬﾊﾚﾈｨﾑ鰒､ｴ��ｵﾈ
    public static class MyGeneralListener implements MKGeneralListener {
        
        @Override
        public void onGetNetworkState(int iError) {
//            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
//                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "ﾄ�ｵﾄﾍ�ﾂ邉�ｴ�ｲ｣｡",
//                    Toast.LENGTH_LONG).show();
//            }
//            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
//                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "ﾊ菠��ﾈｷｵﾄｼ��ﾌ�ｼ�｣｡",
//                        Toast.LENGTH_LONG).show();
//            }
            // ...
        }

        @Override
        public void onGetPermissionState(int iError) {
//            if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
//                //ﾊﾚﾈｨKeyｴ��｣ｺ
//                Toast.makeText(MyApplication.getInstance().getApplicationContext(), 
//                        "ﾇ�ﾚ DemoApplication.javaﾎﾄｼ�ﾊ菠��ﾈｷｵﾄﾊﾚﾈｨKey｣｡", Toast.LENGTH_LONG).show();
//                MyApplication.getInstance().m_bKeyRight = false;
//            }
        }
    }
	/**
	 * ﾏﾔﾊｾﾗﾖｷ�ｴｮ
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			mData = str;
			if ( locateTextView != null )
				locateTextView.setText("ｵｱﾇｰ｣ｺ" + mData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ｼ猯�ｺｯﾊ�｣ｬﾓﾖﾐﾂﾎｻﾖﾃｵﾄﾊｱｺ�｣ｬｸ�ﾊｽｻｯｳﾉﾗﾖｷ�ｴｮ｣ｬﾊ莎�ｵｽﾆﾁﾄｻﾖﾐ
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return ;

			jingdu = location.getLongitude();
			weidu = location.getLatitude();
			city = location.getCity();
			System.out.println("");
			logMsg(location.getCity());
		}
		
		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null){
				return ;
			}
			
		}
	}
	

	/**
	 * ﾎｻﾖﾃﾌ瞎ﾑｻﾘｵ�ｺｯﾊ�
	 */
	public class NotifyLister extends BDNotifyListener{
		public void onNotify(BDLocation mlocation, float distance){
			mVibrator01.vibrate(1000);
		}
	}
}