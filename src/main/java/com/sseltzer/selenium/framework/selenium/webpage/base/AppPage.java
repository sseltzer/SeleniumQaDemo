package com.sseltzer.selenium.framework.selenium.webpage.base;

import com.sseltzer.selenium.framework.selenium.browsers.Browser;
import com.sseltzer.selenium.framework.selenium.website.AppWebsite;

public class AppPage {

	public static WebPage getInstance(Browser browser) {
		return WebPage.getInstance(browser, new AppWebsite(browser));
	}
}
