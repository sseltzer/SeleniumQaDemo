package com.sseltzer.selenium.framework.verification.junit;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.sseltzer.selenium.framework.selenium.browsers.BrowserFactory;
import com.sseltzer.selenium.framework.selenium.browsers.BrowserFactory.AccessPoint;

/**
 * TestSuites will execute a set number of TestScript classes to perform a given
 * set of tests. This class is extended in each project one or more times to list
 * each of the classes to be executed as a batch of tests. The primary purpose of which
 * is to provide a mechanism for Browser reuse over a given execution cycle instead of
 * initializing a connection to a web browser, creating the process, creating the connecting
 * process and destroying them after each test. Instead, this is done once at the first
 * TestScript execution and maintained through until all TestScript classes have been executed
 * before being destroyed at the end. During normal operation, for example through Eclipse, when
 * one or more TestScript are executed directly, the Browser is created and destroyed for each
 * test that is run. This is intentionally limited for the server execution environment where
 * one browser is desired, thus when a TestSuite is executed, the browser reuse mechanism is
 * invoked.
 *  
 * @author Sean Seltzer
 *
 */
public class TestSuite {

	/**
	 * Sets the BrowserFactory AccessPoint to TEST_SUITE to ensure that the Browser object will
	 * be recycled through the life of the suite in the BrowserFactory multiton factory. Since the
	 * access point is TEST_SUITE, it will not close the browser and reopen it between tests, instead
	 * it will wait for the clear with the TEST_SUITE AccessPoint instead of closing at each individual
	 * TEST_SCRIPT access points. This ensures that the processes generated on the server are limited
	 * and as much is recycled as possible. 
	 */
	@BeforeClass
	public static void setupTestSuite() {
		BrowserFactory.setAccessPoint(AccessPoint.TEST_SUITE);
	}

	/**
	 * Clears the BrowserFactory from the AccessPoint of TEST_SUITE. Since the TEST_SUITE AccessPoint is
	 * set in the {@literal @BeforeClass} the BrowserFactory multiton factory will not clear and close the
	 * browsers between TestScripts. This ensures that the processes generated on the server are limited
	 * and as much is recycled as possible.
	 */
	@AfterClass
	public static void wrapupTestSuite() {
		BrowserFactory.clear(AccessPoint.TEST_SUITE);
	}
}
