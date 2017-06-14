package com.sseltzer.selenium.framework.environment;

import org.junit.Test;

import com.sseltzer.selenium.framework.environment.enums.MobileConfig;
import com.sseltzer.selenium.framework.environment.enums.MobileDeviceEnum;
import com.sseltzer.selenium.framework.selenium.browsers.BrowserFactory;

public class EnvTest {
	
	@Test
	public void doTest() {
		System.setProperty("mobileapp", "I:/_QAMobile/Appium/150406/vaplak_reality1.apk");
		//System.setProperty("mobiledevice", "SAMSUNG-SM-G900V");
		System.setProperty("mobiledevice", "iPhone 6");
		MobileConfig conf = EnvironmentHandler.getMobileConfig();
		MobileDeviceEnum device = EnvironmentHandler.getMobileDevice();
		String appPath = EnvironmentHandler.getMobileAppPath();
		System.out.println(appPath);
		System.out.println(conf.getBrowser());
		System.out.println(conf.getMobileApp());
		System.out.println(device);
		
		BrowserFactory.getAndroidBrowser();
	}

}
