package com.beautifulpromise.application.feedviewer.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beautifulpromise.R;
import com.beautifulpromise.application.feedviewer.FeedItemDTO;
import com.beautifulpromise.application.feedviewer.FeedWithReply;
import com.beautifulpromise.common.utils.WebViewManager;

public class LogListAdapter extends BaseAdapter{
	Context context;
	LayoutInflater inflater;
	ArrayList<FeedItemDTO> arrayListFeedItem;
	int layout;
	
	public LogListAdapter(Context context, int layout, ArrayList<FeedItemDTO> arrayListFeedItem) {
		this.context = context;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.layout = layout;
		this.arrayListFeedItem = arrayListFeedItem; 
	}
	
	@Override
	public int getCount() {
		return arrayListFeedItem.size();
	}

	@Override
	public FeedItemDTO getItem(int position) {
		return arrayListFeedItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 각 항목의 뷰 생성
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		//최초 호출시 커스텀뷰 생성
		if(convertView == null) {
			convertView = inflater.inflate(layout, parent, false);
			
			//promise log 텍스트 삭제
			TextView promiseLog = (TextView) convertView.findViewById(R.id.promiseLogText);
			promiseLog.setVisibility(View.GONE);
		}
		
		//name setting
		TextView name = (TextView)convertView.findViewById(R.id.nameText);
		name.setText(arrayListFeedItem.get(position).getName());
		
		//date setting
		TextView date = (TextView)convertView.findViewById(R.id.dateText);
		date.setText(arrayListFeedItem.get(position).getDate());
		
		//feed setting
		TextView feed = (TextView)convertView.findViewById(R.id.feedText);
		feed.setText(arrayListFeedItem.get(position).getFeed());
		
		//reply image click listener
		ImageView replyImage = (ImageView)convertView.findViewById(R.id.replyImage);
		replyImage.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, FeedWithReply.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		});
		
		//reply setting
		TextView reply = (TextView)convertView.findViewById(R.id.replyText);
//		reply.setText("" +arrayListFeedItem.get(position).getReply());
		reply.setOnClickListener(new TextView.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, FeedWithReply.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);	
			}
		});

		//like image click listener
		ImageView likeImage = (ImageView)convertView.findViewById(R.id.likeImage);
		likeImage.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, FeedWithReply.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		});
		
		//like setting
		TextView like = (TextView)convertView.findViewById(R.id.likeText);
		like.setText("" +arrayListFeedItem.get(position).getLike());
		like.setOnClickListener(new TextView.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, FeedWithReply.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);				
			}
		});
		
		//profile image setting
		WebView profileImage = (WebView)convertView.findViewById(R.id.profileImage);
		profileImage.setWebViewClient(new WebViewManager());
		//캐싱하기
		profileImage.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		profileImage.loadUrl(arrayListFeedItem.get(position).getProfileImagePath());

		//photo image setting
		
		
		return convertView;
	}
	
}
