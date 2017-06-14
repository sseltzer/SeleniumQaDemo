package com.sseltzer.selenium.framework.verification.url;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

import com.sseltzer.selenium.framework.error.ErrorManager;
import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;
import com.sseltzer.selenium.framework.jsoup.toolkit.ParallelWebTools;
import com.sseltzer.selenium.framework.jsoup.webpages.SoupPage;
import com.sseltzer.selenium.framework.selenium.mapping.PageObject;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPage;
import com.sseltzer.selenium.framework.utility.parallelism.ConcurrentWebTools;
import com.sseltzer.selenium.framework.verification.junit.TestScript;

/**
* ImageValidator.java is a collection of methods for checking the response codes 
* of Pages, Images, and iFrames -- anything that is loaded via a seperate GET request on the 
* host page. Selenium is dense to these kinds of objects, and thus will incorrectly report that 
* everything is OK even if the (for instance) Image was broken. 
* 
* TODO: 
* 	- Keep track of and report redirects? 
* 
* @author ckiehl Apr 24, 2014
*/
public class UrlTester extends TestScript {
	private static final int STATUS_OK = 200;
//	private static final int STATUS_REDIRECT = 301;
//	private static final int STATUS_FORBIDDEN = 403;
	private static final String regex404 = "^404 error";
	private static final String STATUS_404 = "404";
	private static final String regexAppError = "^application error";
	private static final String STATUS_APP_ERROR = "application error";

	/**
	* Verifies that the server's response is 200 OK, otherwise
	* throws a framework exception, as the target page failed to load.
	* 
	* Given a 200 OK response, it then checks the body text for a
	* custom 404 Error, 'Application Error,' or any other error response. 
	* 
	* If found, a FrameworkException is Thrown.
	 * @throws Exception 
	*/
	public static void verifyUrl(String url) throws FrameworkException {
		verifyUrls(Arrays.asList(url));
	}
	
	//TODO mw: should throw an exception when list is empty: urls
	public static void verifyUrls(List<String> urls) throws FrameworkException {
		List<Response> responses = ConcurrentWebTools.loadResponses(urls);
		validateResponses(responses);
	}

	public static void verifyPageLink(WebPage webPage, PageObject pageObject) {
		UrlValidator.validateElementContainsURL(webPage, pageObject, "href");
		String url = webPage.interactWithElement().getElementAttribute(pageObject, "href");
		verifyResponse(url);
	}

	public static void verifyImage(WebPage webPage, PageObject pageObject) {
		UrlValidator.validateElementContainsURL(webPage, pageObject, "src");
		String url = webPage.interactWithElement().getElementAttribute(pageObject, "src");
		System.out.println(url);
		verifyResponse(url);
	}

	private static void verifyResponse(String url) {
		Response response = ConcurrentWebTools.loadResponses(url);
		if (response.statusCode() != STATUS_OK) 
			ErrorManager.throwAndDump(formatErrorCode(url, "" + response.statusCode()));
		
		Document responseData = ConcurrentWebTools.parseResponses(response);
		String bodyText = responseData.body().text(); 
		
		if (bodyText.matches(regex404)) 
			ErrorManager.throwAndDump(formatErrorCode(url, STATUS_404));
		
		else if (bodyText.matches(regexAppError)) 
			ErrorManager.throwAndDump(formatErrorCode(url, STATUS_APP_ERROR));
	}

	private static String formatErrorCode(String target, String errorCode) {
		return String.format(
			"\n\nError: Broken url(s) found on page.\nUrl '%s' returned %s\n\n", 
			target, errorCode
		);
	}

	/**
	 * Convenience method for quickly checking all images on the current page. 
	 * 
	 * Automatically parses the Page Source for all <img> tags and verifies that they 
	 * return a status of 200 OK. This is a quick way to check that all images on the 
	 * page are up to date, working, and not stale
	 * 
	 * @param webPage
	 * @throws FrameworkException
	 */
	public static void verifyAllPageImagesNotBroken(WebPage webPage) throws FrameworkException {
		bulkUrlCheck(webPage.getBrowser().getCurrentUrl(), "img", "abs:src");
	}
	
	public static void verifyAllPageImagesNotBroken(String currentUrl) throws FrameworkException {
		bulkUrlCheck(currentUrl, "img", "abs:src");
	}

	/**
	 * Convenience method for quickly checking all links on the current page. 
	 * 
	 * Automatically parses the Page Source for all <a> tags and verifies that they 
	 * return a status of 200 OK. This is a quick way to check that all links on the 
	 * page are up to date, working, and not stale
	 * 
	 * @param webPage
	 * @throws Exception 
	 * @throws FrameworkException
	 */
	public static void verifyAllPageLinksNotBroken(WebPage webPage) throws FrameworkException {
		bulkUrlCheck(webPage.getBrowser().getCurrentUrl(), "a", "abs:href");
	}
	
	public static void verifyAllPageLinksNotBroken(String currentUrl) throws FrameworkException {
		bulkUrlCheck(currentUrl, "a", "abs:href");
	}
	
	private static void bulkUrlCheck(String url, String selector, String targetAttr) {
		SoupPage page = ParallelWebTools.openAndParse(url);
		//System.out.println(page);
		List<String> urls = page.select(selector)
				.filter().attrNotMatching(targetAttr, "") 
				.filter().attrNotContaining("href", "mailto") //mailto links cause it to fail
				.map().toAttr(targetAttr).toList();
		for (String s : urls) System.out.println(s);
		List<Response> responses = ConcurrentWebTools.loadResponses(urls); 
		validateResponses(responses);
	}
	//TODO mw: should throw an exception when list is empty: urls
	//this does not always get all the links. Compare to PageElementVerify.linksReturn200()
	public static void verifyPageLinksInSection(WebPage webPage, PageObject pageObject) throws FrameworkException {
		List<String> urls = removeEmptyItemsFromList(extractUrlsFromSpecificSection(webPage, pageObject));
		List<Response> responses = ConcurrentWebTools.loadResponses(urls);
		validateResponses(responses);
	}
	
	private static void validateResponses(List<Response> responses) {
		String output = "";
		String template = "Status Returned: %s - Url: %s\n";
		for (Response response : responses) {
			
			if (response.statusCode() == STATUS_OK) continue;
			output += String.format(template, response.statusCode(), response.url().toString());
		}
		if (!output.isEmpty()) {
			ErrorManager.throwAndDump("\n\nErrors:\n\n" + output + "\n\n");
		}
	}
	
	private static List<String> extractUrlsFromSpecificSection(WebPage webPage, PageObject pageObject) {
		SoupPage soup = ParallelWebTools.parseString(webPage.getBrowser().getPageSource());
		String anchorSelector = pageObject.toString() + " a";
		return soup.select(anchorSelector).map().toAttr("abs:href").toList();
	}
	
	private static List<String> removeEmptyItemsFromList(List<String> itemList)	{
		Iterator<String> i = itemList.iterator();
		while (i.hasNext()) {
			String item = i.next();

			if (valueIsEmpty(item)) 	{
				i.remove();
			}
		}
		return itemList;
	}
	
	private static Boolean valueIsEmpty(String value) 	{
		if (	(value != null) && !(value.equals(""))	){
			return false;
		}
		return true;
	}
}













