package com.yx.livewhere;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity{

	private ImageButton backButton = null;
	private EditText email,pass = null;
	private Button submitButton = null;
	private ProgressDialog progressDialog = null;
	private Button fogetButton = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ﾃｻﾓﾐｱ��
		setContentView(R.layout.login);
		Parse.initialize(this, "IlQ8wBml4IkepGG0tuphk2VDIAfTuvcXX9OJOJfh", "NMcva8057XBA0mdRwlLKvLGXxL9uzQABVFAaVbyO");
		ParseAnalytics.trackAppOpened(null);
		backButton = (ImageButton)findViewById(R.id.img_search);
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LoginActivity.this.finish();
				overridePendingTransition(R.anim.out, R.anim.out_to_right);
			}
		});
		email = (EditText)findViewById(R.id.login_user_edit);
		pass = (EditText)findViewById(R.id.login_passwd_edit);
		
		fogetButton = (Button)findViewById(R.id.forget_passwd);
		fogetButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, FindActivity.class);
				LoginActivity.this.startActivity(intent);
			}
		});
		
		submitButton = (Button)findViewById(R.id.login_login_btn);
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				progressDialog = ProgressDialog.show(LoginActivity.this, "ｵﾇﾂｼﾖﾐ",
						"ﾇ�ﾔｵﾈ...", true);
				progressDialog.setCancelable(true);
				ParseUser.logInInBackground(email.getText().toString(), pass.getText().toString(), new LogInCallback() {
					
					@Override
					public void done(ParseUser user, ParseException e) {
						// TODO Auto-generated method stub
				        if (user != null) {
									printResult("登陆成功","success","fromLogin");
				        } else {
				        	printResult("登陆失败","false",null);
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
//			if(progressDialog.isShowing() == true){
//				LoginActivity.this.progressDialog.cancel();
//				LoginActivity.this.progressDialog.dismiss();
//			}
			LoginActivity.this.finish();
		}
		return true;
	}
	
	private void printResult(String msg,String msg2,String msg3) {
		progressDialog.dismiss();
		if(msg2.equals("success")){
				Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
				Intent intent  = new Intent();
				intent.putExtra("isCache", "yes");
				intent.putExtra("haveImage", msg3);
				intent.setClass(LoginActivity.this, LoginSuccActivity.class);
				LoginActivity.this.startActivity(intent);
				LoginActivity.this.finish();
		}else{
			Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
		}
	}
}
