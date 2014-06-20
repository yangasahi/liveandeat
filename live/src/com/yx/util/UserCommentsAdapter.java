package com.yx.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.yx.livewhere.ItemActivity;
import com.yx.livewhere.R;
import com.yx.model.UserEvaluate;

public class UserCommentsAdapter extends BaseAdapter{
	private List<UserEvaluate>info = null;
	private Map<Integer,View> rowViews = new HashMap<Integer, View>();
	private Context context = null;
	private AsyncImageLoader loader = new AsyncImageLoader();
	
	public UserCommentsAdapter(List<UserEvaluate> info,Context context){
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
			rowView = layoutInflater.inflate(R.layout.users_comm, null);
			TextView username = (TextView)rowView.findViewById(R.id.man_name);
			TextView content = (TextView)rowView.findViewById(R.id.man_content);
			TextView puttime = (TextView)rowView.findViewById(R.id.timeTextView);
			ImageView userImage = (ImageView)rowView.findViewById(R.id.userImage);
		    ImageView rate =(ImageView)rowView.findViewById(R.id.man_rate0);
		    UserEvaluate userEvaluate = (UserEvaluate)getItem(position);
		  //  loadImage(userEvaluate.getAvatar_url(),userImage);
		    username.setText(userEvaluate.getUname());
		    content.setText(userEvaluate.getContent());
		    Long timestamp = Long.parseLong(userEvaluate.getPubtime()) * 1000;
			puttime.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(timestamp));
		    imageLoader(userImage, userEvaluate.getAvatar_url());
			if(userEvaluate.getScore().equals("0")){
				rate.setImageResource(R.drawable.xing0);
			}else if(userEvaluate.getScore().equals("0.5")){
				rate.setImageResource(R.drawable.xing1);
			}else if(userEvaluate.getScore().equals("1")){
				rate.setImageResource(R.drawable.xing2);
			}else if(userEvaluate.getScore().equals("1.5")){
				rate.setImageResource(R.drawable.xing3);
			}else if(userEvaluate.getScore().equals("2")){
				rate.setImageResource(R.drawable.xing4);
			}else if(userEvaluate.getScore().equals("2.5")){
				rate.setImageResource(R.drawable.xing5);
			}else if(userEvaluate.getScore().equals("3")){
				rate.setImageResource(R.drawable.xing6);
			}else if(userEvaluate.getScore().equals("3.5")){
				rate.setImageResource(R.drawable.xing7);
			}else if(userEvaluate.getScore().equals("4")){
				rate.setImageResource(R.drawable.xing8);
			}else if(userEvaluate.getScore().equals("4.5")){
				rate.setImageResource(R.drawable.xing9);
			}else if(userEvaluate.getScore().equals("5")){
				rate.setImageResource(R.drawable.xing10);
			}
			rowViews.put(position, rowView);
		}
		return rowView;
	}
	
	public void imageLoader(ImageView image, String url) {

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(0).showImageForEmptyUri(0).showImageOnFail(0)
				.resetViewBeforeLoading(false) // default
				.delayBeforeLoading(1000).cacheInMemory(true) // default
				.cacheOnDisc(true) // default
				// .preProcessor(...)
				// .postProcessor(...)
				// .extraForDownloader(...)
				.imageScaleType(ImageScaleType.EXACTLY) // default
				.bitmapConfig(Bitmap.Config.ARGB_8888) // default
				// .decodingOptions(...)
				.displayer(new SimpleBitmapDisplayer()) // default
				.handler(new Handler()) // default
				.build();

		File cacheDir = StorageUtils.getCacheDirectory(context);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCacheExtraOptions(480, 800)
				// default = device screen dimensions
				// .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75,
				// null)
				.threadPoolSize(3)
				// default
				.threadPriority(Thread.NORM_PRIORITY - 1)
				// default
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// default
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
				// default
				.discCache(new UnlimitedDiscCache(cacheDir))
				// default
				.discCacheSize(50 * 1024 * 1024).discCacheFileCount(100)
				.discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
				// .imageDownloader(new BaseImageDownloader(context)) // default
				// .imageDecoder(new BaseImageDecoder()) // default
				.defaultDisplayImageOptions(options) // default
				.writeDebugLogs().build();
		ImageLoader.getInstance().init(config);

		ImageLoader.getInstance().displayImage(url, image);
	}
}
