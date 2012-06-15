package com.beautifulpromise.application;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
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
import com.beautifulpromise.common.repository.Repository;
import com.beautifulpromise.database.NotificationProvider;
import com.beautifulpromise.facebooklibrary.Facebook;
import com.beautifulpromise.facebooklibrary.SessionStore;
import com.facebook.halo.application.types.Notifications;
import com.facebook.halo.application.types.User;
import com.facebook.halo.application.types.connection.Friends;
import com.facebook.halo.framework.core.Connection;

public class BeautifulPromiseActivity extends Activity{

	Facebook mFacebook; 
	
	ImageButton leftMenuBtn;
	ImageButton homeBtn;
	ImageButton notificationBtn;
	ImageView newImage;
	ListView notificationListView;
	
	LinearLayout notificationLayout;
	LinearLayout leftMenuLayout;
	LinearLayout activityLayout;
	
	LinearLayout addPromiseBtn;
	LinearLayout myPromiseBtn;
	LinearLayout helperPromiseBtn;
	LinearLayout friendPromiseBtn;
	LinearLayout pointShopBtn;
	LinearLayout settingBtn;
	
	HorizontalScrollView hscroll;
	private int leftWidth = Var.LEFT_MENUBAR_WIDTH;
	
	NotificationAdapter adapter;
	ContentObserver observer;
	Cursor cursor;
	
	Intent intent = new Intent();
	Handler handler;
	Connection<Notifications> notificaitons;
		
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		leftMenuBtn = (ImageButton) findViewById(R.id.left_menu_button);
		homeBtn = (ImageButton) findViewById(R.id.home_button);
		notificationBtn = (ImageButton) findViewById(R.id.notification_button);
		newImage = (ImageView) findViewById(R.id.new_feeds_image);
		
		addPromiseBtn = (LinearLayout) findViewById(R.id.addPromiseLayout);
		myPromiseBtn = (LinearLayout) findViewById(R.id.myPromiseLayout);
		helperPromiseBtn = (LinearLayout) findViewById(R.id.helperPromiseLayout);
		friendPromiseBtn = (LinearLayout) findViewById(R.id.friendPromiseLayout);
		pointShopBtn = (LinearLayout) findViewById(R.id.pointShopLayout);
		settingBtn = (LinearLayout) findViewById(R.id.accountLayout);
		
		notificationLayout  = (LinearLayout) findViewById(R.id.notification_layout);
		notificationListView = (ListView) findViewById(R.id.notification_listview);
		leftMenuLayout = (LinearLayout) findViewById(R.id.menu_layout);
		activityLayout = (LinearLayout) findViewById(R.id.activity_layout);
		
		leftMenuBtn.setOnClickListener(clickLisetner);
		homeBtn.setOnClickListener(clickLisetner);
		notificationBtn.setOnClickListener(clickLisetner);
		
		addPromiseBtn.setOnClickListener(clickLisetner);
		myPromiseBtn.setOnClickListener(clickLisetner);
		helperPromiseBtn.setOnClickListener(clickLisetner);
		friendPromiseBtn.setOnClickListener(clickLisetner);
		pointShopBtn.setOnClickListener(clickLisetner);
		settingBtn.setOnClickListener(clickLisetner);
		
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
		
		cursor = managedQuery(NotificationProvider.CONTENT_URI, null, null, null, null);
//		TextView text = new TextView(this);
//		text.setText("This is Header");
//		text.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				ContentValues row = new ContentValues();
//				row.put("title", "소지섭 added 4 photos of you.");
//				row.put("send_user_id", "100003943796581");
//				row.put("fb_id", "114560352018757");
//				getContentResolver().insert(NotificationProvider.CONTENT_URI, row);
////				adapter.notifyDataSetChanged();
//			}
//		});
//		notificationListView.addHeaderView(text);
		cursor.moveToFirst();
		adapter = new NotificationAdapter(this, cursor);
		notificationListView.setAdapter(adapter);
		
//		handler = new Handler();
//		Timer timer = new Timer();
//		timer.schedule(task, 1000, 10000);
		
//		int count = cursor.getCount();
//		if(count > 0) {
//			startManagingCursor(cursor);
//			NotificationAdapter adapter = new NotificationAdapter(this, cursor);
//			notificationListView.setAdapter(adapter);
//		}
	} 
	
	View.OnClickListener clickLisetner = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.left_menu_button:
					if(!Var.menuShowFlag){
						mySmoothScrollTo(0, 0);
					}else{
						mySmoothScrollTo(leftWidth, 0);
					}
				break;

			case R.id.home_button:
				intent.setAction("HomeActivity");
				startActivity(intent);
				finish();
				break;
				
			case R.id.notification_button:
				
				if(notificationLayout.isShown())
					notificationLayout.setVisibility(View.GONE);
				else {
					goneNewImage();
					notificationLayout.setVisibility(View.VISIBLE);
				}
				break;	
				
			case R.id.addPromiseLayout:
				mySmoothScrollTo(leftWidth, 0);
				intent.setAction("addpromise.AddPromiseActivity");
				startActivity(intent);
				break;

			case R.id.myPromiseLayout:
				mySmoothScrollTo(leftWidth, 0);
				intent.setAction("feedviewer.PromiseFeedList");
				intent.putExtra("mode", "me");
				startActivity(intent);
				break;
				
			case R.id.helperPromiseLayout:
				mySmoothScrollTo(leftWidth, 0);
				intent.setAction("feedviewer.PromiseFeedList");
				intent.putExtra("mode", "helper");
				startActivity(intent);
				break;
				
			case R.id.friendPromiseLayout:
				mySmoothScrollTo(leftWidth, 0);
				intent.setAction("feedviewer.PromiseFeedList");
				intent.putExtra("mode", "all");
				startActivity(intent);
				break;
				
			case R.id.pointShopLayout:
				mySmoothScrollTo(leftWidth, 0);
				break;
				
			case R.id.accountLayout:
				//logout dialog
				new AlertDialog.Builder(BeautifulPromiseActivity.this)
				.setTitle("로그아웃")
				.setMessage("로그아웃 하시겠습니까?")
				.setIcon(R.drawable.icon)
				.setPositiveButton("확인", logoutClickListener)
				.setNegativeButton("취소", null)
				.show();
				break;
			default:
				break;
			}
		}
	};
	
	/**
	 * Logout dialog 확인 click listener
	 */
	DialogInterface.OnClickListener logoutClickListener = new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			mFacebook = new Facebook(Var.APP_ID);
			SessionStore.clear(BeautifulPromiseActivity.this);
			
			intent = new Intent(BeautifulPromiseActivity.this, Intro.class);
			startActivity(intent);
		}
	};

