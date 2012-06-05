package com.beautifulpromise.common.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

public class DateUtils {

	public static String getDate() {

		DecimalFormat decimalFormat = new DecimalFormat("00");
		DecimalFormat NumFormat = new DecimalFormat("0000");

		Calendar currentDate = Calendar.getInstance();
		int year = currentDate.get(Calendar.YEAR);
		int month = currentDate.get(Calendar.MONTH);
		int date = currentDate.get(Calendar.DATE);

		String resultDate = NumFormat.format(year) + "년 "
				+ (month + 1) + "월 "
				+ (date) + "일";
		return resultDate;
	}
	
	public static String getNextDate() {

		DecimalFormat decimalFormat = new DecimalFormat("00");
		DecimalFormat NumFormat = new DecimalFormat("0000");

		Calendar currentDate = Calendar.getInstance();
		int year = currentDate.get(Calendar.YEAR);
		int month = currentDate.get(Calendar.MONTH);
		int date = currentDate.get(Calendar.DATE);

		String resultDate = NumFormat.format(year) + "년 "
				+ (month + 1) + "월 "
				+ (date + 1) + "일";
		return resultDate;
	}


	public static String convertDateFormat(String dateStr, String format) {
		// ex) Sun, 19 Oct 2008 15:00 GMT ->
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		date.setTime(Date.parse(dateStr));
		return dateFormat.format(date);
	}
	
	public static int getYear(){
		Calendar currentDate = Calendar.getInstance();
		return currentDate.get(Calendar.YEAR);
	}

	public static int getMonth(){
		Calendar currentDate = Calendar.getInstance();
		return currentDate.get(Calendar.MONTH);
	}
	
	public static int getDay(){
		Calendar currentDate = Calendar.getInstance();
		return currentDate.get(Calendar.DATE);
	}

	public static String getCreateDate() {
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		Date currentTime = new Date();
		return formatter.format (currentTime);	
	}
	
	public static String convertStringDate(String dateStr){

		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy년 MM월 dd일");	
		Date date = null;
		try {
			date = (Date) formatter.parse(dateStr);
			Log.i("immk", ""+date.toString());
			formatter = new SimpleDateFormat ("yyyyMMdd");
			return formatter.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}  
	}
}
