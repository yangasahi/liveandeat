package com.yx.livewhere;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.yx.download.AsyncHttpClient;
import com.yx.download.JsonHttpResponseHandler;
import com.yx.model.ImageItem;
import com.yx.model.LocationItem;
import com.yx.model.UserEvaluate;
import com.yx.util.AsyncImageLoader;
import com.yx.util.CallbackImpl;
import com.yx.util.HttpDownloader;
import com.yx.util.ItemLocalAdapter;
import com.yx.xml.ImageContentHandler;
import com.yx.xml.UserEvaluateHandler;

public class ItemActivity extends Activity {

	private AsyncImageLoader loader = new AsyncImageLoader();
	private TextView hotelname, type, price, address, distance, tele,
			man_total, man_name, man_content, puttime, man_total_11 = null;
	private ImageView rate, rate_user, userImage, itemImage0, itemImage1,
			itemImage2, itemImage3, itemImage4, addComm = null;
	private ImageButton backImageButton, addImg = null;
	private List<UserEvaluate> userEvaluateItem;
	private List<ImageItem> imageItem;
	private Handler userHandler = null;
	private LocationItem Mylocation = null;
	private Thread userThread = null;
	private Thread imgThread = null;
	private UserEvaluate user = null;
	private ImageItem[] imgs = new ImageItem[5];
	private LinearLayout linear = null;
	private PopupWindow popupWindow = null;
	private Button camarabutton, xiangceButton = null;
	private boolean display = true;
	private String username = null;
	private ImageButton favarButton,scangButton = null;
	private String isFavor,isLogin,isFoot,UnamefromNearBy = null;
	private TextView fava = null;
	private ProgressBar progress_1,progress_2,progress_3,progress_4 = null;
	private String uid;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题
		setContentView(R.layout.item);
		Parse.initialize(this, "IlQ8wBml4IkepGG0tuphk2VDIAfTuvcXX9OJOJfh", "NMcva8057XBA0mdRwlLKvLGXxL9uzQABVFAaVbyO");
		ParseAnalytics.trackAppOpened(null);
		progress_1 = (ProgressBar)findViewById(R.id.progress_1);
		progress_2 = (ProgressBar)findViewById(R.id.progress_2);
		progress_3 = (ProgressBar)findViewById(R.id.progress_3);
		progress_4 = (ProgressBar)findViewById(R.id.progress_4);
		progress_1.setVisibility(View.VISIBLE);
		progress_2.setVisibility(View.VISIBLE);

		favarButton = (ImageButton) findViewById(R.id.img_me);
		scangButton = (ImageButton)findViewById(R.id.my_favor);
		fava = (TextView) findViewById(R.id.fava);
		man_total = (TextView) findViewById(R.id.man_total);
		man_name = (TextView) findViewById(R.id.man_name);
		man_content = (TextView) findViewById(R.id.man_content);
		puttime = (TextView) findViewById(R.id.timeTextView);
		hotelname = (TextView) findViewById(R.id.hotelname);
		type = (TextView) findViewById(R.id.tele);
		price = (TextView) findViewById(R.id.price0);
		address = (TextView) findViewById(R.id.address);
		distance = (TextView) findViewById(R.id.distance2);
		tele = (TextView) findViewById(R.id.telephone_num);
		man_total_11 = (TextView) findViewById(R.id.man_total_11);

		linear = (LinearLayout) findViewById(R.id.comm_layout);
//		linear.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.putExtra("user", (Serializable) userEvaluateItem);
//				intent.setClass(ItemActivity.this, UserCommentsActivity.class);
//				ItemActivity.this.startActivity(intent);
//			}
//		});

