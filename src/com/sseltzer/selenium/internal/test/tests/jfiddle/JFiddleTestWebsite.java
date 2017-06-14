package com.sseltzer.selenium.internal.test.tests.jfiddle;

import com.sseltzer.selenium.framework.selenium.browsers.Browser;
import com.sseltzer.selenium.framework.selenium.website.Website;

/**
 * To unit test the selenium functions, a stable website with guaranteed 
 * invariants is required. Do to the Dynamic nature of valpak.com, it doens't 
 * make for an ideal candidate when it comes to test control. To solve this, I've 
 * opted to make a series of JFiddle (jfiddle.com) entries which can be used to 
 * test Just One Thing. This allows all the parameters to be controlled, which gives 
 * the tests some more weight in terms of proving "correctness." 
 * 
 * Credentials to the JFiddle: 
 * 		username: ckiehlunittests
 *		password: valpak
 * 
 * @author ckiehl
 *
 */
public class JFiddleTestWebsite extends Website {

	private static String targetUrl = null;

	public JFiddleTestWebsite(Browser browser) {
		super(browser);
	}

	@Override
	public void setBaseURL() {
		super.setBaseURL(targetUrl);
	}

	public static void setTestUrl(String url) {
		targetUrl = url;
	}
}
