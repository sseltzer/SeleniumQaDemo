package com.sseltzer.selenium.framework.selenium.wrappers;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.sseltzer.selenium.framework.error.ErrorManager;
import com.sseltzer.selenium.framework.error.FillDataBuilder;
import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;

import io.appium.java_client.AppiumDriver;

/**
 * The TimingManager controls all timing for the framework. It's main purpose is also to
 * intercept all calls made to the Selenium framework to add in a custom built in timing
 * feature to allow all operations performed on a web page to have an implicit wait when
 * trying to perform certain functions such as finding an element. In Selenium, there is
 * no global way of handling waiting for the availability of elements before trying to 
 * perform actions on them. Thus, this class intercepts all retrieval actions as the Wrapper
 * objects wrap both the WebDriver and WebElement objects which are the two main objects
 * within Selenium when interacting with items on the page. As those two wrappers are
 * redirected here before having their core locational functions called, the timing then
 * becomes implicit. There is a bypass to this feature however, in the TimeoutMode. Which
 * is a required object for every TimingOptions object which is required for performing
 * calls to these functions. The IMMEDIATE mode within TimoutMode will not use the built
 * in timing feature and will perform the requested action immediately. For all other calls,
 * there is the WAIT and FALLBACK features which will wait the specified time in the TimingOptions
 * object with the given poll time.
 * <br><br>
 * The reason why we do this is simple: This Selenium framework is a third party program which
 * must interact with a browser which is a separate process through an intermediary WebDriver
 * implementation which is a third process. In addition, due to the third degree abstraction,
 * the JavaScript on the page is not directly accessible from Selenium so with more modern
 * websites relying more heavily on JavaScript to perform a variety of actions from animations
 * (along with CSS), to rebuilding the page, to dynamically modifying the DOM, we must account
 * for this. As such, when trying to find an element on the page, we don't always know if it
 * will be immediately available. The object may be there, it may be there after JavaScript
 * attaches it to the DOM, it may be there after it fades in using an animation, or a whole
 * plethora of other situations. As previously stated, Selenium provides no single solution for
 * this; but it does give us the Wait functionality to which we are to build our own handlers
 * specific to our websites as each website is different. As such, I've (Sean) implemented a
 * single solution for our needs here using a basic wait function to intercept all calls made
 * to Selenium.
 * <br><br>
 * To further complicate this, each runtime environment operates differently, including servers,
 * memory, speed, browser version, etc. As such the above exists as well as a variety of predefined
 * common functions using the Selenium Wait functionality. See also, the Waiter WebPageAugmentor
 * which allows the tests to easily call these stock statements.
 * <br><br>
 * In addition to the stock wait statements, and the interception of the wait methods, this does
 * not account for all timing situations as listed above. Certain situations when using Selenium
 * will cause hangs. One example of which is described in the TimingOptions Javadocs. Global timeouts
 * are used to control maximum wait times for all operations including page loading for which no
 * other mechanism exists. This is where global implicit timeouts come into play. Say a page loads,
 * if a JavaScript goes 503 on a page at load time, the DOM does not receive the ready state until
 * that service finally times out. This could be 30 seconds or more. While in actuality the page has
 * loaded and is ready for interaction. This causes an issue where Selenium will just hang until it
 * is ready, if ever. There are conditions that can cause infinite hangs. To mitigate this, global
 * implicit timeouts provide a hard stop to the Selenium call and will throw an exception if the
 * implicit timout has been reached. This is a Selenium function, and is utilized here to prevent
 * hangs. The normal wait functionality is fine for use after a page has been loaded and is being
 * interacted with, however, this covers the conditions not directly controllable by the framework
 * when making Selenium calls. 
 * <br><br>
 * The default TimingOptions is FALLBACK, 10000ms wait time, and 100ms poll time.
 * The default global implicit timeout is 15000ms. This includes, page loads, and script loading.
 * This does not include implicit waits, as the above described custom feature handles this.
 * @author Sean Seltzer
 *
 */
public class TimingManager {

	private static final TimeoutMode DEFAULT_TIMEOUT_MODE = TimeoutMode.FALLBACK;

	public enum TimeoutMode {
		IMMEDIATE, WAIT, FALLBACK;
	}