		backImageButton = (ImageButton) findViewById(R.id.img_search);
		backImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ItemActivity.this.setResult(2, null);
				ItemActivity.this.finish();
			}
		});
		addImg = (ImageButton) findViewById(R.id.img_zhuye);
		addImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (display) {
					display = false;
					View layout = getLayoutInflater().inflate(
							R.layout.popwindow, null);
					layout.getBackground().setAlpha(200);
					popupWindow = new PopupWindow(layout, 200, 200);
					popupWindow.showAsDropDown(v);
					popupWindow.showAtLocation(findViewById(R.id.img_zhuye),
							Gravity.CENTER, -500, 0);
					camarabutton = (Button) layout
							.findViewById(R.id.camaraButton);
					xiangceButton = (Button) layout.findViewById(R.id.xiangce);
					xiangceButton.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ParseUser currentUser = ParseUser.getCurrentUser();
							if (currentUser == null) {
								Intent intent = new Intent();
								intent.setClass(ItemActivity.this,
										LoginActivity.class);
								ItemActivity.this.startActivity(intent);
							} else {
								username = currentUser.getUsername().toString();
								Intent intent = new Intent();
								intent.setAction(Intent.ACTION_PICK);
								intent.setType("image/*");
								startActivityForResult(intent, 1);
							}

						}
					});
					camarabutton.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ParseUser currentUser = ParseUser.getCurrentUser();
							if (currentUser == null) {
								Intent intent = new Intent();
								intent.setClass(ItemActivity.this,
										LoginActivity.class);
								ItemActivity.this.startActivity(intent);
							} else {
								username = currentUser.getUsername().toString();
								Intent i = new Intent(
										"android.media.action.IMAGE_CAPTURE");
								startActivityForResult(i, 2);
							}

						}
					});
				} else {
					popupWindow.dismiss();
					display = true;
				}
			}
		});

		rate_user = (ImageView) findViewById(R.id.man_rate0);
		rate = (ImageView) findViewById(R.id.rate1);
		userImage = (ImageView) findViewById(R.id.userImage);
		itemImage0 = (ImageView) findViewById(R.id.itemImage0);
		itemImage1 = (ImageView) findViewById(R.id.itemImage1);
		itemImage2 = (ImageView) findViewById(R.id.itemImage2);
		itemImage3 = (ImageView) findViewById(R.id.itemImage3);
		itemImage4 = (ImageView) findViewById(R.id.itemImage4);
		addComm = (ImageView) findViewById(R.id.img_nearby);
		addComm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ParseUser currentUser = ParseUser.getCurrentUser();
				if (currentUser == null) {
					Intent intent = new Intent();
					intent.setClass(ItemActivity.this, LoginActivity.class);
					ItemActivity.this.startActivity(intent);
				} else {
					Intent intent = new Intent();
					intent.putExtra("ID", Mylocation.getId());
					intent.putExtra("businessname", Mylocation.getName());
					intent.putExtra("username", currentUser.getUsername()
							.toString());
					intent.setClass(ItemActivity.this, AddCommActivity.class);
					ItemActivity.this.startActivity(intent);
				}
			}
		});



		Intent intent = getIntent();
		Mylocation = (LocationItem) intent.getSerializableExtra("MyItem");
		uid = Mylocation.getId();
		isFavor = intent.getStringExtra("isFavor");
		isLogin = intent.getStringExtra("isLogin");
