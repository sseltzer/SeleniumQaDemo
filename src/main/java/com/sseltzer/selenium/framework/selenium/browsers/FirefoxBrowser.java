package com.sseltzer.selenium.framework.selenium.browsers;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.sseltzer.selenium.framework.selenium.wrappers.WebDriverWrapper;

class FirefoxBrowser extends Browser {
	
	private static final ClassLoader classLoader = Browser.class.getClassLoader();
	
	private static final String DRIVER_PATH_OSX = "drivers/osx/geckodriver";
	private static final String DRIVER_PATH_WIN = "drivers/windows/geckodriver.exe";

	private String getDriverPath() {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("windows")) return DRIVER_PATH_WIN;
		else if (osName.contains("mac")) return DRIVER_PATH_OSX;
		return DRIVER_PATH_OSX;
	}

	/**
	 * Create and set the WebDriver for the Browser object. This is unique to each WebDriver instance being
	 * requested for each Browser.
	 * <br><br>
	 * Firefox requires no extra effort since it is supported natively within Selenium. Simply create an
	 * instance of FirefoxDriver and the rest is taken care of.
	 */
	@Override
	public void setWebDriver() {
		File file = new File(classLoader.getResource(getDriverPath()).getFile());	
		System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
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
