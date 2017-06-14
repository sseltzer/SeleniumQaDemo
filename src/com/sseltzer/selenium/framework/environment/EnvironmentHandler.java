package com.sseltzer.selenium.framework.environment;

import com.sseltzer.selenium.framework.environment.enums.BrowserEnum;
import com.sseltzer.selenium.framework.environment.enums.EnvironmentConfig;
import com.sseltzer.selenium.framework.environment.enums.EnvironmentEnum;
import com.sseltzer.selenium.framework.environment.enums.LoggingEnum;
import com.sseltzer.selenium.framework.environment.enums.MobileAppEnum;
import com.sseltzer.selenium.framework.environment.enums.MobileConfig;
import com.sseltzer.selenium.framework.environment.enums.MobileDeviceEnum;
import com.sseltzer.selenium.framework.environment.enums.ServerEnum;

/**
 * The EnvironmentalHandler is the main access point for the framework to get a handle
 * on the target execution environment. The EnvironmentalHandler acts as a static service
 * which will retrieve the environmental variables upon request and return an enumeration
 * representing the environmental variable data. The getVariable(String) function will
 * perform the retrieval of all of the environmental variables. It prefers System.getProperty
 * for variables since the TeamCity servers use them via Build Parameters. If no system properties
 * are found, as a fallback it will check environmental variables. The exact case of the environmental
 * variables is checked first, that is (ENVIRONMENT, SERVER, BROWSER). As a fallback for each properties
 * environmental variables, if the default case is not found, it will check for the lower case
 * variables to attempt to retrieve the environment. 
 * 
 * @author Sean Seltzer
 *
 */
public final class EnvironmentHandler {
	
	private static EnvironmentLoader loader = new EnvironmentLoader();

	private static final String ENVIRONMENT = "environment";
	private static final String SERVER      = "server";
	private static final String BROWSER     = "browser";
	private static final String LOGGING     = "logging";
	private static final String FTP         = "ftp";
	private static final String SCREENSHOT_ON_FAIL = "screenshotonfail";

	private static final String BUILD_NUMBER 	= "build_number";
	private static final String IMAGE_PATH 		= "image_path";
	private static final String COUPON_COUNT_PATH 	= "couponCountPath";

	private static final String LOGGING_RECORD 	= "loggingRecord";
	
	private static final String MOBILE_DEVICE = "mobiledevice";
	private static final String MOBILE_APP_PATH = "mobileapp";


	public static void reload() {
		loader.reload();
	}
	
	public static EnvironmentEnum getEnvironment() {
		return EnvironmentEnum.find(loader.getVariable(ENVIRONMENT));
	}

	public static ServerEnum getServer() {
		return ServerEnum.find(loader.getVariable(SERVER));
	}

	public static BrowserEnum getBrowser() {
		return BrowserEnum.find(loader.getVariable(BROWSER));
	}

	public static EnvironmentConfig getEnvironmentConfig() {
		return EnvironmentConfig.find(getEnvironment(), getServer());
	}

	public static LoggingEnum getLoggingParam() {
		return LoggingEnum.find(loader.getVariable(LOGGING));
	}
	public static String getFTPAddress() {
		return loader.getVariable(FTP);
	}
	public static boolean getScreenshotOnFail() {
		return Boolean.valueOf(loader.getVariable(SCREENSHOT_ON_FAIL));
	}
	public static boolean getLoggingRecordMode() {
		return Boolean.valueOf(loader.getVariable(LOGGING_RECORD));
	}
	
	public static String getBuildNumber() {
		return loader.getVariable(BUILD_NUMBER);
	}
	public static String getImagePath() {
		String path = loader.getVariable(IMAGE_PATH);
		return (path == null) ? "" : path;
	}
	public static String getCouponCountPath() {
		String path = loader.getVariable(COUPON_COUNT_PATH);
		return (path == null) ? "" : path;
	}
	
	public static boolean isMobileTest() {
		if (loader.getVariable(MOBILE_DEVICE) != null && !loader.getVariable(MOBILE_DEVICE).isEmpty()) return true;
		if (loader.getVariable(MOBILE_APP_PATH) != null && !loader.getVariable(MOBILE_APP_PATH).isEmpty()) return true;
		return false;
	}
	
	public static MobileDeviceEnum getMobileDevice() {
		return MobileDeviceEnum.find(loader.getVariable(MOBILE_DEVICE));
	}
	
	public static MobileAppEnum getMobileApp() {
		return  MobileAppEnum.findFromPath(loader.getVariable(MOBILE_APP_PATH));
	}
	public static boolean isMobilePicker() {
		return loader.getVariable(MOBILE_APP_PATH).contains("picker"); 
	}
	
	public static MobileConfig getMobileConfig() {
		return MobileConfig.find(getBrowser(), getMobileApp());
	}
	
	public static String getMobileAppPath() {
		return loader.getVariable(MOBILE_APP_PATH);
	}
}
