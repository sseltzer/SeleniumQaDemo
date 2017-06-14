
package com.sseltzer.selenium.framework.verification.qr;

import org.jsoup.nodes.Document;

import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;
import com.sseltzer.selenium.framework.selenium.mapping.PageObject;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPage;
import com.sseltzer.selenium.framework.utility.parallelism.ConcurrentWebTools;

/**
 *
 *
 * QRDecoder.java
 * 
 * Uses http://zxing.org/ to "scan" a Coupon QR code and verify that it 
 * contains the values expected. 
 *
 * @author ckiehl May 16, 2014
 */
public class QRDecoder {
	
	private static final String BAD_URL_CODE = "Bad URL";
	private static final String SUCCESS_CODE = "Decode Succeeded";
	
	private static final String baseUrl = "http://zxing.org/w/decode?u=";
	private static final String urlRegex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

	public static String decode(WebPage webPage, PageObject pageObject) {
		String url = webPage.interactWithElement().getElementAttribute(pageObject, "src");
		return decode(url);
	}
	
	public static String decode(String qrUrl) {
		Document page = loadPage(qrUrl);
		validateOnSuccessPage(page);
		String decodedQrUrl = getDeodedUrl(page);
		if (!decodedQrUrl.matches(urlRegex))
			throw new FrameworkException("\n\nQR_CODE did not return valid URL\nReturned URL: " + decodedQrUrl);
		return decodedQrUrl;
	}
	
	private static Document loadPage(String url) {
		try {
			return ConcurrentWebTools.openAndParse(baseUrl + url);
		} 	catch (Exception e) {
			throw new FrameworkException("\n\nFailed while attempting to contact QR decode service\n\n");
		}
	}

	private static void validateOnSuccessPage(Document page) {
		String pageHeader = page.select("h1").text();
		if (pageHeader.contains(BAD_URL_CODE))
			throw new FrameworkException("\n\nURL was not valid, or did not return an image\n\n");
		if (!pageHeader.contains(SUCCESS_CODE))
			throw new FrameworkException("\n\nImage could not be decoded\n\n");
	}
	
	private static String getDeodedUrl(Document page) {
		return page.select("tr:nth-child(5) td:nth-child(2)").text();
	}
	

}
