package com.sseltzer.selenium.framework.selenium.webpage.augmentors;

import java.util.ArrayList;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import com.sseltzer.selenium.framework.error.ErrorManager;
import com.sseltzer.selenium.framework.error.FillDataBuilder;
import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;
import com.sseltzer.selenium.framework.selenium.mapping.PageObject;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPageAugmentor;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPageBase;
import com.sseltzer.selenium.framework.selenium.wrappers.LocatorType;
import com.sseltzer.selenium.framework.selenium.wrappers.SelectType;
import com.sseltzer.selenium.framework.selenium.wrappers.SelectWrapper;
import com.sseltzer.selenium.framework.selenium.wrappers.TimingManager;
import com.sseltzer.selenium.framework.selenium.wrappers.WebElementWrapper;
import com.sseltzer.selenium.framework.strings.maps.PublicErrorStrings;
import com.sseltzer.selenium.framework.verification.junit.JUnitTester;
import com.sseltzer.selenium.framework.verification.validation.ArgumentValidator;

/**
 * The ElementInteractor class is a WebPageAugmentor which provides functions
 * which allow the framework to interact with elements on a page.
 * 
 * @author Sean Seltzer
 * 
 */
public class ElementInteractor extends WebPageAugmentor {

	private JUnitTester tester;

	public ElementInteractor(WebPageBase webPage) {
		super(webPage);
		tester = new JUnitTester(webPage);
	}

	///////////////////////////////////// Clicking /////////////////////////////////

	/**
	 * This function will click an element on the screen. It relies on
	 * findElements for element retrieval and therefore uses the same selector
	 * detection as well as error detection. As a result, this function will
	 * forward the ErrorManager filled string in the event of an error to the
	 * calling test. Note, it is of importance to restate that either an ID or
	 * CSS selector may be used here as the tag, and that the presence of a
	 * special character is the determining factor of whether or not a CSS or ID
	 * call is made.
	 * 
	 * @param pageElement is the tag of the element to be retrieved (ID or CSS). CSS must have a special character in the tag.
	 * @is thrown when the tag cannot be found on the page. This provides 
	 * a filled error string and filtered stack trace.
	 */
	public void click(PageObject pageElement) {
		click(pageElement, 0);
	}

	/**
	 * This function will click an element from a collection. It relies on
	 * findElements for element retrieval and therefore uses the same selector
	 * detection as well as error detection. As a result, this function will
	 * forward the ErrorManager filled string in the event of an error to the
	 * calling test. Note, it is of importance to restate that either an ID or
	 * CSS selector may be used here as the tag, and that the presence of a
	 * special character is the determining factor of whether or not a CSS or ID
	 * call is made. The integer input of this function correlates to the index
	 * of the element in the collection. For example, if the tag has five
	 * children, and i = 4, then it will attempt to click the 5th element. Note
	 * that CSS selectors start counting at 1, where normal programs start at 0.
	 * As this retrieves a *List* of elements, then we must start counting at 0.
	 * Therefore to get the 5th element, i must equal 4; despite that a CSS
	 * nth-child selector would be 5.
	 * 
	 * @param pageElement is the tag of the element to be retrieved (ID or CSS). CSS must have a special character in the tag.
	 * @param i is the index of the child element to click.
	 * @is thrown when the tag cannot be found on the page. This provides 
	 * a filled error string and filtered stack trace.
	 */
	public void click(PageObject pageElement, int i) {
		ArgumentValidator.create().validate(pageElement, "pageElement");
		ArrayList<WebElementWrapper> elements = webPage.locateElement().findElements(pageElement);
		tester.verifyArrayBounds(i, elements, pageElement);
		TimingManager.waitForElementVisibleBy(elements.get(i), TimingManager.DEFAULT_TIMING_OPTIONS);
		try {
			elements.get(i).click();
		} catch (Exception e) {
			ErrorManager.throwAndDump(PublicErrorStrings.ELEMENT_NOT_CLICKABLE, FillDataBuilder.create(pageElement.toString()));
		}
	}
	
	public void pressEnter(PageObject pageElement) {
		pressEnter(pageElement, 0);
	}
	public void pressEnter(PageObject pageElement, int i) {
		ArgumentValidator.create().validate(pageElement, "pageElement");
		ArrayList<WebElementWrapper> elements = webPage.locateElement().findElements(pageElement);
		tester.verifyArrayBounds(i, elements, pageElement);
		TimingManager.waitForElementVisibleBy(elements.get(i), TimingManager.DEFAULT_TIMING_OPTIONS);
		try {
			elements.get(i).sendKeys(Keys.ENTER);
		} catch (Exception e) {
			ErrorManager.throwAndDump(PublicErrorStrings.ELEMENT_NOT_CLICKABLE, FillDataBuilder.create(pageElement.toString()));
		}
	}
	
