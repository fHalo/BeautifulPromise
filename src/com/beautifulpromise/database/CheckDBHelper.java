package com.beautifulpromise.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CheckDBHelper extends SQLiteOpenHelper {
	public CheckDBHelper(Context context) {
		super(context, "check.db", null, 1);
	}

	public void onCreate(SQLiteDatabase db) {
		StringBuffer buffer = new StringBuffer();
//		buffer.append("CREATE TABLE feed (");
//		buffer.append("id INTEGER PRIMARY KEY AUTOINCREMENT,");
//		buffer.append("promiseid INT,");
//		buffer.append("check INT");
//		buffer.append(")");
//		db.execSQL(buffer.toString());
//		
		buffer.setLength(0);
		buffer.append("CREATE TABLE gps (");
		buffer.append("id INTEGER PRIMARY KEY AUTOINCREMENT,");
		buffer.append("promiseid INTEGER,");
		buffer.append("latitude DOUBLE,");
		buffer.append("longitude DOUBLE");
		buffer.append(")");
		db.execSQL(buffer.toString());
		
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS gps");
		db.execSQL("DROP TABLE IF EXISTS feed");
		onCreate(db);
	}
}
