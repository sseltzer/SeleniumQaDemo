package com.sseltzer.selenium.framework.selenium.webpage.augmentors;

import java.util.ArrayList;

import org.openqa.selenium.Point;

import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;
import com.sseltzer.selenium.framework.selenium.mapping.PageObject;
import com.sseltzer.selenium.framework.selenium.webpage.base.TagDetector;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPageAugmentor;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPageBase;
import com.sseltzer.selenium.framework.selenium.wrappers.LocatorType;
import com.sseltzer.selenium.framework.selenium.wrappers.TimingManager;
import com.sseltzer.selenium.framework.selenium.wrappers.TimingOptions;
import com.sseltzer.selenium.framework.selenium.wrappers.WebElementWrapper;

/**
 * The ElementInteractor class is a WebPageAugmentor which provides functions
 * which allow the framework to locate elements on a web page.
 * 
 * @author Sean Seltzer
 * 
 */
public class ElementLocator extends WebPageAugmentor {

	public ElementLocator(WebPageBase webPage) {
		super(webPage);
	}

	/**
	 * This function will get a single element from a page. This function will
	 * dynamically detect what type of tag was provided (ID or CSS) based on the
	 * existence of a special character in the given selector. This function
	 * relies on TimingManager to perform the search and ErrorManager to
	 * propagate exceptions encountered during retrieval. See the TagDetector
	 * for the specific PageObject type identification method.
	 * 
	 * @param pageElement
	 *            is the tag of the element to be retrieved (ID or CSS). CSS
	 *            must have a special character in the tag.
	 * @return The WebElementWrapper for the given tag.
	 * @throws FrameworkException
	 *             is thrown when the tag cannot be found on the page. This
	 *             provides a filled error string and filtered stack trace.
	 */
	public WebElementWrapper findElement(PageObject pageElement) {
		return findElement(pageElement, TimingManager.DEFAULT_TIMING_OPTIONS);
	}

	public WebElementWrapper findElement(PageObject pageElement, int i) {
		return findElements(pageElement, TimingManager.DEFAULT_TIMING_OPTIONS).get(i);
	}

	/**
	 * This function will get a single element from a page. This function will
	 * dynamically detect what type of tag was provided (ID or CSS) based on the
	 * existence of a special character in the given selector. This function
	 * relies on TimingManager to perform the search and ErrorManager to
	 * propagate exceptions encountered during retrieval. See the TagDetector
	 * for the specific PageObject type identification method. <br>
	 * <br>
	 * This function also takes a specified timing if the default is not
	 * desired.
	 * 
	 * @param pageElement
	 *            is the tag of the element to be retrieved (ID or CSS). CSS
	 *            must have a special character in the tag.
	 * @param timingOptions
	 *            timing control for element retrieval.
	 * @return The WebElementWrapper for the given tag.
	 * @throws FrameworkException
	 *             is thrown when the tag cannot be found on the page. This
	 *             provides a filled error string and filtered stack trace.
	 */
	public WebElementWrapper findElement(PageObject pageElement, TimingOptions timingOptions) {
		return getWebDriver().findElement(TagDetector.detectBy(getWebDriver(), pageElement), timingOptions);
	}

	/**
	 * This function will get a single element from a page. This function will
	 * dynamically detect what type of tag was provided (ID or CSS) based on the
	 * existence of a special character in the given selector. This function
	 * relies on TimingManager to perform the search and ErrorManager to
	 * propagate exceptions encountered during retrieval. See the TagDetector
	 * for the specific PageObject type identification method. <br>
	 * <br>
	 * This function may be used to specify an exact retrieval method.
	 * findElement(PageObject) should be used normally instead of this function.
	 * 
	 * @param locatorType
	 *            desired retrieval method.
	 * @param pageElement
	 *            is the tag of the element to be retrieved (ID or CSS). CSS
	 *            must have a special character in the tag.
	 * @return The WebElementWrapper for the given tag.
	 * @throws FrameworkException
	 *             is thrown when the tag cannot be found on the page. This
	 *             provides a filled error string and filtered stack trace.
	 */
	public WebElementWrapper findElement(LocatorType locatorType, PageObject pageElement) {
		return findElement(locatorType, pageElement, TimingManager.DEFAULT_TIMING_OPTIONS);
	}

