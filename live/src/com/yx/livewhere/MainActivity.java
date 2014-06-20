package com.yx.livewhere;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;
import com.parse.ParseUser;
import com.yx.util.AsyncImageLoader;
import com.yx.util.CallbackImpl;
import com.yx.util.MyApplication;
import com.yx.util.ShakeListener;
import com.yx.util.ShakeListener.OnShakeListener;

public class MainActivity extends Activity {


	private AsyncImageLoader loader = new AsyncImageLoader();
	private ImageButton mTab1,mTab5,backButton;//控制底部Tab跳转
	private TextView locateTextView,hostname1,tele1,price1,address1,hostname2,tele2,price2,address2,hostname3,tele3,price3,address3,hostname4,tele4,price4,address4,hostname5,tele5,price5,address5 = null;// 查找当前位置
	private ShakeListener shakeListener = null;
	private Vibrator mVibrator = null;
	private Thread netThread = null;
	private boolean isConnect = false;// 判断是否连接网络，若连接网络，则可以使用其他的功能
	private double longitude;// 当前位置的经度
	private double latitude;// 当前位置的维度
	private LocationClient mLocClient;
	private Button button = null;
	private ImageView imageView1,imageView2,imageView3,imageView4,imageView5= null;
	private String city = null;
	private ProgressBar progress = null;
	private RelativeLayout relar1,relar2,relar3,relar4,relar5 = null;
	private boolean isClickFlag = true; 
	private TextView hintText1,hintText2,hintText3,hintText4,hintText5 = null;
	private boolean logOut = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题
//		setContentView(R.layout.main);
		Parse.initialize(this, "IlQ8wBml4IkepGG0tuphk2VDIAfTuvcXX9OJOJfh", "NMcva8057XBA0mdRwlLKvLGXxL9uzQABVFAaVbyO");
		ParseAnalytics.trackAppOpened(null);
//	    if(!(isNetworkAvailable(MainActivity.this) || isWiFiActive(MainActivity.this))){
//        	Toast.makeText(MainActivity.this,
//					"无网络连接，请先启动网络！", Toast.LENGTH_LONG).show();
//        }else{
//        	backButton = (ImageButton)findViewById(R.id.img_tab_now);
//    		backButton.setOnClickListener(new OnClickListener() {
//    			
//    			@Override
//    			public void onClick(View v) {
//    				// TODO Auto-generated method stub
//    				Intent intent = new Intent();
//    				intent.setClass(MainActivity.this, SetActivity.class);
//    				MainActivity.this.startActivity(intent);
//    			}
//    		});
//    		
//    		relar1 = (RelativeLayout)findViewById(R.id.relar1);
//    		relar2 = (RelativeLayout)findViewById(R.id.relar1_2);
//    		relar3 = (RelativeLayout)findViewById(R.id.relar1_3);
//    		relar4 = (RelativeLayout)findViewById(R.id.relar1_4);
//    		relar5 = (RelativeLayout)findViewById(R.id.relar1_5);
//    		
//    		
//    		progress = (ProgressBar)findViewById(R.id.progress);
//    		progress.setVisibility(View.VISIBLE);
//    		imageView1 = (ImageView)findViewById(R.id.itemImage0);
//    		hostname1 = (TextView)findViewById(R.id.hotelname);
//    		tele1 = (TextView)findViewById(R.id.tele);
//    		price1 = (TextView)findViewById(R.id.price0);
//    		address1 = (TextView)findViewById(R.id.address);
//    		hintText1 = (TextView)findViewById(R.id.hintText1);
//    		imageView2 = (ImageView)findViewById(R.id.itemImage0_2);
//    		hostname2 = (TextView)findViewById(R.id.hotelname_2);
//    		tele2 = (TextView)findViewById(R.id.tele_2);
//    		price2 = (TextView)findViewById(R.id.price0_2);
//    		address2 = (TextView)findViewById(R.id.address_2);
//    		hintText2 = (TextView)findViewById(R.id.hintText2);
//    		imageView3 = (ImageView)findViewById(R.id.itemImage0_3);
//    		hostname3 = (TextView)findViewById(R.id.hotelname_3);
//    		tele3 = (TextView)findViewById(R.id.tele_3);
//    		price3 = (TextView)findViewById(R.id.price0_3);
//    		address3 = (TextView)findViewById(R.id.address_3);
//    		hintText3 = (TextView)findViewById(R.id.hintText3);
//    		imageView4 = (ImageView)findViewById(R.id.itemImage0_4);
//    		hostname4 = (TextView)findViewById(R.id.hotelname_4);
//    		tele4 = (TextView)findViewById(R.id.tele_4);
//    		price4 = (TextView)findViewById(R.id.price0_4);
//    		address4 = (TextView)findViewById(R.id.address_4);
//    		hintText4 = (TextView)findViewById(R.id.hintText4);
//    		imageView5 = (ImageView)findViewById(R.id.itemImage0_5);
//    		hostname5 = (TextView)findViewById(R.id.hotelname_5);
//    		tele5 = (TextView)findViewById(R.id.tele_5);
//    		price5 = (TextView)findViewById(R.id.price0_5);
//    		address5 = (TextView)findViewById(R.id.address_5);
//    		hintText5 = (TextView)findViewById(R.id.hintText5);
//    		
//    		
//    		
//    		button = (Button)findViewById(R.id.img_search);
//    		button.setOnClickListener(new OnClickListener() {
//    			
//    			@Override
//    			public void onClick(View v) {
//    				// TODO Auto-generated method stub
//    				showDialog(MainActivity.this);
//    			}
//    		});
//    		
//
//    		
//    		
//    		mTab1 = (ImageButton)findViewById(R.id.img_nearby);
//    		mTab5 = (ImageButton)findViewById(R.id.img_me);
//    		
//    		mTab1.setOnClickListener(new OnClickListener() {
//    			
//    			@Override
//    			public void onClick(View v) {
//    				// TODO Auto-generated method stub
//    				Intent intent = new Intent();
////    				intent.setClass(MainActivity.this, NearbyActivity.class);
//    				MainActivity.this.startActivity(intent);
//    				MainActivity.this.finish();
//    			}
//    		});
//           
//           
//            mTab5.setOnClickListener(new OnClickListener() {
//    			
//    			@Override
//    			public void onClick(View v) {
//    				// TODO Auto-generated method stub			
//    				ParseUser currentUser = ParseUser.getCurrentUser();
//    				if (currentUser != null) {
//    				    Intent intent = new Intent();
//    				    intent.putExtra("isCache", "yes");
//    					intent.putExtra("haveImage", "fromLogin");
//    					intent.setClass(MainActivity.this, LoginSuccActivity.class);
//    					MainActivity.this.startActivity(intent);
//    				} else {
//    					Intent intent2 = new Intent();
//    					intent2.setClass(MainActivity.this, MyActivity.class);
//    					MainActivity.this.startActivity(intent2);
//    				}
//
//    				MainActivity.this.finish();
//    			}
//    		});
//    		
//    		
//    		locateTextView = (TextView) findViewById(R.id.locateText);