//		isFoot = intent.getStringExtra("isFoot");
		UnamefromNearBy = intent.getStringExtra("username");
		hotelname.setText(Mylocation.getName());
		type.setText("类型: " + Mylocation.getCate());
		price.setText("人均消费: " + Mylocation.getCost() + " 元");
		address.setText("地址: " + Mylocation.getAddr());
		distance.setText(Mylocation.getDist() + "km");
		tele.setText(Mylocation.getTel());
		if (Mylocation.getRate().equals("0")) {
			rate.setImageResource(R.drawable.xing0);
		} else if (Mylocation.getRate().equals("0.5")) {
			rate.setImageResource(R.drawable.xing1);
		} else if (Mylocation.getRate().equals("1")) {
			rate.setImageResource(R.drawable.xing2);
		} else if (Mylocation.getRate().equals("1.5")) {
			rate.setImageResource(R.drawable.xing3);
		} else if (Mylocation.getRate().equals("2")) {
			rate.setImageResource(R.drawable.xing4);
		} else if (Mylocation.getRate().equals("2.5")) {
			rate.setImageResource(R.drawable.xing5);
		} else if (Mylocation.getRate().equals("3")) {
			rate.setImageResource(R.drawable.xing6);
		} else if (Mylocation.getRate().equals("3.5")) {
			rate.setImageResource(R.drawable.xing7);
		} else if (Mylocation.getRate().equals("4")) {
			rate.setImageResource(R.drawable.xing8);
		} else if (Mylocation.getRate().equals("4.5")) {
			rate.setImageResource(R.drawable.xing9);
		} else if (Mylocation.getRate().equals("5")) {
			rate.setImageResource(R.drawable.xing10);
		}

		favorprint(isFavor,isLogin);
		if(ParseUser.getCurrentUser()!=null){
			ParseUser currentUser = ParseUser.getCurrentUser();
			ParseQuery q = new ParseQuery("footprint");
			q.whereEqualTo("BusinessID", Mylocation.getId());
			q.whereEqualTo("username", currentUser.getUsername().toString());
			q.findInBackground(new FindCallback() {

				@Override
				public void done(List<ParseObject> arg0, ParseException e) {
					// TODO Auto-generated method stub
					if(e == null){
						if(arg0.size() == 0){
							isFoot = "not";
							footprint(isFoot, isLogin);
						}else{
							isFoot = "is";
							footprint(isFoot, isLogin);
						}
					}else{
						isFoot = "not";
						footprint(isFoot, isLogin);
					}
				}
			});
		}else{
			favarButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent();
					i.setClass(ItemActivity.this, LoginActivity.class);
					ItemActivity.this.startActivity(i);	
				}
			});
		}


		AsyncHttpClient client = new AsyncHttpClient();
		client.get(
				ItemActivity.this,
				"http://openapi.aibang.com/biz/"
				+ Mylocation.getId()
				+ "/comments?app_key=0f1958aad1e661519a3e44e5c560f4f2&alt=json&from=1&to=30",
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONObject jsonObject) {
						progress_1.setVisibility(View.GONE);
				
						try {
							man_total.setText("评价:(共" + jsonObject.getString("total") + "条)");
							JSONObject json = jsonObject
									.getJSONObject("comments");
							JSONArray array = json.getJSONArray("comment");
							JSONObject u = array.getJSONObject(0);
							man_name.setText(u.getString("uname"));
							
							imageLoader(userImage, u.getString("avatar_url"));
							man_content.setText(u.getString("content"));
							Long timestamp = Long.parseLong(u.getString("pubtime")) * 1000;
							puttime.setText(new SimpleDateFormat(
									"yyyy/MM/dd HH:mm:ss").format(timestamp));
							if (u.getString("score").equals("0")) {
								rate_user.setImageResource(R.drawable.xing0);
							} else if (u.getString("score").equals("0.5")) {
								rate_user.setImageResource(R.drawable.xing1);
							} else if (u.getString("score").equals("1")) {
								rate_user.setImageResource(R.drawable.xing2);
							} else if (u.getString("score").equals("1.5")) {
								rate_user.setImageResource(R.drawable.xing3);
							} else if (u.getString("score").equals("2")) {
								rate_user.setImageResource(R.drawable.xing4);
							} else if (u.getString("score").equals("2.5")) {
								rate_user.setImageResource(R.drawable.xing5);
							} else if (u.getString("score").equals("3")) {
								rate_user.setImageResource(R.drawable.xing6);
							} else if (u.getString("score").equals("3.5")) {
								rate_user.setImageResource(R.drawable.xing7);
							} else if (u.getString("score").equals("4")) {
								rate_user.setImageResource(R.drawable.xing8);
							} else if (u.getString("score").equals("4.5")) {
								rate_user.setImageResource(R.drawable.xing9);
							} else if (u.getString("score").equals("5")) {
								rate_user.setImageResource(R.drawable.xing10);
							}
	
							linear.setOnClickListener(new OnClickListener() {
	
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Intent intent = new Intent();
									intent.putExtra("user", uid);
									intent.setClass(ItemActivity.this, UserCommentsActivity.class);
									ItemActivity.this.startActivity(intent);
								}
							});
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						super.onSuccess(jsonObject);
					}

				});

		AsyncHttpClient client1 = new AsyncHttpClient();
		client1.get(
				ItemActivity.this,
				"http://openapi.aibang.com/biz/"
				+ Mylocation.getId()
				+ "/pics?app_key=0f1958aad1e661519a3e44e5c560f4f2&alt=json&from=1&to=5",
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONObject jsonObject) {
						progress_2.setVisibility(View.GONE);
				
						try {
							man_total_11.setText("商户图片(共" + jsonObject.getString("total")
									+ "张)");
							JSONObject json = jsonObject
									.getJSONObject("pics");
							JSONArray array = json.getJSONArray("pic");
							if(array.length() == 1){
								imageLoader(itemImage0, array.getJSONObject(0).getString("url"));
							}else if(array.length() == 2){
								imageLoader(itemImage0, array.getJSONObject(0).getString("url"));
								imageLoader(itemImage1, array.getJSONObject(1).getString("url"));
							}else if(array.length() == 3){
								imageLoader(itemImage0, array.getJSONObject(0).getString("url"));
								imageLoader(itemImage1, array.getJSONObject(1).getString("url"));
								imageLoader(itemImage2, array.getJSONObject(2).getString("url"));
							}else if(array.length() == 4){
								imageLoader(itemImage0, array.getJSONObject(0).getString("url"));
								imageLoader(itemImage1, array.getJSONObject(1).getString("url"));
								imageLoader(itemImage2, array.getJSONObject(2).getString("url"));
								imageLoader(itemImage3, array.getJSONObject(3).getString("url"));
							}else if(array.length() == 5){
								imageLoader(itemImage0, array.getJSONObject(0).getString("url"));
								imageLoader(itemImage1, array.getJSONObject(1).getString("url"));
								imageLoader(itemImage2, array.getJSONObject(2).getString("url"));
								imageLoader(itemImage3, array.getJSONObject(3).getString("url"));
								imageLoader(itemImage4, array.getJSONObject(4).getString("url"));
							}
	
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						super.onSuccess(jsonObject);
					}

				});