	/**
	 * This function will get a single element from a page. This function will
	 * dynamically detect what type of tag was provided (ID or CSS) based on the
	 * existence of a special character in the given selector. This function
	 * relies on TimingManager to perform the search and ErrorManager to
	 * propagate exceptions encountered during retrieval. See the TagDetector
	 * for the specific PageObject type identification method. <br>
	 * <br>
	 * This function may be used to specify an exact retrieval method.
	 * findElement(PageObject) should be used normally instead of this function.
	 * This function also takes a specified timing if the default is not
	 * desired.
	 * 
	 * @param locatorType
	 *            desired retrieval method.
	 * @param pageElement
	 *            is the tag of the element to be retrieved (ID or CSS). CSS
	 *            must have a special character in the tag.
	 * @param timingOptions
	 *            timing control for element retrieval.
	 * @return The WebElementWrapper for the given tag.
	 * @throws FrameworkException
	 *             is thrown when the tag cannot be found on the page. This
	 *             provides a filled error string and filtered stack trace.
	 */
	public WebElementWrapper findElement(LocatorType locatorType, PageObject pageElement, TimingOptions timingOptions) {
		return getWebDriver().findElement(locatorType.getByWrapper(getWebDriver(), pageElement), timingOptions);
	}

	/**
	 * This function will get a list of elements from a page. This function will
	 * dynamically detect what type of tag was provided (ID or CSS) based on the
	 * existence of a special character in the given selector. This function
	 * relies on TimingManager to perform the search and ErrorManager to
	 * propagate exceptions encountered during retrieval. See the TagDetector
	 * for the specific PageObject type identification method.
	 * 
	 * @param pageElement
	 *            is the tag of the element to be retrieved (ID or CSS). CSS
	 *            must have a special character in the tag.
	 * @return The list of WebElementWrappers for the given tag.
	 * @throws FrameworkException
	 *             is thrown when the tag cannot be found on the page. This
	 *             provides a filled error string and filtered stack trace.
	 */
	public ArrayList<WebElementWrapper> findElements(PageObject pageElement) {
		return findElements(pageElement, TimingManager.DEFAULT_TIMING_OPTIONS);
	}

	/**
	 * This function will get a list of elements from a page. This function will
	 * dynamically detect what type of tag was provided (ID or CSS) based on the
	 * existence of a special character in the given selector. This function
	 * relies on TimingManager to perform the search and ErrorManager to
	 * propagate exceptions encountered during retrieval. See the TagDetector
	 * for the specific PageObject type identification method. <br>
	 * <br>
	 * This function also takes a specified timing if the default is not
	 * desired.
	 * 
	 * @param pageElement
	 *            is the tag of the element to be retrieved (ID or CSS). CSS
	 *            must have a special character in the tag.
	 * @param timingOptions
	 *            timing control for element retrieval.
	 * @return The list of WebElementWrappers for the given tag.
	 * @throws FrameworkException
	 *             is thrown when the tag cannot be found on the page. This
	 *             provides a filled error string and filtered stack trace.
	 */
	public ArrayList<WebElementWrapper> findElements(PageObject pageElement, TimingOptions timingOptions) {
		return getWebDriver().findElements(TagDetector.detectBy(getWebDriver(), pageElement), timingOptions);
	}

