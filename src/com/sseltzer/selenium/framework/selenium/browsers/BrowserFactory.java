package com.sseltzer.selenium.framework.selenium.browsers;

import java.util.HashMap;

import com.sseltzer.selenium.framework.environment.EnvironmentHandler;
import com.sseltzer.selenium.framework.environment.enums.BrowserEnum;

/**
 * The BrowserFactory class follows a Multiton Factory pattern. The Browser class represents a single connection to a
 * browser. For some browsers a second service executable must be invoked to facilitate that connection. The
 * cost of constantly creating and destroying processes can be too much when many tests are run especially when
 * the tests do not follow normal behavior and Selenium closes before the external processes are shut down. This
 * can leave dangling processes. In an attempt to limit the number of processes, and in general just be efficient,
 * the Browser object is reused as much as possible. The BrowserFactory follows the Factory pattern to make Browser
 * object creation easy, and maps to an enum for cleaner code. The Multiton pattern is invoked for two main purposes.
 * One, to persist anything between tests in JUnit, it must be static. Two, it would be unwise to assume that parallel
 * browsers will never be used for testing at some point. There is no reason why a single browser must be used, so as
 * to not create a Singleton which limits to one single Browser object, a Multiton is used instead. Thus, we maintain
 * a single instance of each browser at any given time. Upon request (between tests) this instance may be requested
 * and the browser and supporting services are reused. In addition, TestSuites were added to further streamline this.
 * Since Browser is created for each instance of TestScript, when running multiple TestScript classes, a Browser would
 * be created and destroyed for each one. To eliminate this, and reuse a single browser for the entire execution 
 * session, TestSuites are created using the concept of an AccessPoint. An AccessPoint gives a reference to what
 * context the Browser is being requested for. This is especially important when closing Browsers. Since TestScript
 * will create and destroy the Browser instance at each execution, we must have a way to flag the BrowserFactory to
 * not actually destroy the Browser if we want to reuse it. The AccessPoint states the context of the caller. Either
 * TEST_SUITE or TEST_CLASS. If Browser is first requested from a TestScript, it may be destroyed by that TestScript
 * when it is finished. This is the situation if you run a single method, or single test class, specifically in Eclipse.
 * On the TeamCity servers, however, TestSuites are run. This tells the BrowserFactory that the context is within a suite
 * and that it should only ever allow the Browser instance to be destroyed when the suite is done. So each TestScript which
 * requests the Browser cannot close it prematurely. No changes are required to allow for TestScripts and TestSuites to
 * be run to expect different behavior; the BrowserFactory needs only to know the context of execution and will make
 * the determination of whether or not it is appropriate to allow Browser reuse or not.   
 * @author Sean Seltzer
 *
 */
public final class BrowserFactory {
	private BrowserFactory() {
	}

	private static final HashMap<BrowserEnum, Browser> instances = new HashMap<BrowserEnum, Browser>();

	private static AccessPoint accessPoint = AccessPoint.NOT_INIT;

	/**
	 * Sets the current AccessPoint for the BrowserFactory. If a TestScript object requests a Browser it will
	 * send TEST_SCRIPT. If a suite requests one it will send TEST_SUITE. A TEST_SUITE may overwrite a TEST_SCRIPT
	 * but a TEST_SCRIPT cannot overwrite a TEST_SUITE. Since the Suite will be responsible for causing the
	 * BrowserFactory to reuse the Browser, we do not want a TEST_SCRIPT to close it prematurely. There should never
	 * be a situation where a TEST_SUITE can overwrite a TEST_SCRIPT since the TestScript object should clear the
	 * browser when it is finished. However, when a TEST_SUITE is run, for each TestScript object that requests a
	 * Browser it will send a TEST_SCRIPT AccessPoint which will be disregarded.
	 * @param accessPoint current access point in the framework for the requested browser. Used for Browser reuse.  
	 */
	public static void setAccessPoint(AccessPoint accessPoint) {
		if (BrowserFactory.accessPoint == AccessPoint.TEST_SUITE) return;
		BrowserFactory.accessPoint = accessPoint;
	}

	/**
	 * Returns the default browser based on environmental variables.
	 * @param accessPoint current access point in the framework for the requested browser. Used for Browser reuse.
	 * @return a Browser instance containing a WebDriver for Selenium interaction. 
	 */
	public static Browser getBrowser(AccessPoint accessPoint) {
		return getBrowser(accessPoint, EnvironmentHandler.getBrowser());
	}

