package com.sseltzer.selenium.framework.selenium.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.sseltzer.selenium.framework.selenium.wrappers.WebDriverWrapper;

class ChromeBrowser extends Browser {
	private static final String CHROMEDRIVER_PATH = "src/main/resources/chromedriver";

	/**
	 * Create and set the WebDriver for the Browser object. This is unique to each WebDriver instance being
	 * requested for each Browser.
	 * <br><br>
	 * Chrome requires that a third party executable chromedriver.exe be used. This is created and maintained
	 * by Google itself. Communication with Chrome would not be possible without using this extra executable.
	 * Within Selenium, not only does the WebDriver have to be instantiated, but it must be provided a service
	 * by creating a ChromeDriverService using the path to the chromedriver.exe.
	 */
	@Override
	public void setWebDriver() {
		System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--disable-extensions");
		WebDriver webDriver = new ChromeDriver(chromeOptions);
		super.webDriverWrapper = WebDriverWrapper.convert(webDriver);

	}

	/**
	 * The quit function will ensure that the connection to the WebDriver is closed.
	 * <br><br>
	 * Since Chrome requires the extra service, it must also be destroyed along with the WebDriver instance.
	 */
	@Override
	public void quit() {
		if (getWebDriver() != null) {
			getWebDriver().quit();
		}
	}
}
