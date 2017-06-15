
package com.sseltzer.selenium.framework.verification.support;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 *
 *
 * Simple data class for handling HttpResponses. 
 * This lightweight alternative to keeping HttpUrlConnection
 * objects alive while threading in the UrlTester class. 
 *
 * @author ckiehl May 19, 2014
 */
public class HttpResponseObject {
	private String url;
	private Integer statusCode;
	
	public HttpResponseObject(HttpURLConnection connection) {
		this.url = connection.getURL().toString();
		try {
			this.statusCode = connection.getResponseCode();
		} catch (IOException e) {
			this.statusCode = null;
		}
	}

	public String getUrl() {
		return url;
	}

	public Integer getStatusCode() {
		return statusCode;
	}
}
