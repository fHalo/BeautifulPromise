package com.beautifulpromise.common.alarm;

import java.util.Calendar;

import com.beautifulpromise.application.checkpromise.Cycle_Gps_Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class Alarm {

	Context context;

	public void SetAlarm(Context context, int index) {
		this.context = context;
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		Intent intent;
		PendingIntent sender;

		// 일반적인 알람
		if (index == 1) {
			intent = new Intent(context, Cycle_Gps_Alarm.class);
			sender = PendingIntent.getBroadcast(context, 0, intent, 0);

			// 알람 시간
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.add(Calendar.SECOND, 3);

			// 알람 등록
			am.set(AlarmManager.RTC, calendar.getTimeInMillis(), sender);
		}
		// GPS알람
		else if (index == 2) {
			intent = new Intent(context, Cycle_Gps_Alarm.class);
			sender = PendingIntent.getBroadcast(context, 0, intent, 0);

			// 알람 시간
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.add(Calendar.SECOND, 3);

			// 알람 등록
			am.set(AlarmManager.RTC, calendar.getTimeInMillis(), sender);
		}
	}
}
