package com.sseltzer.selenium.framework.selenium.browsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;


import org.openqa.selenium.Alert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.sseltzer.selenium.framework.file.disk.DiskManager;
import com.sseltzer.selenium.framework.file.disk.TimestampedPackage;
import com.sseltzer.selenium.framework.selenium.wrappers.WebDriverWrapper;

/**
 * The Browser object is intended to represent a browser connection and provide access to various
 * browser specific functions. The main communication route to Selenium is through WebDriver. The
 * concept of a WebDriver is essentially "a platform and language-neutral interface and associated
 * protocol that allows programs or scripts to introspect into and control the behavior of a web
 * browser" (http://www.w3.org/TR/webdriver/) Essentially there is a WebDriver object per connection
 * to a browser process. Each WebDriver object must be initialized uniquely depending on which browser
 * is being utilized and some browsers (Chrome) need a separate executable to communicate through.
 * Once a WebDriver object is acquired though, all communication is consistent since WebDriver is
 * designed to provide a single universal interface for communication. The Browser object is intended
 * to utilize this fact as the Browser object itself is abstract. The basic functions to utilize the
 * Browser are implemented here and each extending class only provides the instantiation and closure
 * of the WebDriver object.
 * <br><br>
 * The Browser object provides methods to assist in cookie management. Provisions are provided to
 * prepopulate cookies before a browser is used. Other functions exist to get the current page's URL,
 * page source, and manage windows, frames, and alerts. A Browser object is required for most other
 * framework objects as all communication must happen through a WebDriver. As such, the BrowserFactory
 * is used to create and manage these connections using a multiton pattern. Also, the WebDriver object
 * (Browser) is reused as much as possible as to not needlessly create and destroy extra processes during
 * testing. A Browser represents one connection to a browser process thus by reusing a Browser, we limit
 * the number of processes.
 * @author Sean Seltzer
 *
 */
public abstract class Browser {

	protected WebDriverWrapper webDriverWrapper;
	private Collection<Cookie> cookies = new ArrayList<Cookie>();

	public Browser() {
		setWebDriver();
	}

	/**
	 * Abstract method to create the WebDriver which each extending class must uniquely perform.
	 */
	public abstract void setWebDriver();

	/**
	 * Return the wrapped WebDriver to the caller. This is the main point of access for all other
	 * framework classes to communicate through Selenium, and probably the most called function of
	 * this class. 
	 * @return the WebDriver instance referring to the browser.
	 */
	public WebDriverWrapper getWebDriver() {
		return webDriverWrapper;
	}

	/**
	 * Close the WebDriver connection and perform any other closing actions. This is useful for when an
	 * intermediary service is required for browser communication and must be closed in addition to the
	 * browser connection.
	 */
	public abstract void quit();

	/**
	 * Add a cookie to the Browser to be prepopulated at the beginning of an @Test function.
	 * @param cookie to be added before an @Test function is executed.
	 */
	public void prepopulateCookie(Cookie cookie) {
		cookies.add(cookie);
	}

	/**
	 * Load the prepopulated cookies into the browser.
	 */
	public void loadPrepopulatedCookies() {
		for (Cookie cookie : cookies) addCookie(cookie);
	}

	/**
	 * Immediately adds a cookie to the browser.
	 * @param cookie to be immediately added to the browser.
	 */
	public void addCookie(Cookie cookie) {
		getWebDriver().manage().addCookie(cookie);
	}

	/**
	 * Clears the cookies in the browser.
	 */
	public void clearCookies() {
		getWebDriver().manage().deleteAllCookies();
	}

	/**
	 * Returns raw HTML page source.
	 * @return raw HTML page source.
	 */
	public String getPageSource() {
		return getWebDriver().getPageSource();
	}

	/**
	 * Returns the URL of the current window.
	 * @return the URL of the current window.
	 */
	public String getCurrentUrl() {
		return getWebDriver().getCurrentUrl();
	}

	/**
	 * Returns a set of String IDs of the currently active windows.
	 * @return a set of String IDs of the currently active windows.
	 */
	public Set<String> getAllActiveWindowHandles() {
		return getWebDriver().getWindowHandles();
	}

	/**
	 * Returns the count the number of currently active windows.
	 * @return the count the number of currently active windows.
	 */
	public int getCountOfActiveWindowHandles() {
		return getAllActiveWindowHandles().size();
	}

	/**
	 * Focus a window by String ID.
	 * @param windowHandleID String ID of a window to focus.
	 */
	public void switchFocusToSpecificWindow(String windowHandleID) {
		getWebDriver().switchTo().window(windowHandleID);
	}

