package com.sseltzer.selenium.framework.verification.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import com.sseltzer.selenium.framework.error.ErrorManager;
import com.sseltzer.selenium.framework.error.FillDataBuilder;
import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;
import com.sseltzer.selenium.framework.selenium.mapping.PageObject;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPageBase;
import com.sseltzer.selenium.framework.selenium.wrappers.LocatorType;
import com.sseltzer.selenium.framework.selenium.wrappers.TimingManager;
import com.sseltzer.selenium.framework.selenium.wrappers.TimingOptions;
import com.sseltzer.selenium.framework.selenium.wrappers.WebElementWrapper;
import com.sseltzer.selenium.framework.strings.maps.InternalErrorStrings;
import com.sseltzer.selenium.framework.strings.maps.PublicErrorStrings;
import com.sseltzer.selenium.framework.utility.sort.SortValidator;
import com.sseltzer.selenium.framework.utility.strings.StringNormalizer;
import com.sseltzer.selenium.framework.verification.validation.ArgumentValidator;

/**
 * The JUnitTester object is a utility object to make testing simpler. It contains useful common 
 * tests that are performed repeatedly, and can simplify the testing process. This object also 
 * utilizes the ErrorManager error platform and will use the ErrorManager form fill strings to 
 * provide useful error information with filtered stack traces to allow for the easy identification 
 * of error sources. There are several purposes to this object and it should be used wherever possible.
 * <br><br> 
 * The JUnitTester object:
 * <br>
 * <ul>
 * <li> Provides the ability to quickly perform common tests.
 * <li> Provides the built in usage of improved error management. 
 * <li> Provides overloads to use String tags, as well as PageObjects for simplicity.
 * <li> Allows collections of WebElement objects to be managed more easily.
 * <li> Allows tests to be shorter and more readable.
 * </ul>
 * @author Sean Seltzer
 */
public class JUnitTester {

	private WebPageBase webPage;
	private static final String DATA_TAG_PREFIX = "[data-page=\"";
	private static final String DATA_TAG_SUFFIX = "\"]";

	/**
	 * This constructor will retain the WebPage object for use with the tests. As the WebPage
	 * is interacted with, and links are clicked, the same instance may still be used at any time
	 * to validate the page. In essence, as the WebPage moves, this will move with it.
	 * @param webPage the WebPage instance to test against.
	 */
	public JUnitTester(WebPageBase webPage) {
		this.webPage = webPage;
	}

	public WebPageBase getWebPage() {
		return webPage;
	}

	//////////////////////////// Page Integrity ////////////////////////////////////////////
	/**
	 * Validates that the pageElement provided contains a string that exactly matches the
	 * body attribute on the page.
	 * <br><br>
	 * E.g. On the Valpak.com Welcome page:
	 * <br>
	 * <pre>{@literal
	 * <body data-page="welcome">
	 * }</pre>
	 * @param dataPageTag is a mapping to the value of the data-page attribute.
	 * @throws FrameworkException 
	 */
	public void verifyDataPage(PageObject dataPageTag) {
		ArgumentValidator.create().validate(dataPageTag, "dataPageTag");
		verifyDataPage(dataPageTag.toString());
	}