    		location();
    		this.finish();
    		Intent intent = new Intent();
    		intent.setClass(MainActivity.this, MainingActivity.class);
    		startActivity(intent);
    		
    		System.out.println("yangxu------------->");
//        }
    

	}

	// }
	// 定义震动
	public void startVibrato() {
		mVibrator.vibrate(new long[] { 500, 200, 500, 200 }, -1); // 第一个｛｝里面是节奏数组，
																	// 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
	}

	//晃动函数
	private void shake(){
		// 晃动处理
				mVibrator = (Vibrator) getApplication().getSystemService(
						VIBRATOR_SERVICE);
				shakeListener = new ShakeListener(this);
				shakeListener.setOnShakeListener(new OnShakeListener() {

					@Override
					public void onShake() {
						// TODO Auto-generated method stub
						shakeListener.stop();
						startVibrato(); // 开始 震动
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(MainActivity.this, "什么都没发现哦！",
										Toast.LENGTH_SHORT).show();
								mVibrator.cancel();
								shakeListener.start();
							}
						}, 2000);

					}
				});

	}

	//定义根据经纬度查找地点的函数
	private void location(){
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
//		mLocClient = ((MyApplication)getApplication()).mLocationClient;
//		((MyApplication)getApplication()).locateTextView = locateTextView;
//		mLocClient.start();

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);				//打开gps
		option.setCoorType("bd09ll");		//设置坐标类型
		option.setAddrType("all");		//设置地址信息，仅设置为“all”时有地址信息，默认无地址信息
		option.setScanSpan(5000);	//设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
		mLocClient.setLocOption(option);
		mLocClient.requestLocation();
  String c;
  double jing,wei;
			c = ((MyApplication)getApplication()).city;
			jing = ((MyApplication) getApplication()).jingdu;
			wei = ((MyApplication) getApplication()).weidu;
			
