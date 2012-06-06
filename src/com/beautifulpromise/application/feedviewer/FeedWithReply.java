package com.beautifulpromise.application.feedviewer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beautifulpromise.R;
import com.beautifulpromise.application.BeautifulPromiseActivity;
import com.beautifulpromise.application.feedviewer.adapter.ReplyListAdapter;
import com.beautifulpromise.common.repository.Repository;
import com.beautifulpromise.common.utils.WebViewManager;
import com.facebook.halo.application.types.Post;
import com.facebook.halo.application.types.User;

public class FeedWithReply extends BeautifulPromiseActivity{
	//handler
	Handler mHandler;

	//feed item 객체
	FeedItemDTO feedItem;
	
	//user 객체
	User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 		
        LinearLayout replyListLayout = (LinearLayout)View.inflate(this, R.layout.feedviewer_reply_list, null);
        setActivityLayout(replyListLayout);
        
        //reply 위에 feed와 ,아래 댓글달기 합치기
		ListView replyList = (ListView)findViewById(R.id.replyList);
        LinearLayout replyHeaderLayout = (LinearLayout)View.inflate(this, R.layout.feedviewer_reply_header_feed, null);
        replyList.addHeaderView(replyHeaderLayout);
        
        //feed id setting
        Intent intent = getIntent();
        String feedId = intent.getStringExtra("feedId");
        
        //feed data 가져오기 -> performance issue 있음 (전 액티비티에서 feed객체 넘겨받아야함)
        Post feed = new Post();
        feed = feed.createInstance(feedId);
        feedItem = new FeedItemDTO(feed);
        
        //Reply header feed에 각 view 값 setting
        setHeaderFeedView(feedItem);
		
		//adapter 생성 후 레이아웃&데이터 세팅
		ReplyListAdapter replyListAdapter = new ReplyListAdapter(this, R.layout.feedviewer_reply_item, feedItem.getComment(), feedItem.getLike());
		
		//list view 생성
		ListView feedList = (ListView)findViewById(R.id.replyList);
		
		//list view 와 adapter 연결
		feedList.setAdapter(replyListAdapter);
		
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
		else
			photoImage.loadUrl(feedItem.getPhotoImagePath());
		
		//like count setting
		TextView likeCount = (TextView)findViewById(R.id.likeCountText);
		likeCount.setText(""+feedItem.getLikeCount());
		
		//like setting
		final TextView like = (TextView)findViewById(R.id.likeFeed);
		if(isUserLiked(feedItem)) { //사용자가 좋아한 댓글인지 아닌지 체크
			like.setText("좋아요 취소");
		}
		like.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(like.getText().equals("좋아요")) { //좋아요하기
					like.setText("좋아요 취소");
					user.publishLikes(feedItem.getId());
				}
				else { //좋아요 취소하기
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
	private boolean isUserLiked(FeedItemDTO feedItem) {
		//좋아요한 id들과 자신의 id 를 비교
		for(int i = 0; i < feedItem.getLikeCount(); i++) {
			if(feedItem.getLike().getData().get(i).getId().equals(user.getId()))
				return true;
		}
		return false;
	}
	

}
