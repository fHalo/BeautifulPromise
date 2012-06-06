package com.beautifulpromise.application.feedviewer.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beautifulpromise.R;
import com.beautifulpromise.common.repository.Repository;
import com.beautifulpromise.common.utils.WebViewManager;
import com.facebook.halo.application.types.Comment;
import com.facebook.halo.application.types.Post.Comments;
import com.facebook.halo.application.types.Post.Likes;
import com.facebook.halo.application.types.User;

public class ReplyListAdapter extends BaseAdapter{
	Context context;
	LayoutInflater inflater;
	int layout;
	Comments comments;
	Likes likes;
	User user;
	List<Comment> commentList;
	Comment comment;
	TextView like;

	public ReplyListAdapter(Context context, int layout, Comments comments, Likes likes) {
		this.context = context;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.layout = layout;
		this.comments = comments;
		this.likes = likes;
		user = Repository.getInstance().getUser();
		
		//댓글 리스트 생성(좋아요 여부 판단용)
		commentList = new ArrayList<Comment>();
		for(int i = 0; i < comments.getCount(); i++) {
			comment = new Comment();
			comment = comment.createInstance(comments.getData().get(i).getId());
			commentList.add(comment);
		}
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
	
		/************************************
		 * reply layout 
		 ************************************/
		
		//name setting
		final TextView name = (TextView)convertView.findViewById(R.id.replyNameText);
		name.setText(comments.getData().get(position).getFrom().getName());
		name.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("22","CC");
			}
		});

		//feed setting
		TextView feed = (TextView)convertView.findViewById(R.id.replyFeedText);
		feed.setText(comments.getData().get(position).getMessage());
		
		//reply profile image setting
		WebView profileImage = (WebView)convertView.findViewById(R.id.replyProfileImage);
		profileImage.setWebViewClient(new WebViewManager());
		profileImage.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		profileImage.loadUrl(comments.getData().get(position).getFrom().getPicture());
		
		//date setting
		final TextView date = (TextView)convertView.findViewById(R.id.replyDateText);
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd aa hh:mm");
		date.setText("" + df.format(comments.getData().get(position).getCreatedTime()));
//		date.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Log.e("CC","CC");
//			}
//		});
		
		//좋아요
		like = (TextView)convertView.findViewById(R.id.replyLikeText);
		if(commentList.get(position).getUserLikes()) { //사용자가 좋아한 댓글인지 아닌지 체크
			like.setText("　　좋아요 취소");
		}
		like.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("AB","SD");
				if(like.getText().equals("좋아요")) { //좋아요하기
					like.setText("좋아요 취소");
//					user.publishLikes(comments.getData().get(position).getId());
				}
				else { //좋아요 취소하기
					like.setText("좋아요");
//					user.publishUndoLikes(comments.getData().get(position).getId());
				}
				
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
