package com.sseltzer.selenium.internal.test.tests.verification;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;
import com.sseltzer.selenium.framework.selenium.mapping.PageObject;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPage;
import com.sseltzer.selenium.framework.verification.junit.TestScript;
import com.sseltzer.selenium.framework.verification.url.UrlTester;
import com.sseltzer.selenium.internal.test.website.TestWebsite;

/**
 *
 *
 * TestUrlTester.java
 *
 * @author ckiehl Jun 13, 2014
 */
public class TestUrlTester extends TestScript {

	private static PageObject BANNER = new PageObject(".billboard img");
	private static final PageObject SEO_CATEGORY_LINK = new PageObject(".go-text-other p a[data-dtm*=browse]");
	private static final PageObject SEO_CATEGORIES = new PageObject(".cell");

	@Test(expected = FrameworkException.class)
	public void testVerifyUrlThrowsExceptionOnBadResponse() throws FrameworkException {
		List<String> testUrls = Arrays.asList(
			"http://httpstat.us/300",
			"http://httpstat.us/400",
			"http://httpstat.us/401",
			"http://httpstat.us/402",
			"http://httpstat.us/403",
			"http://httpstat.us/404");
		UrlTester.verifyUrls(testUrls);
	}

	@Test
	public void testVerifyUrlPassesOnValidUrl() throws Exception {
		List<String> testUrls = Arrays.asList(
			"http://httpstat.us/200",
			"http://httpstat.us/302"
		);
		UrlTester.verifyUrls(testUrls);
		UrlTester.verifyUrl(testUrls.get(0));
	}

	@Test(expected = FrameworkException.class)
	public void testVerifyPageLinkThrowsExceptionForInvalidPageElement() {
		WebPage webPage = new TestWebsite(getBrowser()).open("home");
		PageObject objectWithoutAnHref = BANNER;
		UrlTester.verifyPageLink(webPage, objectWithoutAnHref);
	}

	@Test
	public void testVerifyPageLinkPassesOnValidPageElement() {
		WebPage webPage = new TestWebsite(getBrowser()).open("home");
		PageObject objectWithHref = SEO_CATEGORY_LINK;
		UrlTester.verifyPageLink(webPage, objectWithHref);
	}

	@Test(expected = FrameworkException.class)
	public void testVerifyImageThrowsExceptionForInvalidPageElement() {
		WebPage webPage = new TestWebsite(getBrowser()).open("home");
		PageObject objectWithInvalidCssSelector = SEO_CATEGORIES;
		UrlTester.verifyImage(webPage, objectWithInvalidCssSelector);
	}

	@Test
	public void testVerifyImagePassesOnValidPageElementAndGoodURL() throws IOException {
		WebPage webPage = new TestWebsite(getBrowser()).open("home");
		PageObject objectWithHref = BANNER;
		UrlTester.verifyImage(webPage, objectWithHref);
	}
	
}
