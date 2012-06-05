package com.beautifulpromise.application.addpromise;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;

import com.beautifulpromise.common.utils.ImageUtils;
import com.beautifulpromise.common.utils.MessageUtils;
import com.facebook.halo.application.types.connection.Friends;

public class FriendImageAdapter extends BaseAdapter{

	Context context;
	ArrayList<Friends> friendsList;
	
	public FriendImageAdapter(Context context, ArrayList<Friends> friendsList) {
		this.context = context;
		this.friendsList = friendsList;
	}
	
	@Override
	public int getCount() {
		if(friendsList != null)
			return friendsList.size();
		return 0;
	}

	@Override
	public Object getItem(int position) {

		if(friendsList != null && getCount() != 0)
			return friendsList.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Friends friend = (Friends) getItem(position);
		WebView imageView = new WebView(context);
//		imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		imageView.loadDataWithBaseURL(null, ImageUtils.webViewImageReSize(MessageUtils.FACEBOOK_GRAPH_URL + friend.getId() + "/picture" ), "text/html", "utf-8", null);
		return imageView;
	}

	public float getScale(boolean focused, int offset) {
		return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
	}
}