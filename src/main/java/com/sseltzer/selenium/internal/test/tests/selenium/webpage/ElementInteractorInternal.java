/**
 *
 *
 * ElementInteractorUnittsts.java
 *
 * @author ckiehl Mar 25, 2014
 */
package com.sseltzer.selenium.internal.test.tests.selenium.webpage;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.sseltzer.selenium.framework.data.DataAggregator;
import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;
import com.sseltzer.selenium.framework.selenium.mapping.PageObject;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPage;
import com.sseltzer.selenium.framework.selenium.wrappers.SelectType;
import com.sseltzer.selenium.framework.selenium.wrappers.TimingManager;
import com.sseltzer.selenium.framework.selenium.wrappers.TimingOptions;
import com.sseltzer.selenium.framework.selenium.wrappers.WebElementWrapper;
import com.sseltzer.selenium.framework.verification.junit.JUnitTester;
import com.sseltzer.selenium.framework.verification.junit.TestScript;
import com.sseltzer.selenium.internal.test.tests.jfiddle.JFiddleTestWebsite;

import junit.framework.AssertionFailedError;

/**
 * 
 * ElementInteractor Unit Tests 
 * 
 * Tests that all methods in the ElementInteractor perform as 
 * expected, throw as expected, and just generally behave well. 
 *
 * This group of test relies on a custom JFiddle page. (http://fiddle.jshell.net/ckiehlunittests/7rU8m/show)
 * While it ads a bit of cruft to the tests having to keep track of additional urls and html structures, 
 * it makes up for that limitation (in my mind) by providing a concrete set of 
 * invaraiants against which to test (compared to, say, something like running 
 * the tests against valpak.com, which is too dynamic in nature to use as a 
 * validator against the tests).
 * 
 *  For convenience, all JFiddles are JQuery enabled, so the console can be used to 
 *  validate if a test fails for an unexpected reason. 
 * 
 * @author ckiehl
 *
 */
public class ElementInteractorInternal extends TestScript {

	/*
	 * Target fiddle url and dropdown classname
	 * Relevant HTML: 
	 * <select class="selectbox">
	 * 	<option value="volvo">Volvo</option>
	 *  <option value="saab">Saab</option>
	 *  <option value="mercedes">Mercedes</option>
	 *  <option value="audi">Audi</option>
	 *</select>
	 * 
	 */
	private String DROPDOWN_TEST_URL = "http://jsfiddle.net/ckiehlunittests/7rU8m/show/";
	private PageObject dropDownBox = new PageObject(".selectbox");

	/*
	 * Target fiddle enabled/disabled buttons 
	 * 
	 * Relevant HTML: 
	 * <button id="disabledbutton">Disabled Button</button>
	 * <button id="enabledbutton">Enabled Button</button>
	 * 
	 */
	private PageObject disabledButton = new PageObject("disabledbutton");
	private PageObject enabledButton = new PageObject("enabledbutton");

	/*
	 * Target fiddle url invisible button
	 * 
	 * Relevant HTML: 
	 * <button id="disabledbutton">Disabled Button</button>
	 * <button id="enabledbutton">Enabled Button</button>
	 * 
	 */
	private PageObject buttonThatGoesinvisibleOnClick = new PageObject("buttonthatturnsinvisible");

	/**
	 * Target Text Input Field
	 * 
	 * Relevant HTML:
	 * 
	 * <div>
	 *   <form id="form">
	*     Text Field
	*     <input type="text" value="Enter Text" id="textinput"> 
	*     </input>
	*   </form>
	* </div>
	* 
	 */
	private PageObject formTag = new PageObject("formtag");
	private PageObject formInputField = new PageObject("textinput");

	/**
	 * Target Button hover trigger
	 * 
	 * Relevant HTML: 
	 * 
	 * <div>
	 *   <button id="buttonwithpopup">hover</button>
	 *   <div id="hiddendiv">
	 *     <button id="thehiddenbutton">Revealed Button</button>
	 *   </div>
	 * </div>
	 *
	 * Relevant Javascript (the "success" message is injected into the html)
	 * 
	 * $('#buttonwithpopup').hover(function(e) {
	*   $('#hiddendiv').css("visibility", "visible");
	* });
	* 
	* $('#thehiddenbutton').on('click', function(e) {
	*   $('#hiddendiv').append('<p id="header">success</p>');  
	* });
	 * 
	 */
	private PageObject hoverButton = new PageObject("buttonwithpopup");
	private PageObject buttonShownAfterHover = new PageObject("thehiddenbutton");
	private PageObject hoverButtonClickSuccessMsg = new PageObject("success");