	public static final long DEFAULT_WAIT_TIME_MILLIS = 10000;
	public static final long DEFAULT_POLL_TIME_MILLIS = 100;
	public static TimingOptions DEFAULT_TIMING_OPTIONS = new TimingOptions(DEFAULT_TIMEOUT_MODE, DEFAULT_WAIT_TIME_MILLIS, DEFAULT_POLL_TIME_MILLIS);
	public static final TimingOptions IMMEDIATE_DEFAULT_OPTIONS = new TimingOptions(TimeoutMode.IMMEDIATE, DEFAULT_WAIT_TIME_MILLIS, DEFAULT_POLL_TIME_MILLIS);

	private static final long DEFAULT_IMPLICIT_TIME_MILLIS = 15000;
	private static long implicitTimeMillis = DEFAULT_IMPLICIT_TIME_MILLIS;

	/** 
	 * Should be used ONLY when running internal tests.   
	 */
	public static void setDefaultTimingOptions(TimingOptions timingOptions) {
		DEFAULT_TIMING_OPTIONS = timingOptions;
	}

	public static void resetDefaultImplicitTimeMillis() {
		implicitTimeMillis = DEFAULT_IMPLICIT_TIME_MILLIS;
	}

	public static long getImplicitTimeMillis() {
		return implicitTimeMillis;
	}

	public static void setImplicitTimeMillis(long implicitTimeMillis) {
		TimingManager.implicitTimeMillis = implicitTimeMillis;
	}

	public static void setGlobalImplicitTimeouts(WebDriverWrapper webDriver) {
		setGlobalImplicitTimeouts(webDriver, implicitTimeMillis);
	}

	public static void setGlobalImplicitTimeouts(WebDriverWrapper webDriver, long millis) {
		Timeouts timeouts = webDriver.getBaseObject().manage().timeouts();
		if ((webDriver.getBase() instanceof AppiumDriver)) return; 
		timeouts.setScriptTimeout(millis, TimeUnit.MILLISECONDS);
		timeouts.pageLoadTimeout(millis, TimeUnit.MILLISECONDS);
		//timeouts.implicitlyWait(millis, TimeUnit.MILLISECONDS);
	}

	/**
	 * Waits for a given duration without any kind of polling. This is a solid wait and currently
	 * performs a Thread.sleep(millis) wrapping any exceptions into a FrameworkException.
	 * @param millis
	 */
	public static void waitForSpecifiedDuration(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}

	/**
	 * Waits for a Selenium ExpectedCondition.
	 * @param webDriver
	 * @param expectedCondition
	 */
	public static void waitFor(WebDriver webDriver, ExpectedCondition<?> expectedCondition) {
		waitFor(webDriver, expectedCondition, DEFAULT_TIMING_OPTIONS);
	}

