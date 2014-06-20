//package com.yx.livewhere;
//
//import java.io.Serializable;
//import java.io.StringReader;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.xml.parsers.SAXParserFactory;
//
//import org.xml.sax.InputSource;
//import org.xml.sax.XMLReader;
//
//import android.app.AlertDialog;
//import android.app.ListActivity;
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Color;
//import android.location.Location;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.Window;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.baidu.mapapi.BMapManager;
//import com.parse.FindCallback;
//import com.parse.Parse;
//import com.parse.ParseAnalytics;
//import com.parse.ParseException;
//import com.parse.ParseObject;
//import com.parse.ParseQuery;
//import com.parse.ParseUser;
//import com.yx.model.LocationItem;
//import com.yx.util.HttpDownloader;
//import com.yx.util.ItemLocalAdapter;
//import com.yx.util.MyApplication;
//import com.yx.xml.LocationContentHandler;
//
//public class NearbyActivity extends ListActivity {
//	private ProgressDialog progressDialog = null;
//	private ItemLocalAdapter itemLocalAdapter = null;
//	private ImageButton mTab3, mTab5;// 控制底部Tab跳转
//	private ImageButton button = null;
//	private ImageButton fanweiButton = null;
//	private ListView listView = null;
//	private LocationClient mLocClient = null;
//	private double lng;
//	private double lat;
//	private String city = null;
//	private Thread LocatItemThread = null;
//	private Handler LocatItemHandler = null;
//	private List<LocationItem> myListLocationItem;
//	private String fanwei = null;
//	private LocationItem MyItem = null;
//	private ParseUser currentUser = null;
//	private String isFoot = null;
//	private boolean isClick = true;
//	private TextView locateText = null;
//	private String cityhotel = null;
//	private String distance = null;
//	private boolean logOut = false;
//
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题
//		setContentView(R.layout.nearby);
//		Parse.initialize(this, "IlQ8wBml4IkepGG0tuphk2VDIAfTuvcXX9OJOJfh", "NMcva8057XBA0mdRwlLKvLGXxL9uzQABVFAaVbyO");
//		ParseAnalytics.trackAppOpened(null);
//		Toast.makeText(this, fanwei, Toast.LENGTH_LONG);
//		locateText = (TextView)findViewById(R.id.locateText);
//		locateText.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				AlertDialog.Builder builder = new AlertDialog.Builder(NearbyActivity.this);  
//		        builder.setTitle("请选择");  
//		       builder.setSingleChoiceItems(R.array.select3, 0, new DialogInterface.OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					String hoddy=getResources().getStringArray(R.array.select3)[which];
//		            System.out.println("yangxu--->" + hoddy);
//		            if(hoddy.equals("附近宾馆")){
//		            	progressDialog.show();
//		            	cityhotel = "宾馆";
//		            	updateListView(lng, lat, city, distance,cityhotel);
//		            	locateText.setText("附近宾馆");
//		            }else if(hoddy.equals("附近餐馆")){
//		            	progressDialog.show();
//		            	cityhotel = "餐馆";
//		            	updateListView(lng, lat, city, distance,cityhotel);
//		            	locateText.setText("附近餐馆");
//		            }
//		            dialog.dismiss();
//				}
//			});
//		       AlertDialog dialog = builder.create();
//		       dialog.show();
//			}
//		});
//		fanweiButton = (ImageButton) findViewById(R.id.fanweiButton);
//		fanweiButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				AlertDialog.Builder builder = new AlertDialog.Builder(NearbyActivity.this);  
//		        builder.setTitle("请选择");  
//		       builder.setSingleChoiceItems(R.array.select2, 0, new DialogInterface.OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					String hoddy=getResources().getStringArray(R.array.select2)[which];
//		            if(hoddy.equals("一公里")){
//		            	progressDialog.show();
//		            	distance = "1000";
//		            	updateListView(lng, lat, city, distance,cityhotel);
//		            }
//		            if(hoddy.equals("三公里")){
//		            	progressDialog.show();
//		            	distance = "3000";
//		            	updateListView(lng, lat, city, distance,cityhotel);
//		            }else if(hoddy.equals("五公里")){
//		            	progressDialog.show();
//		            	distance = "5000";
//		            	updateListView(lng, lat, city, distance,cityhotel);
//		            }else if(hoddy.equals("十公里")){
//		            	progressDialog.show();
//		            	distance = "10000";
//		            	updateListView(lng, lat, city, distance,cityhotel);
//		            }
//		            dialog.dismiss();
//				}
//			});
//		       AlertDialog dialog = builder.create();
//		       dialog.show();
//			}
//		});
//
//		listView = getListView();
//
//		listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position,
//					long id) {
//				// TODO Auto-generated method stub
//				if(isClick){
//					MyItem = (LocationItem)listView.getItemAtPosition(position);
//					currentUser = ParseUser.getCurrentUser();
//					if (currentUser != null) {
//
//						ParseQuery query = new ParseQuery("favorite");
//						query.whereEqualTo("BusinessID", MyItem.getId());
//						query.whereEqualTo("username", currentUser.getUsername().toString());
//
//						query.findInBackground(new FindCallback() {
//
//							@Override
//							public void done(List<ParseObject> arg0, ParseException e) {
//								// TODO Auto-generated method stub
//								if (e == null) {
//									if(arg0.size() == 0){
//										Intent intent2 = new Intent();
//										intent2.putExtra("MyItem", MyItem);
//										intent2.putExtra("username", currentUser.getUsername().toString());
//										intent2.putExtra("isFavor", "not");
//										intent2.putExtra("isLogin", "is");
//										intent2.setClass(NearbyActivity.this, ItemActivity.class);
//										NearbyActivity.this.startActivityForResult(intent2, 0);
//									}else{
//									System.out.println("gaosiwei--->" + "isis");
//									Intent intent = new Intent();
//									intent.putExtra("MyItem", MyItem);
//									intent.putExtra("username", currentUser.getUsername().toString());
//									intent.putExtra("isFavor", "is");
//									intent.putExtra("isLogin", "is");
//									intent.setClass(NearbyActivity.this, ItemActivity.class);
//									NearbyActivity.this.startActivityForResult(intent, 0);
//									}
//								} else {
//									Intent intent2 = new Intent();
//									intent2.putExtra("MyItem", MyItem);
//									intent2.putExtra("username", currentUser.getUsername().toString());
//									intent2.putExtra("isFavor", "not");
//									intent2.putExtra("isLogin", "is");
//									intent2.setClass(NearbyActivity.this, ItemActivity.class);
//									NearbyActivity.this.startActivityForResult(intent2, 0);
//									Log.d("失败", "查询错误: " + e.getMessage());
//								}
//
//							}
//						});
//
//					} else {
//						Intent intent3 = new Intent();
//						intent3.putExtra("isFavor", "not");
//						intent3.putExtra("isLogin", "not");
//						intent3.putExtra("MyItem", MyItem);
//						intent3.setClass(NearbyActivity.this, ItemActivity.class);
//						NearbyActivity.this.startActivityForResult(intent3, 0);
//					}
//				}
//
//				isClick = false;
//
//			}
//		});
//
//		listView.setCacheColorHint(Color.TRANSPARENT);
//		progressDialog = ProgressDialog.show(NearbyActivity.this, "请稍等",
//				"正在努力加载中...", true);
//		progressDialog.setCancelable(true);
//		MyApplication app = (MyApplication) this.getApplication();
//		if (app.mBMapManager == null) {
//			app.mBMapManager = new BMapManager(getApplication());
//			app.mBMapManager.init(app.strKey,
//					new MyApplication.MyGeneralListener());
//		}
//		app.mBMapManager.start();
//
//		mLocClient = ((MyApplication) getApplication()).mLocationClient;
//		// ((MyApplication)getApplication()).locateTextView = locateTextView;
//		mLocClient.start();
//
//		LocationClientOption option = new LocationClientOption();
//		option.setOpenGps(true); // 打开gps
//		option.setCoorType("bd09ll"); // 设置坐标类型
//		option.setAddrType("all"); // 设置地址信息，仅设置为“all”时有地址信息，默认无地址信息
//		option.setScanSpan(500); // 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
//		mLocClient.setLocOption(option);
//		mLocClient.requestLocation();
//		lng = ((MyApplication) getApplication()).jingdu;
//		lat = ((MyApplication) getApplication()).weidu;
//		city = ((MyApplication) getApplication()).city;
//
//		button = (ImageButton) findViewById(R.id.map_button);
//		button.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.setClass(NearbyActivity.this, MymapActivity.class);
//				intent.putExtra("items", (Serializable)myListLocationItem);
//				NearbyActivity.this.startActivity(intent);
//			}
//		});
//
//
//		mTab3 = (ImageButton) findViewById(R.id.img_zhuye);
////		mTab5 = (ImageButton) findViewById(R.id.img_my);
//		mTab3.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.setClass(NearbyActivity.this, MainActivity.class);
//				NearbyActivity.this.startActivity(intent);
//				NearbyActivity.this.finish();
//			}
//		});
//
//		mTab5.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				ParseUser currentUser = ParseUser.getCurrentUser();
//				if (currentUser != null) {
//					Intent intent = new Intent();
//					 intent.putExtra("isCache", "yes");
//						intent.putExtra("haveImage", "fromLogin");
//				   intent.setClass(NearbyActivity.this, LoginSuccActivity.class);
//				   NearbyActivity.this.startActivity(intent);
//				   NearbyActivity.this.finish();
//				} else {
//					Intent intent2 = new Intent();
//					intent2.setClass(NearbyActivity.this, MyActivity.class);
//					NearbyActivity.this.startActivity(intent2);
//					NearbyActivity.this.finish();
//				}
//			}
//		});
//
//		LocatItemHandler = new Handler(){
//
//			@Override
//			public void handleMessage(Message msg) {
//				// TODO Auto-generated method stub
//				switch (msg.what) {
//                   case 0:
//           			listView.setAdapter(itemLocalAdapter);
//           			progressDialog.dismiss();
//                }
//			}
//
//		};
//
//
//		LocatItemThread = new Thread(new Runnable() {
//			boolean Myflag = true;
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while(Myflag){
//				if (lng != 0.0 && lat != 0.0 && city != null) {
//					distance = "1000";
//					cityhotel = "宾馆";
//					updateListView(lng, lat, city,distance,cityhotel);
//					Myflag = false;
//				}
//				}
//			}
//		});
//		LocatItemThread.start();
//	}
//
//	@Override
//	protected void onPause() {
//		MyApplication app = (MyApplication) this.getApplication();
//		app.mBMapManager.stop();
//		super.onPause();
//	}
//
//	@Override
//	protected void onResume() {
//		MyApplication app = (MyApplication) this.getApplication();
//		app.mBMapManager.start();
//		super.onResume();
//	}
//
//	@Override
//	protected void onListItemClick(ListView l, View v, int position, long id) {
//		// TODO Auto-generated method stub
//		super.onListItemClick(l, v, position, id);
//		Toast.makeText(NearbyActivity.this, "你点击了第"+(position+1)+"项", Toast.LENGTH_SHORT).show();
//	}
//
//
//
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// TODO Auto-generated method stub
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//			if(logOut == false){
//				Toast.makeText(NearbyActivity.this, "提示：再按一次退出吃住无忧！", Toast.LENGTH_SHORT).show();
//				logOut = true;
//			}else{
//				NearbyActivity.this.finish();
//			}
//		}
//		return true;
//	}
//
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		switch (resultCode) {
//		case 1:
//			NearbyActivity.this.finish();
//			break;
//		case 2:
//			isClick = true;
//			break;
//		default:
//			break;
//		}
//	}
//
//	public double getDistance(double lat1, double lon1, double lat2, double lon2) {
//		float[] results = new float[1];
//		Location.distanceBetween(lat1, lon1, lat2, lon2, results);
//		return results[0];
//	}
//
//	/**
//	 * 根据URL下载xml文件
//	 * */
//	private String downloadXML(String urlStr) {
//		HttpDownloader httpDownloader = new HttpDownloader();
//		String result = httpDownloader.download(urlStr);
//		return result;
//	}
//
//	private void parse(String xmlStr) {
//		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
//		List<LocationItem> infos = new ArrayList<LocationItem>();
//		try {
//			XMLReader xmlReader = saxParserFactory.newSAXParser()
//					.getXMLReader();
//
//			LocationContentHandler locationContentHandler = new LocationContentHandler(
//					infos);
//			xmlReader.setContentHandler(locationContentHandler);
//			xmlReader.parse(new InputSource(new StringReader(xmlStr)));
//			for(Iterator iterator = infos.iterator(); iterator.hasNext();){
//				LocationItem locationInfo = (LocationItem) iterator.next();
//				locationInfo.setDistance(getDistance(lat, lng, locationInfo.getLat(), locationInfo.getLng()));
//			}
//			myListLocationItem=infos;
//
//		itemLocalAdapter = new ItemLocalAdapter(infos, NearbyActivity.this);
//		Message msg = new Message();
//        msg.what = 0;
//        LocatItemHandler.sendMessage(msg);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	private void updateListView(double lng, double lat, String c,String distance_item,String c2) {
//      
//
//		try {
//			cityhotel = URLEncoder.encode(c2, "UTF-8");
//			c = URLEncoder.encode(c, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//		String xml = downloadXML("http://openapi.aibang.com/search?app_key=0f1958aad1e661519a3e44e5c560f4f2&lat="
//				+ lat
//				+ "&lng="
//				+ lng
//				+ "&q="
//				+ cityhotel
//				+ "&city="
//				+ c
//				+ "&as="+distance_item+"&from=1&to=20&rc=6");
//
//		parse(xml);
//
//	}
//
//
// }