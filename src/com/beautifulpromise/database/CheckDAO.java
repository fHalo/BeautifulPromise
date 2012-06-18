package com.beautifulpromise.database;

import java.util.ArrayList;

import junit.framework.TestFailure;

import com.beautifulpromise.common.dto.AddPromiseDTO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CheckDAO {
	private CheckDBHelper checkDBHelper;
	SQLiteDatabase db;
	public CheckDAO(CheckDBHelper checkDBHelper) {
		this.checkDBHelper = checkDBHelper;
		db = this.checkDBHelper.getWritableDatabase();
	}
	
	public void close(){
		db.close();
	}
	//GPS관련 메소드
	public boolean gpsinit(){
		db.delete("gps", null, null);
		return true;
		
	}

	public boolean gpsinsert(String id, Double Latitude,Double Longitude){
		ContentValues row;
		row = new ContentValues();
		row.put("promiseid", id);
		row.put("latitude", Latitude);
		row.put("longitude", Longitude);
		db.insert("gps", null, row);
		return true;
		
	}
	
	public Double[] getGPS(String id){
		Double [] Location = new Double[2];
		Cursor cursor = db.rawQuery("SELECT latitude, longitude FROM gps WHERE promiseid=" + id, null);
		cursor.moveToNext();
		Location[0] = cursor.getDouble(0);
		Location[1] = cursor.getDouble(1);
		return Location;
	}

	//feedcheck메소드
	public boolean feedcheckupdate(String id, int check){
		try {
			ContentValues row;
			row = new ContentValues();
			row.put("promiseid", id);
			row.put("feedcheck", check);
			db.update("feed", row, "promiseid=" + id, null);
		} catch (Exception e) {
			Log.e("ou", e.toString());
			return false;
		}
		return true;
	}
	
	public boolean feedcheckinsert(String id, int check){
		try {

			ContentValues row;
			row = new ContentValues();
			row.put("promiseid", id);
			row.put("feedcheck", check);
			db.insert("feed", null ,row);
		} catch (Exception e) {
			Log.e("ou", e.toString());
			return false;
		}
		return true;
	}
	
	public int feedcheckdo(String id){
		Cursor cursor = db.rawQuery("SELECT feedcheck FROM feed WHERE promiseid=" + id , null);
		int check;
		if(cursor.getCount() != 0)
		{
			cursor.moveToNext();
			check = cursor.getInt(0);
		}
		else
		{
			feedcheckinsert(id, 0);
			check = 0;
		}
		cursor.close();
 		return check;
	}
	
	public boolean feedcheckinit(ArrayList<AddPromiseDTO> promisedto){
		ContentValues row;
		row = new ContentValues();
		db.delete("feed", null, null);
		try {
			for (AddPromiseDTO temppromise : promisedto) {
				row.clear();
				row.put("promiseid", temppromise.getPostId());
				row.put("feedcheck", 0);
				db.insert("feed", null, row);
			}
		} catch (Exception e) {
			Log.e("ou", e.toString());
			return false;
		}
		return true;		
	}
}
