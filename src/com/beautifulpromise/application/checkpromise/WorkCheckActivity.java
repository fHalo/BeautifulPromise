package com.beautifulpromise.application.checkpromise;

import java.util.Calendar;

import com.beautifulpromise.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class WorkCheckActivity extends Activity {
	Chronometer mChronometer;
	Boolean StartCheck;
	Boolean check = false;
	Button startbutton;
	Button creationbutton;
	long temptime = 0;
	long a = 0;
	
	Calendar StartTime;
	Calendar EndTime;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkpromise_workcheck_activity);

		StartCheck = true;
		
		mChronometer = (Chronometer) findViewById(R.id.chronometer);
		
		startbutton = (Button) findViewById(R.id.checkpromise_workcheck_start_btn);
		startbutton.setOnClickListener(mStartListener);
		
		creationbutton = (Button) findViewById(R.id.checkpromise_workcheck_post_btn);
		creationbutton.setOnClickListener(mCreationListener);
	}

	View.OnClickListener mStartListener = new OnClickListener() {
		public void onClick(View v) {
			if(StartCheck == true)
			{
				if(check == false)
				{
					check =true;
					StartTime = Calendar.getInstance();
					
					mChronometer.setBase(SystemClock.elapsedRealtime());
					mChronometer.start();
					startbutton.setText("정 지");
					startbutton.setBackgroundResource(R.drawable.check_activity_stop);
					StartCheck = false;
					
				}else if(check == true)
				{
					mChronometer.setBase(mChronometer.getBase() + SystemClock.elapsedRealtime() - temptime);
					mChronometer.start();
					startbutton.setText("정 지");
					startbutton.setBackgroundResource(R.drawable.check_activity_stop);
					StartCheck = false;
				}
				
			}else if(StartCheck == false)
			{
				temptime = SystemClock.elapsedRealtime();
				a= mChronometer.getBase();
				mChronometer.stop();
				startbutton.setText("시 작");
				startbutton.setBackgroundResource(R.drawable.check_activity_start);
				StartCheck = true;
			}
		}
	};
	
	View.OnClickListener mCreationListener = new OnClickListener() {
		public void onClick(View v) {			
			if(StartCheck == true)
			{
				Feed(temptime - a);
			}
			else if(StartCheck == false)
			{
				Feed(SystemClock.elapsedRealtime() - mChronometer.getBase());
			}
		}
	};
	
	public void Feed(Long Time)
	{
		EndTime = Calendar.getInstance();
		Time = Time / 60000;
		String SendMessage = Integer.toString(StartTime.get(Calendar.YEAR))+"년 "+Integer.toString(StartTime.get(Calendar.MONTH)+1)+"월 "+Integer.toString(StartTime.get(Calendar.DATE))+"일 "+Integer.toString(StartTime.get(Calendar.HOUR_OF_DAY))+"시부터 "+Long.toString(Time)+"분간 목표달성을 위해 노력했습니다.";
		Intent intent = new Intent(WorkCheckActivity.this, WorkCheckFeedActivity.class);
		intent.putExtra("Time", SendMessage);
		startActivityForResult(intent,0);
	}
	
}