
package com.sseltzer.selenium.framework.utility.plugin_container;

import java.io.IOException;

import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;
import com.sseltzer.selenium.framework.selenium.browsers.Browser;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPage;
import com.sseltzer.selenium.framework.selenium.wrappers.TimingManager;

/**
 *
 *
 * PluginContainer.java
 *
 * @author ckiehl Jul 9, 2014
 */
public class PluginContainer {
	private static final String OS_NAME = System.getProperty("os.name");
	
	public static void closeIfOnWindowsPlatform(WebPage webPage) {
		closeIfOnWindowsPlatform(webPage.getBrowser()); 
	}
	
	public static void closeIfOnWindowsPlatform(Browser browser) {
		if (runningFirefoxOnWindowsPlatform(browser)) 
			killFirefoxPluginContainer();
		TimingManager.waitForSpecifiedDuration(500);
	}

	private static Boolean runningFirefoxOnWindowsPlatform(Browser browser) {
		return OS_NAME.startsWith("Win") && browser.getClass().getName().contains("Firefox");
	}
	
	private static void killFirefoxPluginContainer() throws FrameworkException {
		try { Runtime.getRuntime().exec("taskkill /F /IM plugin-container.exe"); } 
		catch (IOException e) { throw new FrameworkException(e); }
		TimingManager.waitForSpecifiedDuration(1000);
	}
}
