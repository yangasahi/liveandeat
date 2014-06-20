package com.yx.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.yx.model.ImageItem;

public class ImageAdapter extends BaseAdapter{
	
	private List<ImageItem> info = null;
	private Map<Integer,View> rowViews = new HashMap<Integer, View>();
	private Context context = null;
	private AsyncImageLoader loader = new AsyncImageLoader();

	public ImageAdapter(List<ImageItem> info,Context context){
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
		
		ImageView imageView = new ImageView(context);  
		
		ImageItem imageItem = (ImageItem)getItem(position);
		
		 loadImage(imageItem.getUrl(),imageView);
		//给ImageView设置资源  
		imageView.setLayoutParams(new Gallery.LayoutParams(20, 20));
		//设置布局 图片200×200显示  
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		//设置比例类型  
		return imageView;  
		
	}
	private void loadImage( final String url ,ImageView imageView){
		CallbackImpl callbackImpl = new CallbackImpl(imageView);
		Drawable cacheImage = loader.loadDrawable(url, callbackImpl);
		if(cacheImage != null){
			imageView.setImageDrawable(cacheImage);
		}
	}
}
