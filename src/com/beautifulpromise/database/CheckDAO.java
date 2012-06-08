package com.beautifulpromise.database;

import java.util.ArrayList;

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
	
	//GPS관련 메소드
	public boolean gpsinit(){
		db.delete("gps", null, null);
		return false;
		
	}
	
	public boolean gpsinsert(Double Latitude,Double Longitude){
		ContentValues row;
		row = new ContentValues();
		row.put("latitude", Latitude);
		row.put("longitude", Longitude);
		db.insert("gps", null, row);
		return false;
		
	}
	
	public boolean gpssampleinsert(){
		return false;
	}
	
	//feedcheck메소드
	
	public boolean feedcheckinsert(int id){
		try {
			ContentValues row;
			row = new ContentValues();
			row.put("promiseid", id);
			row.put("feedcheck", 1);
			db.update("feed", row, "promiseid=" + id, null);
		} catch (Exception e) {
			Log.e("ou", e.toString());
			return false;
		}
		
		return true;
	}
	
	public int feedcheckdo(int id){
		Cursor cursor = db.rawQuery("SELECT feedcheck FROM feed WHERE promiseid=" + id, null);
		cursor.moveToNext();
		int check = cursor.getInt(0);
		return check;
	}
	
	public boolean feedcheckinit(ArrayList<AddPromiseDTO> promisedto){
		ContentValues row;
		row = new ContentValues();
		db.delete("feed", null, null);
		try {
			for (AddPromiseDTO temppromise : promisedto) {
				row.clear();
				row.put("promiseid", temppromise.getId());
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
