package com.beautifulpromise.common.alarm;

import com.beautifulpromise.application.HomeAlarmActivity;
import com.beautifulpromise.common.database.CheckDAO;
import com.beautifulpromise.common.database.CheckDBHelper;
import com.beautifulpromise.common.dto.AddPromiseDTO;

import android.app.Dialog;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
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

public class AlarmReceiver extends BroadcastReceiver {

	LocationListener mLocationListener;
	LocationManager lm;
	CheckDBHelper gps_DBHelper;
	Context context;
	CheckDAO checkDAO;
	AddPromiseDTO promiseobject;

	public void onReceive(Context context, Intent intent) {

		this.context = context;
		CheckDBHelper checkDBHelper = new CheckDBHelper(this.context);
		checkDAO = new CheckDAO(checkDBHelper);

		// home에서 객체 받아오기
		Object tempobject = intent.getExtras().get("PromiseDTO");
		promiseobject = (AddPromiseDTO) tempobject;

		if (promiseobject.getCategoryId() == 0) {
			lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

			mLocationListener = new LocationListener() {
				public void onLocationChanged(Location location) {
					if (location != null) {

						Double Latitude = location.getLatitude();
						Double Longitude = location.getLongitude();
						checkDAO.gpsinit();
						checkDAO.gpsinsert(promiseobject.getPostId(), Latitude, Longitude);
						checkDAO.close();

						lm.removeUpdates(mLocationListener);
					}
				}

				public void onProviderDisabled(String arg0) {
					Toast.makeText(AlarmReceiver.this.context,
							"provider disabled " + arg0, Toast.LENGTH_SHORT)
							.show();
				}

				public void onProviderEnabled(String arg0) {
					Toast.makeText(AlarmReceiver.this.context,
							"provider enabled " + arg0, Toast.LENGTH_SHORT)
							.show();
				}

				public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
					// TODO Auto-generated method stub
					Toast.makeText(AlarmReceiver.this.context,
							"GPS 상태가 변경되었습니다.\n" + arg0 + ", " + arg1 + "", Toast.LENGTH_SHORT).show();
				}
			};
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000,
					5, mLocationListener);

		} 
		
		Intent i = new Intent(AlarmReceiver.this.context, HomeAlarmActivity.class);
		Bundle extras = new Bundle();
		extras.putSerializable("PromiseDTO", promiseobject);
		i.putExtras(extras);

		PendingIntent pi = PendingIntent.getActivity(AlarmReceiver.this.context, 0, i, PendingIntent.FLAG_ONE_SHOT);
		try {
			pi.send();
		} catch (CanceledException e) {

		}
	}
}
