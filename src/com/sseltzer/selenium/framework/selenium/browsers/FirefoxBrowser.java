package com.sseltzer.selenium.framework.selenium.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.sseltzer.selenium.framework.selenium.wrappers.WebDriverWrapper;

class FirefoxBrowser extends Browser {
	
	private static final String DRIVER_PATH = "/usr/local/bin/geckodriver";


	/**
	 * Create and set the WebDriver for the Browser object. This is unique to each WebDriver instance being
	 * requested for each Browser.
	 * <br><br>
	 * Firefox requires no extra effort since it is supported natively within Selenium. Simply create an
	 * instance of FirefoxDriver and the rest is taken care of.
	 */
	@Override
	public void setWebDriver() {
		System.setProperty("webdriver.gecko.driver", DRIVER_PATH);
		WebDriver webDriver = new FirefoxDriver();
		super.webDriverWrapper = WebDriverWrapper.convert(webDriver);
	}

	/**
	 * The quit function will ensure that the connection to the WebDriver is closed.
	 * <br><br>
	 * Firefox requires no extra functions, so simply call quit on the WebDriver.
	 */
	@Override
	public void quit() {
		if (getWebDriver() != null) getWebDriver().quit();
	}
}
