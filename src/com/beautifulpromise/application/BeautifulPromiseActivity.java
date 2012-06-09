package com.beautifulpromise.application;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beautifulpromise.R;
import com.beautifulpromise.application.addpromise.AddPromiseActivity;
import com.beautifulpromise.application.feedviewer.PromiseFeedList;
import com.beautifulpromise.common.Var;
import com.beautifulpromise.database.DatabaseHelper;
import com.beautifulpromise.database.NotificationProvider;
import com.beautifulpromise.facebooklibrary.Facebook;

public class BeautifulPromiseActivity extends Activity{

	Facebook mFacebook; 
	
	ImageButton leftMenuBtn;
	ImageButton homeBtn;
	ImageButton notificationBtn;
	
	Button addPromiseBtn;
	Button myPromiseBtn;
	Button helperPromiseBtn;
	Button friendPromiseBtn;
	Button pointShopBtn;
	Button settingBtn;
	ListView notificationListView;
	
	LinearLayout notificationLayout;
	LinearLayout leftMenuLayout;
	LinearLayout activityLayout;
	
	HorizontalScrollView hscroll;
	private int leftWidth = Var.LEFT_MENUBAR_WIDTH;
	
	NotificationAdapter adapter;
	ContentObserver observer;
	Cursor cursor;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		leftMenuBtn = (ImageButton) findViewById(R.id.left_menu_button);
		homeBtn = (ImageButton) findViewById(R.id.home_button);
		notificationBtn = (ImageButton) findViewById(R.id.notification_button);
		
		addPromiseBtn = (Button) findViewById(R.id.add_promise_btn);
		myPromiseBtn = (Button) findViewById(R.id.my_promise_btn);
		helperPromiseBtn = (Button) findViewById(R.id.helper_promise_btn);
		friendPromiseBtn = (Button) findViewById(R.id.friend_promise_btn);
		pointShopBtn = (Button) findViewById(R.id.pointshop_btn);
		settingBtn = (Button) findViewById(R.id.setting_btn);
		
		notificationLayout  = (LinearLayout) findViewById(R.id.notification_layout);
		notificationListView = (ListView) findViewById(R.id.notification_listview);
		leftMenuLayout = (LinearLayout) findViewById(R.id.menu_layout);
		activityLayout = (LinearLayout) findViewById(R.id.activity_layout);
		
		leftMenuBtn.setOnClickListener(buttonClickLisetner);
		homeBtn.setOnClickListener(buttonClickLisetner);
		notificationBtn.setOnClickListener(buttonClickLisetner);
		
		addPromiseBtn.setOnClickListener(buttonClickLisetner);
		myPromiseBtn.setOnClickListener(buttonClickLisetner);
		helperPromiseBtn.setOnClickListener(buttonClickLisetner);
		friendPromiseBtn.setOnClickListener(buttonClickLisetner);
		pointShopBtn.setOnClickListener(buttonClickLisetner);
		settingBtn.setOnClickListener(buttonClickLisetner);
		
		hscroll = (HorizontalScrollView) findViewById(R.id.horizontal_scrollview);
		mySmoothScrollTo(leftWidth, 0);
		hscroll.setOnTouchListener(hscrollTouchListener);
		
		notificationLayout.setVisibility(View.GONE);
		
		//TODO Test
//		ContentValues row = new ContentValues();
//		row.put("title", "Jaemyung Shin commented on 소지섭's photo of you: \"test\"");
//		row.put("send_user_id", "100001066448386");
//		row.put("fb_id", "113835812091211");
//		getContentResolver().insert(NotificationProvider.CONTENT_URI, row);
		
//		ContentValues row = new ContentValues();
//		row.put("title", "소지섭 added 4 photos of you.");
//		row.put("send_user_id", "100003943796581");
//		row.put("fb_id", "114560352018757");
//		cr.insert(NotificationProvider.CONTENT_URI, row);
		
//		Cursor cursor = getContentResolver().query(NotificationProvider.CONTENT_URI, null, null, null, null);
//		cursor.moveToFirst();
//		startManagingCursor(cursor);
		cursor = managedQuery(NotificationProvider.CONTENT_URI, null, null, null, null);
		TextView text = new TextView(this);
		text.setText("This is Header");
		text.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ContentValues row = new ContentValues();
				row.put("title", "소지섭 added 4 photos of you.");
				row.put("send_user_id", "100003943796581");
				row.put("fb_id", "114560352018757");
				getContentResolver().insert(NotificationProvider.CONTENT_URI, row);
//				adapter.notifyDataSetChanged();
			}
		});
		notificationListView.addHeaderView(text);
		cursor.moveToFirst();
		adapter = new NotificationAdapter(this, cursor);
		notificationListView.setAdapter(adapter);
		
