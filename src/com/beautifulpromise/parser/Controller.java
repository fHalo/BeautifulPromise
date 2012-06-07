package com.beautifulpromise.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.beautifulpromise.common.repository.Repository;
import com.beautifulpromise.common.utils.MessageUtils;

public class Controller {
	
	HttpClients client;
	
	public Controller(){
		client = new HttpClients();
	}
	
	/**
	 * @description 신규 체크 생성
	 * @param postId(facebook Post Id), checkId (facebook Post Id)
	 * @return boolean
	 */
	public boolean PublishCheck(String postId, String checkId) {
		boolean isSuccess = false;
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("td_article_id", postId));
		params.add(new BasicNameValuePair("tdc_article_id", checkId));
		String data = client.getUrlToJson(MessageUtils.SET_NEW_CHECK, params);
		if(data != null)
			isSuccess = client.getResult(data);
		return isSuccess;
	}
	
	/**
	 * @description 포인트추가
	 * @param point
	 * @return boolean
	 */
	public boolean AddPoint (int point) {
		boolean isSuccess = false;
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("fb_key", Repository.getInstance().getUserId()));
		params.add(new BasicNameValuePair("point", ""+point));
		String data = client.getUrlToJson(MessageUtils.SET_NEW_POINT, params);
		if(data != null)
			isSuccess = client.getResult(data);
		return isSuccess;
	}
	
	/**
	 * @description 캠페인에 포인트 추가 (기부)
	 * @param projectId, point
	 * @return boolean
	 */
	public boolean DonationPointToProject (int projectId, int point) {
		boolean isSuccess = false;
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("pl_key", ""+projectId));
		params.add(new BasicNameValuePair("point", ""+point));
		String data = client.getUrlToJson(MessageUtils.SET_ADD_PROJECT_POINT, params);
		if(data != null)
			isSuccess = client.getResult(data);
		return isSuccess;
	}
	
	/**
	 * @description Todo 체크 리스트
	 * @param postId
	 * @return boolean
	 */

	@SuppressWarnings({"unchecked", "rawtypes"})
	public ArrayList<String> GetCheckList (String postId) {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("td_article_id", postId));
		String data = client.getUrlToJson(MessageUtils.GET_TODO_CHECK_LIST, params);
		if(data != null){
			ArrayList<HashMap> rows = client.getResultList(data);
			for(HashMap row : rows){
				list.add((String) row.get("tdc_article_id"));
			}
		}
		return list;
	}
	
	/**
	 * @description Todo 의 헬퍼 리스트
	 * @param postId
	 * @return boolean
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public ArrayList<String> GetHelperList (String postId) {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("td_article_id", ""+postId));
		String data = client.getUrlToJson(MessageUtils.GET_TODO_HELPER_LIST, params);
		if(data != null){
			ArrayList<HashMap> rows = client.getResultList(data);
			for(HashMap row : rows){
				list.add((String) row.get("fb_member_fb_key"));
			}
		}
		return list;
	}
	
	/**
	 * @description Todo 체크 리스트
	 * @param postId
	 * @return boolean
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public ArrayList<String> GetProjectStatus (int projectId) {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("pl_key", ""+projectId));
		String data = client.getUrlToJson(MessageUtils.GET_PROJECT_STATUS, params);
		if(data != null){
			ArrayList<HashMap> rows = client.getResultList(data);
			for(HashMap row : rows){
				list.add((String) row.get("pl_goal"));
				list.add((String) row.get("pl_now_status"));
			}
		}
		return list;
	}
}
