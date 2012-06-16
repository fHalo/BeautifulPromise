package com.beautifulpromise.application.feedviewer;

import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.beautifulpromise.R;
import com.beautifulpromise.application.addpromise.AddPromiseActivity;
import com.beautifulpromise.application.addpromise.RepeatDayDialog;
import com.beautifulpromise.application.addpromise.UploadDonationLetterDialog;
import com.beautifulpromise.database.DatabaseHelper;
import com.beautifulpromise.database.GoalsDAO;
import com.beautifulpromise.parser.Controller;
import com.facebook.halo.application.types.Post;

public class PromiseCheck extends Activity {
	//feed item 들을 담고있는 array
	ArrayList<FeedItemDTO> arrayFeedItem;
	
	//서버에서 받아올 feed item list
	ArrayList<String> checkList;
	
	//server와의 interface 객체
	Controller ctrl;
	
	//progress bar
	LinearLayout feedProgressLayout;
	
	LinearLayout checkListLayout;
	ListView feedList;
	Button yesBtn;
	Button noBtn;
	Intent intent;
	
	String mode;
	String feedId;
	boolean isCheck;
	
	Post feed;
	
	//feed item 객체
	FeedItemDTO feedItem;
	
	GoalsDAO goalsDAO;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedviewer_check_promise);
		
		setVariable();
		
		FeedLoadAsyncTask task = new FeedLoadAsyncTask();
		task.execute();
		
		
		//set yes btn click listener
		yesBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//페북에 성공 글 올리기
				Toast.makeText(PromiseCheck.this, "success", Toast.LENGTH_LONG).show();
//				goalsDAO.update(id, result)
				
//				UploadDonationLetterDialog.Builder builder = new UploadDonationLetterDialog.Builder(PromiseCheck.this, true, feedId);
				UploadDonationLetterDialog.Builder builder = new UploadDonationLetterDialog.Builder(PromiseCheck.this, true, "161990977266519");
				Dialog dialog = builder.create();
				dialog.show();
			}
		});
		
		//set no btn click listener
		noBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//페북에 실패 글 올리기
				Toast.makeText(PromiseCheck.this, "failed", Toast.LENGTH_LONG).show();
//				goalsDAO.update(id, result)
				
				UploadDonationLetterDialog.Builder builder = new UploadDonationLetterDialog.Builder(PromiseCheck.this, false, "161990977266519");
				Dialog dialog = builder.create();
				dialog.show();
			}
		});
	}
	
	private void setVariable() {
		//feed item 들을 담고있는 array
		arrayFeedItem = new ArrayList<FeedItemDTO>();
		checkList = new ArrayList<String>();
		ctrl = new Controller();
		
		mode = "me";
		isCheck = true;
		
		//list view 생성
		feedList = (ListView)findViewById(R.id.promiseCheckList);
		
		yesBtn = (Button)findViewById(R.id.yesButton);
		noBtn = (Button)findViewById(R.id.noButton);
		
		//progress bar
		feedProgressLayout = (LinearLayout) findViewById(R.id.checkProgressLayout);
		
		intent = getIntent();
		feedId = intent.getStringExtra("feedId");
		
		//db
		DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
		goalsDAO = new GoalsDAO(databaseHelper);
	}
	
	private class FeedLoadAsyncTask extends AsyncTask<URL, Integer, Long> {

		@Override
		protected Long doInBackground(URL... params) {
			checkList = ctrl.GetCheckList(feedId);
			
			//가져온 데이터를 arrayList에 담음
			for(String s : checkList) {
				Log.e("s : ", "" + s);
				feed = new Post();
				feed = feed.createInstance(s);
				if(feed != null ) {
					feedItem = new FeedItemDTO(feed);
					arrayFeedItem.add(feedItem);
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Long result) {
			//progress bar 없에고, 받아온 데이터 띄워줌
			feedProgressLayout.setVisibility(View.GONE);
			feedList.setVisibility(View.VISIBLE);
			
			//adapter 생성 후 레이아웃&데이터 세팅
			FeedListAdapter feedListAdapter = new FeedListAdapter(getApplicationContext(), R.layout.feedviewer_feed_item, arrayFeedItem, mode, isCheck);
			
			//list view 와 adapter 연결
			feedList.setAdapter(feedListAdapter);
			
			super.onPostExecute(result);
		}
	}

}
