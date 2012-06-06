package com.beautifulpromise.common.alarm;

import java.util.Calendar;

import com.beautifulpromise.R;
import com.beautifulpromise.application.checkpromise.CameraDialog;
import com.beautifulpromise.application.checkpromise.CycleCheckActivity;
import com.beautifulpromise.application.checkpromise.CycleGpsAlarm;
import com.beautifulpromise.common.utils.ImageUtils;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

public class Alarm {

	Context context;

	public void SetAlarm(Context context, int index) {
		this.context = context;
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		Intent intent;
		PendingIntent sender;

		// 일반적인 알람
		if (index == 1) {
			intent = new Intent(context, CycleGpsAlarm.class);
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
			intent = new Intent(context, CycleGpsAlarm.class);
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
