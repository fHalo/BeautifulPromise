package com.beautifulpromise.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class NotificationProvider extends ContentProvider {

	public static Uri CONTENT_URI = Uri.parse("content://com.beautifulpromise.database/notification");
	SQLiteDatabase db;
	public static final String TABLE = "Notifications";
	
	@Override
	public boolean onCreate() {
		DatabaseHelper helper = new DatabaseHelper(getContext());
		db = helper.getWritableDatabase();
		return true;
	}
	
	@Override
	public String getType(Uri uri) { 
		return null;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		
		return 0;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long id = db.insert(TABLE, null, values);
		if(id > 0){
			Uri notiUri = ContentUris.withAppendedId(CONTENT_URI, id);
			getContext().getContentResolver().notifyChange(notiUri, null);
			Log.i("immk", "notifyChange");
			return uri;
		}
		return null;
	}


	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		String sql;
		sql = "SELECT * FROM " + TABLE ;  // " WHERE _id="+uri.getPathSegments().get(1)
		Cursor cursor = db.rawQuery(sql, null);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
}