//		userHandler = new Handler() {
//
//			@Override
//			public void handleMessage(Message msg) {
//				// TODO Auto-generated method stub
//				switch (msg.what) {
//				case 0:
//					if (user != null) {
//						progress_1.setVisibility(View.GONE);
////						man_total.setText("评价:(共" + user.getTotal() + "条)");
//						man_name.setText(user.getUname());
//						System.out.println("zhouruixin--->"
//								+ user.getAvatar_url());
//						loadImage(user.getAvatar_url(), userImage);
//						man_content.setText(user.getContent());
//						Long timestamp = Long.parseLong(user.getPubtime()) * 1000;
//						puttime.setText(new SimpleDateFormat(
//								"yyyy/MM/dd HH:mm:ss").format(timestamp));
//						if (user.getScore().equals("0")) {
//							rate_user.setImageResource(R.drawable.xing0);
//						} else if (user.getScore().equals("0.5")) {
//							rate_user.setImageResource(R.drawable.xing1);
//						} else if (user.getScore().equals("1")) {
//							rate_user.setImageResource(R.drawable.xing2);
//						} else if (user.getScore().equals("1.5")) {
//							rate_user.setImageResource(R.drawable.xing3);
//						} else if (user.getScore().equals("2")) {
//							rate_user.setImageResource(R.drawable.xing4);
//						} else if (user.getScore().equals("2.5")) {
//							rate_user.setImageResource(R.drawable.xing5);
//						} else if (user.getScore().equals("3")) {
//							rate_user.setImageResource(R.drawable.xing6);
//						} else if (user.getScore().equals("3.5")) {
//							rate_user.setImageResource(R.drawable.xing7);
//						} else if (user.getScore().equals("4")) {
//							rate_user.setImageResource(R.drawable.xing8);
//						} else if (user.getScore().equals("4.5")) {
//							rate_user.setImageResource(R.drawable.xing9);
//						} else if (user.getScore().equals("5")) {
//							rate_user.setImageResource(R.drawable.xing10);
//						}
//
//						linear.setOnClickListener(new OnClickListener() {
//
//							@Override
//							public void onClick(View v) {
//								// TODO Auto-generated method stub
//								Intent intent = new Intent();
//								intent.putExtra("user", (Serializable) userEvaluateItem);
//								intent.setClass(ItemActivity.this, UserCommentsActivity.class);
//								ItemActivity.this.startActivity(intent);
//							}
//						});
//					}
//					break;
//				case 1:
//					progress_2.setVisibility(View.GONE);
//					if (imageItem != null) {
//						for (int i = 0; i < imageItem.size(); i++) {
//							imgs[i] = imageItem.get(i);
//						}
//						man_total_11.setText("商户图片(共" + imgs[0].getTotal()
//								+ "张)");
//
//						if (imgs[0] != null) {
//							loadImage(imgs[0].getThumbnail_url(), itemImage0);
//						}
//						if (imgs[1] != null) {
//							loadImage(imgs[1].getThumbnail_url(), itemImage1);
//						}
//						if (imgs[2] != null) {
//							loadImage(imgs[2].getThumbnail_url(), itemImage2);
//						}
//						if (imgs[3] != null) {
//							loadImage(imgs[3].getThumbnail_url(), itemImage3);
//						}
//						if (imgs[4] != null) {
//							loadImage(imgs[4].getThumbnail_url(), itemImage4);
//						}
//					}
//				}
//			}
//
//		};

