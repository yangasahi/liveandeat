package com.yx.livewhere;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class LeftDialogActivity extends Activity{
	//private MyDialog dialog;
		private LinearLayout layout;
		private RadioGroup rg = null;
		private RadioButton r1 = null;
		private RadioButton r3 = null;
		private RadioButton r5 = null;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.left_dialog);
			r1 = (RadioButton)findViewById(R.id.b1);
			r3 = (RadioButton)findViewById(R.id.b3);
			r5 = (RadioButton)findViewById(R.id.b5);
		
		rg = (RadioGroup)findViewById(R.id.radioGroup);
			rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub

					if(checkedId==r1.getId()){
						LeftDialogActivity.this.setResult(1000, null);
						LeftDialogActivity.this.finish();
					}
					if(checkedId==r3.getId()){
						LeftDialogActivity.this.setResult(3000, null);
						LeftDialogActivity.this.finish();
					}
					if(checkedId==r5.getId()){
						LeftDialogActivity.this.setResult(5000, null);
						LeftDialogActivity.this.finish();
					}
				
				}
				
			}
			);
			
			
			
			layout=(LinearLayout)findViewById(R.id.main_dialog_layout);
			layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！", 
							Toast.LENGTH_SHORT).show();	
				}
			});
		}

		@Override
		public boolean onTouchEvent(MotionEvent event){
			finish();
			return true;
		}
	
}
