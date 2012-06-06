package com.beautifulpromise.application.feedviewer;

import java.text.SimpleDateFormat;

import com.facebook.halo.application.types.Post;
import com.facebook.halo.application.types.Post.Comments;
import com.facebook.halo.application.types.Post.Likes;
import com.facebook.halo.application.types.User;

public class FeedItemDTO {
	
	private String id;
	private String profileImagePath;
	private String name;
	private String date;
	private String photoImagePath;
	private String feed;
	private Comments comment;
	private Likes like;
	private Long commentCount;
	private Long likeCount;
	private boolean liked;
	
	public FeedItemDTO() {}
	
	/**
	 * post 객체를 feed 객체로 변환하는 생성자
 	 * @param post
	 */
	public FeedItemDTO(Post post) {
		id = post.getId();
		
		name = post.getFrom().getName();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd aa hh:mm");
		date = df.format(post.getCreatedTime());
		
		User user = new User();
		user = user.createInstance(post.getFrom().getId());
		
		profileImagePath = user.picture();
		
		photoImagePath = post.getPicture();
		
		feed = post.getMessage();
		
		like = post.getLikes();
		
		comment = post.getComments();
		
		if(like == null)
			likeCount = 0L;
		else 
			likeCount = like.getCount();
		
		if(comment == null)
			commentCount = 0L;
		else
			commentCount = comment.getCount();
	}
	
	/**
	 * Getter & Setter
	 */
	public final String getProfileImagePath() {
		return profileImagePath;
	}
	public final void setProfileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}
	public final String getName() {
		return name;
	}
	public final void setName(String name) {
		this.name = name;
	}
	public final String getDate() {
		return date;
	}
	public final void setDate(String date) {
		this.date = date;
	}
	public final String getPhotoImagePath() {
		return photoImagePath;
	}
	public final void setPhotoImagePath(String photoImagePath) {
		this.photoImagePath = photoImagePath;
	}
	public final String getFeed() {
		return feed;
	}
	public final void setFeed(String feed) {
		this.feed = feed;
	}

	public final Comments getComment() {
		return comment;
	}

	public final void setComment(Comments comment) {
		this.comment = comment;
	}

	public final Likes getLike() {
		return like;
	}

	public final void setLike(Likes like) {
		this.like = like;
	}

	public final Long getCommentCount() {
		return commentCount;
	}

	public final void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	public final Long getLikeCount() {
		return likeCount;
	}

	public final void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}


}