package com.beautifulpromise.application.feedviewer;

import java.net.URL;
import java.util.ArrayList;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
	
	//feed item 객체
	FeedItemDTO feedItem;
	
	//server와의 interface 객체
	Controller ctrl;
	
	//사용자 정보를 담고 있는 객체 
	User user;
	
	//피드 정보를 담고 있는 객체
	Post feed;
	
	//progress bar
	LinearLayout feedProgressLayout;
	
	Intent intent;
	String mode;
	LinearLayout feedListLayout;
	FeedListAdapter feedListAdapter;
	ListView feedList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        //Layout setting
		feedListLayout = (LinearLayout)View.inflate(this, R.layout.feedviewer_feed_list, null);
		setActivityLayout(feedListLayout);
		
		//variable setting -> setContentView 다음에 나와야함
		setVariable();
		
		FeedLoadAsyncTask task = new FeedLoadAsyncTask();
		task.execute();
		
	}
	
	private void setVariable() {
		//객체생성
		arrayFeedItem = new ArrayList<FeedItemDTO>();
		toDoList = new ArrayList<String>();
		user = Repository.getInstance().getUser();
		ctrl = new Controller();
		feed = new Post();
		intent = getIntent();
		
//		mode = intent.getStringExtra("mode");
		mode = "me";
		
		//progress bar
		feedProgressLayout = (LinearLayout) findViewById(R.id.feedProgressLayout);
	
		//list view 생성
		feedList = (ListView)feedListLayout.findViewById(R.id.feedList);
	}
	
	private class FeedLoadAsyncTask extends AsyncTask<URL, Integer, Long> {

		@Override
		protected Long doInBackground(URL... params) {
//			//서버에서 자신의 피드 리스트 가져옴
//			toDoList = ctrl.GetTodoList(mode);
//			
//			//가져온 데이터를 arrayList에 담음
//			for(String s : toDoList) {
//				feed = feed.createInstance(s);
//				feedItem = new FeedItemDTO(feed);
//				arrayFeedItem.add(feedItem);
//			}
			
	        Post feed = new Post();
	        feed = feed.createInstance("114940478645556");
	        feedItem = new FeedItemDTO(feed);
	        arrayFeedItem.add(feedItem);
	        feedItem = new FeedItemDTO(feed);
	        arrayFeedItem.add(feedItem);
	        feedItem = new FeedItemDTO(feed);
	        arrayFeedItem.add(feedItem);
	        
//	        ctrl.PublishCheck("114940478645556", "11494047864555");
//	        toDoList = ctrl.GetCheckList("114940478645556");
	        Log.e("CHECK", "" + toDoList);
	        
			return null;
		}

		@Override
		protected void onPostExecute(Long result) {
			//progress bar 없에고, 받아온 데이터 띄워줌
			feedProgressLayout.setVisibility(View.GONE);
			feedList.setVisibility(View.VISIBLE);
			
			//adapter 생성 후 레이아웃&데이터 세팅
			feedListAdapter = new FeedListAdapter(getApplicationContext(), R.layout.feedviewer_feed_item, arrayFeedItem, mode);
			
			//list view 와 adapter 연결
			feedList.setAdapter(feedListAdapter);
			
			super.onPostExecute(result);
		}

	}
	
}
