//package com.yx.livewhere;
//
//import java.io.File;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.parse.LogInCallback;
//import com.parse.Parse;
//import com.parse.ParseAnalytics;
//import com.parse.ParseUser;
//import com.parse.SaveCallback;
//
//public class ReImageActivity extends Activity{
//
//	private ImageView imageView = null;
//	private ImageButton backButton = null;
//	private Button button = null;
//	private ProgressDialog progressDialog = null;
//	private String path = null;
//	private ParseUser currentUser = null;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.reimage);
//		Parse.initialize(this, "IlQ8wBml4IkepGG0tuphk2VDIAfTuvcXX9OJOJfh", "NMcva8057XBA0mdRwlLKvLGXxL9uzQABVFAaVbyO");
//		ParseAnalytics.trackAppOpened(null);
//		backButton = (ImageButton)findViewById(R.id.img_search);
//		backButton.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				ReImageActivity.this.finish();
//			}
//		});
//		Intent intent = getIntent();
//	    path = intent.getStringExtra("path");
//		Bitmap bm = BitmapFactory.decodeFile(path);
//		
//		imageView = (ImageView)findViewById(R.id.twill);
//		imageView.setImageBitmap(bm);
//		button = (Button)findViewById(R.id.login_login_btn);
//		button.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				progressDialog = ProgressDialog.show(ReImageActivity.this, "提交中",
//						"请稍等...", true);
//				progressDialog.setCancelable(true);
//			 currentUser = ParseUser.getCurrentUser();
//				BmobFile bmobfile;
//				try {
//					// 请确保sdcard目录下有Vlog.xml此文件（可换为自己的文件）
//					bmobfile = new BmobFile("image", new File(path));
//					bmobfile.save();
//
//					currentUser.put("image", bmobfile);
//				} catch (Exception e) {
//					Log.i("bmob", "保存文件错误：" + e.getMessage());
//				}
//				currentUser.saveInBackground(new SaveCallback() {
//					
//					@Override
//					public void done(BmobException e) {
//						// TODO Auto-generated method stub
//						if (e == null) {
//							//更新成功	
//							String pass = null;
//							try {
//								pass = currentUser.get("password").toString();
//							} catch (BmobException e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							}
//							printResult("修改成功!","success",currentUser.getUsername().toString(),pass);
//						} else {
//							//更新失败
//							printResult("修改失败!","false",null,null);
//						}
//					}
//				});
//			}
//		});
//	}
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// TODO Auto-generated method stub
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//   			ReImageActivity.this.progressDialog.cancel();
//   			ReImageActivity.this.progressDialog.dismiss();
//   			ReImageActivity.this.finish();
//		}
//		return true;
//	}
//	
//	private void printResult(String msg,String msg2,String username,String password) {
//		if(msg2.equals("success")){
//			BmobUser.logInInBackground(username, password, new LogInCallback() {
//			    public void done(BmobUser user, BmobException e) {
//			        if (user != null) {
//			        	Intent intent  = new Intent();
//						intent.setClass(ReImageActivity.this, LoginSuccActivity.class);
//						ReImageActivity.this.startActivity(intent);
//						progressDialog.dismiss();
//			        } 
//			    }
//			});
//			Toast.makeText(ReImageActivity.this, msg, Toast.LENGTH_SHORT).show();
//			
//		
//		}else{
//			Toast.makeText(ReImageActivity.this, msg, Toast.LENGTH_SHORT).show();
//		}
//	}
//
//	
//}
