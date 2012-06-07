package com.beautifulpromise.application.feedviewer.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beautifulpromise.R;
import com.beautifulpromise.application.feedviewer.FeedItemDTO;
import com.beautifulpromise.application.feedviewer.FeedWithReply;
import com.beautifulpromise.application.feedviewer.PromiseLogList;
import com.beautifulpromise.common.utils.ImageUtils;
import com.beautifulpromise.common.utils.WebViewManager;

public class FeedListAdapter extends BaseAdapter {
	Context context;
	LayoutInflater inflater;
	ArrayList<FeedItemDTO> arrayListFeedItem;
	int layout;
	String url;
	
	public FeedListAdapter(Context context, int layout, ArrayList<FeedItemDTO> arrayListFeedItem) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		//최초 호출시 커스텀뷰 생성
		if(convertView == null) {
			convertView = inflater.inflate(layout, parent, false);
		}
		
		//name setting
		TextView name = (TextView)convertView.findViewById(R.id.nameText);
		name.setText(arrayListFeedItem.get(position).getName());
		
		//date setting
		TextView date = (TextView)convertView.findViewById(R.id.dateText);
		date.setText(arrayListFeedItem.get(position).getDate());
		
		//profile Image setting with caching
		WebView profileImage = (WebView)convertView.findViewById(R.id.profileImage);
		profileImage.setWebViewClient(new WebViewManager());
		profileImage.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		profileImage.loadUrl(arrayListFeedItem.get(position).getProfileImagePath());
		
		//photo image setting
		WebView photoImage = (WebView)convertView.findViewById(R.id.photoImage);
		photoImage.setWebViewClient(new WebViewManager());
		photoImage.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		if(arrayListFeedItem.get(position).getPhotoImagePath() == null)
			photoImage.setVisibility(View.GONE);
		else {
			Log.e("position : " + position, arrayListFeedItem.get(position).getPhotoImagePath());
			url = ImageUtils.webViewImageReSize(arrayListFeedItem.get(position).getPhotoImagePath());
			photoImage.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
//			Log.e("EE", "H:" + photoImage.getHeight() + "// w : " + photoImage.getWidth());
//			 url = arrayListFeedItem.get(position).getPhotoImagePath();
//			 photoImage.loadUrl(url);
		}
		
		//feed setting
		TextView feed = (TextView)convertView.findViewById(R.id.feedText);
		feed.setText(arrayListFeedItem.get(position).getFeed());
		
		//reply setting
		TextView reply = (TextView)convertView.findViewById(R.id.replyText);
		reply.setText("" +arrayListFeedItem.get(position).getCommentCount());
		
		//like setting
		final TextView like = (TextView)convertView.findViewById(R.id.likeText);
		like.setText("" +arrayListFeedItem.get(position).getLikeCount());
		
		//reply & like click listener
		RelativeLayout viewerBottom = (RelativeLayout)convertView.findViewById(R.id.feedViewerBottom);
		viewerBottom.setOnClickListener(new RelativeLayout.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, FeedWithReply.class);
				intent.putExtra("feedId", arrayListFeedItem.get(position).getId());
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		});
		
		//promise log click listener
		TextView promiseLog = (TextView) convertView.findViewById(R.id.promiseLogText);
		promiseLog.setOnClickListener(new TextView.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, PromiseLogList.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		});
		
		return convertView;
	}
	
}
