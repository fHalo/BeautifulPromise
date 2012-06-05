package com.beautifulpromise.common.utils;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewManager extends WebViewClient {

	/**
	 * 요청을 기본 웹킷아닌 액티비티내의 웹뷰에서 처리한다(캐싱 세팅을 위해 필요)
	 * 여길 거치지 않으면 캐싱 세팅이 안먹힘
	 */
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		view.loadUrl(url);
		return true;
	}

	/**
	 * webView imaage Resize
	 * @param url
	 * @return html body
	 */
	public static String webViewImageReSize(String url) {
		StringBuffer sb = new StringBuffer("<HTML>");
		sb.append("<HEAD>");
		sb.append("<META http-equiv=Content-Type content=\"text/html; charset=utf-8\">");
		sb.append("<META content=\"TAGFREE Active Designer v3.0\" name=GENERATOR>");
		sb.append("<META name=\"viewport\" content=\"user-scalable=yes, initial-scale=0.1, maximum-scale=0.0, minimum-scale=0.0");
		sb.append("</HEAD>");
		sb.append("<BODY style=\"margin:0;padding:0\">");
		sb.append("<img width=100%;height:100% src=\"" + url + "\"/>");
		// sb.append("</OBJECT>");
		sb.append("</BODY>");
		sb.append("</HTML>");
		String htmlBody = sb.toString();

		return htmlBody;
	}
}
