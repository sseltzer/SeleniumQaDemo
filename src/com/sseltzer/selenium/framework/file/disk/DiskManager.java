package com.sseltzer.selenium.framework.file.disk;

public class DiskManager {

	private final static String LOG_PATH = "C:/local_repos/properties/ja_properties/properties/junit_support_files/Omniture/";
	//private final static String LOG_PATH = "C:/selenium_upload/local/";
	private final static String LOG_PREFIX = "logs";
	private final static String SS_PATH = "/app/continuousintegration/selenium/images/";
	//private final static String SS_PATH = "C:/selenium_upload/local/";
	private final static String SS_PREFIX = "img";

	private static GitFilePackage logManager = null;
	private static TimestampedPackage screenshotManager = null;

	public static GitFilePackage getLogManager() {
		if (logManager == null) logManager = new GitFilePackage(LOG_PATH, LOG_PREFIX);
		return logManager;
	}

	public static TimestampedPackage getScreenshotManager() {
		if (screenshotManager == null) screenshotManager = new TimestampedPackage(SS_PATH, SS_PREFIX);
		return screenshotManager;
	}
}