	public void sendKeys(PageObject pageElement, CharSequence... keys) {
		sendKeys(pageElement, 0, keys);
	}
	public void sendKeys(PageObject pageElement, int i, CharSequence... keys) {
		ArgumentValidator.create().validate(pageElement, "pageElement");
		ArrayList<WebElementWrapper> elements = webPage.locateElement().findElements(pageElement);
		tester.verifyArrayBounds(i, elements, pageElement);
		TimingManager.waitForElementVisibleBy(elements.get(i), TimingManager.DEFAULT_TIMING_OPTIONS);
		try {
			elements.get(i).sendKeys(keys);
		} catch (Exception e) {
			ErrorManager.throwAndDump(PublicErrorStrings.ELEMENT_NOT_CLICKABLE, FillDataBuilder.create(pageElement.toString()));
		}
	}

	
	public void clickByName(PageObject pageElement) {
		clickByName(pageElement, 0);
	}
	public void clickByName(PageObject pageElement, int i) {
		ArgumentValidator.create().validate(pageElement, "pageElement");
		ArrayList<WebElementWrapper> elements = webPage.locateElement().findElements(LocatorType.NAME, pageElement);
		tester.verifyArrayBounds(i, elements, pageElement);
		TimingManager.waitForElementVisibleBy(elements.get(i), TimingManager.DEFAULT_TIMING_OPTIONS);
		try {
			elements.get(i).click();
		} catch (Exception e) {
			ErrorManager.throwAndDump(PublicErrorStrings.ELEMENT_NOT_CLICKABLE, FillDataBuilder.create(pageElement.toString()));
		}
	}

	
	
	///////////////////////////////////// Hovering /////////////////////////////////

	/**
	 * This function will hover over an element on the page. This is used to
	 * cause a CSS selector to show a flyout element to be displayed, such in
	 * the case of the Valpak.com left nav buttons. The same ID/CSS selector
	 * detection rules apply here as it builds on the same underlying
	 * functionality.
	 * 
	 * @param pageElement is the element to be hovered over.
	 * @is thrown if the element does not exist.
	 */
	public void hover(PageObject pageElement) {
		hover(pageElement, 0);
	}

