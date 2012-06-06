package com.beautifulpromise.application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import com.beautifulpromise.R;
import com.beautifulpromise.application.checkpromise.CycleCheckActivity;
import com.beautifulpromise.application.checkpromise.CycleGpsAlarm;
import com.beautifulpromise.application.checkpromise.EtcCheckActivity;
import com.beautifulpromise.application.checkpromise.WorkCheckActivity;
import com.beautifulpromise.application.checkpromise.WorkCheckFeedActivity;
import com.beautifulpromise.common.alarm.Alarm;
import com.beautifulpromise.common.dto.AddPromiseDTO;
import com.beautifulpromise.database.DatabaseHelper;
import com.beautifulpromise.database.GoalsDAO;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class HomeActivity extends BeautifulPromiseActivity {
	/** Called when the activity is first created. */

	ListView PromiseListView;
	MyListAdapter MyAdapter;
	ArrayList<AddPromiseDTO> promisedto;
//	ArrayList<TodayPromiseDTO> arItem;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout layout = (LinearLayout) View.inflate(this,
				R.layout.homeactivity, null);
		setActivityLayout(layout);

		Calendar oCalendar = Calendar.getInstance();
		DatabaseHelper databaseHelper = new DatabaseHelper(this);
		GoalsDAO dao = new GoalsDAO(databaseHelper);

		promisedto = dao.getGoalList(oCalendar.get(Calendar.DAY_OF_WEEK));
		
		for (int i = 0; i < promisedto.size(); i++) {
			promisedto.get(i).setD_day(promisedto.get(i).getEndDate());
		}
//		arItem = new ArrayList<TodayPromiseDTO>();
//		TodayPromiseDTO promiseObject;
//
//		for (AddPromiseDTO addPromiseDTO : promisedto) {
//			promiseObject = new TodayPromiseDTO(addPromiseDTO.getTitle(), addPromiseDTO.getEndDate(), addPromiseDTO.getCategoryId(), false);
//			arItem.add(promiseObject);
//		}

		MyAdapter = new MyListAdapter(this, R.layout.homeactivity_list, promisedto);

		PromiseListView = (ListView) findViewById(R.id.list);
		PromiseListView.setAdapter(MyAdapter);
		PromiseListView.setItemsCanFocus(false);
		PromiseListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

//		Alarm alarm = new Alarm();
//		alarm.SetAlarm(this, 1);
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		Intent intent;
		PendingIntent sender;
		intent = new Intent(HomeActivity.this, CycleGpsAlarm.class);
		sender = PendingIntent.getBroadcast(HomeActivity.this, 0, intent, 0);

		// 알람 시간
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, 10);

		// 알람 등록
		am.set(AlarmManager.RTC, calendar.getTimeInMillis(), sender);
	}

	class MyListAdapter extends BaseAdapter implements OnClickListener {
		Context maincon;
		LayoutInflater Inflater;
		ArrayList<AddPromiseDTO> arSrc;
		int layout;

		public MyListAdapter(Context context, int alayout,
				ArrayList<AddPromiseDTO> aarSrc) {
			maincon = context;
			Inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			arSrc = aarSrc;
			layout = alayout;
		}

		public int getCount() {
			return arSrc.size();
		}

		public AddPromiseDTO getItem(int position) {
			return arSrc.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = Inflater.inflate(layout, parent, false);
			}

			TextView promisenametxt = (TextView) convertView
					.findViewById(R.id.promisename);
			TextView d_daytxt = (TextView) convertView.findViewById(R.id.d_day);
			ImageView checkimg = (ImageView) convertView.findViewById(R.id.home_check);
			
			
			
			
			promisenametxt.setText(arSrc.get(position).getTitle());

			//D-Day, D-day가 지나서 평가를 해야되는 약속들
			if(arSrc.get(position).getResult() == 0 && arSrc.get(position).getD_day() < 1)
			{
				d_daytxt.setText("D-Day");
				d_daytxt.setTextColor(Color.RED);
			}
			//오늘 피드를 올린 약속
			else
			{
				d_daytxt.setText("D-" + String.valueOf(arSrc.get(position).getD_day()));
			}
			//오늘 피드를 올리지 않은 약속
//			else
//			{
//				d_daytxt.setText("D-" + String.valueOf(arSrc.get(position).getD_day()));
//			}
			
			
			convertView.setTag(position);
			convertView.setOnClickListener(this);

			return convertView;
		}

		public void onClick(View v) {
			int position = (Integer) v.getTag();

			AddPromiseDTO promiseObject = getItem(position);
			Intent intent;
			
			Bundle extras = new Bundle(); 
			extras.putSerializable("PromiseDTO", promiseObject); 
			
			//주기(GPS)
			if (promiseObject.getCategoryId() == 0) {
				intent = new Intent(HomeActivity.this, CycleCheckActivity.class);
			}
			//운동/공부 (타이머)
			else if (promiseObject.getCategoryId() == 1) {
				intent = new Intent(HomeActivity.this, WorkCheckActivity.class);
			} 
			
			else {
				intent = new Intent(HomeActivity.this, EtcCheckActivity.class);
			}
			intent.putExtras(extras);
			startActivityForResult(intent,0);
		}
	}
}