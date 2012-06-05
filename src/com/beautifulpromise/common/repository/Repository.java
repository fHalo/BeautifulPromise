package com.beautifulpromise.common.repository;

import com.facebook.halo.application.types.User;

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
}