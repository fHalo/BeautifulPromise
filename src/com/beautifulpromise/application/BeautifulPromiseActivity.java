package com.beautifulpromise.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.beautifulpromise.R;
import com.beautifulpromise.application.addpromise.AddPromiseActivity;
import com.beautifulpromise.application.feedviewer.PromiseFeedList;
import com.beautifulpromise.common.Var;
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
	
	LinearLayout observerLayout;
	LinearLayout notificationLayout;
	LinearLayout leftMenuLayout;
	LinearLayout activityLayout;
	
	HorizontalScrollView hscroll;
	private int leftWidth = Var.LEFT_MENUBAR_WIDTH;
	
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
		
		observerLayout = (LinearLayout) findViewById(R.id.observer_image);
		notificationLayout  = (LinearLayout) findViewById(R.id.notification_image);
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
				else
					notificationLayout.setVisibility(View.VISIBLE);
				
				observerLayout.setVisibility(View.GONE);
				break;	
				
			case R.id.add_promise_btn:
				mySmoothScrollTo(leftWidth, 0);
				Var.menuShowFlag = false;
				intent = new Intent(BeautifulPromiseActivity.this, AddPromiseActivity.class);
				startActivity(intent);
				break;

			case R.id.my_promise_btn:
				intent = new Intent(BeautifulPromiseActivity.this, PromiseFeedList.class);
				startActivity(intent);
				break;
				
			case R.id.helper_promise_btn:
				intent = new Intent(BeautifulPromiseActivity.this, PromiseFeedList.class);
				startActivity(intent);
				break;
				
			case R.id.friend_promise_btn:
				intent = new Intent(BeautifulPromiseActivity.this, PromiseFeedList.class);
				startActivity(intent);
				break;
				
			case R.id.pointshop_btn:
				
				break;
				
			case R.id.setting_btn:
				
				break;
			default:
				break;
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
