package com.beautifulpromise.application.feedviewer.adapter;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beautifulpromise.R;
import com.beautifulpromise.common.utils.WebViewManager;
import com.facebook.halo.application.types.Post.Comments;
import com.facebook.halo.application.types.Post.Likes;

public class ReplyListAdapter extends BaseAdapter{
	Context context;
	LayoutInflater inflater;
	int layout;
	Comments comments;
	Likes likes;

	public ReplyListAdapter(Context context, int layout, Comments comments, Likes likes) {
		this.context = context;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.layout = layout;
		this.comments = comments;
		this.likes = likes;
	}

	/**
	 * 각 항목의 뷰 생성
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		//최초 호출시 커스텀뷰 생성
		if(convertView == null) {
			convertView = inflater.inflate(layout, parent, false);
		}
	
		/************************************
		 * reply layout 
		 ************************************/
		
		//name setting
		TextView name = (TextView)convertView.findViewById(R.id.replyNameText);
		name.setText(comments.getData().get(position).getFrom().getName());
		
		//date setting
		TextView date = (TextView)convertView.findViewById(R.id.replyDateText);
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd aa hh:mm");
		date.setText("" + df.format(comments.getData().get(position).getCreatedTime()));
		
		//feed setting
		TextView feed = (TextView)convertView.findViewById(R.id.replyFeedText);
		feed.setText(comments.getData().get(position).getMessage());
		
		//reply profile image setting
		WebView profileImage = (WebView)convertView.findViewById(R.id.replyProfileImage);
		profileImage.setWebViewClient(new WebViewManager());
		profileImage.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		profileImage.loadUrl(comments.getData().get(position).getFrom().getPicture());
		
		//좋아요
		final TextView like = (TextView) convertView.findViewById(R.id.replyLikeText);
		like.setOnClickListener(new TextView.OnClickListener() {
			public void onClick(View v) {
				if(like.getText().equals("좋아요"))
					like.setText("좋아요 취소");
				else
					like.setText("좋아요");
				
				//좋아요 요청
				
			}
		});
		
		return convertView;
	}

	@Override
	public int getCount() {
		String count = null;
		if(comments == null) //comment null 일때
			count = "0";
		else //comment 있을때
			count = "" + comments.getCount();
		
		return Integer.parseInt(count);
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
