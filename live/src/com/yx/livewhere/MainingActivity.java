package com.yx.livewhere;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.yx.download.AsyncHttpClient;
import com.yx.download.JsonHttpResponseHandler;
import com.yx.model.LocationItem;
import com.yx.util.ItemLocalAdapter;
import com.yx.util.MyApplication;
import com.yx.xListView.XListView;
import com.yx.xListView.XListView.IXListViewListener;

public class MainingActivity extends Activity implements IXListViewListener {

	private XListView listView = null;
	private SlidingMenu menu = null;
	private LocationClient mLocClient = null;
	private double lng;
	private double lat;
	private List<LocationItem> myListLocationItem;
	private String city = null;
	private String city2;
	private String ishotel;
	private ItemLocalAdapter adapter;
	private ImageView menuButton = null;
	private Handler mHandler = null;
	private int start = 1;
	
	private ParseUser currentUser = null;
	private String isFoot = null;
	private TextView locateText = null;
	private LocationItem MyItem = null;
	
	private TextView rest,hotel,recomm,myPage,fanweiButton,name,about,question,textHelp;
	private String fanwei="1000";
	private String isrest = "饭店";
	private ProgressDialog progressDialog = null;
	
	private List<LocationItem> lo = new ArrayList<LocationItem>();
	private ImageView locateView = null;
	private boolean can = true;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.maining);
		Parse.initialize(this, "IlQ8wBml4IkepGG0tuphk2VDIAfTuvcXX9OJOJfh", "NMcva8057XBA0mdRwlLKvLGXxL9uzQABVFAaVbyO");
		ParseAnalytics.trackAppOpened(null);
				mHandler = new Handler();
		name = (TextView)findViewById(R.id.name);
		locateView = (ImageView)findViewById(R.id.locateView);
		// 实例化广告条
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		// 获取要嵌入广告条的布局
		LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);
		// 将广告条加入到布局中
		adLayout.addView(adView);
		adView.setAdListener(new AdViewListener() {
		    @Override
		    public void onSwitchedAd(AdView adView) {
		        // 切换广告并展示
		    }

		    @Override
		    public void onReceivedAd(AdView adView) {
		        // 请求广告成功
		    }

		    @Override
		    public void onFailedToReceivedAd(AdView adView) {
		        // 请求广告失败
		    }
		});
