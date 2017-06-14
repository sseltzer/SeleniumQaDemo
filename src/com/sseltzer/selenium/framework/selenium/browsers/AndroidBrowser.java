package com.sseltzer.selenium.framework.selenium.browsers;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.sseltzer.selenium.framework.environment.EnvironmentHandler;
import com.sseltzer.selenium.framework.selenium.wrappers.WebDriverWrapper;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class AndroidBrowser extends Browser {

	@Override
	public void setWebDriver() {
		String appPath = EnvironmentHandler.getMobileAppPath();
		if (appPath.endsWith(".app")) throw new RuntimeException("iOS mobileapppath given, but android mobiledevice specified.");
		if (!appPath.endsWith(".apk")) appPath += ".apk";
		//File app = new File(appPath);
		//if (!app.exists()) throw new RuntimeException("mobileapp specified does not exist: " + appPath);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, EnvironmentHandler.getMobileDevice());
		//capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		capabilities.setCapability(MobileCapabilityType.APP, appPath);
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
		capabilities.setCapability("unicodeKeyboard", true);
		capabilities.setCapability("resetKeyboard", true);
		try {
			WebDriver webDriver = new AndroidDriver(new URL("http://10.163.7.1:4723/wd/hub"), capabilities);
			super.webDriverWrapper = WebDriverWrapper.convert(webDriver);
		} catch (MalformedURLException e) {
			e.printStackTrace();//  192.168.1.5 -- 10.163.7.1
		}
	}

	@Override
	public void quit() {
		if (getWebDriver() != null) getWebDriver().quit();
	}
}