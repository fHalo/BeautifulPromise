package com.beautifulpromise.common.alarm;

import java.util.ArrayList;
import java.util.Calendar;

import com.beautifulpromise.common.dto.AddPromiseDTO;
import com.beautifulpromise.database.DatabaseHelper;
import com.beautifulpromise.database.GoalsDAO;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Alarm {

	Context context;
	
	public void SetAlarm(Context context, ArrayList<AddPromiseDTO> promisedto) {
		this.context = context;
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		Intent intent;
		PendingIntent sender;

		for (AddPromiseDTO Promise : promisedto) {
			//주기 알람(GPS)
			if (Promise.getCategoryId() == 0) {
				intent = new Intent(context, AlarmReceiver.class);
				
				Bundle extras = new Bundle();
				extras.putSerializable("PromiseDTO", Promise);
				intent.putExtras(extras);
				sender = PendingIntent.getBroadcast(context, 0, intent, 0);

				// 알람 시간
				Calendar calendar = Calendar.getInstance();
//				calendar.set(calendar.getTime().getYear(), calendar.getTime().getMonth(), calendar.getTime().getDay(), Promise.getTime(), Promise.getMin());
//				calendar.set(2012,5,10,17,18);
				calendar.setTimeInMillis(System.currentTimeMillis());
				calendar.add(Calendar.SECOND, 10);

				// 알람 등록
				am.set(AlarmManager.RTC, calendar.getTimeInMillis(), sender);
				break;
			}
			//운동 알람
			else if ((Promise.getCategoryId() == 1))
			{
				intent = new Intent(context, AlarmReceiver.class);
				
				Bundle extras = new Bundle();
				extras.putSerializable("PromiseDTO", Promise);
				intent.putExtras(extras);
				sender = PendingIntent.getBroadcast(context, 0, intent, 0);

				// 알람 시간
				Calendar calendar = Calendar.getInstance();
//				calendar.set(calendar.getTime().getYear(), calendar.getTime().getMonth(), calendar.getTime().getDay(), Promise.getTime(), Promise.getMin());
//				calendar.set(2012,5,10,17,18);
				Log.e("ou", Integer.toString(calendar.getTime().getYear())+ Integer.toString(calendar.getTime().getMonth())+ Integer.toString(calendar.getTime().getDay())+Integer.toString(Promise.getTime()) +Integer.toString(Promise.getMin()));
				calendar.setTimeInMillis(System.currentTimeMillis());
				calendar.add(Calendar.SECOND, 15);

				// 알람 등록
				am.set(AlarmManager.RTC, calendar.getTimeInMillis(), sender);
				break;
			}
			else
			{
			}
		}
	}
}
