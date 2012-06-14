package com.beautifulpromise.application.addpromise;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.beautifulpromise.common.dto.AddPromiseDTO;
import com.beautifulpromise.common.parser.HttpClients;
import com.beautifulpromise.common.repository.Repository;
import com.beautifulpromise.common.utils.DateUtils;
import com.beautifulpromise.common.utils.MessageUtils;
import com.facebook.halo.application.types.connection.Friends;

public class AddPromiseController {
	
	HttpClients client;
	
	public AddPromiseController(){
		client = new HttpClients();
	}
	
	public boolean InsertPromise(int campaignId, String facebookId, String title, String startDate, String endDate, ArrayList<Friends> helpers){
		boolean isSuccess = false;
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("fb_key", Repository.getInstance().getUserId()));
		params.add(new BasicNameValuePair("pl_key", ""+campaignId));
		params.add(new BasicNameValuePair("td_article_id", facebookId));
		params.add(new BasicNameValuePair("td_title", title));
		params.add(new BasicNameValuePair("td_start_date", startDate));
		params.add(new BasicNameValuePair("td_end_date", endDate));
		String friendStr = "";
		if(helpers != null && helpers.size() > 0){
			int size = helpers.size();
			for(int i = 0 ; i < size ; i++){
				friendStr += helpers.get(i).getId();
				if(i != size-1)
					friendStr += ",";
			}
			params.add(new BasicNameValuePair("helper_list", friendStr));
		}
		String data = client.getUrlToJson(MessageUtils.SET_NEW_TODO, params);
		if(data != null)
			isSuccess = client.getResult(data);
		return isSuccess;
	}
	
	public boolean InsertPromise(AddPromiseDTO addPromiseDTO){
		boolean isSuccess = false;
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("fb_key", Repository.getInstance().getUserId()));
		params.add(new BasicNameValuePair("pl_key", ""+addPromiseDTO.getDonation().getId()));
		params.add(new BasicNameValuePair("td_article_id", addPromiseDTO.getPostId()));
		params.add(new BasicNameValuePair("td_title", addPromiseDTO.getTitle()));
		params.add(new BasicNameValuePair("td_start_date", DateUtils.convertStringDate(addPromiseDTO.getStartDate())));
		params.add(new BasicNameValuePair("td_end_date", DateUtils.convertStringDate(addPromiseDTO.getEndDate())));
		
		String friendStr = "";
		if(addPromiseDTO.getHelperList() != null &&  addPromiseDTO.getHelperList().size() > 0){
			int size = addPromiseDTO.getHelperList().size();
			for(int i = 0 ; i < size ; i++){
				friendStr += addPromiseDTO.getHelperList().get(i).getId();
				if(i != size-1)
					friendStr += ",";
			}
			params.add(new BasicNameValuePair("helper_list", friendStr));
		}
		String data = client.getUrlToJson(MessageUtils.SET_NEW_TODO, params);
		if(data != null)
			isSuccess = client.getResult(data);
		return isSuccess;
	}
}
