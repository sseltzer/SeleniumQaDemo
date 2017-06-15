package com.sseltzer.selenium.framework.selenium.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.sseltzer.selenium.framework.selenium.wrappers.WebDriverWrapper;

/**
 * 
 * IExplorerBrowser.java
 * 
 * @author ckiehl Jun 11, 2014
 */
public class IExplorerBrowser extends Browser {
	private static final String DRIVER_PATH = "/c:/selenium_drivers/IEDriverServer.exe";

	@Override
	public void setWebDriver() {
		System.setProperty("webdriver.ie.driver", DRIVER_PATH);
		WebDriver webDriver = new InternetExplorerDriver();
		super.webDriverWrapper = WebDriverWrapper.convert(webDriver);
	}

	@Override
	public void quit() {
		if (getWebDriver() != null) getWebDriver().quit();
	}

}