	/**
	 * Target: H1 tag 
	 * 
	 * Relevant Fiddle HTML 
	 * <h1>I AM A HEADER</h1>
	 */
	private PageObject headerElement = new PageObject("header");

	@Override
	public void populateData(DataAggregator data) {
	}

	@Test
	public void testSelectFromDropDownIndexCorrectlyUpdates() {
		//DataAggregator data = DataAggregator.getInstance();
		JFiddleTestWebsite.setTestUrl(DROPDOWN_TEST_URL);
		WebPage webPage = new JFiddleTestWebsite(getBrowser()).open();
		JUnitTester tester = new JUnitTester(webPage);

		webPage.interactWithElement().selectElementFromDropDown(dropDownBox, 0);
		tester.verifyElementTextMatches(new PageObject(".selectbox option:nth-child(1)"), "Volvo");
		webPage.interactWithElement().selectElementFromDropDown(dropDownBox, 1);
		tester.verifyElementTextMatches(new PageObject(".selectbox option:nth-child(2)"), "Saab");
		webPage.interactWithElement().selectElementFromDropDown(dropDownBox, 2);
		tester.verifyElementTextMatches(new PageObject(".selectbox option:nth-child(3)"), "Mercedes");
		webPage.interactWithElement().selectElementFromDropDown(dropDownBox, 3);
	}

	@Test(expected = FrameworkException.class)
	public void testSelectFromDropDownIndexThrowsOutoutBoundsError() {
		//DataAggregator data = DataAggregator.getInstance();
		JFiddleTestWebsite.setTestUrl(DROPDOWN_TEST_URL);
		WebPage webPage = new JFiddleTestWebsite(getBrowser()).open();
		//JUnitTester tester = new JUnitTester(webPage);

		webPage.interactWithElement().selectElementFromDropDown(dropDownBox, 4);
	}

	@Test
	public void testSelectFromDropDownByNameCorrectlyUpdates() {
		//DataAggregator data = DataAggregator.getInstance();
		JFiddleTestWebsite.setTestUrl(DROPDOWN_TEST_URL);
		WebPage webPage = new JFiddleTestWebsite(getBrowser()).open();
		JUnitTester tester = new JUnitTester(webPage);

		webPage.interactWithElement().selectElementFromDropDown(dropDownBox, "Volvo", SelectType.DISPLAY_TEXT);
		webPage.interactWithElement().selectElementFromDropDown(dropDownBox, "volvo", SelectType.VALUE);
		tester.verifyElementTextMatches(new PageObject(".selectbox option:nth-child(1)"), "Volvo");
		webPage.interactWithElement().selectElementFromDropDown(dropDownBox, "Saab", SelectType.DISPLAY_TEXT);
		webPage.interactWithElement().selectElementFromDropDown(dropDownBox, "saab", SelectType.VALUE);
		tester.verifyElementTextMatches(new PageObject(".selectbox option:nth-child(2)"), "Saab");
		webPage.interactWithElement().selectElementFromDropDown(dropDownBox, "Mercedes", SelectType.DISPLAY_TEXT);
		webPage.interactWithElement().selectElementFromDropDown(dropDownBox, "mercedes", SelectType.VALUE);
		tester.verifyElementTextMatches(new PageObject(".selectbox option:nth-child(3)"), "Mercedes");
	}

	@Test(expected = FrameworkException.class)
	public void testSelectFromDropDownByIncorrectNameThrowNotFoundError() {
		//DataAggregator data = DataAggregator.getInstance();
		JFiddleTestWebsite.setTestUrl("http://jsfiddle.net/ckiehlunittests/7rU8m/show/");
		WebPage webPage = new JFiddleTestWebsite(getBrowser()).open();
		//JUnitTester tester = new JUnitTester(webPage);

		String junkOptionData = "MercEdes";
		webPage.interactWithElement().selectElementFromDropDown(dropDownBox, junkOptionData, SelectType.VALUE);
	}

