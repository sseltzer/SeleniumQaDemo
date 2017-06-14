package com.sseltzer.selenium.framework.selenium.webpage.base;

import com.sseltzer.selenium.framework.selenium.browsers.Browser;
import com.sseltzer.selenium.framework.selenium.website.Website;

/**
 * The WebPage object is a Singleton object which provides an interface to
 * communicate with all web pages. Once a Browser and Website object are created
 * and passed in to create a WebPage object, the user will interact with the
 * same instance of the WebPage through the life of the test. As in a typical
 * Singleton, getInstance is used to get the current instance of the static
 * WebPage object. <br>
 * <br>
 * While the Page object is a super class which provides an abstraction from
 * Selenium to make calls to a web page, the intent of the WebPage is to enhance
 * the Page object while providing generic access methods to all WebPages and
 * effectively "moves with you" as you traverse a website. When a WebPage is
 * created and a browser is opened to a website, PageObjects or Strings are
 * passed here to perform actions. For instance, to click an element on the web
 * page, a call may be made with the clickButton function. A PageObject or
 * String is used as a reference to the element to be interacted with. The
 * PageObject contains the same String that is to be used as the regular String.
 * This is simply a JavaScript ID or a CSS selector. For example, "geobtn" may
 * be an ID that references a button on the screen that may be passed in to
 * clickButton. At the same token, ".btn-back" is a selector for a different
 * button. Both of these Strings may be passed in and the referenced elements
 * are clicked. The differentiation between the two is automatically detected.
 * The presence of a "." is the indication that the String references a CSS
 * selector as opposed to an ID. This theme is carried through to all WebPage
 * functions, to simplify WebPage interaction. <br>
 * <br>
 * In abstracting the Page Object Model to a single Generic Model, we have a
 * uniform access method for all elements on the screen. Only a String reference
 * need be known to communicate with a page and we no longer need individual
 * methods to connect to a specific object. To compliment this, Map objects are
 * created which are static PageObjects (A PageObject is a glorified String
 * container). That can potentially store other information about an element
 * beyond just ID and CSS tag. Each Map object contains public static
 * PageObjects which in turn contain a String, which is a pre-mapped element
 * from a page. This element may be anything, all are stored uniformly. So, with
 * WebPage being able to access anything with only a single identifier, and the
 * Map objects to provide a consistent and abstracted way to catalog items on
 * the screen, WebPage then becomes a platform to connect to a page. Since it is
 * a Singleton, it then becomes a platform to communicate with the website, as
 * the WebPage will travel with you from page to page. To add new elements, or
 * modify existing elements, the individual Strings in the Map objects are the
 * only things that need to be changed, and then they may be used with any
 * function that will accept a page object. So the page mapping then becomes
 * abstracted from the communication schema.
 * 
 * @author Sean Seltzer
 * 
 */
public class WebPage extends WebPageBase {

	/**
	 * This is the normal constructor for creating a WebPage. Simply provide a
	 * Browser and Website object which will initialize the Page the super
	 * constructor.
	 * 
	 * @param browser
	 *            is the Browser object reference to create the Page.
	 * @param website
	 *            is the Website object reference to create the Page.
	 */
	private WebPage(Browser browser, Website website) {
		super(browser, website);
	}

	// Singleton
	private static WebPage webPage;

	/**
	 * The getInstance(Browser, Website) function is the main method of
	 * instantiation. This method should be used to get a WebPage under normal
	 * circumstances.
	 * 
	 * @param browser
	 *            is the Browser object to open the page (only used if WebPage
	 *            has not been initialized or if close was called)
	 * @param website
	 *            is the Website object to open the page (only used if WebPage
	 *            has not been initialized or if close was called)
	 * @return The static WebPage object.
	 */
	public static WebPage getInstance(Browser browser, Website website) {
		if (webPage == null) webPage = new WebPage(browser, website);
		return webPage;
	}

	public static WebPage getCurrentPage() {
		return webPage;
	}

	public static String getCurrentUrl()	{
		return webPage.getBrowser().getCurrentUrl();
	}
	
	/**
	 * This static function will close the page and null the singleton WebPage
	 * forcing a reinstantiation on the next getInstance call.
	 */
	public static void close() {
		if (webPage == null) return;
		webPage = null;
	}

	public void refresh() {
		webPage.getWebDriver().navigate().refresh();
	}

	public void back() {
		webPage.getWebDriver().navigate().back();
	}
}
