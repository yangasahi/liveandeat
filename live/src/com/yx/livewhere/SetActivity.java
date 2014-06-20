package com.yx.livewhere;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class SetActivity extends Activity{

	private ImageButton backButton = null;
	private RelativeLayout recomm,aboutus = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题
		setContentView(R.layout.set);
		
		recomm = (RelativeLayout)findViewById(R.id.recomm);
		recomm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SetActivity.this, RecommActivity.class);
				SetActivity.this.startActivity(intent);
			}
		});
		
		aboutus = (RelativeLayout)findViewById(R.id.aboutus);
		aboutus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SetActivity.this, AboutActivity.class);
				SetActivity.this.startActivity(intent);
			}
		});
		
		backButton = (ImageButton)findViewById(R.id.img_search);
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SetActivity.this.finish();
			}
		});
	}


}
