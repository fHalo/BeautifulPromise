package com.beautifulpromise.application.addpromise;

import android.graphics.Bitmap;

public class DonationDTO {

	private int id;
	private String title;
	private Bitmap bitmap;
	
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
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
}
