package com.sseltzer.selenium.framework.selenium.wrappers;

import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.sseltzer.selenium.framework.error.ErrorManager;
import com.sseltzer.selenium.framework.error.FillDataBuilder;
import com.sseltzer.selenium.framework.strings.maps.PublicErrorStrings;

import io.appium.java_client.AppiumDriver;

public class WebDriverWrapper extends SearchContextBridge {

	private WebDriver webDriver;

	public WebDriverWrapper(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public static WebDriverWrapper convert(WebDriver webDriver) {
		return new WebDriverWrapper(webDriver);
	}

	@Override
	protected WebDriver getWebDriver() {
		return getBaseObject();
	}

	@Override
	protected WebDriver getBaseObject() {
		return webDriver;
	}
	
	public WebDriver getBase() {
		return webDriver;
	}

	@Override
	protected Class<?> getBaseObjectClass() {
		return webDriver.getClass();
	}

	public void close() {
		webDriver.close();
	}

	@Override
	protected WebElementWrapper findElementCore(ByWrapper by) {
		try {
			return WebElementWrapper.convert(this, webDriver.findElement(by.getBaseObject()));
		} catch (Exception e) {
			FillDataBuilder fillData = FillDataBuilder.create(by.getBaseObject().toString());
			ErrorManager.throwAndDump(PublicErrorStrings.ELEMENT_NOT_EXIST, fillData);
		}
		// This can't execute, either the element is returned from findElement, or an exception is thrown by the wait function.
		ErrorManager.throwDeadCode();
		return null;
	}

	@Override
	protected ArrayList<WebElementWrapper> findElementsCore(ByWrapper by) {
		try {
			return WebElementWrapper.convert(this, webDriver.findElements(by.getBaseObject()));
		} catch (Exception e) {
			FillDataBuilder fillData = FillDataBuilder.create(by.getBaseObject().toString());
			ErrorManager.throwAndDump(PublicErrorStrings.ELEMENT_NOT_EXIST, fillData);
		}
		// This can't execute, either the element is returned from findElement, or an exception is thrown by the wait function.
		ErrorManager.throwDeadCode();
		return null;
	}

	@Override
	public WebElementWrapper findElement(ByWrapper by, TimingOptions timingOptions) {
		if (!(webDriver instanceof AppiumDriver))TimingManager.waitForValpakComPageLoad(this);
		return TimingManager.findElement(this, by, timingOptions);
	}

	@Override
	public ArrayList<WebElementWrapper> findElements(ByWrapper by, TimingOptions timingOptions) {
		if (!(webDriver instanceof AppiumDriver)) TimingManager.waitForValpakComPageLoad(this);
		return TimingManager.findElements(this, by, timingOptions);
	}

	public void get(String url) {
		webDriver.get(url);
	}

	public String getCurrentUrl() {
		return webDriver.getCurrentUrl();
	}

	public String getPageSource() {
		return webDriver.getPageSource();
	}

	public String getTitle() {
		return webDriver.getTitle();
	}

	public String getWindowHandle() {
		return webDriver.getWindowHandle();
	}

	public Set<String> getWindowHandles() {
		return webDriver.getWindowHandles();
	}

	public WebDriver.Options manage() {
		return webDriver.manage();
	}

	public WebDriver.Navigation navigate() {
		return webDriver.navigate();
	}

	public void quit() {
		webDriver.quit();
		webDriver = null;
	}

	public WebDriver.TargetLocator switchTo() {
		return webDriver.switchTo();
	}

	public Actions getActionBuilder() {
		return new Actions(getBaseObject());
	}

	public String getSessionID() {
		return ((RemoteWebDriver) webDriver).getSessionId().toString();
	}

	public byte[] takeScreenshot() {
		return ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
	}
}
