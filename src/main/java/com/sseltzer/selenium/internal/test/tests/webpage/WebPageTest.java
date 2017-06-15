package com.sseltzer.selenium.internal.test.tests.webpage;

import org.junit.Test;

import com.sseltzer.selenium.framework.selenium.wrappers.TimingManager;
import com.sseltzer.selenium.framework.verification.junit.TestScript;
import com.sseltzer.selenium.internal.test.website.GoogleWebsite;

public class WebPageTest extends TestScript {

	@Test
	public void testOpenGoogle() {
		new GoogleWebsite(getBrowser()).open();
		TimingManager.waitForSpecifiedDuration(3000);
	}
}
