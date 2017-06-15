package com.sseltzer.selenium.internal.test.tests.selenium.webpage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.sseltzer.selenium.framework.selenium.mapping.PageObject;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPage;
import com.sseltzer.selenium.framework.selenium.wrappers.WebElementWrapper;
import com.sseltzer.selenium.framework.verification.junit.TestScript;
import com.sseltzer.selenium.internal.test.tests.jfiddle.JFiddleTestWebsite;

/**
 * 
 * 
 * Tests that all methods in the ElementLocator perform as expected, throw as
 * expected, and just generally behave well.
 * 
 * This group of test relies on a custom JFiddle page.
 * (http://fiddle.jshell.net/ckiehlunittests/7rU8m/show) While it ads a bit of
 * cruft to the tests having to keep track of additional urls and html
 * structures, it makes up for that limitation (in my mind) by providing a
 * concrete set of invaraiants against which to test (compared to, say,
 * something like running the tests against valpak.com, which is too dynamic in
 * nature to use as a validator against the tests).
 * 
 * For convenience, all JFiddles are JQuery enabled, so the console can be used
 * to validate if a test fails for an unexpected reason.
 * 
 * @author ckiehl Apr 7, 2014
 */
public class ElementLocatorInternal extends TestScript {

	private String JFIDDLE_TEST_URL = "http://jsfiddle.net/ckiehlunittests/7rU8m/show/";

	// <button id="disabledbutton">Disabled Button</button>
	private PageObject diabledButtonID = new PageObject("disabledbutton");

	// <input type="text" value="Enter Text" id="textinput">
	private PageObject formFieldByAttibuteExistence = new PageObject("input[value]");

	// <input type="text" value="Enter Text" id="textinput">
	private PageObject formFieldByAttributeValue = new PageObject("input[value='Enter Text']");

	// <a href="http://www.google.com">google.com</a>
	// <a href="http://www.valpak.com">valpak.com</a>
	// should find None.
	private PageObject visitedLinks = new PageObject("a:visited");

	// <a href="http://www.google.com">google.com</a>
	// <a href="http://www.valpak.com">valpak.com</a>
	// Should find all.
	private PageObject unvisitedLinks = new PageObject("a:link");

	// <a href="http://www.google.com">google.com</a>
	private PageObject attribStarSelector = new PageObject("a[href*='gle.com']");

	// <a href="http://www.google.com">google.com</a>
	private PageObject attribCaretSelector = new PageObject("a[href^=http]");

	// <a href="http://www.google.com">google.com</a>
	private PageObject attribDollarSelector = new PageObject("a[href$='.com']");

	// <a href="http://www.google.com">google.com</a>
	private PageObject attribTildeSelector = new PageObject("[data-name~='jello']");

	private PageObject pseudoAllSelector = new PageObject("*");
	private PageObject pseudoNotSelector = new PageObject("*:not(button)");

	/**
	 * Checks that all css selectors, even the less common ones, behave as
	 * expected.
	 */
	@Test
	public void testFindElementCssSelectorStressTest() {
		JFiddleTestWebsite.setTestUrl(JFIDDLE_TEST_URL);
		WebPage webPage = new JFiddleTestWebsite(getBrowser()).open();

		// by ID
		WebElementWrapper disabledBtn = webPage.locateElement().findElement(diabledButtonID);
		assertEquals(disabledBtn.getText(), "Disabled Button");

		// By Attribute
		WebElementWrapper formField1 = webPage.locateElement().findElement(formFieldByAttibuteExistence);
		assertEquals("input[value]", formField1.getAttribute("value"), "Enter Text");

		// by Attribute Value
		WebElementWrapper formField2 = webPage.locateElement().findElement(formFieldByAttributeValue);
		assertEquals("input[value='Enter Text']", formField2.getAttribute("value"), "Enter Text");

		// By :Visited Pseudo Selector
		List<WebElementWrapper> allVisitedLinks = webPage.locateElement().findElements(visitedLinks);
		assertEquals("a:visited", 0, allVisitedLinks.size());

		// By :Link Pseudo Selector
		List<WebElementWrapper> allunvisitedLinks = webPage.locateElement().findElements(unvisitedLinks);
		assertEquals(":Link", 2, allunvisitedLinks.size());

		// by attribute * selector
		List<WebElementWrapper> anchorsByStarSelector = webPage.locateElement().findElements(attribStarSelector);
		assertEquals("a[href*='gle.com']", 1, anchorsByStarSelector.size());

		// by attribute ^ selector
		List<WebElementWrapper> anchoraByAttribCaretSelector = webPage.locateElement().findElements(attribCaretSelector);
		assertEquals("href^='http']", 2, anchoraByAttribCaretSelector.size());

		// by attribute $ selector
		List<WebElementWrapper> anchoraByAttribDollarSelector = webPage.locateElement().findElements(attribDollarSelector);
		assertEquals("href$='.com']", 2, anchoraByAttribDollarSelector.size());

		// by attribute ~ selector
		WebElementWrapper tagByTildeSelector = webPage.locateElement().findElement(attribTildeSelector);
		assertEquals("[data-name~='jello']", "I AM A HEADER", tagByTildeSelector.getText());

		// filtering by :not() pseudo selector
		List<WebElementWrapper> elementsByStarFilteredByNotSelector = webPage.locateElement().findElements(pseudoNotSelector);
		assertEquals("*:not(button)", 23, elementsByStarFilteredByNotSelector.size());

		List<WebElementWrapper> elementsByStarSelector = webPage.locateElement().findElements(pseudoAllSelector);
		assertEquals("*:not()", 30, elementsByStarSelector.size());

	}

	@Test
	public void testIsElementDisplayed() {
		JFiddleTestWebsite.setTestUrl(JFIDDLE_TEST_URL);
		WebPage webPage = new JFiddleTestWebsite(getBrowser()).open();

		assertFalse(webPage.locateElement().isElementDisplayed(new PageObject("hiddendiv")));
		assertTrue(webPage.locateElement().isElementDisplayed(new PageObject("nonhiddendiv")));
	}

	@Test
	public void testIsElementEnabled() {
		JFiddleTestWebsite.setTestUrl(JFIDDLE_TEST_URL);
		WebPage webPage = new JFiddleTestWebsite(getBrowser()).open();

		assertFalse(webPage.locateElement().isElementEnabled(new PageObject("initiallydisabled")));
		assertTrue(webPage.locateElement().isElementEnabled(new PageObject("enabledbutton")));
	}

	@Test
	public void testIsElementSelected() {
		JFiddleTestWebsite.setTestUrl(JFIDDLE_TEST_URL);
		WebPage webPage = new JFiddleTestWebsite(getBrowser()).open();

		PageObject checkbox = new PageObject("checkboxid");

		assertFalse(webPage.locateElement().isElementSelected(checkbox));
		webPage.interactWithElement().click(checkbox);
		assertTrue(webPage.locateElement().isElementSelected(checkbox));
	}

}
