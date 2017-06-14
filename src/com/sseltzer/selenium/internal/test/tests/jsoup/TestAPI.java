
package com.sseltzer.selenium.internal.test.tests.jsoup;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sseltzer.selenium.framework.error.ErrorManager;
import com.sseltzer.selenium.framework.jsoup.elements.WebElements;
import com.sseltzer.selenium.framework.jsoup.toolkit.ParallelWebTools;
import com.sseltzer.selenium.framework.jsoup.webpages.SoupPage;
import com.sseltzer.selenium.framework.jsoup.webpages.SoupPageGroup;

/**
 *
 *
 * TestAPI.java
 *
 * @author ckiehl Aug 20, 2014
 */
public class TestAPI {
	private static String testHtml = ""
		+"<!DOCTYPE html>"
		+"<html>"
		+ "<head>"
		+  "<meta charset=\"UTF-8\">"
		+  "<title>Test Data</title>"
		+ "</head>"
		+ "<body>"
		+  "<h1>This is a Page Title</h1>"
		+  "<p>"
		+  	"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod "
		+  	"tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, "
		+  	"<a href=\"www.google.com\">quis </a>nostrud exercitation ullamco laboris nisi "
		+  	"ut aliquip ex ea commodo consequat."
		+ 	"</p>"
		+	"<ul>"
		+		"<li><a href=\"www.yahoo.com\">yahoo</a></li>"
		+		"<li><a href=\"www.gmail.com\">gmail</a></li>"
		+		"<li><a href=\"www.hotmail.com\">hotmail</a></li>"
		+		"<li><a href=\"www.somemail.com\">somemail</a></li>"
		+		"<li><a href=\"www.aol.what\">aol</a></li>"
		+	"</ul>   "
		+ "</body>"
		+ "</html>";
	
	private static Elements data; 
	
	@BeforeClass
	public static void setUp() throws IOException {
		Document d = Jsoup.parse(testHtml);
		data = d.select("li a");
	}
	
	@Test
	public void adsf() {
		SoupPage page = ParallelWebTools.openAndParse("http://vpdev.valpak.com/coupons/coupon-codes/featured"); 
		WebElements activeUrls = page.select(".results-toolbar a").filter().attrMatching("abs:href", "");
		
		List<String> dropdownUrls = activeUrls.map().toAttr("abs:href").toList(); 
		List<String> expectedText = activeUrls.map().toText().toList();
		
		SoupPageGroup pages = ParallelWebTools.openAndParse(dropdownUrls);
		List<String> pageTitlesFound = pages.select("h1").flatten().map().toText().toList(); 
		
		for (int i = 0; i < pageTitlesFound.size(); i++) {
			String expected = expectedText.get(i);
			String found = pageTitlesFound.get(i);
			if (!found.contains(expected)) {
				ErrorManager.throwAndDump("Oh no!");
			}
		}
	}
}









