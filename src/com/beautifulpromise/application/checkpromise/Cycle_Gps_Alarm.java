package com.beautifulpromise.application.checkpromise;

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

public class Cycle_Gps_Alarm extends BroadcastReceiver{
	
	LocationListener mLocationListener;
	LocationManager lm;
	Cycle_Gps_DBHelper gps_DBHelper;
	Context context;
	
	public void onReceive(Context context, Intent intent) {
		
		this.context = context;
		gps_DBHelper = new Cycle_Gps_DBHelper(context);
		
		lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
 		mLocationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				if (location != null) {
					SQLiteDatabase db;
					ContentValues row;
					
					db= gps_DBHelper.getWritableDatabase();
					
					Double Latitude = location.getLatitude();
					Double Longitude = location.getLongitude();
					
					db.delete("gps", null, null);
					row = new ContentValues();
					row.put("latitude", Latitude);
					row.put("longitude", Longitude);
					db.insert("gps", null, row);
					lm.removeUpdates(mLocationListener);
				}
			}
			
			public void onProviderDisabled(String arg0) {
				Toast.makeText(Cycle_Gps_Alarm.this.context, "provider disabled " + arg0,
								Toast.LENGTH_SHORT).show();
			}

			public void onProviderEnabled(String arg0) {
				Toast.makeText(Cycle_Gps_Alarm.this.context, "provider enabled " + arg0,
								Toast.LENGTH_SHORT).show();
			}

			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				// TODO Auto-generated method stub
				Toast.makeText(Cycle_Gps_Alarm.this.context,
						"GPS 상태가 변경되었습니다.\n" + arg0 + ", " + arg1 + "",
						Toast.LENGTH_SHORT).show();
			}
		};
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 5,
				mLocationListener);
	}
}
