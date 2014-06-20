package com.yx.livewhere;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.OverlayItem;
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
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RefreshCallback;
import com.parse.SaveCallback;
import com.yx.util.AsyncImageLoader;
import com.yx.util.CallbackImpl;
import com.yx.util.FootItemsOverLay;
import com.yx.util.MyApplication;

public class LoginSuccActivity extends MapActivity {

	private TextView nickName = null;
	private ImageButton loginButton, editButton, favarButton,commButton = null;
	private ImageView imageView = null;
	private boolean display = true;
	private PopupWindow popupWindow = null;
	private Button camarabutton, xiangceButton = null;
	private ParseUser currentUser = null;
	private AsyncImageLoader loader = new AsyncImageLoader();
	private String isCache = null;
	private String haveImage = null;
	MapView mMapView = null;
	private boolean logOut = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题
		setContentView(R.layout.login_success);
		Parse.initialize(this, "IlQ8wBml4IkepGG0tuphk2VDIAfTuvcXX9OJOJfh",
				"NMcva8057XBA0mdRwlLKvLGXxL9uzQABVFAaVbyO");
		ParseAnalytics.trackAppOpened(null);
		favarButton = (ImageButton) findViewById(R.id.favar);
		commButton = (ImageButton)findViewById(R.id.comm);
		
		loginButton = (ImageButton) findViewById(R.id.login_button);

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
		mMapView.getController().setZoom(5);
		mMapView.invalidate();