	/**
	 * This function will hover over an element on the page from a collection.
	 * This is used to cause a CSS selector to show a flyout element to be
	 * displayed, such in the case of the Valpak.com left nav buttons. The same
	 * ID/CSS selector detection rules apply here as it builds on the same
	 * underlying functionality.
	 * 
	 * @param pageElement is the collection of elements to be hovered over.
	 * @param i is the index of the element in the pageElement collection.
	 * @is thrown if the element does not exist.
	 */
	public void hover(PageObject pageElement, int i) {
		ArgumentValidator.create().validate(pageElement, "pageElement");
		ArrayList<WebElementWrapper> elements = webPage.locateElement().findElements(pageElement);
		tester.verifyArrayBounds(i, elements, pageElement);
		try {
			Actions action = getWebDriver().getActionBuilder().moveToElement(elements.get(i).getBaseObject());
			action.perform();
			action.release();
		} catch (Exception e) {
			FillDataBuilder fillData = FillDataBuilder.create(pageElement.toString());
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
	}

	///////////////////////////////////// Hover Clicking /////////////////////////////////

	/**
	 * This function is essentially a compound function provided as a
	 * convenience to perform a more complex action. This function uses
	 * hoverElement, waitForElementToLoad, and clickButton to hover over a given
	 * element on the page, wait for an element to be displayed by CSS, then
	 * click an element after it is displayed. This is applicable to numerous
	 * places around the site such as the left nav, or the CSS hidden drop
	 * boxes. Since this function uses a combination of functions, there are
	 * multiple exceptions that may be thrown. If the hoverElement or the
	 * flyoutElement does not exist, an exception will be thrown, or if the
	 * flyoutElement does not appear after a given duration. The same ID/CSS
	 * selector detection rules apply here as it builds on the same underlying
	 * functionality.
	 * 
	 * @param hoverElement is the element to hover over to make flyoutElement visible.
	 * @param flyoutElement is the desired element to be clicked.
	 * @is thrown if either element does not exist or if the flyoutElement is not displayed in time.
	 */
	public void clickHoverButton(PageObject hoverElement, PageObject flyoutElement) {
		clickHoverButton(hoverElement, 0, flyoutElement);
	}

	/**
	 * This function is essentially a compound function provided as a
	 * convenience to perform a more complex action. This function uses
	 * hoverElement, waitForElementToLoad, and clickButton to hover over a given
	 * element on the page, wait for an element to be displayed by CSS, then
	 * click an element after it is displayed. However, the difference with this
	 * function lies in that hoverElement may be a collection. This is used when
	 * there are several elements which all have flyout elements that are
	 * displayed. The index provided refers to the index of the hoverElement to
	 * create the flyout. This is applicable certain instances around the site
	 * such as the left nav, where multiple elements have a flyout. Since this
	 * function uses a combination of functions, there are multiple exceptions
	 * that may be thrown. If the hoverElement or the flyoutElement does not
	 * exist, an exception will be thrown, or if the flyoutElement does not
	 * appear after a given duration. The same ID/CSS selector detection rules
	 * apply here as it builds on the same underlying functionality.
	 * 
	 * @param hoverElement is the element to hover over to make flyoutElement visible.
	 * @param i is the index of the hoverElement to hover over when there is a ollection of elements that have flyouts.
	 * @param flyoutElement is the desired element to be clicked.
	 * @is thrown if either element does not exist or if the flyoutElement is not displayed in time.
	 */
	public void clickHoverButton(PageObject hoverElement, int i, PageObject flyoutElement) {
		hover(hoverElement, i);
		webPage.waitFor().elementPresent(flyoutElement, TimingManager.DEFAULT_TIMING_OPTIONS);
		click(flyoutElement);
	}

	///////////////////////////////////// Element Form Fill /////////////////////////////////
	/**
	 * This function will send the given string to an element on the page. This
	 * is used to form fill input boxes on the page.
	 * 
	 * @param pageElement is the element to send the String to.
	 * @param data is the data to send to the page.
	 * @is thrown if the element does not exist.
	 */
	public void formFill(PageObject pageElement, String data) {
		formFill(pageElement, data, 0);
	}

	public void formFill(PageObject pageElement, String data, int i) {
		ArgumentValidator.create().validate(pageElement, "pageElement");
		ArrayList<WebElementWrapper> elements = webPage.locateElement().findElements(pageElement);
		tester.verifyArrayBounds(i, elements, pageElement);
		try {
			elements.get(i).clear();
			elements.get(i).sendKeys(data);
		} catch (Exception e) {
			ErrorManager.throwAndDump(PublicErrorStrings.FAILED_FORM_FILL, FillDataBuilder.create(pageElement.toString(), data));
		}
	}

	public void clearFormText(PageObject pageElement) {
		clearFormText(pageElement, 0);
	}

	public void clearFormText(PageObject pageElement, int i) {
		ArgumentValidator.create().validate(pageElement, "pageElement");
		ArrayList<WebElementWrapper> elements = webPage.locateElement().findElements(pageElement);
		tester.verifyArrayBounds(i, elements, pageElement);
		try {
			elements.get(i).clear();
		} catch (Exception e) {
			// TODO Handle this
			// ErrorManager.throwAndDump(PublicErrorStrings.FAILED_FORM_CLEAR,
			// FillDataBuilder.create(pageElement.toString(), ""));
		}
	}

	////////////////////////////////////// Select Elements ////////////////////////////////////////

	/**
	 * Select an element from a drop box by index number. Index should be zero
	 * to one minus the size of the select box.
	 * 
	 * @param pageElement
	 * @param index
	 * @throws FrameworkException
	 */
	public void selectElementFromDropDown(PageObject pageElement, int index) {
		ArgumentValidator.create().validate(pageElement, "pageElement");
		SelectWrapper select = new SelectWrapper(getWebDriver(), webPage.locateElement().findElement(pageElement));
		tester.verifyArrayBounds(index, select.getOptions(), pageElement);
		select.selectByIndex(index);
	}

	/**
	 * Select an element from a drop box by display name or value. Display name
	 * is the actual text that is displayed in the box and value is the html
	 * value assigned to a given name.
	 * 
	 * @param pageElement
	 * @param elementText
	 * @param selectType
	 * @throws FrameworkException
	 */
	public void selectElementFromDropDown(PageObject pageElement, String elementText, SelectType selectType) {
		ArgumentValidator.create().validate(pageElement, "pageElement").validate(elementText, "elementText");
		if (!webPage.locateElement().findElement(pageElement).getTagName().equals("select")) {
			// This was throwing an error in the SelectWrapper constructor. Sending in an element other
			// than a <select> is user error, I'd argue. So, throwing an internal on for this.
			ErrorManager.throwAndDumpInternalError(PublicErrorStrings.BAD_TAG_NAME, pageElement.toString());
		}
		SelectWrapper select = new SelectWrapper(getWebDriver(), webPage.locateElement().findElement(pageElement));
		switch (selectType) {
			case VALUE:
				select.selectByValue(elementText);
				break;
			case DISPLAY_TEXT:
				select.selectByVisibleText(elementText);
				break;
		}
	}

	/**
	 * Retrieve an element's text from the DOM.
	 * 
	 * @param pageElement element to retrieve the text from.
	 * @return element text.
	 * @throws FrameworkException
	 */
	public String getElementText(PageObject pageElement) {
		ArgumentValidator.create().validate(pageElement, "pageElement");
		return webPage.locateElement().findElement(pageElement).getText();
	}

	public String getElementText(PageObject pageElement, int i) {
		ArgumentValidator.create().validate(pageElement, "pageElement");
		return webPage.locateElement().findElement(pageElement, i).getText();
	}

	public String getElementAttribute(PageObject pageElement, String attr) {
		ArgumentValidator.create().validate(pageElement, "pageElement").validate(attr, "attr");
		return webPage.locateElement().findElement(pageElement).getAttribute(attr);
	}

	public String getElementAttribute(PageObject pageElement, String attr, int i) {
		ArgumentValidator.create().validate(pageElement, "pageElement").validate(attr, "attr");
		return webPage.locateElement().findElement(pageElement, i).getAttribute(attr);
	}
}
