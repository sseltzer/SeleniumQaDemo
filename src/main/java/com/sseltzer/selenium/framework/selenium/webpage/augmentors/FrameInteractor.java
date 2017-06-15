package com.sseltzer.selenium.framework.selenium.webpage.augmentors;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;
import com.sseltzer.selenium.framework.selenium.mapping.PageObject;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPageAugmentor;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPageBase;
import com.sseltzer.selenium.framework.selenium.wrappers.WebDriverWrapper;
import com.sseltzer.selenium.framework.verification.validation.ArgumentValidator;

/**
 * The ElementInteractor class is a WebPageAugmentor which provides functions which allow the framework
 * to interact with browser windows and iframes.
 * @author Sean Seltzer
 */
public class FrameInteractor extends WebPageAugmentor {

	public FrameInteractor(WebPageBase webPage) {
		super(webPage);
	}

	/**
	 * Returns the window dimensions in pixels.
	 * @return the window dimensions in pixels.
	 */
	public Dimension getWindowDimensions() {
		return getWebDriver().manage().window().getSize();
	}

	/**
	 * Returns the page title.
	 * @return the page title.
	 */
	public String getPageTitle() {
		return getWebDriver().getTitle();
	}

	/**
	 * This function will switch to a frame on the page. This is mainly used for embedded modals
	 * which are attached to the DOM via iFrame. Frames may not be accessed directly through the
	 * DOM at the root page, but must be switched to and from in order to access the parent/child
	 * pages.
	 * @param pageElement is the tag of the frame, ie an iFrame ID.
	 * @
	 */
	public void switchToFrame(PageObject pageElement) {
		ArgumentValidator.create().validate(pageElement, "pageElement");
		getWebDriver().switchTo().frame(pageElement.toString());
	}
	
	public void switchToFrameByCSS(PageObject pageElement) {
		ArgumentValidator.create().validate(pageElement, "pageElement");
		WebElement e = webPage.locateElement().findElement(pageElement).getBaseObject();
		getWebDriver().switchTo().frame(e);
	}

	/**
	 * This function will switch to the parent frame after switching to an embedded frame.
	 * This is used after switchToFrame to be able to access the parent page again. After using
	 * this function, the iFrame elements will no longer be accessible. 
	 */
	public void switchFromFrame() {
		getWebDriver().switchTo().defaultContent();
	}

	/**
	 * This function will look through all open windows checking for any window whose title
	 * contains the given String. If multiple windows exist that contain the same partial title,
	 * then the first one found is returned.
	 * @param partialTitle String of the partial title to search.
	 * @throws FrameworkException
	 */
	public void focusWindowByPartialTitle(String partialTitle) {
		ArgumentValidator.create().validate(partialTitle, "partialTitle");
		getBrowser().focusWindowByPartialTitle(partialTitle);
	}

	/**
	 * This function will look through all open windows checking for any window whose title
	 * matches the given String. If multiple windows exist that match the same exact title,
	 * then the first one found is returned.
	 * @param exactTitle String of the exact title to search.
	 * @throws FrameworkException
	 */
	public void focusWindowByExactTitle(String exactTitle) {
		ArgumentValidator.create().validate(exactTitle, "exactTitle");
		getBrowser().focusWindowByExactTitle(exactTitle);
	}
	
	/**
	 * This function will look through all open windows checking for any window whose URL
	 * matches the given String. If multiple windows exist that match the same exact title,
	 * then the first one found is returned.
	 * @param url String of the exact url to search.
	 * @throws FrameworkException
	 */
	public void focusWindowByExactUrl(String url) {
		WebDriverWrapper driver = webPage.getBrowser().getWebDriver();
		for (String handle : driver.getWindowHandles()) {
			driver.switchTo().window(handle);
			if (driver.getCurrentUrl().equals(url))
				break;
		}
	}
	
	/**
	 * This function will look through all open windows checking for any window whose URL
	 * contains the given String. If multiple windows exist that contain the same string,
	 * then the first one found is returned.
	 * @param partialUrl String of the partial URL to search.
	 * @throws FrameworkException
	 */
	public void focusWindowByPartialUrl(String partialUrl) {
		WebDriverWrapper driver = webPage.getBrowser().getWebDriver();
		for (String handle : driver.getWindowHandles()) {
			driver.switchTo().window(handle);
			if (driver.getCurrentUrl().contains(partialUrl)) 
				break;
		}
	}
}
