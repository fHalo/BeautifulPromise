package com.beautifulpromise.application.feedviewer;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.beautifulpromise.R;
import com.beautifulpromise.application.BeautifulPromiseActivity;
import com.beautifulpromise.application.feedviewer.adapter.LogListAdapter;

public class PromiseLogList extends BeautifulPromiseActivity{
	//feed item 들을 담고있는 array
	ArrayList<FeedItemDTO> arrayFeedItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//menu bar 추가
        LinearLayout feedListLayout = (LinearLayout)View.inflate(this, R.layout.feedviewer_feed_list, null);
        
        setActivityLayout(feedListLayout);
		
		//feed item 들을 담고있는 array
		arrayFeedItem = new ArrayList<FeedItemDTO>();
		
		//feed item 객체
		FeedItemDTO feedItem;
		
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
		
		
		//adapter 생성 후 레이아웃&데이터 세팅
		LogListAdapter logListAdapter = new LogListAdapter(this, R.layout.feedviewer_feed_item, arrayFeedItem);
		
		//list view 생성
		ListView feedList = (ListView)findViewById(R.id.feedList);
		
		//list view 와 adapter 연결
		feedList.setAdapter(logListAdapter);
		
	}

	
}
