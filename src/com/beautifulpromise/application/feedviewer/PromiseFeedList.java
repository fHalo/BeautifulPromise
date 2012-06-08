package com.beautifulpromise.application.feedviewer;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.beautifulpromise.R;
import com.beautifulpromise.application.BeautifulPromiseActivity;
import com.beautifulpromise.application.feedviewer.adapter.FeedListAdapter;
import com.beautifulpromise.common.repository.Repository;
import com.beautifulpromise.parser.Controller;
import com.facebook.halo.application.types.Post;
import com.facebook.halo.application.types.User;

public class PromiseFeedList extends BeautifulPromiseActivity{
	//feed item 들을 담고있는 array
	ArrayList<FeedItemDTO> arrayFeedItem;
	
	//서버에서 받아올 feed item list
	ArrayList<String> toDoList;
	
	//handler
	Handler mHandler;

	//feed item 객체
	FeedItemDTO feedItem;
	
	//server와의 interface 객체
	Controller ctrl;
	
	//사용자 정보를 담고 있는 객체 
	User user;
	
	//피드 정보를 담고 있는 객체
	Post feed;
	
	Intent intent;
	String mode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//객체생성
		arrayFeedItem = new ArrayList<FeedItemDTO>();
		toDoList = new ArrayList<String>();
		user = Repository.getInstance().getUser();
		ctrl = new Controller();
		feed = new Post();
		intent = getIntent();
		
//		mode = intent.getStringExtra("mode");
		mode = "me";
		
        //웹에서 데이터 받아오기
        mHandler = new Handler();
        mHandler.post(new Runnable() {

			@Override
			public void run() {
				//서버에서 자신의 피드 리스트 가져옴
				toDoList = ctrl.GetTodoList(mode);
				
				//가져온 데이터를 arrayList에 담음
				for(String s : toDoList) {
					feed = feed.createInstance(s);
					feedItem = new FeedItemDTO(feed);
					arrayFeedItem.add(feedItem);
				}
		        
//		        feed = feed.createInstance("100002579649067_289219304507389");
//		        feed = feed.createInstance("100003162160041_238075626307841");
//		        feed = feed.createInstance("113840848757374");
//		        feedItem = new FeedItemDTO(feed);
//		        arrayFeedItem.add(feedItem);
//		        
//		        feed = feed.createInstance("374610762596551");
//		        feedItem = new FeedItemDTO(feed);
//		        arrayFeedItem.add(feedItem);
//		        
//		        feed = feed.createInstance("369804936410467");
//		        feedItem = new FeedItemDTO(feed);
//		        arrayFeedItem.add(feedItem);
			}
        });
		
        //Layout setting
		LinearLayout feedListLayout = (LinearLayout)View.inflate(this, R.layout.feedviewer_feed_list, null);
		setActivityLayout(feedListLayout);
		
		//adapter 생성 후 레이아웃&데이터 세팅
		FeedListAdapter feedListAdapter = new FeedListAdapter(this, R.layout.feedviewer_feed_item, arrayFeedItem, mode);
		
		//list view 생성
		ListView feedList = (ListView)feedListLayout.findViewById(R.id.feedList);
		
		//list view 와 adapter 연결
		feedList.setAdapter(feedListAdapter);
	}

}
