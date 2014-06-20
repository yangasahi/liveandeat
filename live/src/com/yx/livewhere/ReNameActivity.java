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

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ReNameActivity extends Activity {

	private ImageButton backButton = null;
	private EditText editText = null;
	private Button button = null;
	private ProgressDialog progressDialog = null;
	private ParseUser currentUser = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题
		setContentView(R.layout.renickname);
		Parse.initialize(this, "IlQ8wBml4IkepGG0tuphk2VDIAfTuvcXX9OJOJfh", "NMcva8057XBA0mdRwlLKvLGXxL9uzQABVFAaVbyO");
		ParseAnalytics.trackAppOpened(null);
		backButton = (ImageButton) findViewById(R.id.img_search);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ReNameActivity.this.finish();
			}
		});
		editText = (EditText) findViewById(R.id.login_user_edit);
		button = (Button) findViewById(R.id.login_login_btn);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				progressDialog = ProgressDialog.show(ReNameActivity.this,
						"提交中", "请稍等...", true);
				progressDialog.setCancelable(true);
				currentUser = ParseUser.getCurrentUser();
				currentUser.put("username", editText.getText().toString());
				currentUser.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						// TODO Auto-generated method stub
						if (e == null) {
							// 更新成功
							printResult("修改成功!", "success");
						} else {
							// 更新失败
							printResult("修改失败!", "false");
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
//				ReNameActivity.this.progressDialog.cancel();
//				ReNameActivity.this.progressDialog.dismiss();
//			}
			ReNameActivity.this.finish();
		}
		return true;
	}

	private void printResult(String msg, String msg2) {
		if (msg2.equals("success")) {
			progressDialog.dismiss();
								Intent intent = new Intent();
								intent.setClass(ReNameActivity.this,
										LoginSuccActivity.class);
								intent.putExtra("isCache", "yes");
								intent.putExtra("haveImage", "fromLogin");
								ReNameActivity.this.startActivity(intent);
								ReNameActivity.this.finish();
		} else {
			Toast.makeText(ReNameActivity.this, msg, Toast.LENGTH_SHORT).show();
		}
	}
}