	/**
	 * Returns a specified browser based on the given BrowserEnum.
	 * @param accessPoint current access point in the framework for the requested browser. Used for Browser reuse.
	 * @param browserEnum which Browser object is being requested.
	 * @return a Browser instance containing a WebDriver for Selenium interaction.
	 */
	public static Browser getBrowser(AccessPoint accessPoint, BrowserEnum browserEnum) {
		setAccessPoint(accessPoint);
		switch (browserEnum) {
			default:
			case NONE:
			case CHROME:
				return getChromeBrowser();
			case IE:
				return getIExplorerBrowser();
			case ANDROID:
				return getAndroidBrowser();
			case IOS:
				return getIOSBrowser();
			case FIREFOX:
				return getFirefoxBrowser();
		}
	}

	/**
	 * Specifically return a InternetExplorerBrowser instance. This uses Lazy instantiation for the Multiton.
	 * If it is null, create a new Browser instance. Return the browser instance.
	 * @return a SeleniumBrowser instance containing a WebDriver for Selenium interaction.
	 */
	private static Browser getIExplorerBrowser() {
		if (!instances.containsKey(BrowserEnum.IE)) instances.put(BrowserEnum.IE, new IExplorerBrowser());
		return instances.get(BrowserEnum.IE);
	}

	/**
	 * Specifically return a FirefoxBrowser instance. This uses Lazy instantiation for the Multiton.
	 * If it is null, create a new Browser instance. Return the browser instance.
	 * @return a FirefoxBrowser instance containing a WebDriver for Selenium interaction.
	 */
	public static Browser getFirefoxBrowser() {
		if (!instances.containsKey(BrowserEnum.FIREFOX)) instances.put(BrowserEnum.FIREFOX, new FirefoxBrowser());
		return instances.get(BrowserEnum.FIREFOX);
	}

	/**
	 * Specifically return a ChromeBrowser instance. This uses Lazy instantiation for the Multiton.
	 * If it is null, create a new Browser instance. Return the browser instance.
	 * @return a ChromeBrowser instance containing a WebDriver for Selenium interaction.
	 */
	public static Browser getChromeBrowser() {
		if (!instances.containsKey(BrowserEnum.CHROME)) instances.put(BrowserEnum.CHROME, new ChromeBrowser());
		return instances.get(BrowserEnum.CHROME);
	}
	
	public static Browser getAndroidBrowser() {
		if (!instances.containsKey(BrowserEnum.ANDROID)) instances.put(BrowserEnum.ANDROID, new AndroidBrowser());
		return instances.get(BrowserEnum.ANDROID);
	}
	
	public static Browser getIOSBrowser() {
		if (!instances.containsKey(BrowserEnum.IOS)) instances.put(BrowserEnum.IOS, new IOSBrowser());
		return instances.get(BrowserEnum.IOS);
	}

	/**
	 * Request that the Multiton Factory clear the Browser being requested. This is context sensitive and the
	 * request may be ignored. If the AccessPoint used to retrieve the browser initially agrees with the context
	 * of the clear call, the BrowserFactory will clear its instances. If the initial AccessPoint is a suite and
	 * the clear context is a class, it will be ignored. Suite clear calls are always performed.
	 * @param context the context in which the clear is being requested.
	 */
	public static void clear(AccessPoint context) {
		if (context == AccessPoint.TEST_SUITE) clear();
		if (BrowserFactory.accessPoint == AccessPoint.TEST_CLASS && context == AccessPoint.TEST_CLASS) clear();
	}

	/**
	 * Private function to call quit on all Browser instances contained in this Multiton, before clearing the
	 * actual collection of Browser instances.
	 */
	private static void clear() {
		for (Browser b : instances.values()) b.quit();
		instances.clear();
	}

	/**
	 * Enumeration to represent from where the Browser instance is being requested from. TEST_SUITES and TEST_CLASS
	 * may be used to control Browser reuse. The reuse is entirely controlled by which context was used when requesting
	 * the Browser object initially and from where the clear calls are being made from.
	 * @author Sean Seltzer
	 *
	 */
	public enum AccessPoint {
		NOT_INIT, TEST_SUITE, TEST_CLASS,
	}
}
