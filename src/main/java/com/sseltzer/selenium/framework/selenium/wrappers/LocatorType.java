package com.sseltzer.selenium.framework.selenium.wrappers;

import com.sseltzer.selenium.framework.error.ErrorManager;
import com.sseltzer.selenium.framework.selenium.mapping.PageObject;

public enum LocatorType {
	ID, CLASS_NAME, CSS, LINK_TEXT, NAME, PARTIAL_LINK_TEXT, TAG_NAME, XPATH;

	public ByWrapper getByWrapper(WebDriverWrapper webDriver, PageObject pageElement) {
		String locatorStr = pageElement.toString();
		switch (this) {
			case ID: 				return ByWrapper.id(webDriver, locatorStr);
			case CLASS_NAME:		return ByWrapper.className(webDriver, locatorStr);
			case CSS:				return ByWrapper.cssSelector(webDriver, locatorStr);
			case XPATH:				return ByWrapper.xpath(webDriver, locatorStr);
			case TAG_NAME:			return ByWrapper.tagName(webDriver, locatorStr);
			case NAME:				return ByWrapper.name(webDriver, locatorStr);
			case LINK_TEXT:			return ByWrapper.linkText(webDriver, locatorStr);
			case PARTIAL_LINK_TEXT: return ByWrapper.partialLinkText(webDriver, locatorStr);
		}
		ErrorManager.throwDeadCode();
		return null;
	}
}
