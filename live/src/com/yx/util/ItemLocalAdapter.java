package com.yx.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.yx.livewhere.R;
import com.yx.model.LocationItem;

public class ItemLocalAdapter extends BaseAdapter{
	private List<LocationItem>info = null;
	private Map<Integer,View> rowViews = new HashMap<Integer, View>();
	private Context context = null;
	private Location location, location2 = null;
	private LocationManager locationManager;
	private String uc;
	
	
	public ItemLocalAdapter(List<LocationItem> info,Context context){
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
			TextView distance = (TextView)rowView.findViewById(R.id.distance);
		    TextView price = (TextView)rowView.findViewById(R.id.price);
		    ImageView rate =(ImageView)rowView.findViewById(R.id.rate0);
		    TextView mapBtn = (TextView)rowView.findViewById(R.id.map_btn);
		    final LocationItem locationItem = (LocationItem)getItem(position);
		   
		    mapBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					LatLng coordinate = new LatLng(getLocation()
							.getLatitude(), getLocation()
							.getLongitude());

					 uc = locationItem.getName();
					Intent intent = new Intent();
					intent.setClassName("com.google.android.apps.maps",
							"com.google.android.maps.MapsActivity");
					if (isIntentAvailable(intent)) {
						Intent i = new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("http://ditu.google.cn/maps?f=d&source=s_d&saddr="
										+ coordinate.latitude
										+ ","
										+ coordinate.longitude
										+ "&daddr=" + uc + "&hl=zh"));
						i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
								& Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
						i.setClassName("com.google.android.apps.maps",
								"com.google.android.maps.MapsActivity");
						context.startActivity(i);
					} else {
						Intent i = new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("http://ditu.google.cn/maps?f=d&source=s_d&saddr="
										+ coordinate.latitude
										+ ","
										+ coordinate.longitude
										+ "&daddr=" + uc + "&hl=zh"));
						context.startActivity(i);
					}
				}
			});
		    
			hotelname.setText(locationItem.getName());
			telenumber.setText(locationItem.getTel());
			address.setText("’nš¬:" + locationItem.getAddr());
			price.setText(locationItem.getCost());
			distance.setText(String.valueOf(locationItem.getDist())+ " km");
			if(locationItem.getRate().equals("0")){
				rate.setImageResource(R.drawable.xing0);
			}else if(locationItem.getRate().equals("0.5")){
				rate.setImageResource(R.drawable.xing1);
			}else if(locationItem.getRate().equals("1")){
				rate.setImageResource(R.drawable.xing2);
			}else if(locationItem.getRate().equals("1.5")){
				rate.setImageResource(R.drawable.xing3);
			}else if(locationItem.getRate().equals("2")){
				rate.setImageResource(R.drawable.xing4);
			}else if(locationItem.getRate().equals("2.5")){
				rate.setImageResource(R.drawable.xing5);
			}else if(locationItem.getRate().equals("3")){
				rate.setImageResource(R.drawable.xing6);
			}else if(locationItem.getRate().equals("3.5")){
				rate.setImageResource(R.drawable.xing7);
			}else if(locationItem.getRate().equals("4")){
				rate.setImageResource(R.drawable.xing8);
			}else if(locationItem.getRate().equals("4.5")){
				rate.setImageResource(R.drawable.xing9);
			}else if(locationItem.getRate().equals("5")){
				rate.setImageResource(R.drawable.xing10);
			}
			rowViews.put(position, rowView);
		}
		return rowView;
	}

	private boolean isIntentAvailable(Intent intent) {
		List<ResolveInfo> activities = context.getPackageManager()
				.queryIntentActivities(intent,
						PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
		return activities.size() != 0;
	}

	public Location getLocation() {

		try {
			locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);

			// getting GPS status
			boolean isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			boolean isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
				System.out.println("not");
			} else {
				// this.canGetLocation = true;
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER, 5000, 500,
							new LocationListener() {

								@Override
								public void onStatusChanged(String provider,
										int status, Bundle extras) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onProviderEnabled(String provider) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onProviderDisabled(String provider) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onLocationChanged(Location location) {
									// TODO Auto-generated method stub

								}
							});
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							location2 = location;
						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER, 5000, 500,
								new LocationListener() {

									@Override
									public void onStatusChanged(
											String provider, int status,
											Bundle extras) {
										// TODO Auto-generated method stub
									}

									@Override
									public void onProviderEnabled(
											String provider) {
										// TODO Auto-generated method stub
									}

									@Override
									public void onProviderDisabled(
											String provider) {
										// TODO Auto-generated method stub
									}

									@Override
									public void onLocationChanged(
											Location location) {
										// TODO Auto-generated method stub
									}
								});
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								location2 = location;
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location2;
	}
}
