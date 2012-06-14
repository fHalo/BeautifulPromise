package com.beautifulpromise.parser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.beautifulpromise.common.utils.MessageUtils;
import com.facebook.halo.framework.json.JsonObject;

import android.util.Log;

public class HttpClients {
	
	DefaultHttpClient mHttpClient;
	
	public HttpClients(){
		
		mHttpClient = new DefaultHttpClient(); 
		HttpParams params = mHttpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 5000);
	}
	
	public String getUrlToJson(String site, ArrayList<NameValuePair> params) {
		
		try {
			URI url = null;
			if(params == null){
				url = URIUtils.createURI("http", MessageUtils.SERVER_ADDRESS, -1, site, null, null);
			} else{
				url = URIUtils.createURI("http", MessageUtils.SERVER_ADDRESS, -1, site, URLEncodedUtils.format(params, "UTF-8"), null);
			}
			HttpGet httpGet = new HttpGet(url);
			HttpResponse getResponse = mHttpClient.execute(httpGet);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK)
				return null;

			HttpEntity getResponseEntity = getResponse.getEntity();
			if (getResponseEntity != null)
				return EntityUtils.toString(getResponseEntity,"UTF-8");

		} catch (ClientProtocolException e) {
			return null;
		} catch (IOException e) {
			return null;
		} catch (URISyntaxException e) {
			return null;
		}
		return null;
	}
	
	public boolean getResult(String data){
		try {
			JSONObject json = new JSONObject(data);
			return json.getBoolean("result");
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	public ArrayList getResultList(String data){
		
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		try {
			JSONObject json = new JSONObject(data);
			JSONArray jarray = json.getJSONArray("data");
			
			for(int i = 0 ; i < jarray.length() ; i++){
				HashMap<String, String> map = new HashMap<String,String>();
				JSONObject object = jarray.getJSONObject(i);
				Iterator iterator = object.keys();
				while(iterator.hasNext()){
					String key = (String) iterator.next();
					String value = object.getString(key);
					map.put(key, value);
				}
				list.add(map);
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
