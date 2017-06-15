
package com.sseltzer.selenium.framework.verification.url;

import org.junit.Test;

import com.sseltzer.selenium.framework.jsoup.urls.ValpakUrl;

/**
 *
 *
 * ManualUrlEnvTest.java
 *
 * @author ckiehl Sep 5, 2014
 */
public class ManualUrlEnvTest {
	
	/**
	 * For manually verifying that it picks up and
	 * responds to env variables. 
	 */
	@Test 
	public void printValpakUrlOutput() {
		System.out.println(ValpakUrl.from("/coupons/home"));
	}
}
