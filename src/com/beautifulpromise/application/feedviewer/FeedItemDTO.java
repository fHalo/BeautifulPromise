package com.beautifulpromise.application.feedviewer;

import java.text.SimpleDateFormat;

import com.facebook.halo.application.types.Post;
import com.facebook.halo.application.types.Post.Comments;
import com.facebook.halo.application.types.User;
import com.facebook.halo.application.types.infra.NamedFacebookType;
import com.facebook.halo.framework.core.Connection;

public class FeedItemDTO {
	
	private String id;
	private String profileImagePath;
	private String name;
	private String date;
	private String photoImagePath;
	private String feed;
	private Comments comment;
	private Connection<NamedFacebookType> likePeople;
	private Long commentCount;
	private Long likeCount;
	
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
		
		likePeople = post.likePeople();
		
		comment = post.getComments();
		
		if(post.getLikesCount() == null)
			likeCount = 0L;
		else
			likeCount = post.getLikesCount();
		
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

	public final Connection<NamedFacebookType> getLikePeople() {
		return likePeople;
	}

	public final void setLikePeople(Connection<NamedFacebookType> likePeople) {
		this.likePeople = likePeople;
	}


}