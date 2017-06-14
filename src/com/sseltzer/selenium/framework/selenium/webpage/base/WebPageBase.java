package com.sseltzer.selenium.framework.selenium.webpage.base;

import com.sseltzer.selenium.framework.selenium.browsers.Browser;
import com.sseltzer.selenium.framework.selenium.webpage.augmentors.ElementInteractor;
import com.sseltzer.selenium.framework.selenium.webpage.augmentors.ElementLocator;
import com.sseltzer.selenium.framework.selenium.webpage.augmentors.FrameInteractor;
import com.sseltzer.selenium.framework.selenium.webpage.augmentors.Waiter;
import com.sseltzer.selenium.framework.selenium.website.Website;
import com.sseltzer.selenium.framework.selenium.wrappers.TimingManager;
import com.sseltzer.selenium.framework.selenium.wrappers.WebDriverWrapper;

/**
 * The basic idea of the WebPage object is to provide a single point of access to the current page of 
 * the browser at any given time. Contained in the WebPage object are generic functions which allow
 * a single point of access for WebPage functions whether it be finding an element on the page, clicking
 * an element, form filling a field, etc. WebPage is the single source of interaction and eliminates
 * duplication over the Page Object Model which required that a new function be created for every action.
 * The WebPageBase object must be given a Browser and a Website object when instantiated. The Browser
 * object ultimately is the reference to WebDriver which controls the browser itself, and is what allows
 * the WebPage to manipulate the page. The Website object is just a reference to the Website that was used
 * to create the WebPage and makes it available publicly. 
 * <br><br>
 * The WebPageBase object implements the basic functionality of the WebPage object. To avoid having a 
 * "god object" WebPage's functionality is split over several classes. WebPageBase itself is a composite
 * of several WebPageAugmentor classes. The WebPage object then keeps a singleton instance of WebPageBase
 * which it serves to provide access to the composite augmentor classes which contain the real functionality.
 * WebPageBase itself does nothing more than instantiates and provides access to the composite augmentors.
 *    
 * @author Sean Seltzer
 *
 */
public class WebPageBase {

	private Browser browser;
	private Website website;

	private final ElementLocator elementLocator;
	private final ElementInteractor elementInteractor;
	private final FrameInteractor frameInteractor;
	private final Waiter waiter;

	public WebPageBase(Browser browser, Website website) {
		this.browser = browser;
		this.website = website;
		TimingManager.setGlobalImplicitTimeouts(getWebDriver());

		elementInteractor = new ElementInteractor(this);
		elementLocator = new ElementLocator(this);
		frameInteractor = new FrameInteractor(this);
		waiter = new Waiter(this);
	}

	/**
	 * Returns the Browser object that the WebPage is using to connect to Selenium.
	 * @return the Browser object that the WebPage is using to connect to Selenium.
	 */
	public Browser getBrowser() {
		return browser;
	}

	/**
	 * Returns the Website object used to create the WebPage object.
	 * @return the Website object used to create the WebPage object.
	 */
	public Website getWebsite() {
		return website;
	}

	/**
	 * Returns the wrapped WebDriver which is the access point to Selenium.
	 * @return the wrapped WebDriver which is the access point to Selenium.
	 */
	protected WebDriverWrapper getWebDriver() {
		return getBrowser().getWebDriver();
	}

	/**
	 * Returns the ElementLocator augmentor which is used to locate web elements on the
	 * page using the WebDriver.
	 * @return the ElementLocator augmentor which is used to locate web elements.
	 */
	public ElementLocator locateElement() {
		return elementLocator;
	}

	/**
	 * Returns the ElementInteractor augmentor which is used to interact with web elements
	 * on the page using the WebDriver.
	 * @return the ElementInteractor augmentor which is used to interact with web elements.
	 */
	public ElementInteractor interactWithElement() {
		return elementInteractor;
	}

	/**
	 * Returns the FrameInteractor augmentor which is used to interact with the browser and
	 * browser windows using WebDriver.
	 * @return the FrameInteractor augmentor which is used to interact with the browser.
	 */
	public FrameInteractor interactWithFrame() {
		return frameInteractor;
	}

	/**
	 * Returns the Waiter augmentor which is used to wait for web elements and web element states
	 * using the WebDriver. 
	 * @return the Waiter augmentor which is used to wait for web elements and web element states.
	 */
	public Waiter waitFor() {
		return waiter;
	}
}