		final Drawable marker = LoginSuccActivity.this.getResources()
				.getDrawable(R.drawable.location_red);// 创建图标资源（用于显示在overlayItem所标记的位置）
		// 为maker定义位置和边界
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());
		ParseQuery footQuery = new ParseQuery("footprint");
		currentUser = ParseUser.getCurrentUser();
		footQuery
				.whereEqualTo("username", currentUser.getUsername().toString());
		footQuery.findInBackground(new FindCallback() {
			@Override
			public void done(List<ParseObject> footprints, ParseException e) {
				if (e == null) {
					if (footprints.size() != 0) {
						// Get list of players
						FootItemsOverLay overlays = new FootItemsOverLay(
								marker, LoginSuccActivity.this);
						for (int i = 0; i < footprints.size(); i++) {
							GeoPoint ptitem = new GeoPoint((int) (Double
									.parseDouble(footprints.get(i).getString(
											"lat")) * 1E6), (int) (Double
									.parseDouble(footprints.get(i).getString(
											"lng")) * 1E6));
							OverlayItem overlayItem = new OverlayItem(ptitem,
									null, footprints.get(i).getString(
											"businessName"));
							overlays.addOverlay(overlayItem);
							mMapView.getOverlays().add(overlays);
							mMapView.invalidate();
							isFoot(true);
						}

					} else if(footprints.size() == 0){
						Toast.makeText(LoginSuccActivity.this, "还没有留下足迹...",
								Toast.LENGTH_LONG).show();
						isFoot(true);
					}
				} else {
					Toast.makeText(LoginSuccActivity.this, "读取我的足迹出错...",
							Toast.LENGTH_LONG).show();
				}

			}
		});

		nickName = (TextView) findViewById(R.id.nicheng);
		imageView = (ImageView) findViewById(R.id.add_friend);
		Intent intent = new Intent();
		intent = getIntent();
		isCache = intent.getStringExtra("isCache");
		haveImage = intent.getStringExtra("haveImage");
		// if (isCache.equals("yes") && haveImage.equals("is")) {
		// currentUser = ParseUser.getCurrentUser();
		// nickName.setText(currentUser.getUsername());
		// } else
		if (isCache.equals("yes") && haveImage.equals("fromLogin")) {
			currentUser = ParseUser.getCurrentUser();
			nickName.setText(currentUser.getUsername());
			ParseFile bmobFile;
			bmobFile = currentUser.getParseFile("uimage");
			if (bmobFile != null) {
				System.out.println("jinru");
				imageLoader(imageView, bmobFile.getUrl().toString());
			}

		} else if (isCache.equals("yes") && haveImage.equals("fromRegist")) {
			currentUser = ParseUser.getCurrentUser();
			nickName.setText(currentUser.getUsername());
		}

		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (display) {
					display = false;
					View layout = getLayoutInflater().inflate(
							R.layout.popwindow, null);
					layout.getBackground().setAlpha(200);
					popupWindow = new PopupWindow(layout, 200, 150);
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
							Intent intent = new Intent();
							intent.setAction(Intent.ACTION_PICK);
							intent.setType("image/*");
							startActivityForResult(intent, 1);
						}
					});
					camarabutton.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							Intent i = new Intent(
									"android.media.action.IMAGE_CAPTURE");
							startActivityForResult(i, 2);
						}
					});
				} else {
					popupWindow.dismiss();
					display = true;
				}
			}
		});

		editButton = (ImageButton) findViewById(R.id.editNickname);
		editButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LoginSuccActivity.this, ReNameActivity.class);
				LoginSuccActivity.this.startActivity(intent);
			}
		});

		isFoot(false);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK && null != data) {
			switch (requestCode) {
			case 1:
				Uri originalUri = data.getData();
				compress(originalUri);
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
				Uri uri = Uri.fromFile(new File("/sdcard/Czwy/picture.jpg"));
				compress(uri);
				break;
			case 3:
				Bundle extras = data.getExtras();
				Bitmap p = extras.getParcelable("data");
				ReImage(p);
				imageView.setImageBitmap(p);
				break;
			}
		}

	}

	private void ReImage(Bitmap bitmap) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		byte[] array = out.toByteArray();
		ParseFile bmobfile = new ParseFile("pic.jpg", array);
		try {
			bmobfile.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentUser.put("uimage", bmobfile);

		currentUser.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				// TODO Auto-generated method stub
				if (e == null) {
					// 更新成功
					System.out.println("gggggg--->" + "更新成功");
				} else {
					// 更新失败
					System.out.println("gggggg--->" + "更新失败");
				}
			}
		});
		currentUser.refreshInBackground(new RefreshCallback() {

			@Override
			public void done(ParseObject arg0, ParseException e) {
				// TODO Auto-generated method stub
				if (e == null) {
					System.out.println("gaosiwei--->更新成功");
				} else {
					System.out.println("gaosiwei--->更新失败");
				}
			}
		});
		// loadImage(url, imageView);
	}

	private void compress(Uri uri) {
		// Uri uri = Uri.fromFile(new File(url));
		Intent intent = new Intent("com.android.camera.action.CROP");
		// 传入裁剪图片的路径uri和需要裁剪的格式
		intent.setDataAndType(uri, "image/*");
		// 可裁剪状态
		intent.putExtra("crop", true);
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 100);
		intent.putExtra("outputY", 100);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

	private void loadImage(final String url, ImageView imageView) {
		CallbackImpl callbackImpl = new CallbackImpl(imageView);
		Drawable cacheImage = loader.loadDrawable(url, callbackImpl);

		if (cacheImage != null) {
			imageView.setImageDrawable(cacheImage);
		}
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {

		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		// Canvas canvas = new Canvas(bitmap);
		// //canvas.setBitmap(bitmap);
		// drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
		// drawable.getIntrinsicHeight());
		// drawable.draw(canvas);
		return bitmap;
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK  && event.getRepeatCount() == 0) {
			
				LoginSuccActivity.this.finish();
				overridePendingTransition(R.anim.out, R.anim.out_to_right);
		
		}
		return true;
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 点击按钮之前，先判断地图是否加载完成
	 * */
	private void isFoot(boolean flag) {
		if (flag) {
			loginButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ParseUser.logOut(); // 清除缓存用户对象
					Intent intent = new Intent();
					intent.setClass(LoginSuccActivity.this, MyActivity.class);
					LoginSuccActivity.this.startActivity(intent);
					LoginSuccActivity.this.finish();
				}
			});
			

			favarButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(LoginSuccActivity.this,
							MyFavorActivity.class);
					LoginSuccActivity.this.startActivity(intent);
				}
			});
			commButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(LoginSuccActivity.this,
							MyCommActivity.class);
					LoginSuccActivity.this.startActivity(intent);
				}
			});
		} else {
			loginButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(LoginSuccActivity.this, "正在加载地图，请稍候...,",
							Toast.LENGTH_LONG).show();
				}
			});
			favarButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(LoginSuccActivity.this, "正在加载地图，请稍候...,",
							Toast.LENGTH_LONG).show();
				}
			});

          commButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(LoginSuccActivity.this, "正在加载地图，请稍候...,",
							Toast.LENGTH_LONG).show();
				}
			});
         
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

		File cacheDir = StorageUtils.getCacheDirectory(LoginSuccActivity.this);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				LoginSuccActivity.this)
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