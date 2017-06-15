package com.sseltzer.selenium.framework.selenium.wrappers;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

/**
 * Wrapper class for the Selenium By object. Converts all native Selenium returns to their wrapped
 * counterparts.
 * @author Sean Seltzer
 *
 */
public class ByWrapper {
	private WebDriverWrapper webDriver;
	private By by;

	public ByWrapper(WebDriverWrapper webDriver, By by) {
		this.webDriver = webDriver;
		this.by = by;
	}

	public static ByWrapper convert(WebDriverWrapper webDriver, By by) {
		return new ByWrapper(webDriver, by);
	}

	protected By getBaseObject() {
		return by;
	}

	public static ByWrapper className(WebDriverWrapper webDriver, String className) {
		return ByWrapper.convert(webDriver, By.className(className));
	}

	public static ByWrapper cssSelector(WebDriverWrapper webDriver, String selector) {
		return ByWrapper.convert(webDriver, By.cssSelector(selector));
	}

	public boolean equals(Object o) {
		return by.equals(o);
	}

	public WebElementWrapper findElement(SearchContext context) {
		return WebElementWrapper.convert(webDriver, by.findElement(context));
	}

	public ArrayList<WebElementWrapper> findElements(SearchContext context) {
		return WebElementWrapper.convert(webDriver, by.findElements(context));
	}

	public static ByWrapper id(WebDriverWrapper webDriver, String id) {
		return ByWrapper.convert(webDriver, By.id(id));
	}

	public static ByWrapper linkText(WebDriverWrapper webDriver, String linkText) {
		return ByWrapper.convert(webDriver, By.linkText(linkText));
	}

	public static ByWrapper name(WebDriverWrapper webDriver, String name) {
		return ByWrapper.convert(webDriver, By.name(name));
	}

	public static ByWrapper partialLinkText(WebDriverWrapper webDriver, String linkText) {
		return ByWrapper.convert(webDriver, By.partialLinkText(linkText));
	}

	public static ByWrapper tagName(WebDriverWrapper webDriver, String name) {
		return ByWrapper.convert(webDriver, By.tagName(name));
	}

	public static ByWrapper xpath(WebDriverWrapper webDriver, String xpathExpression) {
		return ByWrapper.convert(webDriver, By.xpath(xpathExpression));
	}

	public String toString() {
		return by.toString();
	}
}
