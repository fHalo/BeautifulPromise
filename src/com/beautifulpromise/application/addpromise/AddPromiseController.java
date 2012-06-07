package com.beautifulpromise.application.addpromise;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.beautifulpromise.common.repository.Repository;
import com.beautifulpromise.common.utils.MessageUtils;
import com.beautifulpromise.parser.HttpClients;

public class AddPromiseController {
	
	HttpClients client;
	
	public AddPromiseController(){
		client = new HttpClients();
	}
	
	public boolean InsertPromise(int campaignId, String facebookId, String title, String startDate, String endDate){
		boolean isSuccess = false;
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("fb_key", Repository.getInstance().getUserId()));
		params.add(new BasicNameValuePair("pl_key", ""+campaignId));
		params.add(new BasicNameValuePair("td_article_id", facebookId));
		params.add(new BasicNameValuePair("td_title", title));
		params.add(new BasicNameValuePair("td_start_date", startDate));
		params.add(new BasicNameValuePair("td_end_date", endDate));
		String data = client.getUrlToJson(MessageUtils.SET_NEW_TODO, params);
		if(data != null)
			isSuccess = client.getResult(data);
		return isSuccess;
	}
}
