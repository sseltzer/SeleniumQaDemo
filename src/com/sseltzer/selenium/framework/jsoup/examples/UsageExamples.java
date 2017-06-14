
package com.sseltzer.selenium.framework.jsoup.examples;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.sseltzer.selenium.framework.jsoup.elements.WebElements;
import com.sseltzer.selenium.framework.jsoup.elements.WebElementsGroup;
import com.sseltzer.selenium.framework.jsoup.toolkit.ParallelWebTools;
import com.sseltzer.selenium.framework.jsoup.urls.ValpakUrl;
import com.sseltzer.selenium.framework.jsoup.webpages.SoupPage;
import com.sseltzer.selenium.framework.jsoup.webpages.SoupPageGroup;

/**
 *
 *
 * UsageExamples.java
 *
 * @author ckiehl Aug 25, 2014
 */
public class UsageExamples {
	
	@Test 
	public void makeValpakUrl() {
		String homeUrl = ValpakUrl.from("coupons/home");
		String featured = ValpakUrl.from("coupons/coupon-codes/featured");
	}
	
	@Test 
	public void loadPage() {
		String url = "http://www.google.com"; 
		SoupPage page = ParallelWebTools.openAndParse(url);
	}
	
	@Test 
	public void loadPagesConcurrently() {
		List<String> urls = Arrays.asList(
			"http://www.google.com", 
			"http://www.yahoo.com", 
			"http://www.shrimp.com", 
			"http://www.potatoes.com" 
		); 
		SoupPageGroup pages = ParallelWebTools.openAndParse(urls);
	}
	
	@Test 
	public void getBillboardSlides() {
		String url = "http://www.valpak.com"; 
		SoupPage page = ParallelWebTools.openAndParse(url);
		
		WebElements billboards = page.select(".billboards");
	}
	
	@Test 
	public void getBillboardSlideImageSrcAttribute() {
		String url = "http://www.valpak.com"; 
		SoupPage page = ParallelWebTools.openAndParse(url);
		List<String> billboardUrls = page
				.select(".billboards img")
				.map().toAttr("src").toList();
	}
	
	@Test 
	public void getAllPageLinkText() {
		String url = "http://www.valpak.com"; 
		SoupPage page = ParallelWebTools.openAndParse(url);
		List<String> linkText = page.select("a").map().toText().toList();
	}
	
	@Test 
	public void getWebElementsExcludingEmpty() {
		String url = "http://www.valpak.com"; 
		SoupPage page = ParallelWebTools.openAndParse(url);
		WebElements valpakLinks = 
			page.select("a").filter().textContaining("valpak");
	}
	
	@Test 
	public void getPageLinksExcludingJSTriggers() {
		String url = "http://www.valpak.com"; 
		SoupPage page = ParallelWebTools.openAndParse(url);
		WebElements valpakLinks = 
			page.select("a").filter().attrContaining("href","javascript");
	}

	@Test 
	public void selectH1TextFromAllPages() {
		List<String> urls = Arrays.asList(
			"http://www.valpak.com/coupons/savings/Restaurants", 
			"http://www.valpak.com/coupons/savings/Automotive"
		);
		SoupPageGroup pages = ParallelWebTools.openAndParse(urls);
		WebElementsGroup headerGroup = pages.select("h1"); 
		WebElements allElements = pages.select("h1").flatten();
		
	}
	
	@Test 
	public void getAllH1TagsAsSingleList() {
		List<String> urls = Arrays.asList(
			"http://www.valpak.com/coupons/savings/Restaurants", 
			"http://www.valpak.com/coupons/savings/Automotive"
		);
		SoupPageGroup pages = ParallelWebTools.openAndParse(urls);
		WebElements headerGroup = pages.select("h1").flatten(); 
	}
	
	
}