	/**
	 * Validates that the pageElement provided contains a string that exactly matches the
	 * body attribute on the page.
	 * <br><br>
	 * E.g. On the Valpak.com Welcome page:
	 * <br>
	 * <pre>{@literal
	 * <body data-page="welcome">
	 * }</pre>
	 * @param dataPageTag is the string value of the data-page attribute. 
	 * @throws FrameworkException 
	 */
	public void verifyDataPage(String dataPageTag) {
		ArgumentValidator.create().validate(dataPageTag, "dataPageTag");
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.DATA_PAGE, dataPageTag);
		PageObject pageObject = new PageObject(DATA_TAG_PREFIX + dataPageTag + DATA_TAG_SUFFIX);
		assertTrue(assertStr, webPage.locateElement().findElement(LocatorType.CSS, pageObject).getTagName().equals("body"));
	}

	/**
	 * This function will validate whether or not the given string matches exactly the entire
	 * value of the title tag.
	 * <br><br>
	 * E.g. �Valpak Coupons, Free Printable Coupons, Coupon Codes, Coupons by Mail�.
	 * @param titleText must match the title exactly.
	 * @throws FrameworkException 
	 */
	public void verifyPageTitleMatches(String titleText) {
		ArgumentValidator.create().validate(titleText, "titleText");
		FillDataBuilder fillData = FillDataBuilder.create(titleText, webPage.interactWithFrame().getPageTitle());
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.TITLE_MATCHES, fillData);
		assertTrue(assertStr, webPage.interactWithFrame().getPageTitle().equals(titleText));
	}

	/**
	 * This function will validate whether or not the given string is contained within the title.
	 * <br><br>
	 * E.g. �Valpak�.
	 * @param titleText must be contained within the title.
	 * @throws FrameworkException
	 */
	public void verifyPageTitleContains(String titleText) {
		ArgumentValidator.create().validate(titleText, "titleText");
		FillDataBuilder fillData = FillDataBuilder.create(titleText, webPage.interactWithFrame().getPageTitle());
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.TITLE_CONTAINS, fillData);
		assertTrue(assertStr, webPage.interactWithFrame().getPageTitle().contains(titleText));
	}

	/**
	 * This function will verify that the string provided is contained within the URL. Any length 
	 * of string may be used, thus it can be used to validate the base URL: 
	 * <br>
	 * e.g. �/coupons/stores/anytimecostumes?vpref=PrintInsert-AnytimeCostumes2013�
	 * <br><br>
	 * Alternately this may be used to validate URL variables:
	 * <br>
	 * e.g. �contentzoneid=73�
	 * <br><br>
	 * If the title must be matched exactly to validate the page and the URL variables, 
	 * verifyPageURLMatches should be used.
	 * @param expectedURL must match the URL exactly.
	 * @throws FrameworkException 
	 */
	public void verifyPageURLMatches(String expectedURL) {
		ArgumentValidator.create().validate(expectedURL, "expectedURL");
		String actualURL = webPage.getBrowser().getCurrentUrl();
		FillDataBuilder fillData = FillDataBuilder.create(expectedURL, actualURL);
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.URL_MATCHES, fillData);
		assertTrue(assertStr, expectedURL.equals(actualURL));
	}

	/**
	 * This function will verify that the string provided is contained within the URL. Any length 
	 * of string may be used, thus it can be used to validate the base URL:
	 * <br>
	 * e.g. �/coupons/stores/anytimecostumes?vpref=PrintInsert-AnytimeCostumes2013�
	 * <br><br>
	 * Alternately this may be used to validate URL variables:
	 * <br>
	 * e.g. �contentzoneid=73�
	 * <br><br>
	 * If the title must be matched exactly to validate the page and the URL variables, 
	 * verifyPageURLMatches should be used.
	 * @param expectedString must be contained within the URL.
	 * @throws FrameworkException 
	 */
	public void verifyPageURLContains(String expectedString) {
		ArgumentValidator.create().validate(expectedString, "expectedString");
		String url = webPage.getBrowser().getCurrentUrl();
		FillDataBuilder fillData = FillDataBuilder.create(url, expectedString);
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.URL_CONTAINS, fillData);
		assertTrue(assertStr, url.toLowerCase().contains(expectedString.toLowerCase()));
	}

	/**
	 * This function will verify that the page URL does not contain the given String.
	 * @param illegalString is the string that must not be contained within the URL.
	 * @throws FrameworkException 
	 */
	public void verifyPageURLNotContains(String illegalString) {
		ArgumentValidator.create().validate(illegalString, "illegalString");
		String url = webPage.getBrowser().getCurrentUrl();
		FillDataBuilder fillData = FillDataBuilder.create(webPage.getBrowser().getCurrentUrl(), illegalString);
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.URL_NOT_CONTAINS, fillData);
		assertTrue(assertStr, !url.contains(illegalString));
	}

	//////////////////////////////// Element Existence ///////////////////////////////////////
	/**
	 * This function will identify the containing object by a string. This can be either the id string, 
	 * �geobtn� or by a unique CSS identifier �.locsearchbtn�. This function will auto detect whether or 
	 * not you are giving it an ID or CSS tag based on the existence of a �.� in the string, therefore 
	 * CSS tags must contain at least one. This may also retrieve a collection, so the only requirement is 
	 * that there is at least one object that exists by that identifier. If none are found, an exception 
	 * is thrown which should fail the test.
	 * <br>
	 * <pre>{@literal
	 * <input id="geobtn" class="locsearchbtn" type="submit" value="Find Local Savings"
	 * }</pre>
	 * @param pageElement the object(s) to locate.
	 * @throws FrameworkException is thrown if the object cannot be found.
	 */
	public void verifyElementsExist(PageObject pageElement) {
		verifyElementsExist(pageElement, TimingManager.DEFAULT_TIMING_OPTIONS);
	}
	public void verifyElementsExist(PageObject pageElement, TimingOptions timingOptions) {
		ArrayList<WebElementWrapper> elements = webPage.locateElement().findElements(pageElement, timingOptions);
		if (elements == null || elements.size() <= 0) ErrorManager.throwAndDump(PublicErrorStrings.ELEMENT_NOT_EXIST, pageElement);
	}
	public void verifyElementsExist(PageObject pageElement, String errorMessage) {
		verifyElementsExist(pageElement, TimingManager.DEFAULT_TIMING_OPTIONS, errorMessage);
	}
	public void verifyElementsExist(PageObject pageElement, TimingOptions timingOptions, String errorMessage) {
		ArrayList<WebElementWrapper> elements = webPage.locateElement().findElements(pageElement, timingOptions);
		if (elements == null || elements.size() <= 0) {
			ErrorManager.throwAndDump(PublicErrorStrings.CUSTOM_MESSAGE, FillDataBuilder.create(errorMessage));
		}
	}

	/**
	 * This function will identify the containing object by a string. This can be either the id string, 
	 * �geobtn� or by a unique CSS identifier �.locsearchbtn�. This function will auto detect whether or 
	 * not you are giving it an ID or CSS tag based on the existence of a �.� in the string, therefore 
	 * CSS tags must contain at least one. This may also retrieve a collection, so the requirement here 
	 * is that the size of the collection must match the int count. So if you want to validate that there
	 * are 10 buttons on the screen (for example the category buttons on the results page), then the 
	 * count should be 10. If the count does not match exactly, an exception is thrown which should 
	 * fail the test.
	 * <br>
	 * <pre>{@literal 
	 * <li class="ui-state-default">
	 * <a title="Restaurants" onclick="s_objectID="http://www.valpak.com/coupons/savings/Restaurants/Largo/FL_1";
	 * return this.s_oc?this.s_oc(e):true" href="http://www.valpak.com/coupons/savings/Restaurants/Largo/FL">
	 * Restaurants</a>
	 * </li>
	 * <li class="ui-state-default">
	 * <li class="ui-state-default">
	 * }</pre>
	 * @param pageElement the object(s) to locate.
	 * @param count the number of objects the tag should locate.
	 * @throws FrameworkException is thrown if the number of objects found does not equal the count.
	 */
	public void verifyElementsExist(PageObject pageElement, int count) {
		ArrayList<WebElementWrapper> elements = webPage.locateElement().findElements(pageElement);
		if (elements.size() != count) ErrorManager.throwAndDump(PublicErrorStrings.ELEMENT_NOT_EXIST, pageElement);
	}
	
	/**
	 * This function is essentially a challenge test that will throw an exception if an element exists 
	 * on the page. This should be used in instances to ensure that something does not exist on the page 
	 * when it shouldn�t. E.g. After leaving the Valpak.com welcome page, the �.landingcontent� should no 
	 * longer exist on the page.
	 * @param pageElement the object(s) to locate.
	 * @throws FrameworkException is thrown if the object exists and it shouldn't.
	 */
	public void verifyNoElementsExist(PageObject pageElement) {
		verifyNoElementsExist(pageElement, TimingManager.DEFAULT_TIMING_OPTIONS);
	}
	public void verifyNoElementsExist(PageObject pageElement, String errorMessage) {
		verifyNoElementsExist(pageElement, TimingManager.DEFAULT_TIMING_OPTIONS, errorMessage);
	}
	public void verifyNoElementsExist(PageObject pageElement, TimingOptions timingOptions) {
		ArrayList<WebElementWrapper> elements = webPage.locateElement().findElements(pageElement, timingOptions);
		if (elements.size() == 0) return;
		ErrorManager.throwAndDump(PublicErrorStrings.ELEMENTS_EXIST, pageElement);
	}

	public void verifyNoElementsExist(PageObject pageElement, TimingOptions timingOptions, String errorMessage) {
		ArrayList<WebElementWrapper> elements = webPage.locateElement().findElements(pageElement, timingOptions);
		if (elements.size() == 0) return;
		ErrorManager.throwAndDump(PublicErrorStrings.CUSTOM_MESSAGE, FillDataBuilder.create(errorMessage));
	}
	
	////////////////////////////// Element Attributes ///////////////////////////////////////
	/**
	 * This function determines whether or not the given element is displayed on the screen. 
	 * This does not do an existence test; it merely does a visibility test. Use verifyElementsExist 
	 * for an existence test.
	 * @param pageElement the object(s) to test visibility.
	 * @throws FrameworkException is thrown is the object is not visible or element does not exist.
	 */
	public void verifyElementVisible(PageObject pageElement) {
		verifyElementVisible(pageElement, 0, TimingManager.DEFAULT_TIMING_OPTIONS);
	}
	
	public void verifyElementVisible(PageObject pageElement, TimingOptions timingOptions) {
		verifyElementVisible(pageElement, 0, timingOptions);
	}
	
	public void verifyElementVisible(PageObject pageElement, int i) {
		verifyElementVisible(pageElement, 0, TimingManager.DEFAULT_TIMING_OPTIONS);
	}
	public void verifyElementVisible(PageObject pageElement, int i, String errorString) {
		verifyElementVisible(pageElement, 0, TimingManager.DEFAULT_TIMING_OPTIONS, errorString);
	}
	
	public void verifyElementVisible(PageObject pageElement, int i, TimingOptions timingOptions, String errorString) {
		String assertStr = errorString + PublicErrorStrings.ELEMENT_NOT_VISIBLE.toString() + "\n" + pageElement.toString();
		assertTrue(assertStr, webPage.locateElement().findElements(pageElement, timingOptions).get(i).isDisplayed());
	}
	
	public void verifyElementVisible(PageObject pageElement, int i, TimingOptions timingOptions) {
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.ELEMENT_NOT_VISIBLE, pageElement.toString());
		assertTrue(assertStr, webPage.locateElement().findElements(pageElement, timingOptions).get(i).isDisplayed());
	}

	public void verifyElementInvisible(PageObject pageElement) {
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.ELEMENT_VISIBLE, pageElement.toString());
		assertTrue(assertStr, !webPage.locateElement().isElementDisplayed(pageElement));
	}

	public void verifyElementOnScreen(PageObject pageElement) {
		assertTrue("Element not on screen, expected on.", isOnScreen(pageElement));
	}

	public void verifyElementOffScreen(PageObject pageElement) {
		assertTrue("Element on screen, expected off.", !isOnScreen(pageElement));
	}

	private boolean isOnScreen(PageObject pageElement) {
		ArgumentValidator.create().validate(pageElement, "pageElement");
		Point location = webPage.locateElement().getElementLocation(pageElement);
		if (location.getX() < 0) return false;
		if (location.getY() < 0) return false;
		Dimension dim = webPage.interactWithFrame().getWindowDimensions();
		if (location.getX() > dim.getWidth()) return false;
		if (location.getY() > dim.getHeight()) return false;
		return true;
	}

	/**
	 * This function will retrieve an object based on a tag, similar to verifyElementsExist, except 
	 * that it will compare the object�s CSS to the provided tag. This is useful when retrieving a 
	 * list of objects such as the 10 category buttons on the results page, then comparing each one
	 * to a tag to determine which one is selected, or if one or more are disabled. In this example, 
	 * all of them will have the CSS �ui-state-default�, but may or may not have the additional 
	 * �ui-state-disabled� selector.
	 * <br><br>
	 * It is important to note here that when doing this comparison, the function will strip the �.� 
	 * from each CSS before the comparison. This is done because valid HTML does not require it, even
	 * if we used a �.� to find the element to begin with. This eliminates the conditions where pages 
	 * might be inconsistent between each other, one using it and the other not.
	 * @param pageElement is the element to verify the presence of the CSS. 
	 * @param cssTag is the object containing the CSS to check for.
	 * @throws FrameworkException is thrown if the element does not exist or if the object does not contain the CSS.
	 */
	public void verifyElementHasCSS(PageObject pageElement, PageObject cssTag) {
		verifyElementHasCSS(pageElement, 0, cssTag);
	}
	
	public void verifyElementHasCSS(PageObject pageElement, int i, PageObject cssTag) {
		ArgumentValidator.create().validate(cssTag, "cssTag");
		FillDataBuilder fillData = FillDataBuilder.create(pageElement, cssTag);
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.CSS_CONTAINS, fillData);
		String tagCSS = StringNormalizer.css(webPage.locateElement().findElement(pageElement, i).getAttribute("class"));
		String normalizedCSS = StringNormalizer.css(cssTag.toString());
		assertTrue(assertStr, tagCSS.contains(normalizedCSS));
	}

	public void verifyElementNotHasCSS(PageObject pageElement, PageObject cssTag) {
		ArgumentValidator.create().validate(cssTag, "cssTag");
		FillDataBuilder fillData = FillDataBuilder.create(pageElement, cssTag);
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.CSS_CONTAINS, fillData);
		String tagCSS = StringNormalizer.css(webPage.locateElement().findElement(pageElement).getAttribute("class"));
		String normalizedCSS = StringNormalizer.css(cssTag.toString());
		assertTrue(assertStr, !tagCSS.contains(normalizedCSS));
	}

	/**
	 * This function will retrieve the text from an object based on a tag and will compare the input text 
	 * to the text in the object. The text must match exactly the object text. E.g. the text �Restaurants� 
	 * in the above example will match exactly.
	 * <br>
	 * <pre>{@literal
	 * <a title="Restaurants" onclick="s_objectID="http://www.valpak.com/coupons/savings/Restaurants/Largo/FL_1";
	 * return this.s_oc?this.s_oc(e):true" href="http://www.valpak.com/coupons/savings/Restaurants/Largo/FL">
	 * Restaurants</a>
	 * }</pre>
	 * @param pageElement is the element to verify the textStr matches. 
	 * @param textStr is the String of text to match in the pageElement.
	 * @throws FrameworkException is thrown if the element does not exist or the text does not match.
	 */
	public void verifyElementTextMatches(PageObject pageElement, String textStr) {
		verifyElementTextMatches(pageElement, textStr, 0);
	}

	/**
	 * This function will retrieve the text from an object in a collection at index i based on a tag and will 
	 * compare the input text to the text in the object. The text must match exactly the object text. E.g. the 
	 * text �Restaurants� in the example will match exactly.
	 * <br>
	 * <pre>{@literal
	 * <li class="ui-state-default ui-tabs-selected">
	 * <li class="ui-state-default">
	 * <a title="Restaurants" onclick="s_objectID=
	 * "http://www.valpak.com/coupons/savings/Restaurants/Largo/FL_1";return this.s_oc?this.s_oc(e):true" 
	 * href="http://www.valpak.com/coupons/savings/Restaurants/Largo/FL">Restaurants</a> 
	 * </li> 
	 * <li class="ui-state-default">
	 * }</pre>
	 * @param pageElement is the element to verify the textStr matches. 
	 * @param textStr is the String of text to match in the pageElement.
	 * @param i is the index of the element in the collection.
	 * @throws FrameworkException is thrown if the element does not exist or the text does not match.
	 */
	public void verifyElementTextMatches(PageObject pageElement, String textStr, int i) {
		ArgumentValidator.create().validate(textStr, "textStr");
		ArrayList<WebElementWrapper> elements = webPage.locateElement().findElements(pageElement);
		verifyArrayBounds(i, elements, pageElement);
		String actualStr = elements.get(i).getText().trim();
		FillDataBuilder fillData = FillDataBuilder.create(pageElement.toString(), textStr, actualStr);
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.TEXT_MATCHES, fillData);
		assertTrue(assertStr, textStr.equals(actualStr));
	}

	/**
	 * This function will retrieve the text from and object based on a tag and will compare the input text to 
	 * the text in the object. The text must be contained within the object text. E.g. the text �taur� in the 
	 * above example will be contained within the object text.
	 * <br>
	 * <pre>{@literal
	 * <a title="Restaurants" onclick="s_objectID=
	 * "http://www.valpak.com/coupons/savings/Restaurants/Largo/FL_1";return this.s_oc?this.s_oc(e):true" 
	 * href="http://www.valpak.com/coupons/savings/Restaurants/Largo/FL">Restaurants</a>
	 * }</pre>
	 * @param pageElement is the element to verify the textStr is contained within.
	 * @param textStr is the String of text to match in the pageElement.
	 * @throws FrameworkException is thrown if the element does not exist or the text does not match.
	 */
	public void verifyElementTextContains(PageObject pageElement, String textStr) {
		verifyElementTextContains(pageElement, textStr, 0);
	}

	/**
	 * This function will retrieve the text from an object in a collection at index i based on a tag and will 
	 * compare the input text to the text in the object. The text must be contained within the object text. 
	 * E.g. the text �taur� in the above example will be contained within the object text.
	 * mwood: made case insensitive
	 * <br>
	 * <pre>{@literal
	 * <li class="ui-state-default ui-tabs-selected">
	 * <li class="ui-state-default">
	 * <a title="Restaurants" onclick="s_objectID=
	 * "http://www.valpak.com/coupons/savings/Restaurants/Largo/FL_1";return this.s_oc?this.s_oc(e):true" 
	 * href="http://www.valpak.com/coupons/savings/Restaurants/Largo/FL">Restaurants</a>
	 * </li>
	 * <li class="ui-state-default">
	 * }</pre>
	 * @param pageElement is the element to verify the textStr is contained within.
	 * @param textStr is the String of text to match in the pageElement.
	 * @param i is the index of the element in the collection.
	 * @throws FrameworkException is thrown if the element does not exist or the text does not match.
	 */
	public void verifyElementTextContains(PageObject pageElement, String textStr, int i) {
		ArgumentValidator.create().validate(textStr, "textStr");
		FillDataBuilder fillData = FillDataBuilder.create(pageElement.toString(), textStr);
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.TEXT_CONTAINS, fillData);
		ArrayList<WebElementWrapper> elements = webPage.locateElement().findElements(pageElement);
		verifyArrayBounds(i, elements, pageElement);
		assertTrue(assertStr, elements.get(i).getText().toLowerCase().contains(textStr.toLowerCase()));
	}

	public void verifyElementTextNotContains(PageObject pageElement, String textStr) {
		verifyElementTextNotContains(pageElement, textStr, 0);
	}

	public void verifyElementTextNotContains(PageObject pageElement, String textStr, int i) {
		ArgumentValidator.create().validate(textStr, "textStr");
		FillDataBuilder fillData = FillDataBuilder.create(pageElement.toString(), textStr);
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.TEXT_CONTAINS, fillData);
		ArrayList<WebElementWrapper> elements = webPage.locateElement().findElements(pageElement);
		verifyArrayBounds(i, elements, pageElement);
		assertTrue(assertStr, !elements.get(i).getText().toLowerCase().contains(textStr.toLowerCase()));
	}

	/**
	 * This function will compare whether or not the elementText has at least one match against  
	 * any of the testStrings. This is used primarily for dealing with objects which have multiple states 
	 * which cannot be known ahead of time. A good example of this is the distance display on the coupon slugs 
	 * (Valpak.com v8.5). They can exist in one of two states: X miles away, or "serving you area." This function 
	 * gives abilty required to check for both. 
	 * 
	 * <pre> 
	 * 1.  \\ String that we would expect to see in the display
	 * 2.  List<String> expectedTextStrings = Arrays.asList("away", "serving");
	 * 3.  \\ Grab the text that's actually displaying
	 * 4.  distanceDisplayText = webPage.interactWithElement().getElementText(MyMap.COUNPON_DISTANCE, i);
	 * 5.  \\ assert that it contains the expected elements
	 * 6.  tester.verifyElementTextContainsAtLeastOneOf(distanceDisplayText, expectedTextStrings);
	 * </pre>
	 *  
	 * @param elementText: The text output from a pageElement
	 * @param testStrings: A list of string to test against
	 * @throws FrameworkException 
	 */
	public void verifyElementTextContainsAtLeastOneOf(String elementText, List<String> testStrings) {
		ArgumentValidator.create().validate(elementText, "elementText").validate(testStrings, "testStrings");
		// Collects all the strings into one list
		List<String> allStrings = new ArrayList<String>(Arrays.asList(elementText));
		allStrings.addAll(testStrings);
		// Fill the databuilder
		FillDataBuilder fillData = FillDataBuilder.create(testStrings.toArray(new String[testStrings.size()]));
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.TEXT_CONTAINS_ONE_OF, fillData);
		assertTrue(assertStr, ContainsAtLeastOneOf(elementText, testStrings));
	}

	/**
	 * Tests that the elementText contains at least one of the supplied testStrings
	 * @param elementText
	 * @param testStrings
	 * @return boolean  
	 */
	private boolean ContainsAtLeastOneOf(String elementText, List<String> testStrings) {
		elementText = elementText.toLowerCase();
		for (String s : testStrings) if (elementText.contains(s.toLowerCase())) return true;
		return false;
	}

	/**
	 * This function will validate that the given element does not have text. For example, this is useful for 
	 * checking if an error is not displayed.
	 * <br><br>
	 * <pre>{@literal
	 * <div id="keywordsError" class="error"></div>
	 * }</pre>
	 * @param pageElement is the element to verify the text is blank.
	 * @throws FrameworkException is thrown if the element does not exist or the text is not blank.
	 */
	public void verifyElementTextBlank(PageObject pageElement) {
		FillDataBuilder fillData = FillDataBuilder.create(pageElement.toString());
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.TEXT_BLANK, fillData);
		// findElement throws if the element does not exist, so it always should.
		assertTrue(assertStr, StringUtils.isEmpty(webPage.locateElement().findElement(pageElement).getText()));
	}
	
	public void verifyElementTextNotBlank(PageObject pageElement) {
		verifyElementTextNotBlank(pageElement, 0);
	}
	
	public void verifyElementTextNotBlank(PageObject pageElement, int i) {
		FillDataBuilder fillData = FillDataBuilder.create(pageElement.toString());
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.TEXT_BLANK, fillData);
		// findElement throws if the element does not exist, so it always should.
		assertFalse(assertStr, StringUtils.isEmpty(webPage.locateElement().findElements(pageElement).get(0).getText()));
	}

	/**
	 * This function will verify that the given text string matches given attribute in an object. For example, 
	 * a pageElement object can be passed in referencing an object, then an attribute name provided such as 
	 * �data-url� then the string that the attribute is expected to match. This can be used to test any attribute.
	 * <br><br>
	 * <pre>{@literal
	 * <a class="offer-cta ir" 
	 * onclick="s_objectID="http://www.valpak.com/coupons/stores/eyebuydirect?pageType=popup_1";
	 * return this.s_oc?this.s_oc(e):true" data-options="{"newWindow": true, "winTitle": 
	 * "Coupon Code | 15% off a pair of glasses."}" data-url="http://www.savings.com/mpclick?placementid=19494065
	 * &url=http%3A%2F%2Fwww.savings.com%2Fmpofferref%3Fofferid%3D6305542&vprefid=999999999&contentzoneid=125" 
	 * href="/coupons/stores/eyebuydirect?pageType=popup">
	 * }</pre>
	 * @param pageElement is the element to verify the specified attribute contains the expected value.
	 * @param attrStr is the attribute name in the element.
	 * @param expectedValue is the expected value of the attribute.
	 * @throws FrameworkException is thrown if the element does not exist or the attribute does not match the text.
	 */
	public void verifyElementAttributeMatches(PageObject pageElement, String attrStr, String expectedValue) {
		ArgumentValidator.create().validate(attrStr, "attrStr").validate(expectedValue, "expectedValue");
		String actualValue = webPage.locateElement().findElement(pageElement).getAttribute(attrStr);
		FillDataBuilder fillData = FillDataBuilder.create(pageElement.toString(), attrStr);
		verifyAttributeStrNotNull(fillData, actualValue);
		fillData.add(expectedValue).add(actualValue);
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.ELEMENT_ATTR_MISMATCH, fillData);
		assertEquals(assertStr, expectedValue, actualValue);
	}

	/**
	 * This function will verify that the given text string matches given attribute in an object. For example, 
	 * a pageElement object can be passed in referencing an object, then an attribute name provided such as 
	 * �data-url� then the string that the attribute is expected to contain. This can be used to test any attribute.
	 * <br><br>
	 * <pre>{@literal
	 * <a class="offer-cta ir" 
	 * onclick="s_objectID="http://www.valpak.com/coupons/stores/eyebuydirect?pageType=popup_1";
	 * return this.s_oc?this.s_oc(e):true" data-options="{"newWindow": true, "winTitle": 
	 * "Coupon Code | 15% off a pair of glasses."}" data-url="http://www.savings.com/mpclick?placementid=19494065
	 * &url=http%3A%2F%2Fwww.savings.com%2Fmpofferref%3Fofferid%3D6305542&vprefid=999999999&contentzoneid=125" 
	 * href="/coupons/stores/eyebuydirect?pageType=popup">
	 * }</pre>
	 * @param pageElement is the element to verify the specified attribute contains the expected value.
	 * @param attrStr is the attribute name in the element.
	 * @param expectedValue is the expected value of the attribute.
	 * @throws FrameworkException is thrown if the element does not exist or the attribute does not contain the text.
	 */
	public void verifyElementAttributeContains(PageObject pageElement, String attrStr, String expectedValue) {
		ArgumentValidator.create().validate(attrStr, "attrStr").validate(expectedValue, "expectedValue");
		String actualValue = webPage.locateElement().findElement(pageElement).getAttribute(attrStr);
		FillDataBuilder fillData = FillDataBuilder.create(pageElement.toString(), attrStr);
		verifyAttributeStrNotNull(fillData, actualValue);
		fillData.add(expectedValue).add(actualValue);
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.ELEMENT_ATTR_MISMATCH, fillData);
		assertTrue(assertStr, actualValue.contains(expectedValue));
	}
	
	public void verifyElementAttributeContains(PageObject pageElement, String attrStr, String expectedValue, int i) {
		ArgumentValidator.create().validate(attrStr, "attrStr").validate(expectedValue, "expectedValue");
		String actualValue = webPage.locateElement().findElements(pageElement).get(i).getAttribute(attrStr);
		FillDataBuilder fillData = FillDataBuilder.create(pageElement.toString(), attrStr);
		verifyAttributeStrNotNull(fillData, actualValue);
		fillData.add(expectedValue).add(actualValue);
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.ELEMENT_ATTR_MISMATCH, fillData);
		assertTrue(assertStr, actualValue.contains(expectedValue));
	}

	public void verifyElementAttributeNotBlank(PageObject pageElement, String attrStr) {
		ArgumentValidator.create().validate(attrStr, "attrStr");
		String actualValue = webPage.locateElement().findElement(pageElement).getAttribute(attrStr);
		FillDataBuilder fillData = FillDataBuilder.create(pageElement.toString(), attrStr);
		verifyAttributeStrNotNull(fillData, actualValue);
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.ATTRIBUTE_NOT_BLANK, fillData);
		assertTrue(assertStr, !actualValue.isEmpty());
	}

	/**
	 * This function will verify that the given text string does not exist in the attribute of an object. For example, 
	 * a pageElement object can be passed in referencing an object, then an attribute name provided such as 
	 * �data-url� then the string that the attribute is expected to lack. This can be used to test any attribute.
	 * <br><br>
	 * <pre>{@literal
	 * <a class="offer-cta ir" 
	 * onclick="s_objectID="http://www.valpak.com/coupons/stores/eyebuydirect?pageType=popup_1";
	 * return this.s_oc?this.s_oc(e):true" data-options="{"newWindow": true, "winTitle": 
	 * "Coupon Code | 15% off a pair of glasses."}" data-url="http://www.savings.com/mpclick?placementid=19494065
	 * &url=http%3A%2F%2Fwww.savings.com%2Fmpofferref%3Fofferid%3D6305542&vprefid=999999999&contentzoneid=125" 
	 * href="/coupons/stores/eyebuydirect?pageType=popup">
	 * }</pre>
	 * @param pageElement is the element to verify the specified attribute contains the expected value.
	 * @param attrStr is the attribute name in the element.
	 * @param illegalValue is the value that must not be contained in the attribute.
	 * @throws FrameworkException is thrown if the element does not exist or the attribute contains the text.
	 */
	public void verifyElementAttributeNotContains(PageObject pageElement, String attrStr, String illegalValue) {
		ArgumentValidator.create().validate(attrStr, "attrStr").validate(illegalValue, "illegalValue");
		String actualValue = webPage.locateElement().findElement(pageElement).getAttribute(attrStr);
		FillDataBuilder fillData = FillDataBuilder.create(pageElement.toString(), attrStr);
		verifyAttributeStrNotNull(fillData, actualValue);
		fillData.add(illegalValue).add(actualValue);
		String assertStr = ErrorManager.fillErrorString(PublicErrorStrings.ELEMENT_ATTR_ILLEGAL, fillData);
		assertTrue(assertStr, !actualValue.contains(illegalValue));
	}

	private void verifyAttributeStrNotNull(FillDataBuilder fillData, String attrStr) {
		if (attrStr != null) return;
		ErrorManager.throwAndDump(PublicErrorStrings.ELEMENT_ATTR_NOT_EXIST, fillData.add(attrStr));
	}

	/**
	 * make sure an arraylist is not empty and has at least the specified size. 
	 * Use this to ensure an array has at least 1 element. it can have more but, it can't have none
	 * @param minimumSize - minimum count of elements in the ArrayList
	 * @param elements - ArrayList to check
	 * @param pageElement - used for error logging only
	 */
	//fail if size = 0 or if size is less than expected
	public void verifyMinArraySize(int minimumSize, ArrayList<?> elements, PageObject pageElement) {
		if (elements.size() == 0 || minimumSize > elements.size()) {
			FillDataBuilder fillData = FillDataBuilder.create(pageElement.toString(), String.valueOf(minimumSize), String.valueOf(elements.size()));
			ErrorManager.throwAndDump(PublicErrorStrings.ELEMENT_NOT_EXIST_AT_ELEMENT, fillData);
		}
		if (minimumSize < 0) {
			FillDataBuilder fillData = FillDataBuilder.create("index >= 0 && index < " + elements.size(), String.valueOf(minimumSize));
			ErrorManager.throwAndDumpInternalError(InternalErrorStrings.INVALID_INT, fillData);
		}
	}

	
	public void verifyArrayBounds(int index, ArrayList<?> elements, PageObject pageElement) {
		if (elements.size() == 0 || index >= elements.size()) {
			FillDataBuilder fillData = FillDataBuilder.create(pageElement.toString(), String.valueOf(index), String.valueOf(elements.size()));
			ErrorManager.throwAndDump(PublicErrorStrings.ELEMENT_NOT_EXIST_AT_ELEMENT, fillData);
		}
		if (index < 0) {
			FillDataBuilder fillData = FillDataBuilder.create("index >= 0 && index < " + elements.size(), String.valueOf(index));
			ErrorManager.throwAndDumpInternalError(InternalErrorStrings.INVALID_INT, fillData);
		}
	}

	public void verifyCount(PageObject firstElement, PageObject secondElement) {
		ArgumentValidator.create().validate(firstElement, "firstElement").validate(secondElement, "secondElement");
		int count = webPage.locateElement().findElements(firstElement).size();
		verifyCount(secondElement, count);
	}

	public void verifyCount(PageObject firstElement, int count) {
		ArgumentValidator.create().validate(firstElement, "firstElement").validatePositive(count, "count");
		int verifyCount = webPage.locateElement().findElements(firstElement).size();
		if (count != verifyCount) ErrorManager.throwAndDump(PublicErrorStrings.COUNT_MISMATCH, FillDataBuilder.create(count, verifyCount));
	}
	
	//used for count of buttons to national ads because there can be same as or more buttons than national ads. 
	//first parameter is count of buttons, second count is national ads. 
	public void verifyCountGreaterThanOrEqual(PageObject firstElement, PageObject secondElement) {
		int firstElementCount = webPage.locateElement().findElements(firstElement).size();
		ArgumentValidator.create().validate(firstElement, "first Element").validatePositive(firstElementCount, "first Element Count");
		int secondElementCount = webPage.locateElement().findElements(secondElement).size();
		ArgumentValidator.create().validate(secondElement, "second Element").validatePositive(secondElementCount, "second Element Count");
		if (firstElementCount < secondElementCount) { 
			ErrorManager.throwAndDump(PublicErrorStrings.COUNT_MISMATCH, FillDataBuilder.create(secondElementCount, firstElementCount));
		}
	}
	
	public void verifyCountGreaterThanOrEqual(PageObject firstElement, int lowestValue) {
		ArgumentValidator.create().validate(firstElement, "firstElement").validatePositive(lowestValue, "count");
		int verifyCount = webPage.locateElement().findElements(firstElement).size();
		if (lowestValue > verifyCount) ErrorManager.throwAndDump(PublicErrorStrings.COUNT_MISMATCH, FillDataBuilder.create(lowestValue, verifyCount));
	}

	public void verifyElementsSortedAtoZ(PageObject pageElement) {
		verifyElementsSorted(pageElement, SortValidator.ascending());
	}

	public void verifyElementsSortedZtoA(PageObject pageElement) {
		verifyElementsSorted(pageElement, SortValidator.descending());
	}

	public void verifyElementsSorted(PageObject pageElement, SortValidator validator) {
		ArgumentValidator.create().validate(pageElement, "firstElement").validate(validator, "validator");
		List<WebElementWrapper> elements = webPage.locateElement().findElements(pageElement);

		if (elements.size() < 2) return;

		String next;
		String current;
		for (int i = 0; i < elements.size() - 1; ++i) {
			current = elements.get(i).getText();
			next = elements.get(i + 1).getText();
			if (!validator.isSorted(current, next)) {
				ErrorManager.throwAndDump(PublicErrorStrings.IMPROPER_SORT, FillDataBuilder.create(pageElement));
			}
		}
	}
}