//	TimerTask myTask = new TimerTask() {
//		
//		@Override
//		public void run() {
//			
//			Log.i("immk", "TimerTask Start");
//			
//			ContentResolver cr = getContentResolver();
////			ContentValues row = new ContentValues();
////			row.put("title", "Jaemyung Shin commented on 소지섭's photo of you: \"test\"");
////			row.put("send_user_id", "100001066448386");
////			row.put("fb_id", "113835812091211");
////			cr.insert(NotificationProvider.CONTENT_URI, row);
//			
//			ContentValues row = new ContentValues();
//			row.put("title", "소지섭 added 4 photos of you.");
//			row.put("send_user_id", "100003943796581");
//			row.put("fb_id", "114560352018757");
//			cr.insert(NotificationProvider.CONTENT_URI, row);
//		}
//	};
	
	TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			Log.i("immk", "aaaa");
			User user = Repository.getInstance().getUser();
			notificaitons = user.notifications();
			if(notificaitons.getData().size()>0){
				handler.post(new Runnable() {
					@Override
					public void run() {
						ContentResolver cr = getContentResolver();						
						for(Notifications notification : notificaitons.getData()){
							Log.i("immk", "id : " + notification.getId() + " title : " + notification.getTitle());
//							Uri notiUri = ContentUris.withAppendedId(NotificationProvider.CONTENT_URI, id);
//							Cursor cursor = cr.query(NotificationProvider.CONTENT_URI, new String[]{"_id"}, "fb_id=?", new String[]{notification.getId()}, null);
							Cursor cursor = cr.query(NotificationProvider.CONTENT_URI, new String[]{"_id"}, "fb_id=?", new String[]{notification.getId()}, null);
							if(cursor.getCount()==0){	
								ContentValues row = new ContentValues();
								row.put("title", notification.getTitle());
								row.put("send_user_id", notification.getFrom().getId());
								row.put("fb_id", notification.getId());
								cr.insert(NotificationProvider.CONTENT_URI, row);
								visibleNewImage();
							}
						}
					}
				});
			}
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
			} else {							// 왼쪽 메뉴 사라지게
				mySmoothScrollTo(leftWidth, y);
			}
		}
	}
	
	protected void mySmoothScrollTo(final int x, final int y) {
		hscroll.post(new Runnable() {
			@Override
			public void run() {
				if(x == 0)
					Var.menuShowFlag = true;
				else
					Var.menuShowFlag = false;
				
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
	
//	private IRemoteServiceCallback remoteServiceCallback = new IRemoteServiceCallback.Stub() {
//		
//		@Override
//		public String MessageCallback(int message) {
//			Log.i("immk", "callback : "+message);
//			return null;
//		}
//	};
//	
//	private ServiceConnection connection = new ServiceConnection() {
//		
//		@Override
//		public void onServiceConnected(ComponentName name, IBinder service) {
//			mService = IRemoteService.Stub.asInterface(service);
//			try {
//				mService.registerCallback(remoteServiceCallback);
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		@Override
//		public void onServiceDisconnected(ComponentName name) {
//			mService = null;
//		}
//	};
	
	public void visibleNewImage(){
		if(newImage.getVisibility() == View.INVISIBLE)
			newImage.setVisibility(View.VISIBLE);
	}
	public void goneNewImage(){
		newImage.setVisibility(View.INVISIBLE);
	}
	
	
}
