package com.beautifulpromise.application.addpromise;

import java.util.ArrayList;

import com.facebook.halo.application.types.connection.Friends;

import android.graphics.Bitmap;

public class AddPromiseDTO {

	private int id;
	private String userId;
	private int categoryId;
	private String title;
	private String startDate; 
	private String endDate;
	private boolean[] dayPeriod;
	private int time;
	private int min;
	private float latitue;
	private float longitude;
	private String content;
	private ArrayList<Friends> helperList;
	private DonationDTO donation;
	private String createDate;
	private String signPath;
	private Bitmap signBitmap;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public boolean[] getDayPeriod() {
		return dayPeriod;
	}
	public void setDayPeriod(boolean[] selectDay) {
		this.dayPeriod = selectDay;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ArrayList<Friends> getHelperList() {
		return helperList;
	}
	public void setHelperList(ArrayList<Friends> helperList) {
		this.helperList = helperList;
	}
	public String getSignPath() {
		return signPath;
	}
	public void setSignPath(String signPath) {
		this.signPath = signPath;
	}
	public Bitmap getSignBitmap() {
		return signBitmap;
	}
	public void setSignBitmap(Bitmap signBitmap) {
		this.signBitmap = signBitmap;
	}
	public DonationDTO getDonation() {
		return donation;
	}
	public void setDonation(DonationDTO donation) {
		this.donation = donation;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public float getLatitue() {
		return latitue;
	}
	public void setLatitue(float latitue) {
		this.latitue = latitue;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
}
