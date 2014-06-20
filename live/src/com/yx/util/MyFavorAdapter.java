package com.yx.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.yx.livewhere.R;

public class MyFavorAdapter extends BaseAdapter{

	private List<ParseObject>info = null;
	private Map<Integer,View> rowViews = new HashMap<Integer, View>();
	private Context context = null;
	
	public MyFavorAdapter(List<ParseObject> info,Context context){
		this.info = info;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return info.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return info.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = rowViews.get(position);
		if(rowView == null){
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			rowView = layoutInflater.inflate(R.layout.localiten, null);
			TextView hotelname = (TextView)rowView.findViewById(R.id.hotelname);
			TextView telenumber = (TextView)rowView.findViewById(R.id.telenumber);
			TextView address = (TextView)rowView.findViewById(R.id.address);
		    TextView price = (TextView)rowView.findViewById(R.id.price);
		    ImageView rate =(ImageView)rowView.findViewById(R.id.rate0);
		    ParseObject parseObject = (ParseObject)getItem(position);
		    hotelname.setText(parseObject.getString("businessName"));
			telenumber.setText(parseObject.getString("teleNum"));
			address.setText(parseObject.getString("address"));
			price.setText(parseObject.getString("cost"));
			if(parseObject.getString("score").equals("0")){
				rate.setImageResource(R.drawable.xing0);
			}else if(parseObject.getString("score").equals("0.5")){
				rate.setImageResource(R.drawable.xing1);
			}else if(parseObject.getString("score").equals("1")){
				rate.setImageResource(R.drawable.xing2);
			}else if(parseObject.getString("score").equals("1.5")){
				rate.setImageResource(R.drawable.xing3);
			}else if(parseObject.getString("score").equals("2")){
				rate.setImageResource(R.drawable.xing4);
			}else if(parseObject.getString("score").equals("2.5")){
				rate.setImageResource(R.drawable.xing5);
			}else if(parseObject.getString("score").equals("3")){
				rate.setImageResource(R.drawable.xing6);
			}else if(parseObject.getString("score").equals("3.5")){
				rate.setImageResource(R.drawable.xing7);
			}else if(parseObject.getString("score").equals("4")){
				rate.setImageResource(R.drawable.xing8);
			}else if(parseObject.getString("score").equals("4.5")){
				rate.setImageResource(R.drawable.xing9);
			}else if(parseObject.getString("score").equals("5")){
				rate.setImageResource(R.drawable.xing10);
			}
			rowViews.put(position, rowView);
		}
		return rowView;
	}

}
