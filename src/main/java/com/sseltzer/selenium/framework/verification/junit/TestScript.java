package com.sseltzer.selenium.framework.verification.junit;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.Timeout;

import com.sseltzer.selenium.framework.data.DataAggregator;
import com.sseltzer.selenium.framework.environment.EnvironmentHandler;
import com.sseltzer.selenium.framework.environment.enums.BrowserEnum;
import com.sseltzer.selenium.framework.error.ErrorManager;
import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;
import com.sseltzer.selenium.framework.selenium.browsers.Browser;
import com.sseltzer.selenium.framework.selenium.browsers.BrowserFactory;
import com.sseltzer.selenium.framework.selenium.browsers.BrowserFactory.AccessPoint;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPage;
import com.sseltzer.selenium.framework.utility.plugin_container.PluginContainer;
import com.sseltzer.selenium.framework.verification.junit.TestScriptRunManager.FailMode;

/**
 * The TestScript object is provided as a universal and uniform method in which
 * to create TestScripts. Contained within this object are functions to allow
 * for test scripts to be created quicker and provides common functionality to
 * all test scripts. To use this class, simply extend it in a desired class and
 * begin writing {@literal @Test} functions with no required setup necessary.
 * This class also provides a dual purpose as it provides a method in which to
 * prepopulate the DataAggregator at the execution of the first test case to
 * provide data statically which may be reused throughout the entire test
 * script. In the JUnit Before class, it is determined if the test script being
 * run is the first one for the class, and if so, will call the populateData
 * function which should be overridden by any child classes. After this test
 * script is executed, the AfterClass function will then clean up any data that
 * exists and prepare for the next test script to be run thus preventing any
 * data collision. Unless absolutely required, all test scripts should
 * forseeably extend this class.
 * 
 * @author Carl Paret and Sean Seltzer
 * 
 */
public class TestScript {

	@Rule
	public Timeout globalTimeout = new Timeout(500000);
	@Rule
	public ScreenshotWatcher watcher = new ScreenshotWatcher();

	private static boolean initPop = true;
	private BrowserEnum browserEnum = BrowserEnum.CHROME;
	private static Browser browser = null;
	
	public static String methodName = "";

	/**
	 * Returns the stored browser object initialized in the BeforeClass function.
	 * @return Returns the stored browser object initialized in the BeforeClass function.
	 */
	public static Browser getBrowser() {
		return browser;
	}

	/**
	 * This function will initialize the entire test script. The primary purpose
	 * currently is to determine which browser and environment should be used
	 * based on two system variables: BROWSER and ENVIRONMENT. If the
	 * environmental variables are not found (such as on a local developer's
	 * machine), it will default to using Chrome and the TST environment.
	 */
	@BeforeClass
	public static void setupTestScript() {
		TestScriptRunManager.setFailMode(FailMode.SCRIPT);
	}

	/**
	 * This function will perform actions before each {@literal @Test} case. The
	 * primary purpose of this function is to clear the browser cookies and pre
	 * populate any desired cookies. This function will also determine if the
	 * test script is the first executed test case, and if so, will call the
	 * populateData function to populate the DataAggregator with global data for
	 * the life of the entire script.
	 */
	@Before
	public void setupTest() {
		if (TestScriptRunManager.isAborted()) fail("Test Aborted");
		browserEnum = getDesiredBrowser();
		browser = BrowserFactory.getBrowser(AccessPoint.TEST_CLASS, browserEnum);
		getBrowser().clearCookies();
		// BeforeClass must be static. So we cannot call populate and use inheritance to
		// automatically call the populate function. I'd like the front facing function to
		// be as easy as possible to use, so to automatically call it would be advantageous.
		// Since we can't do this from a static function with an override, we use a static
		// flag, and just use that to determine at the beginning of the first test to populate,
		// then reset it in the AfterClass to make sure it gets cleared for the next test script.
		// Either way, this works, at the beginning of the first call, populate is called which
		// may or may not be used by a TestScript object. If it is used, we have one function that
		// we can use to populate all of the test data for the whole script.
		if (initPop) {
			populateData(DataAggregator.getInstance());
			try {
				if (!preScriptCheck()) {
					TestScriptRunManager.abort();
					fail("Test precheck failed.");
				}
			} catch (FrameworkException e) {
				TestScriptRunManager.abort();
				fail("Test precheck failed: " + e.getMessage());
			}
			initPop = false;
		}
		methodName = getMethodFileName();
	}

	/**
	 * This function will perform actions after the entire test script is run.
	 * This is used for cleanup and will close the browser, reset the
	 * populateData flag, and clear the DataAggregator for the next test script
	 * preventing data collision.
	 */
	@AfterClass
	public static void wrapupTestScript() {
		// Reset to populate the next set of data for the next script.
		initPop = true;
		// For now, reset data between scripts. So that there is no data collision that will
		// interfere with another script down the line.
		DataAggregator.getInstance().clear();
		BrowserFactory.clear(AccessPoint.TEST_CLASS);
		TestScriptRunManager.reset(FailMode.SCRIPT);
	}

	/**
	 * The wrapupTest {@literal @After} method provides the ability to read the
	 * list of stored exceptions and display them after the test is performed if
	 * the ErrorManager is using error collection mode as opposed to failing on
	 * the first exception. The TestScript will then switch the ErrorManager
	 * back to the default mode (immediate) after the test is performed to
	 * eliminate collision between tests as order is not guaranteed in JUnit.
	 * 
	 * @throws FrameworkException
	 */
	@After
	public void wrapupTest() {
		if (WebPage.getCurrentPage() != null) getBrowser().closeAllWindowsNotMatchingExactTitle(WebPage.getCurrentPage().interactWithFrame().getPageTitle());
		ArrayList<FrameworkException> exceptions = ErrorManager.getExceptions();
		if (exceptions.size() > 0) {
			StringBuffer exceptionStr = new StringBuffer();
			for (FrameworkException e : exceptions) exceptionStr.append(e.getMessage());
			exceptions.clear();
			throw new FrameworkException(exceptionStr.toString());
		}
		ErrorManager.setErrorModeDefault();
		PluginContainer.closeIfOnWindowsPlatform(getBrowser());
	}

	/**
	 * This function will be called before the first {@literal @Test} case
	 * function is called. The primary purpose of this function is to be
	 * overridden by test scripts to allow for a global method of populating
	 * data before any test scripts are run. Since the DataAggregator is
	 * implemented statically as a singleton object, the data is retained
	 * throughout the life of all of the JUnit tests and is NOT disposed of
	 * after each test case is executed. This allows data to be populated at the
	 * beginning of a test script and used throughout multiple test cases.
	 * 
	 * @param dataAggregator is a convenience parameter to populate data. This is the same 
	 * as calling DataAggregator.getInstance and is provided here only for clarity to the 
	 * developer in how this function should be used.
	 */
	public void populateData(DataAggregator dataAggregator) {
		// Override this and populate data to be used by the whole script.
		// This will be called before any test executes and will allow the data set in here
		// to be accessible by all tests within the script.
	}

	public boolean preScriptCheck() {
		// Override this if you would like to perform some test to check if the test can run.
		// Returning true will execute the script.
		// Returning false will abort all scripts in the given execution.
		return true;
	}

	public BrowserEnum getDesiredBrowser() {
		return EnvironmentHandler.getBrowser();
	}
	
	@Rule
	public TestName name = new TestName();
	public String getMethodFileName() {
		return this.getClass().getName() + "." + name.getMethodName();
	}
}