	/**
	 * Waits for a Selenium ExpectedCondition with custom TimingOptions. 
	 * @param webDriver
	 * @param expectedCondition
	 * @param timingOptions
	 */
	public static void waitFor(WebDriver webDriver, ExpectedCondition<?> expectedCondition, TimingOptions timingOptions) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver);
		wait.withTimeout(timingOptions.getWaitTime(), TimeUnit.MILLISECONDS);
		wait.pollingEvery(timingOptions.getPollTime(), TimeUnit.MILLISECONDS);
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.until(expectedCondition);
	}

	/**
	 * Calls the core function to perform a find from the base Selenium object. This does not include
	 * any custom timing and is the lowest level call to Selenium outside of the Wrapper object itself.
	 * The SearchContextBridge bridges the WebDriver and WebElement object into a common parent and
	 * enforces the common calls to use upcasting to use these generic functions.
	 * @param context
	 * @param by
	 * @return WebElementWrapper from the core Selenium call.
	 */
	private static WebElementWrapper findElementCore(SearchContextBridge context, ByWrapper by) {
		return context.findElementCore(by);
	}

	/**
	 * Calls the core function to perform a find from the base Selenium object. This does not include
	 * any custom timing and is the lowest level call to Selenium outside of the Wrapper object itself.
	 * The SearchContextBridge bridges the WebDriver and WebElement object into a common parent and
	 * enforces the common calls to use upcasting to use these generic functions.
	 * @param context
	 * @param by
	 * @return WebElementWrapper from the core Selenium call.
	 */
	private static ArrayList<WebElementWrapper> findElementsCore(SearchContextBridge context, ByWrapper by) {
		return context.findElementsCore(by);
	}

	/**
	 * The findElement function serves as the funnel for all framework calls to find an element on a page. In
	 * this function, the TimingOptions is examined to determine which TimingMode to follow and the appropriate
	 * actions are performed. IMMEDIATE mode will immediately call the core find function. WAIT mode will wait
	 * for the element to be present before calling the core find. If the wait times out, an exception is generated.
	 * The FALLBACK mode will wait before calling the core find function. If the wait times out, the exception is
	 * ignored and the core find function is called anyways.
	 * @param context
	 * @param by
	 * @param timingOptions
	 * @return the WebElementWrapper of the desired element.
	 */
	public static WebElementWrapper findElement(SearchContextBridge context, ByWrapper by, TimingOptions timingOptions) {
		switch (timingOptions.getTimeoutMode()) {
			case IMMEDIATE:
				return findElementCore(context, by);
			case WAIT:
				waitForElementPresentBy(context, by, timingOptions);
				return findElementCore(context, by);
			case FALLBACK:
				try {
					waitForElementPresentBy(context, by, timingOptions);
				} catch (Exception e) {
				}
				// Ignore and try anyways - If we still can't find it, an exception is generated from findElement.
				return findElementCore(context, by);
		}
		// This can't execute, either the element is returned from findElement, or an exception is thrown by the wait function.
		ErrorManager.throwDeadCode();
		return null;
	}

	/**
	 * The findElements function serves as the funnel for all framework calls to find elements on a page. In
	 * this function, the TimingOptions is examined to determine which TimingMode to follow and the appropriate
	 * actions are performed. IMMEDIATE mode will immediately call the core find function. WAIT mode will wait
	 * for the element to be present before calling the core find. If the wait times out, an exception is generated.
	 * The FALLBACK mode will wait before calling the core find function. If the wait times out, the exception is
	 * ignored and the core find function is called anyways.
	 * @param context
	 * @param by
	 * @param timingOptions
	 * @return the WebElementWrapper of the desired element.

	 */
	public static ArrayList<WebElementWrapper> findElements(SearchContextBridge context, ByWrapper by, TimingOptions timingOptions) {
		switch (timingOptions.getTimeoutMode()) {
			case IMMEDIATE:
				return findElementsCore(context, by);
			case WAIT:
				waitForElementPresentBy(context, by, timingOptions);
				return findElementsCore(context, by);
			case FALLBACK:
				try {
					waitForElementPresentBy(context, by, timingOptions);
				} catch (Exception e) {
				}
				// Ignore and try anyways - If we still can't find it, an exception is generated from findElement.
				return findElementsCore(context, by);
		}
		// This can't execute, either the element is returned from findElement, or an exception is thrown by the wait function.
		ErrorManager.throwDeadCode();
		return null;
	}

	/**
	 * Waits for a given element to be editable
	 * 
	 * @param context
	 * @param by
	 * @param timingOptions
	 */
	public static void waitForElementEditable(final SearchContextBridge context, final ByWrapper by, final TimingOptions timingOptions) {
		TimingManager.waitForElementPresentBy(context, by, timingOptions);
		TimingManager.waitForElementVisibleBy(context, by, timingOptions);
		try {
			final WebDriver driver = context.getWebDriver();
			final WebElement element = context.getWebDriver().findElement(by.getBaseObject());

			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
			wait.withTimeout(timingOptions.getWaitTime(), TimeUnit.MILLISECONDS);
			wait.pollingEvery(timingOptions.getPollTime(), TimeUnit.MILLISECONDS);
			wait.ignoring(NoSuchElementException.class);
			wait.ignoring(StaleElementReferenceException.class);
			wait.ignoring(WebDriverException.class);
			wait.until(new Function<WebDriver, Boolean>() {
				public Boolean apply(WebDriver webDriver) {
					if (element.isEnabled()) return true;
					return false;
				}
			});
		} catch (RuntimeException e) {
			System.out.println("Bugger..");
			e.printStackTrace();
		}
	}

	/**
	 * This function will wait for the given element to be present. If it is not present by the TimingOptions wait time,
	 * an exception is generated. If it is found using polling, the function will return and no further actions take place
	 * in this function.
	 * @param context
	 * @param by
	 * @param timingOptions
	 */
	public static void waitForElementPresentBy(SearchContextBridge context, ByWrapper by, TimingOptions timingOptions) {
		try {
			waitFor(context.getWebDriver(), ExpectedConditions.presenceOfElementLocated(by.getBaseObject()), timingOptions);
		} catch (RuntimeException e) {
			FillDataBuilder fillData = FillDataBuilder.create(by.getBaseObject().toString(), timingOptions.getWaitTimeAsString());
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
	}

	/**
	 * This function will wait for the given element to be present. If it is not present by the TimingOptions wait time,
	 * an exception is generated. If it is found using polling, the function will return and no further actions take place
	 * in this function.
	 * @param context
	 * @param by
	 * @param timingOptions
	 */
	public static void waitForElementsPresentBy(SearchContextBridge context, ByWrapper by, TimingOptions timingOptions) {
		try {
			waitFor(context.getWebDriver(), ExpectedConditions.presenceOfAllElementsLocatedBy(by.getBaseObject()), timingOptions);
		} catch (RuntimeException e) {
			FillDataBuilder fillData = FillDataBuilder.create(by.getBaseObject().toString(), timingOptions.getWaitTimeAsString());
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
	}

	/**
	 * This function will wait for the given element to not be present. If it is not present by the TimingOptions wait time,
	 * an exception is generated. If it is found using polling, the function will return and no further actions take place
	 * in this function.
	 * @param context
	 * @param by
	 * @param timingOptions
	 */
	public static void waitForElementNotPresentBy(SearchContextBridge context, ByWrapper by, TimingOptions timingOptions) {
		try {
			waitFor(context.getWebDriver(), ExpectedConditions.invisibilityOfElementLocated(by.getBaseObject()), timingOptions);
		} catch (RuntimeException e) {
			FillDataBuilder fillData = FillDataBuilder.create(by.getBaseObject().toString(), timingOptions.getWaitTimeAsString());
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
	}

	/**
	 * This function will wait for the given element to be visible. If it is not present by the TimingOptions wait time,
	 * an exception is generated. If it is found using polling, the function will return and no further actions take place
	 * in this function.
	 * @param context
	 * @param by
	 * @param timingOptions
	 */
	public static void waitForElementVisibleBy(SearchContextBridge context, ByWrapper by, TimingOptions timingOptions) {
		try {
			waitFor(context.getWebDriver(), ExpectedConditions.visibilityOfElementLocated(by.getBaseObject()), timingOptions);
		} catch (RuntimeException e) {
			FillDataBuilder fillData = FillDataBuilder.create(by.getBaseObject().toString(), timingOptions.getWaitTimeAsString());
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
	}

	/**
	 * This function will wait for the given element to be visible. If it is not present by the TimingOptions wait time,
	 * an exception is generated. If it is found using polling, the function will return and no further actions take place
	 * in this function.
	 * @param webElement
	 * @param timingOptions
	 */
	public static void waitForElementVisibleBy(WebElementWrapper webElement, TimingOptions timingOptions) {
		try {
			waitFor(webElement.getWebDriver(), ExpectedConditions.visibilityOf(webElement.getBaseObject()), timingOptions);
		} catch (RuntimeException e) {
			FillDataBuilder fillData = FillDataBuilder.create(timingOptions.getWaitTimeAsString(), webElement.getTagName().toString());
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
	}

	/**
	 * This function will wait for the given element to be invisible. If it is not present by the TimingOptions wait time,
	 * an exception is generated. If it is found using polling, the function will return and no further actions take place
	 * in this function.
	 * @param context
	 * @param by
	 * @param timingOptions
	 */
	public static void waitForElementInvisibleBy(SearchContextBridge context, ByWrapper by, TimingOptions timingOptions) {
		try {
			waitFor(context.getWebDriver(), ExpectedConditions.invisibilityOfElementLocated(by.getBaseObject()), timingOptions);
		} catch (RuntimeException e) {
			FillDataBuilder fillData = FillDataBuilder.create(by.getBaseObject().toString(), timingOptions.getWaitTimeAsString());
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
	}
	@SuppressWarnings("unused")
	public static void waitForValpakComPageLoad(SearchContextBridge context) {
		final String CLASS_NAME = "js-fouc";
		final boolean PRESENT = false;
		
		ByWrapper by = ByWrapper.tagName(new WebDriverWrapper(context.getWebDriver()), "html");
		WebElementWrapper element = context.findElementCore(by);
		String css = element.getAttribute("class");
		if (css.contains(CLASS_NAME) == PRESENT) return;
		new FluentWait<WebElement>(element.getBaseObject()).withTimeout(10000, TimeUnit.MILLISECONDS).pollingEvery(100, TimeUnit.MILLISECONDS).until(new Function<WebElement, Boolean>() {
			public Boolean apply(WebElement element) {
				String newCSS = element.getAttribute("class");
				return (newCSS.contains(CLASS_NAME) == PRESENT);
			}
		});
	}
}