	/**
	 * Returns an Alert object for the current alert dialog box.
	 * @return an Alert object for the current alert dialog box.
	 */
	public Alert switchFocusToAlert() {
		return getWebDriver().switchTo().alert();
	}

	/**
	 * Returns the String id of the current active window.
	 * @return the String id of the current active window.
	 */
	public String getCurrentWindowHandle() {
		return getWebDriver().getWindowHandle();
	}
	
	public void setNewWindowWidth(int width) {
		int height = this.getWebDriver().manage().window().getSize().height; 
		Dimension newSize = new Dimension(width, height);
	    this.getWebDriver().manage().window().setSize(newSize);
	  }
	
	public void maximize() {
		this.getWebDriver().manage().window().maximize();
	}

	/**
	 * Closes the current alert dialog box. This will throw an unchecked exception
	 * if no window exists.
	 */
	public void dismissAlertWindow() {
		switchFocusToAlert().dismiss();
	}

	/**
	 * Attempts to find and focus a window based on a partial String of the page title.
	 * If two windows both contain that particular String, the first one encountered
	 * is returned.
	 * @param partialTitle part of the title of the window to search for to focus.
	 */
	public void focusWindowByPartialTitle(final String partialTitle) {
		performWindowAction(switchWindowAndGetTitle, setFocus, containsString(partialTitle), false);
	}

	/**
	 * Attempts to find and focus a window based on exact window title.
	 * @param exactTitle the exact title of the window to search for and focus.
	 */
	public void focusWindowByExactTitle(final String exactTitle) {
		performWindowAction(switchWindowAndGetTitle, setFocus, equalsString(exactTitle), false);
	}
	
	/**
	 * Attempts to find and focus a window by title matching a regex
	 * @param exactTitle the exact title of the window to search for and focus.
	 */	
	public void focusWindowByTitleRegex(final String titleRegex) {
		performWindowAction(switchWindowAndGetTitle, setFocus, matchesRegexString(titleRegex), false);
	}
	
	/**
	 * Focuses the window that contains the supplied partial url 
	 */
	public void focusWindowByPartialUrl(final String partialUrl) {
		performWindowAction(switchWindowAndGetUrl, setFocus, containsString(partialUrl), false);
	}
	
	/**
	 * Focuses the window that exactly matches the supplied url 
	 */
	public void focusWindowByExactUrl(final String exactUrl) {
		performWindowAction(switchWindowAndGetUrl, setFocus, equalsString(exactUrl), false);
	}
	
	/**
	 * Focuses the window that matches the supplied url regex 
	 */
	public void focusWindowByUrlRegex(final String urlRegex) {
		performWindowAction(switchWindowAndGetUrl, setFocus, matchesRegexString(urlRegex), false);
	}
	
	/**
	 * Closes all windows with titles not containing the supplied regex
	 */
	public void closeAllWindowsNotMatchingPartialTitle(final String partialTitle) {
		performWindowAction(switchWindowAndGetTitle, close, notContainsString(partialTitle), true);
	}
	
	/**
	 * Closes all windows with t not matching the supplied string
	 */
	public void closeAllWindowsNotMatchingExactTitle(final String exactTitle) {
		performWindowAction(switchWindowAndGetTitle, close, notEqualsString(exactTitle), true);
	}
	
	/**
	 * Closes all windows with titles not matching the supplied regex
	 */
	public void closeAllWindowsNotMatchingTitleRegex(final String titleRegex) {
		performWindowAction(switchWindowAndGetTitle, close, notMatchesRegexString(titleRegex), true);
	}
	
	/**
	 * Closes all windows with Urls not containing the supplied regex
	 */
	public void closeAllWindowsNotMatchingPartialUrl(final String partialUrl) {
		performWindowAction(switchWindowAndGetUrl, close, notContainsString(partialUrl), true);
	}
	
	/**
	 * Closes all windows with Urls not matching the supplied string
	 */
	public void closeAllWindowsNotMatchingExactUrl(final String exactUrl) {
		performWindowAction(switchWindowAndGetUrl, close, notEqualsString(exactUrl), true);
	}
	
	/**
	 * Closes all windows with Urls not matching the supplied regex
	 */
	public void closeAllWindowsNotMatchingUrlRegex(final String urlRegex) {
		performWindowAction(switchWindowAndGetUrl, close, notMatchesRegexString(urlRegex), true);
	}
	
	/**
	 * Returns a Predicate which returns whether or not the expected string equals the expected
	 */

	private Predicate<String> equalsString(final String expected) {
		return new Predicate<String>() {
			public boolean apply(String found) { return found.equals(expected); }
			public boolean test(String input) {
				return apply(input);
			}
		};
	}
	
