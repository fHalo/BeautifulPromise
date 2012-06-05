package com.beautifulpromise.application.checkpromise;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Cycle_Gps_DBHelper extends SQLiteOpenHelper {
	public Cycle_Gps_DBHelper(Context context) {
		super(context, "Gps.db", null, 1);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE gps ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
		"latitude DOUBLE, longitude DOUBLE);");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS gps");
		onCreate(db);
	}
}
