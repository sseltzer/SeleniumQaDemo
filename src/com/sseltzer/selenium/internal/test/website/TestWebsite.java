package com.sseltzer.selenium.internal.test.website;

import com.sseltzer.selenium.framework.selenium.browsers.Browser;
import com.sseltzer.selenium.framework.selenium.website.Website;

/**
 *
 *
 * TestWebsite.java
 *
 * @author ckiehl Jun 16, 2014
 */
public class TestWebsite extends Website {

	public TestWebsite(Browser browser) {
		super(browser);
	}

	@Override
	public void setBaseURL() {
		super.setBaseURL("http://vpdev.valpak.com/redesign/");
	}
}