	/**
	 * Returns a Predicate which returns whether or not the expected string contains the expected
	 */
	private Predicate<String> containsString(final String expected) { 
		return new Predicate<String>() {
			public boolean apply(String found) { return found.contains(expected); }
			public boolean test(String input) {
				return apply(input);
			}
		};
	}
	
	/**
	 * Returns a Predicate which returns whether or not the expected string matches the expected
	 */
	private Predicate<String> matchesRegexString(final String expected) {
		return new Predicate<String>() {
			public boolean apply(String found) { return found.matches(expected); }
			public boolean test(String input) {
				return apply(input);
			}
		};
	}
	
	/**
	 * Returns a Predicate which returns whether or not the expected string equals the expected
	 */
	private Predicate<String> notEqualsString(final String expected) {
		return new Predicate<String>() {
			public boolean apply(String found) { return !found.equals(expected); }
			public boolean test(String input) {
				return apply(input);
			}
		};
	}
	
	/**
	 * Returns a Predicate which returns whether or not the expected string contains the expected
	 */
	private Predicate<String> notContainsString(final String expected) { 
		return new Predicate<String>() {
			public boolean apply(String found) { return !found.contains(expected); }
			public boolean test(String input) {
				return apply(input);
			}
		};
	}
	
	/**
	 * Returns a Predicate which returns whether or not the expected string matches the expected
	 */
	private Predicate<String> notMatchesRegexString(final String expected) {
		return new Predicate<String>() {
			public boolean apply(String found) { return !found.matches(expected); }
			public boolean test(String input) {
				return apply(input);
			}
		};
	}
	
	/**
	 * Switches focus to the supplied handle and returns the page title
	 */
	private Function<String, String> switchWindowAndGetTitle = new Function<String, String>() {
		public String apply(String handle) {
			return getWebDriver().switchTo().window(handle).getTitle();
		}
	};
	
	/**
	 * Switches focus to the supplied handle and returns the page url
	 */
	private Function<String, String> switchWindowAndGetUrl = new Function<String, String>() {
		public String apply(String handle) {
			return getWebDriver().switchTo().window(handle).getCurrentUrl();
		}
	};
	
	/**
	 * Instructs the browser to focus the window matching the passed in handle
	 */
	private Function<String, String> setFocus = new Function<String, String>() {
		public String apply(String handle) {
			getWebDriver().switchTo().window(handle);
			return null;
		}
	};
	
	/**
	 * Instructs the browser to close window matching the passed in handle 
	 */
	private Function<String, String> close = new Function<String, String>() {
		public String apply(String handle) {
			getWebDriver().switchTo().window(handle).close();
			return null;
		}
	};
	
	/**
	 * Factory for creating custom window actions. 
	 * 
	 * @param getCriteria (Function)- which criteria to get for later check (e.g. title, url, etc..)
	 * @param actionToPerform (Function)- action to perform if criteria passes check
	 * @param ifPredicate (Predicate) - boolean  test of criteria     
	 */
	private void performWindowAction(Function<String, String> getCriteria,
			Function<String, String> actionToPerform, Predicate<String> ifPredicate, boolean refocus) {
		for (String handle : getAllActiveWindowHandles()) {
			String windowCriteria = getCriteria.apply(handle);
			if (ifPredicate.apply(windowCriteria)) {
				actionToPerform.apply(handle);
				break;
			}
		}
		if (refocus)
			getWebDriver().switchTo().window((String)getAllActiveWindowHandles().toArray()[0]);
	}
	

	/**
	 * Closes all windows that do not match the exact title provided.
	 * @param titles the exact title of the window to keep open.
	 */

	private byte[] takeScreenshot() {
		return webDriverWrapper.takeScreenshot();
	}

	public void screenshotAndUpload(String fileName) {
		/*
		try {
			byte[] data = takeScreenshot();
			FTPClient client = new FTPClient();
			client.connect("10.29.5.41");
			client.login("selenium", "selenium");
			client.setFileType(FTP.BINARY_FILE_TYPE);
			//client.mkd("test");
			client.changeWorkingDirectory("test");
			client.deleteFile("testFile.png");
			OutputStream stream = client.storeFileStream("testFile.png");
			stream.write(data);
			stream.close();
			client.disconnect();
		} catch (Exception e) {
			throw new FrameworkException(e);
		}*/
	}
	public void screenshotAndWriteToDisk(String fileName) {
		TimestampedPackage tp = DiskManager.getScreenshotManager();
		byte[] data = takeScreenshot();
		tp.writeFile(fileName, data);
	}
}
