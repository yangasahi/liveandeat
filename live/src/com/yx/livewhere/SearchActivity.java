package com.yx.livewhere;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class SearchActivity extends Activity{
	private ImageButton imagebackButton;//���Ƶײ�Tab��ת
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// û�б���
		setContentView(R.layout.search);
		  //����activityʱ���Զ����������
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
      
        imagebackButton = (ImageButton)findViewById(R.id.backImageButton);
        imagebackButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SearchActivity.this, MainActivity.class);
				SearchActivity.this.startActivity(intent);
				SearchActivity.this.finish();
			}
		});
        
	}

	
	
	

}
