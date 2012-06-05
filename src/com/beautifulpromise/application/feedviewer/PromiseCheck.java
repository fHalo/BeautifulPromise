package com.beautifulpromise.application.feedviewer;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.beautifulpromise.R;
import com.beautifulpromise.application.feedviewer.adapter.LogListAdapter;

public class PromiseCheck extends Activity {
	//feed item 들을 담고있는 array
	ArrayList<FeedItemDTO> arrayFeedItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.feedviewer_check_promise);
		
		//feed item 들을 담고있는 array
		arrayFeedItem = new ArrayList<FeedItemDTO>();
		
//		//feed item 객체
//		FeedItemDTO feedItem;
//		
//		//feed item 임시 추가
//		feedItem = new FeedItemDTO();
//		feedItem.setDate("2012/4/12 PM 12:12");
//		feedItem.setFeed("hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello ");
//		feedItem.setLike(32);
//		feedItem.setName("Jaemyung Shin!");
//		feedItem.setReply(12);
////		feedItem.setPhotoImagePath(photoImagePath);
//		feedItem.setProfileImagePath("https://fbcdn-profile-a.akamaihd.net/hprofile-ak-snc4/371699_100001066448386_1124509348_q.jpg");
//		arrayFeedItem.add(feedItem);
//		
//		feedItem = new FeedItemDTO();
//		feedItem.setDate("2012/4/12 PM 12:12");
//		feedItem.setFeed("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
//		feedItem.setLike(32);
//		feedItem.setName("Jaemyung Shin!");
//		feedItem.setReply(12);
////		feedItem.setPhotoImagePath(photoImagePath);
//		feedItem.setProfileImagePath("https://fbcdn-profile-a.akamaihd.net/hprofile-ak-snc4/371699_100001066448386_1124509348_q.jpg");
//		arrayFeedItem.add(feedItem);
//		
//		feedItem = new FeedItemDTO();
//		feedItem.setDate("2012/4/12 PM 12:12");
//		feedItem.setFeed("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
//		feedItem.setLike(32);
//		feedItem.setName("Jaemyung Shin!");
//		feedItem.setReply(12);
////		feedItem.setPhotoImagePath(photoImagePath);
//		feedItem.setProfileImagePath("https://fbcdn-profile-a.akamaihd.net/hprofile-ak-snc4/371699_100001066448386_1124509348_q.jpg");
//		arrayFeedItem.add(feedItem);
//		
//		feedItem = new FeedItemDTO();
//		feedItem.setDate("2012/4/12 PM 12:12");
//		feedItem.setFeed("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
//		feedItem.setLike(32);
//		feedItem.setName("Jaemyung Shin!");
//		feedItem.setReply(12);
////		feedItem.setPhotoImagePath(photoImagePath);
//		feedItem.setProfileImagePath("https://fbcdn-profile-a.akamaihd.net/hprofile-ak-snc4/371699_100001066448386_1124509348_q.jpg");
//		arrayFeedItem.add(feedItem);
		
		//adapter 생성 후 레이아웃&데이터 세팅
		LogListAdapter logListAdapter = new LogListAdapter(this, R.layout.feedviewer_feed_item, arrayFeedItem);
		
		//list view 생성
		ListView feedList = (ListView)findViewById(R.id.promiseCheckList);
		
		//list view 와 adapter 연결
		feedList.setAdapter(logListAdapter);
		
		//yes or no button click listener
		Button yesBtn = (Button)findViewById(R.id.yesButton);
		Button noBtn = (Button)findViewById(R.id.noButton);
		
		//set yes btn click listener
		yesBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//페북에 성공 글 올리기
				Toast.makeText(PromiseCheck.this, "success", Toast.LENGTH_LONG).show();
			}
		});
		
		//set no btn click listener
		noBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//페북에 실패 글 올리기
				Toast.makeText(PromiseCheck.this, "failed", Toast.LENGTH_LONG).show();
			}
		});
	}

}