//		int count = cursor.getCount();
//		if(count > 0) {
//			startManagingCursor(cursor);
//			NotificationAdapter adapter = new NotificationAdapter(this, cursor);
//			notificationListView.setAdapter(adapter);
//		}
	} 
	
	View.OnClickListener buttonClickLisetner = new View.OnClickListener() {
		Intent intent;
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.left_menu_button:
					if(!Var.menuShowFlag){
						mySmoothScrollTo(0, 0);
						Var.menuShowFlag = true;
					}else{
						mySmoothScrollTo(300, 0);
						Var.menuShowFlag = false;
					}
				break;

			case R.id.home_button:
				intent = new Intent(BeautifulPromiseActivity.this, HomeActivity.class);
				startActivity(intent);
				break;
				
			case R.id.notification_button:
				if(notificationLayout.isShown())
					notificationLayout.setVisibility(View.GONE);
				else {
					notificationLayout.setVisibility(View.VISIBLE);
//					Log.i("immk", "Timer Start");
//					Timer timer = new Timer();
//					timer.schedule(myTask, 1000);
				}
				//TODO Test
//				ContentResolver cr = getContentResolver();
//				cr.query(NotificationProvider.CONTENT_URI, null, null, null, null);
				
				break;	
				
			case R.id.add_promise_btn:
				mySmoothScrollTo(leftWidth, 0);
				Var.menuShowFlag = false;
				intent = new Intent(BeautifulPromiseActivity.this, AddPromiseActivity.class);
				startActivity(intent);
				break;

			case R.id.my_promise_btn:
				intent = new Intent(BeautifulPromiseActivity.this, PromiseFeedList.class);
				Var.menuShowFlag = false;
				startActivity(intent);
				break;
				
			case R.id.helper_promise_btn:
				intent = new Intent(BeautifulPromiseActivity.this, PromiseFeedList.class);
				Var.menuShowFlag = false;
				startActivity(intent);
				break;
				
			case R.id.friend_promise_btn:
				intent = new Intent(BeautifulPromiseActivity.this, PromiseFeedList.class);
				Var.menuShowFlag = false;
				startActivity(intent);
				break;
				
			case R.id.pointshop_btn:
				Var.menuShowFlag = false;
				break;
				
			case R.id.setting_btn:
				Var.menuShowFlag = false;
				break;
			default:
				break;
			}
		}
	};

	TimerTask myTask = new TimerTask() {
		
		@Override
		public void run() {
			
			Log.i("immk", "TimerTask Start");
			
			ContentResolver cr = getContentResolver();
//			ContentValues row = new ContentValues();
//			row.put("title", "Jaemyung Shin commented on 소지섭's photo of you: \"test\"");
//			row.put("send_user_id", "100001066448386");
//			row.put("fb_id", "113835812091211");
//			cr.insert(NotificationProvider.CONTENT_URI, row);
			
			ContentValues row = new ContentValues();
			row.put("title", "소지섭 added 4 photos of you.");
			row.put("send_user_id", "100003943796581");
			row.put("fb_id", "114560352018757");
			cr.insert(NotificationProvider.CONTENT_URI, row);
		}
	};
	
	
	View.OnTouchListener hscrollTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				int x = hscroll.getScrollX();
				hscrollCheck(x, 0);
			}
			return false;
		}
	};

	public void hscrollCheck(int x, int y) {
		if (x <= leftWidth) {	
			if (x <= leftWidth /2) { 			// 왼쪽 메뉴 보이게
				mySmoothScrollTo(0, y);
				Var.menuShowFlag = true;
			} else {							// 왼쪽 메뉴 사라지게
				mySmoothScrollTo(leftWidth, y);
				Var.menuShowFlag = false;
			}
		}
	}
	
	protected void mySmoothScrollTo(final int x, final int y) {
		hscroll.post(new Runnable() {
			@Override
			public void run() {
				hscroll.smoothScrollTo(x, y);
			}
		});
	}
	
	protected void setActivityLayout(LinearLayout layout){
		activityLayout.addView(layout);
	}
	
	protected void setActivityLayout(ImageView layout){
		activityLayout.addView(layout);
	}
}