//		userThread = new Thread(new Runnable() {
//             boolean Myflag = true;
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while (Myflag) {
//					updateListView(
//							"http://openapi.aibang.com/biz/"
//									+ Mylocation.getId()
//									+ "/comments?app_key=0f1958aad1e661519a3e44e5c560f4f2&from=1&to=30",
//							"user");
//					Myflag = false;
//				}
//			}
//		});
//		userThread.setDaemon(true);
//		userThread.start();
//		imgThread = new Thread(new Runnable() {
//            boolean Myflag2 = true;
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while (Myflag2) {
//					updateListView(
//							"http://openapi.aibang.com/biz/"
//									+ Mylocation.getId()
//									+ "/pics?app_key=0f1958aad1e661519a3e44e5c560f4f2&from=1&to=5",
//							"image");
//					Myflag2 = false;
//				}
//			}
//		});
//		imgThread.start();
	}

	/**
	 * 根据URL下载xml文件
	 * */
	private String downloadXML(String urlStr) {

		HttpDownloader httpDownloader = new HttpDownloader();
		String result = httpDownloader.download(urlStr);
		return result;
	}

	private void parse(String xmlStr, String name) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		if (name.equals("user")) {
			List<UserEvaluate> infos = new ArrayList<UserEvaluate>();
			try {
				XMLReader xmlReader = saxParserFactory.newSAXParser()
						.getXMLReader();

				UserEvaluateHandler userEvaluateHandler = new UserEvaluateHandler(
						infos);
				xmlReader.setContentHandler(userEvaluateHandler);
				xmlReader.parse(new InputSource(new StringReader(xmlStr)));

				userEvaluateItem = infos;
				if (userEvaluateItem.size() != 0) {
					for (int i = 0; i < 1; i++) {
						user = userEvaluateItem.get(i);
					}
				}

				Message msg = new Message();
				msg.what = 0;
				userHandler.sendMessage(msg);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (name.equals("image")) {
			List<ImageItem> infos = new ArrayList<ImageItem>();
			try {
				XMLReader xmlReader = saxParserFactory.newSAXParser()
						.getXMLReader();

				ImageContentHandler imageHandler = new ImageContentHandler(
						infos);
				xmlReader.setContentHandler(imageHandler);
				xmlReader.parse(new InputSource(new StringReader(xmlStr)));

				if (infos.size() != 0) {
					imageItem = infos;
				}

				Message msg = new Message();
				msg.what = 1;
				userHandler.sendMessage(msg);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void updateListView(String url, String name) {

		String xml = downloadXML(url);

		parse(xml, name);

	}

	private void loadImage(final String url, ImageView imageView) {
		CallbackImpl callbackImpl = new CallbackImpl(imageView);
		Drawable cacheImage = loader.loadDrawable(url, callbackImpl);
		if (cacheImage != null) {
			imageView.setImageDrawable(cacheImage);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			ItemActivity.this.setResult(2, null);
			ItemActivity.this.finish();
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK && null != data) {
			switch (requestCode) {
			case 1:
				Bitmap photo = null;
				ContentResolver resolver = getContentResolver();
				// 照片的原始资源地址
				Uri originalUri = data.getData();
				try {
					// 使用ContentProvider通过URI获取原始图片
					photo = MediaStore.Images.Media.getBitmap(resolver,
							originalUri);
					// if (photo != null) {
					// //为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
					// Bitmap smallBitmap = ImageTools.zoomBitmap(photo,
					// photo.getWidth() / SCALE, photo.getHeight() / SCALE);
					// //释放原始图片占用的内存，防止out of memory异常发生
					// photo.recycle();
					//
					// iv_image.setImageBitmap(smallBitmap);
					// }
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				File file1 = new File("/sdcard/Cz/");
				file1.mkdir();// 创建文件夹
				FileOutputStream outStream = null;
				try {
					// 打开指定文件对应的输出流
					outStream = new FileOutputStream("/sdcard/Cz/picture.jpg");
					// 把位图输入到指定文件中
					photo.compress(CompressFormat.JPEG, 100, outStream);
					outStream.close();
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				Intent intent1 = new Intent(this, AddImgActivity.class);
				intent1.putExtra("username", username);
				intent1.putExtra("path", "/sdcard/Cz/picture.jpg");
				intent1.putExtra("ID", Mylocation.getId());
				intent1.putExtra("name", Mylocation.getName());
				startActivity(intent1);
				break;
			case 2:
				Bitmap bitmap2 = (Bitmap) data.getExtras().get("data");
				File file2 = new File("/sdcard/Czwy/");
				file2.mkdir();// 创建文件夹
				FileOutputStream outStream2 = null;
				try {
					// 打开指定文件对应的输出流
					outStream2 = new FileOutputStream(
							"/sdcard/Czwy/picture.jpg");
					// 把位图输入到指定文件中
					bitmap2.compress(CompressFormat.JPEG, 100, outStream2);
					outStream2.close();
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				Intent intent2 = new Intent();
				intent2.putExtra("username", username);
				intent2.putExtra("path", "/sdcard/Czwy/picture.jpg");
				intent2.putExtra("ID", Mylocation.getId());
				intent2.putExtra("name", Mylocation.getName());
				intent2.setClass(ItemActivity.this, AddImgActivity.class);
				ItemActivity.this.startActivity(intent2);
				break;
			}
		}

	}

	/**
	 * 
	 * 用户收藏
	 * */
	private void favorprint(String isFoot,String isUser){

		if(isFoot.equals("is") && isUser.equals("is")){
			Resources res = getResources();
			Drawable drawable = res
					.getDrawable(R.drawable.title_icon_favorite_on);
			scangButton.setBackgroundDrawable(drawable);

			scangButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					progress_4.setVisibility(View.VISIBLE);
					ParseQuery query = new ParseQuery("favorite");
					query.whereEqualTo("BusinessID", Mylocation.getId());
					query.whereEqualTo("username", UnamefromNearBy);
					query.findInBackground(new FindCallback() {

						@Override
						public void done(List<ParseObject> arg0, ParseException e) {
							// TODO Auto-generated method stub
							if (e == null) {
								if(arg0.size() != 0){
									ParseObject b = null;
									for(int i = 0; i<arg0.size(); i++){
										b = arg0.get(i);
									}
									 ParseObject footprint = new ParseObject("favorite");
									 footprint.setObjectId(b.getObjectId());
							           footprint.deleteInBackground(new DeleteCallback() {

										@Override
										public void done(ParseException arg0) {
											// TODO Auto-generated method stub
											if(arg0 == null){
												setUI_1("删除成功!","success");
												progress_4.setVisibility(View.GONE);
												}else{
													setUI_1("删除失败!","false");
													progress_4.setVisibility(View.GONE);
												}
										}
									});


								}else{
									Toast.makeText(ItemActivity.this, "添加失败", Toast.LENGTH_LONG).show();
									progress_4.setVisibility(View.GONE);
								}
							} else {
								Toast.makeText(ItemActivity.this, "添加失败", Toast.LENGTH_LONG).show();
								progress_4.setVisibility(View.GONE);
							}
						}
					});


				}
			});
		}else if(isFoot.equals("not") && isUser.equals("is")){
			scangButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					progress_4.setVisibility(View.VISIBLE);
					ParseObject favor1 = new ParseObject("favorite");
		            favor1.put("username", UnamefromNearBy);
		            favor1.put("BusinessID", Mylocation.getId());
		            favor1.put("businessName", Mylocation.getName());
		            favor1.put("score", Mylocation.getRate());
		            favor1.put("address", Mylocation.getAddr());
		            favor1.put("type", Mylocation.getCate());
		            favor1.put("cost", Mylocation.getCost());
		            favor1.put("teleNum", Mylocation.getTel());
		            favor1.put("lng", Mylocation.getLng());
		            favor1.put("lat", Mylocation.getLat());
		            favor1.put("dist", Mylocation.getDist());
		            favor1.saveInBackground(new SaveCallback() {

						@Override
						public void done(ParseException arg0) {
							// TODO Auto-generated method stub
							if(arg0 == null){
								setUI_1("收藏成功!", "success1");
								progress_4.setVisibility(View.GONE);
							}else{
								setUI_1("收藏失败!", "false1");
								progress_4.setVisibility(View.GONE);
							}
						}
					});
				}
			});
		}else if(isFoot.equals("not") && isUser.equals("not")){
			scangButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent();
					i.setClass(ItemActivity.this, LoginActivity.class);
					ItemActivity.this.startActivity(i);	
				}
			});

		}

	}

	/**
	 * 
	 * 用户到过的地方
	 * */
	private void footprint( String isFava,String isUser){
	if(isFava.equals("is") && isUser.equals("is")){
		Resources res = getResources();
		Drawable drawable = res
				.getDrawable(R.drawable.ic_btn_check_on);
		favarButton.setBackgroundDrawable(drawable);
		fava.setText("已经来过");

		favarButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                progress_3.setVisibility(View.VISIBLE);
				ParseQuery query = new ParseQuery("footprint");
				query.whereEqualTo("BusinessID", Mylocation.getId());
				query.whereEqualTo("username", UnamefromNearBy);
				query.findInBackground(new FindCallback() {

					@Override
					public void done(List<ParseObject> arg0, ParseException e) {
						// TODO Auto-generated method stub
						if (e == null) {
							if(arg0.size() != 0){
								ParseObject b = null;
								for(int i = 0; i<arg0.size(); i++){
									b = arg0.get(i);
								}
								 ParseObject footprint = new ParseObject("footprint");
								 footprint.setObjectId(b.getObjectId());
						           footprint.deleteInBackground(new DeleteCallback() {

									@Override
									public void done(ParseException arg0) {
										// TODO Auto-generated method stub
										if(arg0 == null){
											setUI("删除成功!","success");
											progress_3.setVisibility(View.GONE);
											}else{
												setUI("删除失败!","false");
												progress_3.setVisibility(View.GONE);
											}
									}
								});


							}else{
								Toast.makeText(ItemActivity.this, "添加失败", Toast.LENGTH_LONG).show();
								progress_3.setVisibility(View.GONE);
							}
						} else {
							Toast.makeText(ItemActivity.this, "添加失败", Toast.LENGTH_LONG).show();
							progress_3.setVisibility(View.GONE);
						}
					}
				});


			}
		});
	}else if(isFava.equals("not") && isUser.equals("is")){
		favarButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				progress_3.setVisibility(View.VISIBLE);
	            ParseObject footprin = new ParseObject("footprint");
	            footprin.put("username", UnamefromNearBy);
	            footprin.put("BusinessID", Mylocation.getId());
	            footprin.put("businessName", Mylocation.getName());
	            footprin.put("lng", String.valueOf(Mylocation.getLng()));
	            footprin.put("lat", String.valueOf(Mylocation.getLat()));
	            footprin.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException arg0) {
						// TODO Auto-generated method stub
						if(arg0 == null){
							setUI("添加足迹成功!", "success1");
							progress_3.setVisibility(View.GONE);
						}else{
							setUI("添加足迹失败!", "false1");
							progress_3.setVisibility(View.GONE);
						}
					}
				});
			}
		});
	}else if(isFava.equals("not") && isUser.equals("not")){
		favarButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(ItemActivity.this, LoginActivity.class);
				ItemActivity.this.startActivity(i);	
			}
		});

	}
}


	private void setUI(String msg1,String msg2){
		if(msg2.equals("success")){
			Resources res = getResources();
			Drawable drawable = res
					.getDrawable(R.drawable.ic_btn_check_off);
			favarButton.setBackgroundDrawable(drawable);
			fava.setText("还没来过");
			footprint("not", "is");
			Toast.makeText(ItemActivity.this, msg1, Toast.LENGTH_LONG).show();
		}else if(msg2.equals("false")){
			Toast.makeText(ItemActivity.this, msg1, Toast.LENGTH_LONG).show();
		}else if(msg2.equals("success1")){
			Resources res = getResources();
			Drawable drawable = res
					.getDrawable(R.drawable.ic_btn_check_on);
			favarButton.setBackgroundDrawable(drawable);
			fava.setText("已经来过");
			footprint("is", "is");
			Toast.makeText(ItemActivity.this, msg1, Toast.LENGTH_LONG).show();
		}else if(msg2.equals("false1")){
			Toast.makeText(ItemActivity.this, msg1, Toast.LENGTH_LONG).show();
		}
	}

	private void setUI_1(String msg1,String msg2){
		if(msg2.equals("success")){
			Resources res = getResources();
			Drawable drawable = res
					.getDrawable(R.drawable.title_icon_favorite_off);
			scangButton.setBackgroundDrawable(drawable);
			favorprint("not", "is");
			Toast.makeText(ItemActivity.this, msg1, Toast.LENGTH_LONG).show();
		}else if(msg2.equals("false")){
			Toast.makeText(ItemActivity.this, msg1, Toast.LENGTH_LONG).show();
		}else if(msg2.equals("success1")){
			Resources res = getResources();
			Drawable drawable = res
					.getDrawable(R.drawable.ic_btn_check_on);
			scangButton.setBackgroundDrawable(drawable);
			favorprint("is", "is");
			Toast.makeText(ItemActivity.this, msg1, Toast.LENGTH_LONG).show();
		}else if(msg2.equals("false1")){
			Toast.makeText(ItemActivity.this, msg1, Toast.LENGTH_LONG).show();
		}
	}


	/**
	 * 检查存储卡是否插入
	 * 
	 * @return
	 */
	public static boolean isHasSdcard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	public void imageLoader(ImageView image, String url) {

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(0).showImageForEmptyUri(0).showImageOnFail(0)
				.resetViewBeforeLoading(false) // default
				.delayBeforeLoading(1000).cacheInMemory(true) // default
				.cacheOnDisc(true) // default
				// .preProcessor(...)
				// .postProcessor(...)
				// .extraForDownloader(...)
				.imageScaleType(ImageScaleType.EXACTLY) // default
				.bitmapConfig(Bitmap.Config.ARGB_8888) // default
				// .decodingOptions(...)
				.displayer(new SimpleBitmapDisplayer()) // default
				.handler(new Handler()) // default
				.build();

		File cacheDir = StorageUtils.getCacheDirectory(ItemActivity.this);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				ItemActivity.this)
				.memoryCacheExtraOptions(480, 800)
				// default = device screen dimensions
				// .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75,
				// null)
				.threadPoolSize(3)
				// default
				.threadPriority(Thread.NORM_PRIORITY - 1)
				// default
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// default
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
				// default
				.discCache(new UnlimitedDiscCache(cacheDir))
				// default
				.discCacheSize(50 * 1024 * 1024).discCacheFileCount(100)
				.discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
				// .imageDownloader(new BaseImageDownloader(context)) // default
				// .imageDecoder(new BaseImageDecoder()) // default
				.defaultDisplayImageOptions(options) // default
				.writeDebugLogs().build();
		ImageLoader.getInstance().init(config);

		ImageLoader.getInstance().displayImage(url, image);
	}
}