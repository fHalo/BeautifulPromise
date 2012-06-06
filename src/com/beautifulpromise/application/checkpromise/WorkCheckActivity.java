package com.beautifulpromise.application.checkpromise;

import java.util.Calendar;

import com.beautifulpromise.R;
import com.beautifulpromise.common.dto.AddPromiseDTO;

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
	
	TextView PromisenameText;
	TextView PeriodText;
	Button startbutton;
	Button creationbutton;
	
	AddPromiseDTO promiseobject;
	
	long temptime = 0;
	long a = 0;
	
	Calendar StartTime;
	Calendar EndTime;
	
	Intent intent= getIntent();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkpromise_workcheck_activity);

		StartCheck = true;
		
		PromisenameText = (TextView) findViewById(R.id.checkpromise_workcheck_promisename_text);
		PeriodText = (TextView) findViewById(R.id.checkpromise_workcheck_period_text);
		startbutton = (Button) findViewById(R.id.checkpromise_workcheck_start_btn);
		creationbutton = (Button) findViewById(R.id.checkpromise_workcheck_post_btn);
		
		//home에서 객체 받아오기
		Object tempobject = getIntent().getExtras().get("PromiseDTO");
		promiseobject = (AddPromiseDTO) tempobject;
		
		//약속 제목 텍스트 설정
		PromisenameText.setText(promiseobject.getTitle());
		
		//목표기간 텍스트 설정
		String StartTime = promiseobject.getStartDate();
		StartTime = StartTime.substring(0, 4) + "." + StartTime.substring(4, 6)+ "." + StartTime.substring(6, 8);
		
		String EndTime = promiseobject.getEndDate();
		EndTime = EndTime.substring(0, 4) + "." + EndTime.substring(4, 6)+ "." + EndTime.substring(6, 8);
		
		PeriodText.setText(StartTime + " ~ " + EndTime);
		
		mChronometer = (Chronometer) findViewById(R.id.chronometer);
				
		startbutton.setOnClickListener(mStartListener);		
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
		try
		{
			EndTime = Calendar.getInstance();
			
			Time = Time / 60000;
			String SendMessage = Integer.toString(StartTime.get(Calendar.YEAR))+"년 "+Integer.toString(StartTime.get(Calendar.MONTH)+1)+"월 "+Integer.toString(StartTime.get(Calendar.DATE))+"일 "+Integer.toString(StartTime.get(Calendar.HOUR_OF_DAY))+"시부터 "+Long.toString(Time)+"분간 목표달성을 위해 노력했습니다.";
			Intent intent = new Intent(WorkCheckActivity.this, WorkCheckFeedActivity.class);
			intent.putExtra("Time", SendMessage);
			
			Bundle extras = new Bundle();
			extras.putSerializable("PromiseDTO", promiseobject); 
			intent.putExtras(extras);
			
			startActivityForResult(intent,0);
		}
		catch (Exception e) {
		}
	}
	
}