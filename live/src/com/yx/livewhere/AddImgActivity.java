package com.yx.livewhere;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class AddImgActivity extends Activity {

	private ImageView imageView = null;
	private Bitmap bm = null;
	private String ID = null;
	private String name = null;
	private EditText cost = null;
	private Button submitButton = null;
	private ImageButton backButton = null;
	private String imagpath,username;
	private ProgressDialog progressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题
		setContentView(R.layout.add_img);
		Parse.initialize(this, "IlQ8wBml4IkepGG0tuphk2VDIAfTuvcXX9OJOJfh", "NMcva8057XBA0mdRwlLKvLGXxL9uzQABVFAaVbyO");
		ParseAnalytics.trackAppOpened(null);
		backButton = (ImageButton) findViewById(R.id.img_search);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddImgActivity.this.finish();
			}
		});

		imageView = (ImageView) findViewById(R.id.twill);
		// 接收上一个Activity传过来的相片地址
		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");
		name = intent.getStringExtra("name");
		username = intent.getStringExtra("username");
		String path = intent.getStringExtra("path");
		if (path.equals("/sdcard/Cz/picture.jpg")) {
			bm = BitmapFactory.decodeFile(path);
			imagpath = "/sdcard/Cz/picture.jpg";
		} else if(path.equals("/sdcard/Czwy/picture.jpg")){
			bm = BitmapFactory.decodeFile(path);
			imagpath = "/sdcard/Czwy/picture.jpg";
		}
		imageView.setImageBitmap(bm);

		cost = (EditText) findViewById(R.id.cost_edit);
		submitButton = (Button) findViewById(R.id.submit);
		submitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				progressDialog = ProgressDialog.show(AddImgActivity.this, "提交图片中",
						"请稍等...", true);
				progressDialog.setCancelable(true);
				ParseObject image = new ParseObject("image");
				
				Bitmap bm = BitmapFactory.decodeFile(imagpath);
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
				byte[] array= out.toByteArray();
				ParseFile parseFile = new ParseFile("pic.jpg",array);
				try {
					parseFile.save();
					image.put("imageContent", parseFile);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                image.put("username", username);
				image.put("businessID", ID);
				image.put("businessName", name);
				image.put("title", cost.getText().toString());
//				ParseACL publicACL = new ParseACL();
//				publicACL.setPublicReadAccess(true);
//				publicACL.setPublicWriteAccess(true);      
//				image.setACL(publicACL);
				image.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(ParseException e) {
						// TODO Auto-generated method stub
						if (e == null) {
							printResult("提交成功");
						} else {
							printResult("提交失败");
						}
					}
				});
			}
		});

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
   			AddImgActivity.this.progressDialog.cancel();
   			AddImgActivity.this.progressDialog.dismiss();
   			AddImgActivity.this.finish();
		}
		return true;
	}
	private void printResult(String msg) {
		progressDialog.dismiss();
		Toast.makeText(AddImgActivity.this, msg, Toast.LENGTH_SHORT).show();
	}
}
