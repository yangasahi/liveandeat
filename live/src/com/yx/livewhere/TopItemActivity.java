package com.yx.livewhere;

import java.io.Serializable;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.yx.model.ImageItem;
import com.yx.model.UserEvaluate;
import com.yx.util.AsyncImageLoader;
import com.yx.util.CallbackImpl;
import com.yx.util.HttpDownloader;
import com.yx.xml.ImageContentHandler;
import com.yx.xml.UserEvaluateHandler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TopItemActivity extends Activity{

	private Handler userHandler = null;
	private Thread userThread = null;
	private Thread imgThread = null;
	private String businessID,tel,add,pri,businessName = null;
	private List<UserEvaluate> userEvaluateItem;
	private UserEvaluate user = null;
	private ImageItem[] imgs = new ImageItem[5];
	private List<ImageItem> imageItem;
	private ProgressBar progress_1= null;
//	private ProgressBar progress_1,progress_2 = null;
	private ImageView userImage, itemImage0, itemImage1,
	itemImage2, itemImage3, itemImage4 = null;
	private TextView hotelname,  price, address, tele,
	man_total, man_name, man_content, puttime, man_total_11 = null;
	private LinearLayout linear = null;
	private AsyncImageLoader loader = new AsyncImageLoader();
	private ImageButton backImageButton = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ﾃｻﾓﾐｱ��
		setContentView(R.layout.topitem);
		
		backImageButton = (ImageButton) findViewById(R.id.img_search);
		backImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				TopItemActivity.this.finish();
			}
		});
		
		Intent intent = getIntent();
		businessID = intent.getStringExtra("businessID");
		tel = intent.getStringExtra("tel");
		add = intent.getStringExtra("address");
		pri = intent.getStringExtra("price");
		businessName = intent.getStringExtra("businessName");
		System.out.println("yyyyyyyyyyyyyyy--->" + businessID);
		
		progress_1 = (ProgressBar)findViewById(R.id.progress_1);
//		progress_2 = (ProgressBar)findViewById(R.id.progress_2);
				
		man_total = (TextView) findViewById(R.id.man_total);
		man_name = (TextView) findViewById(R.id.man_name);
		man_content = (TextView) findViewById(R.id.man_content);
		puttime = (TextView) findViewById(R.id.timeTextView);
		hotelname = (TextView) findViewById(R.id.hotelname);
		price = (TextView) findViewById(R.id.price0);
		address = (TextView) findViewById(R.id.address);
		tele = (TextView) findViewById(R.id.telephone_num);
		man_total_11 = (TextView) findViewById(R.id.man_total_11);
		hotelname.setText(businessName);
		price.setText( pri);
		address.setText("ｵﾘﾖｷ: " + add);
		tele.setText(tel);
		
		userImage = (ImageView) findViewById(R.id.userImage);
		itemImage0 = (ImageView) findViewById(R.id.itemImage0);
		itemImage1 = (ImageView) findViewById(R.id.itemImage1);
		itemImage2 = (ImageView) findViewById(R.id.itemImage2);
		itemImage3 = (ImageView) findViewById(R.id.itemImage3);
		itemImage4 = (ImageView) findViewById(R.id.itemImage4);
		
		linear = (LinearLayout) findViewById(R.id.comm_layout);
		
		userHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case 0:
					if (user != null) {
						progress_1.setVisibility(View.GONE);
//						man_total.setText("ﾆﾀｼﾛ:(ｹｲ" + user.getTotal() + "ﾌ�)");
						man_name.setText(user.getUname());
						loadImage(user.getAvatar_url(), userImage);
						man_content.setText(user.getContent());
						Long timestamp = Long.parseLong(user.getPubtime()) * 1000;
						puttime.setText(new SimpleDateFormat(
								"yyyy/MM/dd HH:mm:ss").format(timestamp));
						
						linear.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Intent intent = new Intent();
								intent.putExtra("user", (Serializable) userEvaluateItem);
								intent.setClass(TopItemActivity.this, UserCommentsActivity.class);
								TopItemActivity.this.startActivity(intent);
							}
						});
					}
					break;
				case 1:
//					progress_2.setVisibility(View.GONE);
					if (imageItem != null) {
						for (int i = 0; i < imageItem.size(); i++) {
							imgs[i] = imageItem.get(i);
						}
						man_total_11.setText("ﾉﾌｻｧﾍｼﾆｬ(ｹｲ" + imgs[0].getTotal()
								+ "ﾕﾅ)");

						if (imgs[0] != null) {
							loadImage(imgs[0].getThumbnail_url(), itemImage0);
						}
						if (imgs[1] != null) {
							loadImage(imgs[1].getThumbnail_url(), itemImage1);
						}
						if (imgs[2] != null) {
							loadImage(imgs[2].getThumbnail_url(), itemImage2);
						}
						if (imgs[3] != null) {
							loadImage(imgs[3].getThumbnail_url(), itemImage3);
						}
						if (imgs[4] != null) {
							loadImage(imgs[4].getThumbnail_url(), itemImage4);
						}
					}
				}
			}

		};
		
		
		userThread = new Thread(new Runnable() {
            boolean Myflag = true;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (Myflag) {
					updateListView(
							"http://openapi.aibang.com/biz/"
									+ businessID
									+ "/comments?app_key=0f1958aad1e661519a3e44e5c560f4f2&from=0&to=40",
							"user");
					Myflag = false;
				}
			}
		});
		userThread.setDaemon(true);
		userThread.start();
//		imgThread = new Thread(new Runnable() {
//           boolean Myflag2 = true;
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while (Myflag2) {
//					updateListView(
//							"http://openapi.aibang.com/biz/"
//									+ businessID
//									+ "/pics?app_key=0f1958aad1e661519a3e44e5c560f4f2",
//							"image");
//					Myflag2 = false;
//				}
//			}
//		});
//		imgThread.start();
	}
	private void updateListView(String url, String name) {

		String xml = downloadXML(url);

		parse(xml, name);

	}
	/**
	 * ｸ�ｾﾝURLﾏﾂﾔﾘxmlﾎﾄｼ�
	 * */
	private String downloadXML(String urlStr) {

		HttpDownloader httpDownloader = new HttpDownloader();
		String result = httpDownloader.download(urlStr);
		return result;
	}
	
	private void loadImage(final String url, ImageView imageView) {
		CallbackImpl callbackImpl = new CallbackImpl(imageView);
		Drawable cacheImage = loader.loadDrawable(url, callbackImpl);
		if (cacheImage != null) {
			imageView.setImageDrawable(cacheImage);
		}
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
}
