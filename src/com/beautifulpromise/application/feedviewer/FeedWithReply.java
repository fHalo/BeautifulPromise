package com.beautifulpromise.application.feedviewer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beautifulpromise.R;
import com.beautifulpromise.application.BeautifulPromiseActivity;
import com.beautifulpromise.application.feedviewer.adapter.ReplyListAdapter;
import com.beautifulpromise.common.repository.Repository;
import com.beautifulpromise.common.utils.ImageUtils;
import com.beautifulpromise.common.utils.WebViewManager;
import com.facebook.halo.application.types.Comment;
import com.facebook.halo.application.types.Post;
import com.facebook.halo.application.types.Post.Comments;
import com.facebook.halo.application.types.Post.Likes;
import com.facebook.halo.application.types.User;
import com.facebook.halo.application.types.infra.FacebookType;
import com.facebook.halo.framework.core.Connection;

public class FeedWithReply extends BeautifulPromiseActivity{
	
	//comment 들을 담고있는 array
	ArrayList<Comment> arrayComment;

	//feed item 객체
	FeedItemDTO feedItem;
	
	//user 객체
	User user;
	
	EditText replyText;
	Button submitButton;
	ListView replyList;
	LinearLayout replyHeaderLayout;
	Intent intent;
	String feedId;
	Post feed;
	ReplyListAdapter replyListAdapter;
	ListView feedList;
	Comment comment;
	Comments comments;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 		
		//arrayComment 객체생성
		arrayComment = new ArrayList<Comment>();
		
        LinearLayout replyListLayout = (LinearLayout)View.inflate(this, R.layout.feedviewer_reply_list, null);
        setActivityLayout(replyListLayout);
        
        //reply 위에 feed와 ,아래 댓글달기 합치기
		replyList = (ListView)findViewById(R.id.replyList);
        replyHeaderLayout = (LinearLayout)View.inflate(this, R.layout.feedviewer_reply_header_feed, null);
        replyList.addHeaderView(replyHeaderLayout);
        
        //feed id setting
        intent = getIntent();
        feedId = intent.getStringExtra("feedId");
        
        //feed data 가져오기 -> performance issue 있음 (전 액티비티에서 feed객체 넘겨받아야함)
        feed = new Post();
        feed = feed.createInstance(feedId);
        feedItem = new FeedItemDTO(feed);
        
        //Reply header feed에 각 view 값 setting
        setHeaderFeedView(feedItem);
        
        //feed의 댓글들 comment list에 삽입
        Connection<Comment> comments = feed.comments();
        for(List<Comment> commentList: comments)
        	for(Comment c : commentList) 
        		arrayComment.add(c);
        
		//adapter 생성 후 레이아웃&데이터 세팅
		replyListAdapter = new ReplyListAdapter(this, R.layout.feedviewer_reply_item, arrayComment);
		
		//list view 생성
		feedList = (ListView)findViewById(R.id.replyList);
		
		//list view 와 adapter 연결
		feedList.setAdapter(replyListAdapter);
		
		//댓글달기 
		replyText = (EditText)findViewById(R.id.replyEditText);
		submitButton = (Button)findViewById(R.id.replySubmitButton);
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FacebookType commentId = feed.publishComment(replyText.getText().toString());
				
				//추가된 댓글 listview에 바로 추가
				comment = new Comment();
				comment = comment.createInstance(commentId.getId());
				arrayComment.add(comment);
				replyListAdapter.notifyDataSetChanged();
				
				//edit Text clear
				replyText.setText("");
				replyText.clearFocus();
				
				//keyboard hide
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(replyText.getWindowToken(), 0); 
				
				//맨 밑으로 focusing
				feedList.setSelection(arrayComment.size());
			}
		});
	}
	
	/**
	 * Header Feed에 값들 세팅
	 * @param feedItem
	 */
	private void setHeaderFeedView(final FeedItemDTO feedItem) {
		
		//User object setting
		user = Repository.getInstance().getUser();
		
        //profile Image
        WebView profileImage = (WebView)findViewById(R.id.profileImage2);
        profileImage.setWebViewClient(new WebViewManager());
        profileImage.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        profileImage.loadUrl(feedItem.getProfileImagePath());
        
		//name setting
		TextView name = (TextView)findViewById(R.id.nameText2);
		name.setText(feedItem.getName());
		
		//date setting
		TextView date = (TextView)findViewById(R.id.dateText2);
		date.setText(feedItem.getDate());
		
		//feed setting
		TextView headerFeed = (TextView)findViewById(R.id.feedText2);
		headerFeed.setText(feedItem.getFeed());
		
		//photo image setting
		WebView photoImage = (WebView)findViewById(R.id.photoImage2);
		photoImage.setWebViewClient(new WebViewManager());
		photoImage.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		if(feedItem.getPhotoImagePath() == null)
			photoImage.setVisibility(View.GONE);
		else {
			String url = ImageUtils.webViewImageReSize(feedItem.getPhotoImagePath());
			photoImage.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
		}
		
		//like count setting
		final TextView likeCount = (TextView)findViewById(R.id.likeCountText);
		likeCount.setText(""+feedItem.getLikeCount());
		
		//like setting
		final TextView like = (TextView)findViewById(R.id.likeFeed);
		
		//사용자가 좋아한 댓글인지 아닌지 체크
		if(isUserLiked(feedItem.getLike())) { 
			like.setText("좋아요 취소");
		}
		
		//좋아요 버튼 클릭
		like.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(like.getText().equals("좋아요")) { //좋아요하기
					//좋아요 명수 한명 늘리기
					likeCount.setText("" + (Integer.parseInt(likeCount.getText().toString())+1));
					like.setText("좋아요 취소");
					user.publishLikes(feedItem.getId());
				}
				else { //좋아요 취소하기
					likeCount.setText("" + (Integer.parseInt(likeCount.getText().toString())-1));
					like.setText("좋아요");
					user.publishUndoLikes(feedItem.getId());
				}
			}
		});
	}
	
	/**
	 * 사용자가 해당 피드를 좋아요 했는지 검사
	 * @param feedItem
	 * @return 좋아요했을시 true
	 */
	private boolean isUserLiked(Likes likes) {
		//좋아요한 id들과 자신의 id 를 비교
		for(int i = 0; i < feedItem.getLikeCount(); i++) {
			if(likes.getData().get(i).getId().equals(user.getId()))
				return true;
		}
		return false;
	}
	

}