	@Test
	public void testSelectFromNonSelectElementThrowsInternalError() {
		//DataAggregator data = DataAggregator.getInstance();
		JFiddleTestWebsite.setTestUrl("http://jsfiddle.net/ckiehlunittests/7rU8m/show/");
		WebPage webPage = new JFiddleTestWebsite(getBrowser()).open();
		//JUnitTester tester = new JUnitTester(webPage);
		try {
			webPage.interactWithElement().selectElementFromDropDown(enabledButton, "Saab", SelectType.DISPLAY_TEXT);
		} catch (FrameworkException e) {
			if (!e.getMessage().contains("INTERNAL ERROR")) { throw new AssertionFailedError(String.format("Received Incorrect error message. Expected INTERNAL, received %s", e.getMessage())); }
		}
	}

	@Test
	public void testClickCorrectlyTriggersButton() {
		//DataAggregator data = DataAggregator.getInstance();
		JFiddleTestWebsite.setTestUrl(DROPDOWN_TEST_URL);
		WebPage webPage = new JFiddleTestWebsite(getBrowser()).open();
		//JUnitTester tester = new JUnitTester(webPage);

		WebElementWrapper buttonInDisabledState = webPage.locateElement().findElement(disabledButton);
		if (!buttonInDisabledState.isEnabled()) throw new AssertionError("Button should be enabled");
		webPage.interactWithElement().click(enabledButton);
		WebElementWrapper buttonInEnabledState = webPage.locateElement().findElement(disabledButton);
		if (buttonInEnabledState.isEnabled()) throw new AssertionError("Button should be Disabled");
	}

	@Test
	public void testClickThrowsExceptionWhenClickingOnInvisibleElement() {
		//DataAggregator data = DataAggregator.getInstance();
		JFiddleTestWebsite.setTestUrl(DROPDOWN_TEST_URL);
		WebPage webPage = new JFiddleTestWebsite(getBrowser()).open();
		JUnitTester tester = new JUnitTester(webPage);

		// Make sure the button is visible (default), and invisible after click 
		tester.verifyElementVisible(buttonThatGoesinvisibleOnClick);
		webPage.interactWithElement().click(buttonThatGoesinvisibleOnClick);
		tester.verifyElementInvisible(buttonThatGoesinvisibleOnClick);

		TimingManager.setDefaultTimingOptions(new TimingOptions(TimingManager.TimeoutMode.IMMEDIATE, 100, TimingManager.DEFAULT_POLL_TIME_MILLIS));
		try {
			webPage.interactWithElement().click(buttonThatGoesinvisibleOnClick);
		} catch (FrameworkException e) {
			if (!e.getMessage().contains("Timed out after")) { throw new AssertionFailedError("Received incorrect error message"); }
		}
	}

	@Test
	public void testClearMethodRemovesTextFromFormField() {
		//DataAggregator data = DataAggregator.getInstance();
		JFiddleTestWebsite.setTestUrl(DROPDOWN_TEST_URL);
		WebPage webPage = new JFiddleTestWebsite(getBrowser()).open();
		//JUnitTester tester = new JUnitTester(webPage);

		String textCurrentlyInFormField = webPage.locateElement().findElement(formInputField).getAttribute("value");
		if (!textCurrentlyInFormField.equals("Enter Text")) { throw new AssertionFailedError("text required for test condition not present."); }

		webPage.locateElement().findElement(formInputField).clear();
		textCurrentlyInFormField = webPage.locateElement().findElement(formInputField).getAttribute("value");
		if (!textCurrentlyInFormField.equals("")) { throw new AssertionFailedError("Failed to clear input element"); }
	}

	@Test(expected = FrameworkException.class)
	public void testClearOnNonInputElementThrowsException() {
		//DataAggregator data = DataAggregator.getInstance();
		JFiddleTestWebsite.setTestUrl(DROPDOWN_TEST_URL);
		WebPage webPage = new JFiddleTestWebsite(getBrowser()).open();
		//JUnitTester tester = new JUnitTester(webPage);

		webPage.locateElement().findElement(formTag).clear();
	}

