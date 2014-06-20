package com.yx.livewhere;

import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.yx.util.MyCommentsAdapter;

public class MyCommActivity extends ListActivity{

	private ListView listView = null;
	private ImageButton back = null;
	private MyCommentsAdapter myAdapter = null;
	private ProgressDialog progressDialog = null;
	private ParseUser CurrentUser = null;
	private ImageButton backButton = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题
		setContentView(R.layout.mycomments);
		Parse.initialize(this, "IlQ8wBml4IkepGG0tuphk2VDIAfTuvcXX9OJOJfh", "NMcva8057XBA0mdRwlLKvLGXxL9uzQABVFAaVbyO");
		ParseAnalytics.trackAppOpened(null);
		
		backButton = (ImageButton)findViewById(R.id.img_search);
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyCommActivity.this.finish();
			}
		});
		
		progressDialog = ProgressDialog.show(MyCommActivity.this, "请稍等",
				"正在努力加载中...", true);
		progressDialog.setCancelable(true);
		back = (ImageButton)findViewById(R.id.img_search);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			 	MyCommActivity.this.finish();
			}
		});
		
		listView = getListView();
		listView.setCacheColorHint(Color.TRANSPARENT);
		CurrentUser = ParseUser.getCurrentUser();
		ParseQuery favorQuery= new ParseQuery("comments");
		favorQuery.whereEqualTo("username", CurrentUser.getUsername().toString());
		favorQuery.findInBackground(new FindCallback() {
			
			@Override
			public void done(List<ParseObject> parses, ParseException e) {
				// TODO Auto-generated method stub
				if(e == null){
					if(parses.size() != 0){
						System.out.println("yangxu--->" + "搜索完毕");
						myAdapter = new MyCommentsAdapter(parses, MyCommActivity.this);
						listView.setAdapter(myAdapter);
						progressDialog.dismiss();
					}else{
						Toast.makeText(MyCommActivity.this, "还没有评论过!", Toast.LENGTH_LONG).show();
						progressDialog.dismiss();
					}
				}else{
					Toast.makeText(MyCommActivity.this, "抱歉,查找失败!", Toast.LENGTH_LONG).show();
					progressDialog.dismiss();
				}
			}
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
   			MyCommActivity.this.progressDialog.dismiss();
		}
		return true;
	}
}
