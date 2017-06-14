package com.sseltzer.selenium.framework.environment.enums;

import com.sseltzer.selenium.framework.environment.EnvironmentHandler;

public enum MobileConfig {
	LEGACY_AND  (BrowserEnum.ANDROID, MobileAppEnum.LEGACY),
	LEGACY_IOS  (BrowserEnum.IOS,     MobileAppEnum.LEGACY),
	AR_AND  	(BrowserEnum.ANDROID, MobileAppEnum.AR),
	AR_IOS  	(BrowserEnum.IOS,     MobileAppEnum.AR),
	SKYWAY_AND  (BrowserEnum.ANDROID, MobileAppEnum.SKYWAY),
	SKYWAY_IOS  (BrowserEnum.IOS,     MobileAppEnum.SKYWAY),
	APPLE_WATCH (BrowserEnum.IOS,	  MobileAppEnum.APPLE_WATCH);
	
	private BrowserEnum browser = null;
	private MobileAppEnum app = null;

	private MobileConfig(BrowserEnum browser, MobileAppEnum app) {
		this.browser = browser;
		this.app = app;
	}

	public BrowserEnum getBrowser() {
		return browser;
	}
	
	public MobileAppEnum getMobileApp() {
		return app;
	}

	public static MobileConfig find(String browserStr, String appStr) {
		return find(BrowserEnum.find(browserStr), MobileAppEnum.findFromPath(appStr));
	}
	public static MobileConfig find(BrowserEnum browser, MobileAppEnum app) {
		if (app == null) throw new RuntimeException("Please set a valid mobileapp environmental variable: " + app);
		if (browser == null || browser == BrowserEnum.NONE) browser = EnvironmentHandler.getMobileDevice().getBrowser();
		for (MobileConfig conf : MobileConfig.values())
			if (conf.getBrowser() == browser && conf.getMobileApp() == app) return conf;
		throw new RuntimeException("Invalid combination of browser and app requested: " + browser + " : " + app);
	}
}