//			top(((MyApplication)getApplication()).city,null);


	}



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK  && event.getRepeatCount() == 0) {
			if(logOut == false){
				Toast.makeText(MainActivity.this, "提示：再按一次退出吃住无忧！", Toast.LENGTH_SHORT).show();
				logOut = true;
			}else{
				MainActivity.this.finish();
			}
		}
		return true;
	}



	private void showDialog(Context context){


		AlertDialog.Builder builder = new AlertDialog.Builder(context);  

        builder.setTitle("请选择需要的推荐");  
       builder.setSingleChoiceItems(R.array.select, 0, new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			String hoddy=getResources().getStringArray(R.array.select)[which];

            if(hoddy.equals("吃啥篇(饭店,餐厅,食堂)")){
            	progress.setVisibility(View.VISIBLE);
            	top(city,"no");
            }else{
            	progress.setVisibility(View.VISIBLE);
            	top(city,"is");
            }
            dialog.dismiss();
		}
	});
       AlertDialog dialog = builder.create();
       dialog.show();
	}
	private void top(String c,String islive){
		button.setText("每 日 推 荐Top5         住哪篇");
		if(islive == null || islive.equals("is")){
			ParseQuery parses = new ParseQuery("top");
			parses.whereEqualTo("city", c);
			parses.whereEqualTo("islive", "is");
			parses.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
			parses.findInBackground(new FindCallback() {

				@Override
				public void done(List<ParseObject> arg0, ParseException e) {
					// TODO Auto-generated method stub
					if(e == null){
						if(arg0.size()==0){
							Toast.makeText(MainActivity.this, "对不起，今日无推荐!", Toast.LENGTH_LONG).show();

							progress.setVisibility(View.GONE);
						}
					else if(arg0.size()==1){
							hostname1.setText(arg0.get(0).getString("businessName"));
							tele1.setText("电话:" + arg0.get(0).getString("teleNum"));
							price1.setText("平均:" + arg0.get(0).getString("cost") + "元");
							address1.setText("地址:" + arg0.get(0).getString("address"));
							hintText1.setHint(arg0.get(0).getString("BusinessID"));
							loadImage(arg0.get(0).getString("img"), imageView1);
							progress.setVisibility(View.GONE);
							IsClick(false);
						}else if(arg0.size()==2){
							hostname1.setText(arg0.get(0).getString("businessName"));
							tele1.setText("电话:" + arg0.get(0).getString("teleNum"));
							price1.setText("平均:" + arg0.get(0).getString("cost") + "元");
							address1.setText("地址:" + arg0.get(0).getString("address"));
							hintText1.setHint(arg0.get(0).getString("BusinessID"));
							loadImage(arg0.get(0).getString("img"), imageView1);
							hostname2.setText(arg0.get(1).getString("businessName"));
							tele2.setText("电话:" + arg0.get(1).getString("teleNum"));
							price2.setText("平均:" + arg0.get(1).getString("cost") + "元");
							address2.setText("地址:" + arg0.get(1).getString("address"));
							hintText2.setHint(arg0.get(1).getString("BusinessID"));
							loadImage(arg0.get(1).getString("img"), imageView2);
							progress.setVisibility(View.GONE);
							IsClick(false);
						}else if(arg0.size()==3){
							hostname1.setText(arg0.get(0).getString("businessName"));
							tele1.setText("电话:" + arg0.get(0).getString("teleNum"));
							price1.setText("平均:" + arg0.get(0).getString("cost") + "元");
							address1.setText("地址:" + arg0.get(0).getString("address"));
							hintText1.setHint(arg0.get(0).getString("BusinessID"));
							loadImage(arg0.get(0).getString("img"), imageView1);
							hostname2.setText(arg0.get(1).getString("businessName"));
							tele2.setText("电话:" + arg0.get(1).getString("teleNum"));
							price2.setText("平均:" + arg0.get(1).getString("cost") + "元");
							address2.setText("地址:" + arg0.get(1).getString("address"));
							hintText2.setHint(arg0.get(1).getString("BusinessID"));
							loadImage(arg0.get(1).getString("img"), imageView2);
							hostname3.setText(arg0.get(2).getString("businessName"));
							tele3.setText("电话:" + arg0.get(2).getString("teleNum"));
							price3.setText("平均:" + arg0.get(2).getString("cost") + "元");
							address3.setText("地址:" + arg0.get(2).getString("address"));
							hintText3.setHint(arg0.get(2).getString("BusinessID"));
							loadImage(arg0.get(2).getString("img"), imageView3);
							progress.setVisibility(View.GONE);
							IsClick(false);
						}else if(arg0.size()==4){
							hostname1.setText(arg0.get(0).getString("businessName"));
							tele1.setText("电话:" + arg0.get(0).getString("teleNum"));
							price1.setText("平均:" + arg0.get(0).getString("cost") + "元");
							address1.setText("地址:" + arg0.get(0).getString("address"));
							hintText1.setHint(arg0.get(0).getString("BusinessID"));
							loadImage(arg0.get(0).getString("img"), imageView1);
							hostname2.setText(arg0.get(1).getString("businessName"));
							tele2.setText("电话:" + arg0.get(1).getString("teleNum"));
							price2.setText("平均:" + arg0.get(1).getString("cost") + "元");
							address2.setText("地址:" + arg0.get(1).getString("address"));
							hintText2.setHint(arg0.get(1).getString("BusinessID"));
							loadImage(arg0.get(1).getString("img"), imageView2);
							hostname3.setText(arg0.get(2).getString("businessName"));
							tele3.setText("电话:" + arg0.get(2).getString("teleNum"));
							price3.setText("平均:" + arg0.get(2).getString("cost") + "元");
							address3.setText("地址:" + arg0.get(2).getString("address"));
							hintText3.setHint(arg0.get(2).getString("BusinessID"));
							loadImage(arg0.get(2).getString("img"), imageView3);
							hostname4.setText(arg0.get(3).getString("businessName"));
							tele4.setText("电话:" + arg0.get(3).getString("teleNum"));
							price4.setText("平均:" + arg0.get(3).getString("cost") + "元");
							address4.setText("地址:" + arg0.get(3).getString("address"));
							hintText4.setHint(arg0.get(3).getString("BusinessID"));
							loadImage(arg0.get(3).getString("img"), imageView4);
							progress.setVisibility(View.GONE);
							IsClick(false);
						}else if(arg0.size()==5){
							hostname1.setText(arg0.get(0).getString("businessName"));
							tele1.setText("电话:" + arg0.get(0).getString("teleNum"));
							price1.setText("平均:" + arg0.get(0).getString("cost") + "元");
							address1.setText("地址:" + arg0.get(0).getString("address"));
							hintText1.setHint(arg0.get(0).getString("BusinessID"));
							loadImage(arg0.get(0).getString("img"), imageView1);
							hostname2.setText(arg0.get(1).getString("businessName"));
							tele2.setText("电话:" + arg0.get(1).getString("teleNum"));
							price2.setText("平均:" + arg0.get(1).getString("cost") + "元");
							address2.setText("地址:" + arg0.get(1).getString("address"));
							hintText2.setHint(arg0.get(1).getString("BusinessID"));
							loadImage(arg0.get(1).getString("img"), imageView2);
							hostname3.setText(arg0.get(2).getString("businessName"));
							tele3.setText("电话:" + arg0.get(2).getString("teleNum"));
							price3.setText("平均:" + arg0.get(2).getString("cost") + "元");
							address3.setText("地址:" + arg0.get(2).getString("address"));
							hintText3.setHint(arg0.get(2).getString("BusinessID"));
							loadImage(arg0.get(2).getString("img"), imageView3);
							hostname4.setText(arg0.get(3).getString("businessName"));
							tele4.setText("电话:" + arg0.get(3).getString("teleNum"));
							price4.setText("平均:" + arg0.get(3).getString("cost") + "元");
							address4.setText("地址:" + arg0.get(3).getString("address"));
							hintText4.setHint(arg0.get(3).getString("BusinessID"));
							loadImage(arg0.get(3).getString("img"), imageView4);
							hostname5.setText(arg0.get(4).getString("businessName"));
							tele5.setText("电话:" + arg0.get(4).getString("teleNum"));
							price5.setText("平均:" + arg0.get(4).getString("cost") + "元");
							address5.setText("地址:" + arg0.get(4).getString("address"));
							hintText5.setHint(arg0.get(4).getString("BusinessID"));
							loadImage(arg0.get(4).getString("img"), imageView5);
							progress.setVisibility(View.GONE);
							IsClick(false);
						}
					}else{
						progress.setVisibility(View.GONE);
					}
				}
			});
		}else if(islive.equals("no")){
			button.setText("每 日 推 荐Top5         吃啥篇");
			ParseQuery parses = new ParseQuery("top");
			parses.whereEqualTo("city", c);
			parses.whereEqualTo("islive", "no");
			parses.findInBackground(new FindCallback() {

				@Override
				public void done(List<ParseObject> arg0, ParseException e) {
					// TODO Auto-generated method stub
					if(e == null){
						if(arg0.size()==0){
							Toast.makeText(MainActivity.this, "对不起，今日无推荐!", Toast.LENGTH_LONG).show();
							progress.setVisibility(View.GONE);
						}
					else if(arg0.size()==1){
							hostname1.setText(arg0.get(0).getString("businessName"));
							tele1.setText("电话:" + arg0.get(0).getString("teleNum"));
							price1.setText("平均:" + arg0.get(0).getString("cost") + "元");
							address1.setText("地址:" + arg0.get(0).getString("address"));
							hintText1.setHint(arg0.get(0).getString("BusinessID"));
							loadImage(arg0.get(0).getString("img"), imageView1);
							progress.setVisibility(View.GONE);
							IsClick(false);
						}else if(arg0.size()==2){
							hostname1.setText(arg0.get(0).getString("businessName"));
							tele1.setText("电话:" + arg0.get(0).getString("teleNum"));
							price1.setText("平均:" + arg0.get(0).getString("cost") + "元");
							address1.setText("地址:" + arg0.get(0).getString("address"));
							hintText1.setHint(arg0.get(0).getString("BusinessID"));
							loadImage(arg0.get(0).getString("img"), imageView1);
							hostname2.setText(arg0.get(1).getString("businessName"));
							tele2.setText("电话:" + arg0.get(1).getString("teleNum"));
							price2.setText("平均:" + arg0.get(1).getString("cost") + "元");
							address2.setText("地址:" + arg0.get(1).getString("address"));
							hintText2.setHint(arg0.get(1).getString("BusinessID"));
							loadImage(arg0.get(1).getString("img"), imageView2);
							progress.setVisibility(View.GONE);
							IsClick(false);
						}else if(arg0.size()==3){
							hostname1.setText(arg0.get(0).getString("businessName"));
							tele1.setText("电话:" + arg0.get(0).getString("teleNum"));
							price1.setText("平均:" + arg0.get(0).getString("cost") + "元");
							address1.setText("地址:" + arg0.get(0).getString("address"));
							hintText1.setHint(arg0.get(0).getString("BusinessID"));
							loadImage(arg0.get(0).getString("img"), imageView1);
							hostname2.setText(arg0.get(1).getString("businessName"));
							tele2.setText("电话:" + arg0.get(1).getString("teleNum"));
							price2.setText("平均:" + arg0.get(1).getString("cost") + "元");
							address2.setText("地址:" + arg0.get(1).getString("address"));
							hintText2.setHint(arg0.get(1).getString("BusinessID"));
							loadImage(arg0.get(1).getString("img"), imageView2);
							hostname3.setText(arg0.get(2).getString("businessName"));
							tele3.setText("电话:" + arg0.get(2).getString("teleNum"));
							price3.setText("平均:" + arg0.get(2).getString("cost") + "元");
							address3.setText("地址:" + arg0.get(2).getString("address"));
							hintText3.setHint(arg0.get(2).getString("BusinessID"));
							loadImage(arg0.get(2).getString("img"), imageView3);
							progress.setVisibility(View.GONE);
							IsClick(false);
						}else if(arg0.size()==4){
							hostname1.setText(arg0.get(0).getString("businessName"));
							tele1.setText("电话:" + arg0.get(0).getString("teleNum"));
							price1.setText("平均:" + arg0.get(0).getString("cost") + "元");
							address1.setText("地址:" + arg0.get(0).getString("address"));
							hintText1.setHint(arg0.get(0).getString("BusinessID"));
							loadImage(arg0.get(0).getString("img"), imageView1);
							hostname2.setText(arg0.get(1).getString("businessName"));
							tele2.setText("电话:" + arg0.get(1).getString("teleNum"));
							price2.setText("平均:" + arg0.get(1).getString("cost") + "元");
							address2.setText("地址:" + arg0.get(1).getString("address"));
							hintText2.setHint(arg0.get(1).getString("BusinessID"));
							loadImage(arg0.get(1).getString("img"), imageView2);
							hostname3.setText(arg0.get(2).getString("businessName"));
							tele3.setText("电话:" + arg0.get(2).getString("teleNum"));
							price3.setText("平均:" + arg0.get(2).getString("cost") + "元");
							address3.setText("地址:" + arg0.get(2).getString("address"));
							hintText3.setHint(arg0.get(2).getString("BusinessID"));
							loadImage(arg0.get(2).getString("img"), imageView3);
							hostname4.setText(arg0.get(3).getString("businessName"));
							tele4.setText("电话:" + arg0.get(3).getString("teleNum"));
							price4.setText("平均:" + arg0.get(3).getString("cost") + "元");
							address4.setText("地址:" + arg0.get(3).getString("address"));
							hintText4.setHint(arg0.get(3).getString("BusinessID"));
							loadImage(arg0.get(3).getString("img"), imageView4);
							progress.setVisibility(View.GONE);
							IsClick(false);
						}else if(arg0.size()==5){
							hostname1.setText(arg0.get(0).getString("businessName"));
							tele1.setText("电话:" + arg0.get(0).getString("teleNum"));
							price1.setText("平均:" + arg0.get(0).getString("cost") + "元");
							address1.setText("地址:" + arg0.get(0).getString("address"));
							hintText1.setHint(arg0.get(0).getString("BusinessID"));
							loadImage(arg0.get(0).getString("img"), imageView1);
							hostname2.setText(arg0.get(1).getString("businessName"));
							tele2.setText("电话:" + arg0.get(1).getString("teleNum"));
							price2.setText("平均:" + arg0.get(1).getString("cost") + "元");
							address2.setText("地址:" + arg0.get(1).getString("address"));
							hintText2.setHint(arg0.get(1).getString("BusinessID"));
							loadImage(arg0.get(1).getString("img"), imageView2);
							hostname3.setText(arg0.get(2).getString("businessName"));
							tele3.setText("电话:" + arg0.get(2).getString("teleNum"));
							price3.setText("平均:" + arg0.get(2).getString("cost") + "元");
							address3.setText("地址:" + arg0.get(2).getString("address"));
							hintText3.setHint(arg0.get(2).getString("BusinessID"));
							loadImage(arg0.get(2).getString("img"), imageView3);
							hostname4.setText(arg0.get(3).getString("businessName"));
							tele4.setText("电话:" + arg0.get(3).getString("teleNum"));
							price4.setText("平均:" + arg0.get(3).getString("cost") + "元");
							address4.setText("地址:" + arg0.get(3).getString("address"));
							hintText4.setHint(arg0.get(3).getString("BusinessID"));
							loadImage(arg0.get(3).getString("img"), imageView4);
							hostname5.setText(arg0.get(4).getString("businessName"));
							tele5.setText("电话:" + arg0.get(4).getString("teleNum"));
							price5.setText("平均:" + arg0.get(4).getString("cost") + "元");
							address5.setText("地址:" + arg0.get(4).getString("address"));
							hintText5.setHint(arg0.get(4).getString("BusinessID"));
							loadImage(arg0.get(4).getString("img"), imageView5);
							progress.setVisibility(View.GONE);
							IsClick(false);
						}
					}else{
						progress.setVisibility(View.GONE);
					}
				}
			});
		}

	}
	private void loadImage(final String url, ImageView imageView) {
		CallbackImpl callbackImpl = new CallbackImpl(imageView);
		Drawable cacheImage = loader.loadDrawable(url, callbackImpl);
		if (cacheImage != null) {
			imageView.setImageDrawable(cacheImage);
		}
	}
	private void IsClick(boolean flag){
		if(!flag){
			relar1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.putExtra("businessID", hintText1.getHint().toString());
					intent.putExtra("businessName", hostname1.getText().toString());
					intent.putExtra("tel", tele1.getText().toString());
					intent.putExtra("address", address1.getText().toString());
					intent.putExtra("price", price1.getText().toString());
					intent.setClass(MainActivity.this, TopItemActivity.class);
					MainActivity.this.startActivity(intent);
				}
			});
			relar2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.putExtra("businessID", hintText2.getHint().toString());
					intent.putExtra("businessName", hostname2.getText().toString());
					intent.putExtra("tel", tele2.getText().toString());
					intent.putExtra("address", address2.getText().toString());
					intent.putExtra("price", price2.getText().toString());
					intent.setClass(MainActivity.this, TopItemActivity.class);
					MainActivity.this.startActivity(intent);
				}
			});
			relar3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.putExtra("businessID", hintText3.getHint().toString());
					intent.putExtra("businessName", hostname3.getText().toString());
					intent.putExtra("tel", tele3.getText().toString());
					intent.putExtra("address", address3.getText().toString());
					intent.putExtra("price", price3.getText().toString());
					intent.setClass(MainActivity.this, TopItemActivity.class);
					MainActivity.this.startActivity(intent);
				}
			});
			relar4.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.putExtra("businessID", hintText4.getHint().toString());
					intent.putExtra("businessName", hostname4.getText().toString());
					intent.putExtra("tel", tele4.getText().toString());
					intent.putExtra("address", address4.getText().toString());
					intent.putExtra("price", price4.getText().toString());
					intent.setClass(MainActivity.this, TopItemActivity.class);
					MainActivity.this.startActivity(intent);
				}
			});
			relar5.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.putExtra("businessID", hintText5.getHint().toString());
					intent.putExtra("businessName", hostname5.getText().toString());
					intent.putExtra("tel", tele5.getText().toString());
					intent.putExtra("address", address5.getText().toString());
					intent.putExtra("price", price5.getText().toString());
					intent.setClass(MainActivity.this, TopItemActivity.class);
					MainActivity.this.startActivity(intent);
				}
			});
			flag = true;
		}
	}
	/**
	 * 判断3g网络是否连接
	 * 
	 * */
	public static boolean isNetworkAvailable( Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivity == null) {
                      System.out.println("**** newwork is off");
                        return false;
                } else {
                        NetworkInfo info = connectivity.getActiveNetworkInfo();
                        if(info == null){
                              System.out.println("**** newwork is off");
                                return false;
                        }else{
                                if(info.isAvailable()){
                                      System.out.println("**** newwork is on");
                                        return true;
                                }
                              
                        }
                }
                  System.out.println("**** newwork is off");
        return false;
    }
	/**
	 * 判断wifi网络是否连接
	 * 
	 * */
	public static boolean isWiFiActive(Context inContext) {
		        WifiManager mWifiManager = (WifiManager) inContext
		          .getSystemService(Context.WIFI_SERVICE);
		          WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
		          int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
		          if (mWifiManager.isWifiEnabled() && ipAddress != 0) {
		          System.out.println("**** WIFI is on");
		              return true;
		          } else {
		             System.out.println("**** WIFI is off");
		             return false;   
		         }
		 }
}