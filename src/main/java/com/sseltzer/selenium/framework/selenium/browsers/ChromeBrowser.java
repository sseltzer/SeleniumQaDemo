package com.sseltzer.selenium.framework.selenium.browsers;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.sseltzer.selenium.framework.selenium.wrappers.WebDriverWrapper;

class ChromeBrowser extends Browser {
	private static final ClassLoader classLoader = Browser.class.getClassLoader();
	
	private static final String DRIVER_PATH_OSX = "drivers/osx/chromedriver";
	private static final String DRIVER_PATH_WIN = "drivers/windows/chromedriver.exe";

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
	 * Chrome requires that a third party executable chromedriver.exe be used. This is created and maintained
	 * by Google itself. Communication with Chrome would not be possible without using this extra executable.
	 * Within Selenium, not only does the WebDriver have to be instantiated, but it must be provided a service
	 * by creating a ChromeDriverService using the path to the chromedriver.exe.
	 */
	@Override
	public void setWebDriver() {
		File file = new File(classLoader.getResource(getDriverPath()).getFile());	
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
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
