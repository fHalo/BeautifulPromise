package com.beautifulpromise.application.checkpromise;

import com.beautifulpromise.application.HomeActivity;
import com.beautifulpromise.database.CheckDAO;
import com.beautifulpromise.database.CheckDBHelper;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class CycleGpsAlarm extends BroadcastReceiver{
	
	LocationListener mLocationListener;
	LocationManager lm;
	CheckDBHelper gps_DBHelper;
	Context context;
	CheckDAO checkDAO;
	
	public void onReceive(Context context, Intent intent) {
		
		this.context = context;
		CheckDBHelper checkDBHelper = new CheckDBHelper(this.context);
		checkDAO = new CheckDAO(checkDBHelper);
		
		lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
 		mLocationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				if (location != null) {
					
					Double Latitude = location.getLatitude();
					Double Longitude = location.getLongitude();
					
					ContentValues row;
//					
//					db.delete("gps", null, null);
//					row = new ContentValues();
//					row.put("latitude", Latitude);
//					row.put("longitude", Longitude);
//					db.insert("gps", null, row);
					Toast.makeText(CycleGpsAlarm.this.context, "알람알람", Toast.LENGTH_SHORT).show();
					lm.removeUpdates(mLocationListener);
				}
			}
			
			public void onProviderDisabled(String arg0) {
				Toast.makeText(CycleGpsAlarm.this.context, "provider disabled " + arg0,
								Toast.LENGTH_SHORT).show();
			}

			public void onProviderEnabled(String arg0) {
				Toast.makeText(CycleGpsAlarm.this.context, "provider enabled " + arg0,
								Toast.LENGTH_SHORT).show();
			}

			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				// TODO Auto-generated method stub
				Toast.makeText(CycleGpsAlarm.this.context,
						"GPS 상태가 변경되었습니다.\n" + arg0 + ", " + arg1 + "",
						Toast.LENGTH_SHORT).show();
			}
		};
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 5,
				mLocationListener);
	}
}
