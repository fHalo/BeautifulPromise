package com.beautifulpromise.application.feedviewer;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.beautifulpromise.R;
import com.beautifulpromise.application.BeautifulPromiseActivity;
import com.beautifulpromise.application.feedviewer.adapter.FeedListAdapter;
import com.facebook.halo.application.types.Post;

public class PromiseFeedList extends BeautifulPromiseActivity{
	//feed item 들을 담고있는 array
	ArrayList<FeedItemDTO> arrayFeedItem;
	
	//handler
	Handler mHandler;

	//feed item 객체
	FeedItemDTO feedItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        //웹에서 데이터 받아오기
        mHandler = new Handler();
        mHandler.post(new Runnable() {

			@Override
			public void run() {
		        Post feed = new Post();
		        feed = feed.createInstance("1675068773_2916094596176");
		        feedItem = new FeedItemDTO(feed);
		        arrayFeedItem.add(feedItem);
		        
		        feed = feed.createInstance("100001345286735_359498797438293");
		        feedItem = new FeedItemDTO(feed);
		        arrayFeedItem.add(feedItem);
		        
		        feed = feed.createInstance("1233102578_3213209649135");
		        feedItem = new FeedItemDTO(feed);
		        arrayFeedItem.add(feedItem);
			}
        });
		
        //Layout setting
		LinearLayout feedListLayout = (LinearLayout)View.inflate(this, R.layout.feedviewer_feed_list, null);
		setActivityLayout(feedListLayout);
		
		arrayFeedItem = new ArrayList<FeedItemDTO>();
		
		//adapter 생성 후 레이아웃&데이터 세팅
		FeedListAdapter feedListAdapter = new FeedListAdapter(this, R.layout.feedviewer_feed_item, arrayFeedItem);
		
		//list view 생성
		ListView feedList = (ListView)feedListLayout.findViewById(R.id.feedList);
		
		//list view 와 adapter 연결
		feedList.setAdapter(feedListAdapter);
	}

}
