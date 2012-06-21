package com.beautifulpromise.common.repository;

import com.facebook.halo.application.types.User;

/**
 * @description SingleTon Pattern을 활용하여 로그인한 사용자 정보와 id값을 저장
 * @author immk
 *
 */
public class Repository {

	private static Repository instance = new Repository();
	private User user = new User();

	public Repository() {
	}
	
	public static Repository getInstance(){
		return instance;
	}

	public User getUser() {
		return user;
	}

	public void setUser() {
		this.user = user.createInstance("me");
	}
	
	public String getUserId() {
		return user.getId();
	}
}