//		progressDialog = ProgressDialog.show(MainingActivity.this, "请稍等",
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
//		option.setScanSpan(5000); // 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
//		mLocClient.setLocOption(option);
//		mLocClient.requestLocation();
		
		getloc();		
		listView = (XListView) findViewById(R.id.xListView);
		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setDividerHeight(0);
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(true);
		listView.setXListViewListener(this);
		// mHandler = new Handler();

	

		// configure the SlidingMenu
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidth(50);
		menu.setBehindOffset(110);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.behind_view);
		// init();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				
					MyItem = (LocationItem)listView.getItemAtPosition(position);
					currentUser = ParseUser.getCurrentUser();
					if (currentUser != null) {

						ParseQuery query = new ParseQuery("favorite");
						query.whereEqualTo("BusinessID", MyItem.getId());
						query.whereEqualTo("username", currentUser.getUsername().toString());

						query.findInBackground(new FindCallback() {

							@Override
							public void done(List<ParseObject> arg0, ParseException e) {
								// TODO Auto-generated method stub
								if (e == null) {
									if(arg0.size() == 0){
										Intent intent2 = new Intent();
										intent2.putExtra("MyItem", MyItem);
										intent2.putExtra("username", currentUser.getUsername().toString());
										intent2.putExtra("isFavor", "not");
										intent2.putExtra("isLogin", "is");
										intent2.setClass(MainingActivity.this, ItemActivity.class);
										MainingActivity.this.startActivityForResult(intent2, 0);
									}else{
									Intent intent = new Intent();
									intent.putExtra("MyItem", MyItem);
									intent.putExtra("username", currentUser.getUsername().toString());
									intent.putExtra("isFavor", "is");
									intent.putExtra("isLogin", "is");
									intent.setClass(MainingActivity.this, ItemActivity.class);
									MainingActivity.this.startActivityForResult(intent, 0);
									}
								} else {
									Intent intent2 = new Intent();
									intent2.putExtra("MyItem", MyItem);
									intent2.putExtra("username", currentUser.getUsername().toString());
									intent2.putExtra("isFavor", "not");
									intent2.putExtra("isLogin", "is");
									intent2.setClass(MainingActivity.this, ItemActivity.class);
									MainingActivity.this.startActivityForResult(intent2, 0);
									Log.d("失败", "查询错误: " + e.getMessage());
								}

							}
						});

					} else {
						Intent intent3 = new Intent();
						intent3.putExtra("isFavor", "not");
						intent3.putExtra("isLogin", "not");
						intent3.putExtra("MyItem", MyItem);
						intent3.setClass(MainingActivity.this, ItemActivity.class);
						MainingActivity.this.startActivityForResult(intent3, 0);
					}
				


			}
		});
		
		menuButton = (ImageView) findViewById(R.id.newsfeed_flip);
		menuButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.toggle();
			}
		});

		rest = (TextView)findViewById(R.id.rest);
		rest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				can = true;
				fanweiButton.setVisibility(View.VISIBLE);
				locateView.setVisibility(View.VISIBLE);
				getloc();
				name.setText("附近餐厅");
				menu.toggle();
				isrest = "饭店";
				getBusiness(isrest, fanwei);
			}
		});
		hotel = (TextView)findViewById(R.id.hotel);
		hotel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				can = true;
				fanweiButton.setVisibility(View.VISIBLE);
				locateView.setVisibility(View.VISIBLE);
				getloc();
				name.setText("附近宾馆");
				menu.toggle();
				isrest = "旅馆";
				getBusiness(isrest, fanwei);
			}
		});
		textHelp = (TextView)findViewById(R.id.textHelp);
		textHelp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainingActivity.this, PagerActivity.class);
				MainingActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, 0);
			}
		});
		recomm = (TextView)findViewById(R.id.recomm);
		recomm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				can = false;
				fanweiButton.setVisibility(View.GONE);
				locateView.setVisibility(View.GONE);
				name.setText("每日推荐");
				final ParseUser cu = ParseUser.getCurrentUser();
				menu.toggle();
				if (cu != null) {
					ParseQuery query = new ParseQuery("favorite");
					query.whereEqualTo("username", cu.getString("username"));
					query.findInBackground(new FindCallback() {
					    @Override
					    public void done(List<ParseObject> business, ParseException e) {
					        if (business != null) {
					        	ParseQuery query = new ParseQuery("favorite");
					        	query.whereNotEqualTo("username", cu.getString("username"));
					        	for(int i=0; i<business.size(); i++){
					        		query.whereEqualTo("BusinessID", business.get(i).getString("BusinessID"));
					        	}
					        	query.findInBackground(new FindCallback() {
									
									@Override
									public void done(List<ParseObject> arg0, ParseException arg1) {
										// TODO Auto-generated method stub
										if(arg0 != null){
											ParseQuery query = new ParseQuery("favorite");
								        	for(int i=0; i<arg0.size(); i++){
								        		query.whereEqualTo("username", arg0.get(i).getString("username"));
								        	}
								        	query.findInBackground(new FindCallback() {
												
												@Override
												public void done(List<ParseObject> arg0, ParseException arg1) {
													// TODO Auto-generated method stub
													if(arg0 != null){
													   lo = new ArrayList<LocationItem>(); 
															
														for(int i=0; i<arg0.size(); i++){
															LocationItem l = new LocationItem();
															l.setAddr(arg0.get(i).getString("address"));
															l.setCate(arg0.get(i).getString("type"));
															l.setCost(arg0.get(i).getString("cost"));
//															l.setDesc(arg0.get(i).getString(""));
															l.setDist(arg0.get(i).getString("dist"));
															l.setId(arg0.get(i).getString("BusinessID"));
//															l.setImg_url();
															l.setLat(arg0.get(i).getString("lat"));
															l.setLng(arg0.get(i).getString("lng"));
															l.setRate(arg0.get(i).getString("score"));
															l.setName(arg0.get(i).getString("businessName"));
															l.setTel(arg0.get(i).getString("teleNum"));
															lo.add(l);
														}
														adapter = new ItemLocalAdapter(
																lo,
																MainingActivity.this);
														listView.setAdapter(adapter);
													}
												}
											});
										}
									}
								});
					        } else {
					            // Failed
					        }
					    }
					});
				} else{
					Intent intent2 = new Intent();
					intent2.setClass(MainingActivity.this, MyActivity.class);
					MainingActivity.this.startActivity(intent2);
					overridePendingTransition(R.anim.in_from_right, R.anim.out);
				}
				
			}
		});
		myPage = (TextView)findViewById(R.id.textVip);
		myPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ParseUser currentUser = ParseUser.getCurrentUser();
				if (currentUser != null) {
					Intent intent = new Intent();
					 intent.putExtra("isCache", "yes");
						intent.putExtra("haveImage", "fromLogin");
				   intent.setClass(MainingActivity.this, LoginSuccActivity.class);
				   MainingActivity.this.startActivity(intent);
				   overridePendingTransition(R.anim.in_from_right, R.anim.out);
				} else {
					Intent intent2 = new Intent();
					intent2.setClass(MainingActivity.this, MyActivity.class);
					MainingActivity.this.startActivity(intent2);
					overridePendingTransition(R.anim.in_from_right, R.anim.out);
				}
			}
		});
		about = (TextView)findViewById(R.id.textReview);
		about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainingActivity.this, AboutActivity.class);
				MainingActivity.this.startActivity(intent);
				   overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
		question = (TextView)findViewById(R.id.textQuestion);
		question.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainingActivity.this, RecommActivity.class);
				MainingActivity.this.startActivity(intent);
				   overridePendingTransition(R.anim.in_from_right, R.anim.out);
			}
		});
		fanweiButton = (TextView)findViewById(R.id.fanwei);
		fanweiButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(MainingActivity.this);  
		        builder.setTitle("请选择");  
		       builder.setSingleChoiceItems(R.array.select2, 0, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String hoddy=getResources().getStringArray(R.array.select2)[which];
		            if(hoddy.equals("一公里")){
		            	getloc();
//		            	progressDialog.show();
		            	fanweiButton.setText("一公里");
		            	fanwei = "1000";
		            	getBusiness(isrest, fanwei);
		            }
		            if(hoddy.equals("三公里")){
		            	getloc();
//		            	progressDialog.show();
		            	fanweiButton.setText("三公里");
		            	fanwei = "3000";
		            	getBusiness(isrest, fanwei);
		            	
		            }else if(hoddy.equals("五公里")){
		            	getloc();
//		            	progressDialog.show();
		            	fanweiButton.setText("五公里");
		            	fanwei = "5000";
		            	getBusiness(isrest, fanwei);
		            	
		            }else if(hoddy.equals("十公里")){
		            	getloc();
//		            	progressDialog.show();
		            	fanweiButton.setText("十公里");
		            	fanwei = "10000";
		            	getBusiness(isrest, fanwei);
		            }
		            dialog.dismiss();
				}
			});
		       AlertDialog dialog = builder.create();
		       dialog.show();
			}
		});

			getBusiness(isrest,fanwei);
		
		
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		if(can==true){
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					
						getBusiness(isrest,fanwei);
						listView.stopRefresh();
						listView.stopLoadMore();	
					
					

				}
			}, 3000);
		}
		
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		if(can==true){
			
			try {

				city2 = URLEncoder.encode(city, "UTF-8");
				ishotel = URLEncoder.encode(isrest, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					
					start = start + 9;
					// getNews(start);
					AsyncHttpClient client = new AsyncHttpClient();
					client.get(
							MainingActivity.this,
							"http://openapi.aibang.com/search?app_key=0f1958aad1e661519a3e44e5c560f4f2&alt=json&lat="
									+ lat
									+ "&lng="
									+ lng
									+ "&q="
									+ ishotel
									+ "&city="
									+ city2
									+ "&as="
									+ fanwei
									+ "&from="
									+ start
									+ "&to="
									+ (start + 8)
									+ "&rc=6", new JsonHttpResponseHandler() {

								@Override
								public void onSuccess(JSONObject jsonObject) {
									// TODO Auto-generated method stub
									try {
										JSONObject json = jsonObject
												.getJSONObject("bizs");
										JSONArray array = json
												.getJSONArray("biz");
										JsonUtils jsonUtiles = new JsonUtils();
										myListLocationItem.addAll(jsonUtiles
												.parseItemFromJson(array
														.toString()));
										adapter.notifyDataSetChanged();
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									super.onSuccess(jsonObject);
								}

							});
					start = start + 9;
					listView.stopRefresh();
					listView.stopLoadMore();
				}
			}, 3000);
		}

	}

	public void getloc(){
		lng = ((MyApplication) getApplication()).jingdu;
		lat = ((MyApplication) getApplication()).weidu;
		city = ((MyApplication) getApplication()).city;
	}
	
	public void getBusiness(String r,String f) {
		
		
		if (city == null) {
			Toast.makeText(MainingActivity.this, "正在定位，请稍等...",
					Toast.LENGTH_SHORT).show();
			getloc();
		} else {
			
			try {
				city2 = URLEncoder.encode(city, "UTF-8");
				ishotel = URLEncoder.encode(r, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			AsyncHttpClient client = new AsyncHttpClient();
			client.get(
					MainingActivity.this,
					"http://openapi.aibang.com/search?app_key=0f1958aad1e661519a3e44e5c560f4f2&alt=json&lat="
							+ lat
							+ "&lng="
							+ lng
							+ "&q="
							+ ishotel
							+ "&city="
							+ city2 + "&as=" + f + "&from=1&to=9&rc=6",
					new JsonHttpResponseHandler() {

						@Override
						public void onSuccess(JSONObject jsonObject) {
							try {
								JSONObject json = jsonObject
										.getJSONObject("bizs");
								JSONArray array = json.getJSONArray("biz");
								JsonUtils jsonUtiles = new JsonUtils();

								myListLocationItem = jsonUtiles
										.parseItemFromJson(array.toString());
								adapter = new ItemLocalAdapter(
										myListLocationItem,
										MainingActivity.this);
								listView.setAdapter(adapter);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							super.onSuccess(jsonObject);
						}

					});
		}
	}
}




