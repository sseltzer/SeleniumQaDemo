
package com.sseltzer.selenium.internal.test.tests.jsoup.urls;

import org.junit.Test;

import com.sseltzer.selenium.framework.error.ErrorManager;
import com.sseltzer.selenium.framework.jsoup.urls.ValpakUrl;

/**
 *
 *
 * ValpakUrlTst.java
 *
 * @author ckiehl Sep 22, 2014
 */
public class ValpakUrlTst {

	
	@Test
	public void testValpakUrl() {
		ErrorManager.throwAndDump("\n\nValpak Url: %s\n\n", null,
				ValpakUrl.from("coupons/home"));
		
	}
}
