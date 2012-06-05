package com.beautifulpromise.application.addpromise;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.beautifulpromise.R;
import com.beautifulpromise.application.addpromise.FriendViewAdapter.ViewHolder;
import com.facebook.halo.application.types.User;
import com.facebook.halo.application.types.connection.Friends;
import com.facebook.halo.framework.common.AccessToken;
import com.facebook.halo.framework.core.Connection;

public class FriendViewDialog extends Dialog{
	

    public FriendViewDialog(Context context, int theme) {
        super(context, theme);
    }
	
	public FriendViewDialog(Context context) {
		super(context);
	}
	public static class Builder {
    	 
        private Context context;
        View view;
    	private FriendViewDialog dialog;
    	ListView friendList;
    	Button okayBtn;
    	Button cancelBtn;
    	List<Friends> friends;
    	ArrayList<Friends> helpers;
    	
        public Builder(Context context, List<Friends> friends, ArrayList<Friends> helpers) {
            this.context = context;
            this.friends = friends;
            this.helpers = helpers;
        }
       
        public FriendViewDialog create() {
        	        	
        	LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	dialog = new FriendViewDialog(context, R.style.Dialog);
            view = inflater.inflate(R.layout.addpromise_friend_list_dialog, null);
            
            friendList = (ListView) view.findViewById(R.id.friend_listview);
            
//            FriendViewAdapter adapter = new FriendViewAdapter(context, friends);
            FriendsAdapter<Friends> adapter = new FriendsAdapter<Friends>(context, android.R.layout.simple_list_item_multiple_choice, friends);

            friendList.setItemsCanFocus(true);
            friendList.setAdapter(adapter);
         
            friendList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            
            if(helpers != null){
	            for(Friends helper : helpers){
	            	int position = friends.indexOf(helper);
	            	Log.i("immk", ""+ position);
	            	friendList.setItemChecked(position, true);
	            }
            }
//            friendList.setOnItemClickListener(itemClickListener);

            okayBtn = (Button) view.findViewById(R.id.okay_button);
            cancelBtn = (Button) view.findViewById(R.id.cancel_button);
            
            okayBtn.setOnClickListener(buttonClickListener);
            cancelBtn.setOnClickListener(buttonClickListener);
            
            dialog.setContentView(view);			
            return dialog;
        }
        
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.okay_button:
					SparseBooleanArray selectFriends = friendList.getCheckedItemPositions();
					ArrayList<Friends> friendsArr = new ArrayList<Friends>();
					int count = 0;
					for(int i = 0 ; i < selectFriends.size() ; i++){
						if(selectFriends.valueAt(i)){
							friendsArr.add(friends.get(selectFriends.keyAt(i)));
							count++;
							if(count == 5)
								break;
						}
					}
					((AddPromiseActivity)context).setHelperFriends(friendsArr);
					dialog.cancel();
					break;
				case R.id.cancel_button:
					dialog.cancel();
					break;
				default:
					break;
				}
			}
		};
	}
}
