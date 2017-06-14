package com.sseltzer.selenium.framework.selenium.webpage.base;

import com.sseltzer.selenium.framework.selenium.browsers.Browser;
import com.sseltzer.selenium.framework.selenium.wrappers.WebDriverWrapper;

/**
 * WebPage provides a single interface to communicate with a given page. It provides the functionality
 * to perform any operation in a generic manner such as clicking a button or finding element text. This
 * creates quite a bit of overhead in the sheer number of operations which WebPage provides. To avoid
 * having WebPage be a "god object" which contains too many functions and over extends itself, the concept
 * of the WebPageAugmentor was implemented. This splits the WebPage into several classes, each with a 
 * dedicated purpose and separated set of functionality. The WebPageAugmentor classes are all contained
 * within the WebPage and provide somewhat more of an English based syntax to make it clear what operation
 * is being performed. For example, webPage.waitFor accesses the Waiter augmentor and it is clear that the
 * webPage will wait for some operation. Alternately, webPage.locateElement it is clear that we will be 
 * searching for an element on a page. The Augmentor functions are named similarly to provide clear syntax
 * on which operation is being performed as an easily remembered String. Such as, 
 * webPage.locateElement().findElement.
 * 
 * @author Sean Seltzer
 *
 */
public class WebPageAugmentor {

	protected WebPageBase webPage;

	public WebPageAugmentor(WebPageBase webPage) {
		this.webPage = webPage;
	}

	/**
	 * Returns webpage.getBrowser() as a shortcut to reduce code.
	 * @return webpage.getBrowser() as a shortcut to reduce code.
	 */
	protected Browser getBrowser() {
		return webPage.getBrowser();
	}

	/**
	 * Returns webpage.getBrowser().getWebDriver() as a shortcut to reduce code. 
	 * @return webpage.getBrowser().getWebDriver() as a shortcut to reduce code.
	 */
	protected WebDriverWrapper getWebDriver() {
		return getBrowser().getWebDriver();
	}
}
