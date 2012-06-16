package com.beautifulpromise.application.addpromise;

import android.graphics.drawable.Drawable;

public class DonationDTO {

	private int id;
	private String title;
	private String details;
	private Drawable drawable;
	private Drawable afterDrawable;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Drawable getDrawable() {
		return drawable;
	}
	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Drawable getAfterDrawable() {
		return afterDrawable;
	}
	public void setAfterDrawable(Drawable afterDrawable) {
		this.afterDrawable = afterDrawable;
	}
}
