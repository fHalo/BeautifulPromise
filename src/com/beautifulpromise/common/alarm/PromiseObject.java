package com.beautifulpromise.common.alarm;

public class PromiseObject {
	private String promisename;
	private int d_day;
	private int percent;
	private int category;
	
	
	public PromiseObject(String promisename, int d_day, int percent, int category) 
	{  
		this.promisename = promisename ;  
		this.d_day = d_day ;
		this.percent = percent ;
		this.category = category ;
	}
	
	
	public String getPromisename() {
		return promisename;
	}
	public void setPromisename(String promisename) {
		this.promisename = promisename;
	}
	
	public int getD_day() {
		return d_day;
	}
	public void setD_day(int d_day) {
		this.d_day = d_day;
	}
	
	public int getPercent() {
		return percent;
	}
	public void setPercent(int percent) {
		this.percent = percent;
	}
	
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	
	
	
}
