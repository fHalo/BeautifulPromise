package com.beautifulpromise.parser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

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
		
//		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("id", ApplicationRepository.getInstance().getUserId()));

		try {
			URI url = null;
			if(params == null){
				url = URIUtils.createURI("http", MessageUtils.SERVER_ADDRESS, -1, site, null, null);
			} else{
				url = URIUtils.createURI("http", MessageUtils.SERVER_ADDRESS, -1, site, URLEncodedUtils.format(params, "UTF-8"), null);
			}
			Log.i("immk", url.toString());
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
	
	public ArrayList<Object> getResultList(String data){
		
		ArrayList<Object> list = new ArrayList<Object>();
		
		try {
			JSONObject json = new JSONObject(data);
			JSONArray jarray = json.getJSONArray("data");
			
			for(int i = 0 ; i < jarray.length() ; i++){
				JSONObject object = jarray.getJSONObject(i);
				Log.i("immk", object.keys().toString());
				list.add(object.getString(object.keys().toString()));
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
//	public static boolean insert(ArrayList<HashMap<String, Object>> list){
//		
//		HashMap<String, Object> row = null;
//		for(int i = 0 ; i < list.size() ; i++){
//			
//		}
//		return false;
//	}

}
