package com.sseltzer.selenium.framework.selenium.webpage.augmentors;

import com.sseltzer.selenium.framework.selenium.mapping.PageObject;
import com.sseltzer.selenium.framework.selenium.webpage.base.TagDetector;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPageAugmentor;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPageBase;
import com.sseltzer.selenium.framework.selenium.wrappers.TimingManager;
import com.sseltzer.selenium.framework.selenium.wrappers.TimingOptions;

public class Waiter extends WebPageAugmentor {

	private ElementInteractor elementInteractor;

	public Waiter(WebPageBase webPage) {
		super(webPage);
		elementInteractor = new ElementInteractor(webPage);
	}

	/**
	 * Waits for an element to be Editable. Times out after a given duration. 
	 * 
	 * @param pageElement
	 */
	public void elementEditable(PageObject pageElement) {
		elementEditable(pageElement, TimingManager.DEFAULT_TIMING_OPTIONS);
	}

	public void elementEditable(PageObject pageElement, TimingOptions timingOptions) {
		TimingManager.waitForElementEditable(getWebDriver(), TagDetector.detectBy(getWebDriver(), pageElement), timingOptions);
	}

	/**
	 * Waits for an element to be present. Times out after a given duration and FrameworkException
	 * is thrown.
	 * <br><br>
	 * Default time is specified in TimingManager.
	 * @param pageElement
	 * @
	 */
	public void elementPresent(PageObject pageElement) {
		elementPresent(pageElement, TimingManager.DEFAULT_TIMING_OPTIONS);
	}

	/**
	 * Waits for an element to be present. Times out after a given duration and FrameworkException
	 * is thrown.
	 * @param pageElement
	 * @param timingOptions
	 */
	public void elementPresent(PageObject pageElement, TimingOptions timingOptions) {
		TimingManager.waitForElementPresentBy(getWebDriver(), TagDetector.detectBy(getWebDriver(), pageElement), timingOptions);
	}

	/**
	 * Waits for an element to not be present. Times out after a given duration and FrameworkException
	 * is thrown.
	 * <br><br>
	 * Default time is specified in TimingManager.
	 * @param pageElement
	 */
	public void elementNotPresent(PageObject pageElement) {
		elementNotPresent(pageElement, TimingManager.DEFAULT_TIMING_OPTIONS);
	}

	/**
	 * Waits for an element to not be present. Times out after a given duration and FrameworkException
	 * is thrown.
	 * @param pageElement
	 * @param timingOptions
	 */
	public void elementNotPresent(PageObject pageElement, TimingOptions timingOptions) {
		TimingManager.waitForElementNotPresentBy(getWebDriver(), TagDetector.detectBy(getWebDriver(), pageElement), timingOptions);
	}

	/**
	 * Waits for an element to be visible. Times out after a given duration and FrameworkException
	 * is thrown.
	 * <br><br>
	 * Default time is specified in TimingManager.
	 * @param pageElement
	 */
	public void elementVisible(PageObject pageElement) {
		elementVisible(pageElement, TimingManager.DEFAULT_TIMING_OPTIONS);
	}

	/**
	 * Waits for an element to be visible. Times out after a given duration and FrameworkException
	 * is thrown.
	 * @param pageElement
	 * @param timingOptions
	 */
	public void elementVisible(PageObject pageElement, TimingOptions timingOptions) {
		TimingManager.waitForElementVisibleBy(getWebDriver(), TagDetector.detectBy(getWebDriver(), pageElement), timingOptions);
	}

	/**
	 * Waits for an element to be invisible. Times out after a given duration and FrameworkException
	 * is thrown.
	 * <br><br>
	 * Default time is specified in TimingManager.
	 * @param pageElement
	 */
	public void elementInvisible(PageObject pageElement) {
		elementInvisible(pageElement, TimingManager.DEFAULT_TIMING_OPTIONS);
	}

	/**
	 * Waits for an element to be invisible. Times out after a given duration and FrameworkException
	 * is thrown.
	 * @param pageElement
	 * @param timingOptions
	 */
	public void elementInvisible(PageObject pageElement, TimingOptions timingOptions) {
		TimingManager.waitForElementInvisibleBy(getWebDriver(), TagDetector.detectBy(getWebDriver(), pageElement), timingOptions);
	}

	/**
	 * This function will hover over an element on the page to wait for a flyout element to disappear
	 * off the page. This is used when multiple flyout elements need to be clicked in sequence but
	 * there would be a race condition that would cause a naming conflict when trying to get an
	 * item on the screen. For instance, on the Valpak.com site, the Print buttons have a flyout
	 * element. When trying to click two elements on two different flyouts, Selenium is too quick
	 * for the first element to disappear before hovering over and trying to click the second element.
	 * To mitigate this, another element may be hovered over to wait for the flyout to disappear before
	 * attempting to hover over another element to click its flyout. Otherwise a collection would be
	 * required to click each flyout, which may or may not be consistent between executions. The sequence
	 * would be as follows:
	 * <ul>
	 * <li>Hover over an element to cause a flyout.
	 * <li>Interact with the flyout.
	 * <li>Hover over another element to wait for the flyout to disappear.
	 * <li>Repeat.
	 * </ul>
	 * <br><br>
	 * The same ID/CSS selector detection rules apply here as it builds on the same underlying functionality.
	 * @param hoverElement is the element to hover over while waiting for the flyout to disappear. 
	 * This is arbitrary as once focus is gone from the original hover element, the flyout will disappear.
	 * @param flyoutElement is the element that should disappear after hovering over the hoverElement.
	 * @is thrown if any of the elements don't exist or if the flyout element does not
	 * disappear within the given interval.
	 */
	public void hoverUntilElementDisappears(PageObject hoverElement, PageObject flyoutElement) {
		hoverUntilElementDisappears(hoverElement, flyoutElement, TimingManager.DEFAULT_TIMING_OPTIONS);
	}

	/**
	 * This function will hover over an element on the page to wait for a flyout element to disappear
	 * off the page. This is used when multiple flyout elements need to be clicked in sequence but
	 * there would be a race condition that would cause a naming conflict when trying to get an
	 * item on the screen. For instance, on the Valpak.com site, the Print buttons have a flyout
	 * element. When trying to click two elements on two different flyouts, Selenium is too quick
	 * for the first element to disappear before hovering over and trying to click the second element.
	 * To mitigate this, another element may be hovered over to wait for the flyout to disappear before
	 * attempting to hover over another element to click its flyout. Otherwise a collection would be
	 * required to click each flyout, which may or may not be consistent between executions. The sequence
	 * would be as follows:
	 * <ul>
	 * <li>Hover over an element to cause a flyout.
	 * <li>Interact with the flyout.
	 * <li>Hover over another element to wait for the flyout to disappear.
	 * <li>Repeat.
	 * </ul>
	 * <br><br>
	 * The same ID/CSS selector detection rules apply here as it builds on the same underlying functionality.
	 * @param hoverElement is the element to hover over while waiting for the flyout to disappear. 
	 * This is arbitrary as once focus is gone from the original hover element, the flyout will disappear.
	 * @param flyoutElement is the element that should disappear after hovering over the hoverElement.
	 * @param timingOptions is the amount of time to wait for the flyoutElement to disappear.
	 * @is thrown if any of the elements don't exist or if the flyout element does not
	 * disappear within the given interval.
	 */
	public void hoverUntilElementDisappears(PageObject hoverElement, PageObject flyoutElement, TimingOptions timingOptions) {
		elementInteractor.hover(hoverElement);
		elementNotPresent(flyoutElement, timingOptions);
	}
}