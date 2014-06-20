package com.yx.livewhere;

import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

import com.yx.download.AsyncHttpClient;
import com.yx.download.JsonHttpResponseHandler;
import com.yx.model.UserEvaluate;
import com.yx.util.UserCommentsAdapter;
import com.yx.xListView.XListView;
import com.yx.xListView.XListView.IXListViewListener;

public class UserCommentsActivity extends Activity implements IXListViewListener{

	private UserCommentsAdapter userAdapter = null;
	private ProgressDialog progressDialog = null;
	private ImageButton back = null;
	private XListView listView = null;
	private int start = 1;
	private UserCommentsAdapter adapter;
	private List<UserEvaluate> items;
	private String uid;
	private Handler mHandler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.users_comments);
		mHandler = new Handler();
		back = (ImageButton)findViewById(R.id.img_search);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			 	UserCommentsActivity.this.finish();
			}
		});
		listView = (XListView) findViewById(R.id.xListView);
		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setDividerHeight(0);
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(true);
		listView.setXListViewListener(this);
		Intent intent = getIntent();
		uid  = intent.getStringExtra("user");
		getcomments();
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				getcomments();
				listView.stopRefresh();
				listView.stopLoadMore();

			}
		}, 3000);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = start + 9;
				
				AsyncHttpClient client = new AsyncHttpClient();
				client.get(
						UserCommentsActivity.this,
						"http://openapi.aibang.com/biz/"
						+ uid
						+ "/comments?app_key=0f1958aad1e661519a3e44e5c560f4f2&alt=json&from="+start+"&to="+(start+8),
						new JsonHttpResponseHandler() {

							@Override
							public void onSuccess(JSONObject jsonObject) {		
								try {
									JSONObject json = jsonObject
											.getJSONObject("comments");
									JSONArray array = json.getJSONArray("comment");
									JsonUtils jsonUtiles = new JsonUtils();
									items.addAll(jsonUtiles.parseUserFromJson(array.toString()));
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
		},3000);
	}
	public void getcomments(){
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(
				UserCommentsActivity.this,
				"http://openapi.aibang.com/biz/"
				+ uid
				+ "/comments?app_key=0f1958aad1e661519a3e44e5c560f4f2&alt=json&from=1&to=9",
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONObject jsonObject) {		
						try {
							JSONObject json = jsonObject
									.getJSONObject("comments");
							JSONArray array = json.getJSONArray("comment");
							JsonUtils jsonUtiles = new JsonUtils();
							items = jsonUtiles.parseUserFromJson(array.toString());
							adapter = new UserCommentsAdapter(items, UserCommentsActivity.this);
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