	@Test
	public void testFormFillSuccessullyPopulatedInputElement() {
		//DataAggregator data = DataAggregator.getInstance();
		JFiddleTestWebsite.setTestUrl(DROPDOWN_TEST_URL);
		WebPage webPage = new JFiddleTestWebsite(getBrowser()).open();
		//JUnitTester tester = new JUnitTester(webPage);

		String testString = "Jello!";
		webPage.interactWithElement().formFill(formInputField, testString);
		String textCurrentlyInFormField = webPage.locateElement().findElement(formInputField).getAttribute("value");

		if (!textCurrentlyInFormField.equals(testString)) { throw new AssertionFailedError("FormFill method did not fill in field as expected"); }
	}

	@Test(expected = FrameworkException.class)
	public void testFormFillThrowsExceptionOnNonInputElement() {
		//DataAggregator data = DataAggregator.getInstance();
		JFiddleTestWebsite.setTestUrl(DROPDOWN_TEST_URL);
		WebPage webPage = new JFiddleTestWebsite(getBrowser()).open();
		//JUnitTester tester = new JUnitTester(webPage);

		String testString = "Jello!";
		webPage.interactWithElement().formFill(formTag, testString);
		//String textCurrentlyInFormField = webPage.locateElement().findElement(formInputField).getAttribute("value");
	}

	/**
	 * Tests that hover triggers hoverEvents on the test page
	 *  
	 *  As far as I can tell, there's no inverse of this test. 
	 *  Selenium can "hover" over anything. Barring passing in a bad 
	 *  tag, which would be caught by other error handlers, I think(!) this 
	 *  test covers the method sufficiently. 
	 */
	@Test
	public void testHoverButtonSuccessfullyTriggersElements() {
		//DataAggregator data = DataAggregator.getInstance();
		JFiddleTestWebsite.setTestUrl(DROPDOWN_TEST_URL);
		WebPage webPage = new JFiddleTestWebsite(getBrowser()).open();
		JUnitTester tester = new JUnitTester(webPage);

		tester.verifyElementInvisible(buttonShownAfterHover);
		webPage.interactWithElement().hover(hoverButton);
		tester.verifyElementVisible(buttonShownAfterHover);
	}

	@Test
	public void testHoverClickButtonSuccessfullyTriggersAndclicksOnElements() {
		//DataAggregator data = DataAggregator.getInstance();
		JFiddleTestWebsite.setTestUrl(DROPDOWN_TEST_URL);
		WebPage webPage = new JFiddleTestWebsite(getBrowser()).open();
		JUnitTester tester = new JUnitTester(webPage);

		TimingManager.setDefaultTimingOptions(new TimingOptions(TimingManager.TimeoutMode.IMMEDIATE, 100, TimingManager.DEFAULT_POLL_TIME_MILLIS));

		List<WebElementWrapper> elements = webPage.locateElement().findElements(hoverButtonClickSuccessMsg);
		if (elements.size() > 0) { throw new AssertionFailedError("Page state incorrect. Success Message should not be visible"); }
		webPage.interactWithElement().clickHoverButton(hoverButton, buttonShownAfterHover);
		tester.verifyElementVisible(hoverButtonClickSuccessMsg);
	}

	@Test
	public void testgetElementTextReadsCorrectly() {
		//DataAggregator data = DataAggregator.getInstance();
		JFiddleTestWebsite.setTestUrl(DROPDOWN_TEST_URL);
		WebPage webPage = new JFiddleTestWebsite(getBrowser()).open();
		//JUnitTester tester = new JUnitTester(webPage);

		String pageText = webPage.interactWithElement().getElementText(headerElement);
		String expectedText = "I AM A HEADER";
		if (!pageText.equals(expectedText)) { throw new AssertionFailedError(String.format("Text did not match. Expected %s. Received: %s", expectedText, pageText)); }
	}

	/**
	 * This should be handled
	 * @throws FrameworkException
	 */
	@Ignore
	@Test
	public void testgetElementTextReturnsIncorrectDataWhenReadingFromInputElement() {
		//DataAggregator data = DataAggregator.getInstance();
		JFiddleTestWebsite.setTestUrl(DROPDOWN_TEST_URL);
		WebPage webPage = new JFiddleTestWebsite(getBrowser()).open();
		//JUnitTester tester = new JUnitTester(webPage);

		String pageText = webPage.interactWithElement().getElementText(formInputField);
		String expectedText = "Enter Text";
		if (!pageText.equals(expectedText)) { throw new AssertionFailedError(String.format("Text did not match. Expected %s. Received: %s", expectedText, pageText)); }
	}
}
