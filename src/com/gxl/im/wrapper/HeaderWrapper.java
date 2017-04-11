package com.gxl.im.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class HeaderWrapper {
	
	private static final String HEADER_CONTENT_TYPE = "Content-Type";
	
	private static final String HEADER_AUTH = "Authorization";

	private static final String RESTRICT_ACCESS = "restrict-access";

	private static final String THUMBNAIL = "thumbnail";

	private static final String SHARE_SECRET = "share-secret";

	private static final String ACCEPT = "Accept";
	
	private List<NameValuePair> headers = new ArrayList<NameValuePair>();
	
	public static HeaderWrapper newInstance() {
		return new HeaderWrapper();
	}
	
	public HeaderWrapper addHeader(String key, String value) {
		if( key.equals("") || value.equals("") ){
			return this;
		}
		
		headers.add(new BasicNameValuePair(key, value));
		return this;
	}
	
	public HeaderWrapper addJsonContentHeader() {
		return addHeader(HEADER_CONTENT_TYPE, "application/json");
	}
	
	public HeaderWrapper addAuthorization(String token) {
		return addHeader(HEADER_AUTH, "Bearer " + token);
	}

	public HeaderWrapper addRestrictAccess() {
		return addHeader(RESTRICT_ACCESS, "true");
	}

	public HeaderWrapper addThumbnail() {
		return addHeader(THUMBNAIL, "true");
	}

	public HeaderWrapper addShareSecret(String secret) {
		return addHeader(SHARE_SECRET, secret);
	}

	public HeaderWrapper addMediaAccept() {
		return addHeader(ACCEPT, "application/octet-stream");
	}

	public List<NameValuePair> getHeaders() {
		return headers;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for( NameValuePair header : headers ) {
			sb.append("[").append(header.getName()).append(":").append(header.getValue()).append("] ");
		}
		
		return sb.toString();
	}
	
}
