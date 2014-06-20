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
import android.widget.RatingBar;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class AddCommActivity extends Activity {

	private RatingBar score = null;
	private EditText cost, content = null;
	private String ID, username, businessname = null;
	private Button submitButton = null;
	private ImageButton backButton = null;
	private ProgressDialog progressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题
		setContentView(R.layout.add_comm);
		Parse.initialize(this, "IlQ8wBml4IkepGG0tuphk2VDIAfTuvcXX9OJOJfh", "NMcva8057XBA0mdRwlLKvLGXxL9uzQABVFAaVbyO");
		ParseAnalytics.trackAppOpened(null);
		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");
		username = intent.getStringExtra("username");
		businessname = intent.getStringExtra("businessname");
		backButton = (ImageButton) findViewById(R.id.img_search);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddCommActivity.this.finish();
			}
		});
		score = (RatingBar) findViewById(R.id.score);
		cost = (EditText) findViewById(R.id.cost_edit);
		content = (EditText) findViewById(R.id.comm_edit);
		submitButton = (Button) findViewById(R.id.submit);
		submitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				progressDialog = ProgressDialog.show(AddCommActivity.this, "提交评论中",
						"请稍等...", true);
				progressDialog.setCancelable(true);
				ParseObject comment = new ParseObject("comments");
				comment.put("businessID", ID);
				comment.put("businessname", businessname);
				comment.put("username", username);
				comment.put("score", String.valueOf(score.getRating()));
				comment.put("comtent", content.getText().toString());
				comment.put("cost", cost.getText().toString());
//				ParseACL publicACL = new ParseACL();
//				publicACL.setPublicReadAccess(true);
//				publicACL.setPublicWriteAccess(true);      
//				comment.setACL(publicACL); // allow public read/writes
				comment.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(ParseException e) {
						// TODO Auto-generated method stub
						if (e == null) {
							printResult("评论提交成功");
						} else {
							printResult("评论提交失败");
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
   			AddCommActivity.this.progressDialog.cancel();
   			AddCommActivity.this.progressDialog.dismiss();
   			AddCommActivity.this.finish();
		}
		return true;
	}

	private void printResult(String msg) {
		progressDialog.dismiss();
		Toast.makeText(AddCommActivity.this, msg, Toast.LENGTH_SHORT).show();
	}

}
