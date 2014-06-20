package com.yx.livewhere;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TipActivity extends Activity{

	private Button button = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ﾃｻﾓﾐｱ��
		setContentView(R.layout.tip);
		button = (Button)findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
//				intent.setClass(TipActivity.this, PagerActivity.class);
				TipActivity.this.startActivity(intent);
				TipActivity.this.finish();
			}
		});
		
	}

}
