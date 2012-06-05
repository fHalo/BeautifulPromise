package com.beautifulpromise.application.addpromise;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.beautifulpromise.R;
import com.facebook.halo.application.types.connection.Friends;

public class FriendViewAdapter extends BaseAdapter {
	
	Context context;
	private List<Friends> friendsList;
	private LayoutInflater inflater;
	ViewHolder holder;
	
	public FriendViewAdapter(Context context, List<Friends> friendsList) {
		this.context = context;
		this.friendsList=friendsList;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return getFriendsList().size();
	}

	@Override
	public Object getItem(int position) {
		return getFriendsList().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public class ViewHolder {
		public CheckedTextView friendNameText;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Friends friend = getFriendsList().get(position);

		if(convertView == null) {
			convertView = inflater.inflate(R.layout.addpromise_friendlist_item, null);

			holder = new ViewHolder();
			holder.friendNameText = (CheckedTextView) convertView.findViewById(R.id.friend_name_text);
			convertView.setTag(holder);
		
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.friendNameText.setText(friend.getName());
		
		return convertView;
	}
	
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public List<Friends> getFriendsList() {
		return friendsList;
	}

	public void setFriendsList(List<Friends> friendsList) {
		this.friendsList = friendsList;
	}

}
