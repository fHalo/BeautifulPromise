package com.beautifulpromise.application;

import java.util.ArrayList;

import com.beautifulpromise.R;
import com.beautifulpromise.application.checkpromise.CycleCheckActivity;
import com.beautifulpromise.application.checkpromise.EtcCheckActivity;
import com.beautifulpromise.application.checkpromise.WorkCheckActivity;
import com.beautifulpromise.common.alarm.Alarm;
import com.beautifulpromise.common.alarm.PromiseObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class HomeActivity extends BeautifulPromiseActivity {
	/** Called when the activity is first created. */

	ListView PromiseListView;
	MyListAdapter MyAdapter;
	ArrayList<PromiseObject> arItem;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout layout = (LinearLayout)View.inflate(this, R.layout.homeactivity, null);
		setActivityLayout(layout);
//		setContentView(R.layout.list);

		arItem = new ArrayList<PromiseObject>();

		PromiseObject promiseObject;

		promiseObject = new PromiseObject("금주하기", 10, 0, 3);
		arItem.add(promiseObject);
		promiseObject = new PromiseObject("수영 빠지지 않기", 20, 50, 1);
		arItem.add(promiseObject);
		promiseObject = new PromiseObject("토익 900점 맞기!!", 15, 100, 2);
		arItem.add(promiseObject);

		MyAdapter = new MyListAdapter(this, R.layout.homeactivity_list, arItem);

		PromiseListView = (ListView) findViewById(R.id.list);
		PromiseListView.setAdapter(MyAdapter);
		PromiseListView.setItemsCanFocus(false);
		PromiseListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		Alarm alarm = new Alarm();
		alarm.SetAlarm(this, 1);
	}

	class MyListAdapter extends BaseAdapter implements OnClickListener {
		Context maincon;
		LayoutInflater Inflater;
		ArrayList<PromiseObject> arSrc;
		int layout;

		public MyListAdapter(Context context, int alayout,
				ArrayList<PromiseObject> aarSrc) {
			maincon = context;
			Inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			arSrc = aarSrc;
			layout = alayout;
		}

		public int getCount() {
			return arSrc.size();
		}

		public PromiseObject getItem(int position) {
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
			promisenametxt.setText(arSrc.get(position).getPromisename());

			TextView d_daytxt = (TextView) convertView.findViewById(R.id.d_day);
			d_daytxt.setText("D-"
					+ Integer.toString(arSrc.get(position).getD_day()));

			TextView percenttxt = (TextView) convertView
					.findViewById(R.id.percent);
			percenttxt.setText(Integer.toString(arSrc.get(position)
					.getPercent()) + "%");

			convertView.setTag(position);
			convertView.setOnClickListener(this);

			return convertView;
		}

		public void onClick(View v) {
			int position = (Integer) v.getTag();

			PromiseObject promiseObject = getItem(position);

			if (promiseObject.getCategory() == 1) {
				startActivity(new Intent(HomeActivity.this,
						CycleCheckActivity.class));
			} else if (promiseObject.getCategory() == 2) {
				startActivity(new Intent(HomeActivity.this,
						WorkCheckActivity.class));
			} else {
				startActivity(new Intent(HomeActivity.this,
						EtcCheckActivity.class));
			}

		}
	}
}