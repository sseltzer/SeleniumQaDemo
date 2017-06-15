package com.sseltzer.selenium.framework.selenium.browsers;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.sseltzer.selenium.framework.environment.EnvironmentHandler;
import com.sseltzer.selenium.framework.selenium.wrappers.WebDriverWrapper;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class IOSBrowser extends Browser {

	@Override
	public void setWebDriver() {
		String appPath = EnvironmentHandler.getMobileAppPath();
		if (appPath.endsWith(".apk")) throw new RuntimeException("android mobileapppath given, but iOS mobiledevice specified.");
		if (!appPath.endsWith(".app")) appPath += ".app";
		//File app = new File(appPath);
		//if (!app.exists()) throw new RuntimeException("mobileapp specified does not exist: " + appPath);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.2");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, EnvironmentHandler.getMobileDevice());
		capabilities.setCapability(MobileCapabilityType.APP, appPath);
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
		capabilities.setCapability("screenshotWaitTimeout", 100000);

		try {
			WebDriver webDriver = new IOSDriver(new URL("http://LA008311:4723/wd/hub"), capabilities);
			super.webDriverWrapper = WebDriverWrapper.convert(webDriver);
		} catch (MalformedURLException e) {
			e.printStackTrace();   //LA008311   LA008311
		}
	}

	@Override
	public void quit() {
		if (getWebDriver() != null) getWebDriver().quit();
	}
}

