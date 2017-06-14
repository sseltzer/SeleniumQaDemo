package com.sseltzer.selenium.internal.test.website;

import com.sseltzer.selenium.framework.selenium.browsers.Browser;
import com.sseltzer.selenium.framework.selenium.website.Website;

public class GoogleWebsite extends Website {

	public GoogleWebsite(Browser browser) {
		super(browser);
	}

	@Override
	public void setBaseURL() {
		super.setBaseURL("http://www.google.com");
	}
}
