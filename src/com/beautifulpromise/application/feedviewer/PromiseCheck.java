package com.beautifulpromise.application.feedviewer;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.beautifulpromise.R;
import com.beautifulpromise.parser.Controller;

public class PromiseCheck extends Activity {
	//feed item 들을 담고있는 array
	ArrayList<FeedItemDTO> arrayFeedItem;
	
	//서버에서 받아올 feed item list
	ArrayList<String> checkList;
	
	//server와의 interface 객체
	Controller ctrl;
	
	LinearLayout checkListLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		//Layout setting
//		checkListLayout = (LienearLayout)View.inflate(this, R.layout.feedviewer_check_promise, null);
//		setActivityLayout(checkListLayout);
		
		//feed item 들을 담고있는 array
		arrayFeedItem = new ArrayList<FeedItemDTO>();
		
		
		//adapter 생성 후 레이아웃&데이터 세팅
//		LogListAdapter logListAdapter = new LogListAdapter(this, R.layout.feedviewer_feed_item, arrayFeedItem);
		
		//list view 생성
		ListView feedList = (ListView)findViewById(R.id.promiseCheckList);
		
		//list view 와 adapter 연결
//		feedList.setAdapter(logListAdapter);
		
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