	/**
	 * This function will get a list of elements from a page. This function will
	 * dynamically detect what type of tag was provided (ID or CSS) based on the
	 * existence of a special character in the given selector. This function
	 * relies on TimingManager to perform the search and ErrorManager to
	 * propagate exceptions encountered during retrieval. See the TagDetector
	 * for the specific PageObject type identification method. <br>
	 * <br>
	 * This function may be used to specify an exact retrieval method.
	 * findElements(PageObject) should be used normally instead of this
	 * function.
	 * 
	 * @param locatorType
	 *            desired retrieval method.
	 * @param pageElement
	 *            is the tag of the element to be retrieved (ID or CSS). CSS
	 *            must have a special character in the tag.
	 * @return The list of WebElementWrappers for the given tag.
	 * @throws FrameworkException
	 *             is thrown when the tag cannot be found on the page. This
	 *             provides a filled error string and filtered stack trace.
	 */
	public ArrayList<WebElementWrapper> findElements(LocatorType locatorType, PageObject pageElement) {
		return findElements(locatorType, pageElement, TimingManager.DEFAULT_TIMING_OPTIONS);
	}

	/**
	 * This function will get a list of elements from a page. This function will
	 * dynamically detect what type of tag was provided (ID or CSS) based on the
	 * existence of a special character in the given selector. This function
	 * relies on TimingManager to perform the search and ErrorManager to
	 * propagate exceptions encountered during retrieval. See the TagDetector
	 * for the specific PageObject type identification method. <br>
	 * <br>
	 * This function may be used to specify an exact retrieval method.
	 * findElements(PageObject) should be used normally instead of this
	 * function. This function also takes a specified timing if the default is
	 * not desired.
	 * 
	 * @param locatorType
	 *            desired retrieval method.
	 * @param pageElement
	 *            is the tag of the element to be retrieved (ID or CSS). CSS
	 *            must have a special character in the tag.
	 * @param timingOptions
	 *            timing control for element retrieval.
	 * @return The list of WebElementWrappers for the given tag.
	 * @throws FrameworkException
	 *             is thrown when the tag cannot be found on the page. This
	 *             provides a filled error string and filtered stack trace.
	 */
	public ArrayList<WebElementWrapper> findElements(LocatorType locatorType, PageObject pageElement, TimingOptions timingOptions) {
		return getWebDriver().findElements(locatorType.getByWrapper(getWebDriver(), pageElement), timingOptions);
	}

	/**
	 * Find the coordinate location of the element on the page.
	 * 
	 * @param pageElement
	 *            element to find the location of.
	 * @return the x and y coordinates of the object.
	 * @throws FrameworkException
	 *             is thrown when the tag cannot be found on the page. This
	 *             provides a filled error string and filtered stack trace.
	 */
	public Point getElementLocation(PageObject pageElement) {
		return findElement(pageElement).getLocation();
	}

	/**
	 * Determines if an element is displayed on the page.
	 * 
	 * @param pageObject
	 *            element to find whether or not it's displayed.
	 * @return the display state of the element.
	 * @throws FrameworkException
	 *             is thrown when the tag cannot be found on the page. This
	 *             provides a filled error string and filtered stack trace.
	 */
	public boolean isElementDisplayed(PageObject pageObject) {
		return findElement(pageObject).isDisplayed();
	}

	/**
	 * Determines if an element is enabled on the page.
	 * 
	 * @param pageObject
	 *            element to find whether or not it's enabled.
	 * @return the enabled state of the element.
	 * @throws FrameworkException
	 *             is thrown when the tag cannot be found on the page. This
	 *             provides a filled error string and filtered stack trace.
	 */
	public boolean isElementEnabled(PageObject pageObject) {
		return findElement(pageObject).isEnabled();
	}

	/**
	 * Determines if an element is selected on the page.
	 * 
	 * @param pageObject
	 *            element to find whether or not it's selected.
	 * @return the selected state of the element.
	 * @throws FrameworkException
	 *             is thrown when the tag cannot be found on the page. This
	 *             provides a filled error string and filtered stack trace.
	 */
	public boolean isElementSelected(PageObject pageObject) {
		return findElement(pageObject).isSelected();
	}

	public boolean elementExists(PageObject pageObject) {
		try {
			findElement(pageObject, TimingManager.IMMEDIATE_DEFAULT_OPTIONS);
		} catch (FrameworkException e) {
			return false;
		}
		return true;
	}
}
