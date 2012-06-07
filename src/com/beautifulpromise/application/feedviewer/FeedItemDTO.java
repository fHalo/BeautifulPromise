package com.beautifulpromise.application.feedviewer;

import java.text.SimpleDateFormat;
import java.util.List;

import com.facebook.halo.application.types.Comment;
import com.facebook.halo.application.types.Post;
import com.facebook.halo.application.types.Post.Comments;
import com.facebook.halo.application.types.Post.Likes;
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
	private Likes like;
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
		
		comment = post.getComments();
		
		like = post.getLikes();
		
		if(like == null)
			likeCount = 0L;
		else 
			likeCount = (long) like.getData().size();
		
		if(comment == null)
			commentCount = 0L;
		else {
			commentCount = (long) comment.getData().size();
		}
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
		int length = photoImagePath.length();
		String tmp = photoImagePath.substring(0, length-5) + "n" + photoImagePath.substring(length-4, length);
		return tmp;
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

	public final Likes getLike() {
		return like;
	}

	public final void setLike(Likes like) {
		this.like = like;
	}


}