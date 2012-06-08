package com.beautifulpromise.application;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.beautifulpromise.R;
import com.beautifulpromise.common.utils.ImageUtils;

public class NotificationAdapter extends CursorAdapter{

	Context context;
	private LayoutInflater inflater;
	
	public NotificationAdapter(Context context, Cursor c) {
		super(context, c);
		this.context = context;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		WebView userImage = (WebView) view.findViewById(R.id.user_image);		
		TextView title = (TextView) view.findViewById(R.id.noti_title_textview);
		userImage.setBackgroundColor(Color.TRANSPARENT);
		userImage.loadDataWithBaseURL(null, ImageUtils.webViewImageReSize("http://graph.facebook.com/" + cursor.getString(cursor.getColumnIndex("send_user_id")) +  "/picture"), "text/html", "utf-8",null);
		title.setText(cursor.getString(cursor.getColumnIndex("title")));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return inflater.inflate(R.layout.notificaion_item, null);
	}
	
	
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}

}
