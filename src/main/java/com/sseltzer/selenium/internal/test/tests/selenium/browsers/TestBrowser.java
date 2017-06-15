
package com.sseltzer.selenium.internal.test.tests.selenium.browsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.sseltzer.selenium.framework.selenium.webpage.base.WebPage;
import com.sseltzer.selenium.framework.verification.junit.TestScript;

/**
 *
 *
 * TestBrowser.java
 *
 * @author ckiehl Sep 9, 2014
 */
public class TestBrowser extends TestScript {

	@Test 
	public void testCloseWindowsNotMatchingPartialUrl() {
		WebPage webPage = openGoogle();
		openNewWindow();
		assertEquals(2, getBrowser().getAllActiveWindowHandles().size());

		getBrowser().closeAllWindowsNotMatchingPartialUrl("google");
		
		assertEquals(1, getBrowser().getAllActiveWindowHandles().size());
		assertTrue(getBrowser().getCurrentUrl().contains("google"));
	}
	
	@Test 
	public void testCloseWindowsNotMatchingPartialTitle() {
		WebPage webPage = openGoogle();
		openNewWindow();
		assertEquals(2, getBrowser().getAllActiveWindowHandles().size());

		getBrowser().closeAllWindowsNotMatchingPartialTitle("Google");
		
		assertEquals(1, getBrowser().getAllActiveWindowHandles().size());
		assertTrue(getBrowser().getCurrentUrl().contains("google"));
	}
	
	@Test 
	public void testCloseWindowsNotMatchingExactTitle() {
		WebPage webPage = openGoogle(); 
		openNewWindow();
		
		assertEquals(2, getBrowser().getAllActiveWindowHandles().size());
		
		getBrowser().closeAllWindowsNotMatchingExactTitle("Google");
		
		assertEquals(1, getBrowser().getAllActiveWindowHandles().size());
		assertTrue(getBrowser().getCurrentUrl().contains("google"));
	}
	
	@Test 
	public void testCloseWindowsNotMatchingExactUrl() {
		WebPage webPage = openGoogle(); 
		openNewWindow("https://www.yahoo.com");
		
		assertEquals(2, getBrowser().getAllActiveWindowHandles().size());
		
		getBrowser().closeAllWindowsNotMatchingExactUrl("https://www.yahoo.com");
		
		assertEquals(1, getBrowser().getAllActiveWindowHandles().size());
		assertTrue(getBrowser().getCurrentUrl().contains("yahoo"));
	}
	
	@Test 
	public void testCloseWindowsNotMatchingTitleRegex() {
		WebPage webPage = openGoogle(); 
		openNewWindow();
		
		assertEquals(2, getBrowser().getAllActiveWindowHandles().size());
		
		getBrowser().closeAllWindowsNotMatchingTitleRegex("Goo.*");
		
		assertEquals(1, getBrowser().getAllActiveWindowHandles().size());
		assertTrue(getBrowser().getCurrentUrl().contains("google"));
	}
	
	@Test 
	public void testCloseWindowsNotMatchingUrlRegex() {
		WebPage webPage = openGoogle(); 
		openNewWindow();
		
		assertEquals(2, getBrowser().getAllActiveWindowHandles().size());
		
		getBrowser().closeAllWindowsNotMatchingUrlRegex(".*google.*");
		
		assertEquals(1, getBrowser().getAllActiveWindowHandles().size());
		assertTrue(getBrowser().getCurrentUrl().contains("google"));
	}
	
	@Test 
	public void testFocusWindowByExactTitle() {
		WebPage webPage = openGoogle(); 
		openNewWindow("https://www.yahoo.com");
		
		assertEquals(2, getBrowser().getAllActiveWindowHandles().size());
		
		getBrowser().focusWindowByExactTitle("Yahoo");
		assertTrue(getBrowser().getCurrentUrl().contains("yahoo"));
		getBrowser().closeAllWindowsNotMatchingPartialUrl("yahoo");
	}
	
	@Test 
	public void testFocusWindowByPartialTitle() {
		WebPage webPage = openGoogle(); 
		openNewWindow("https://www.yahoo.com");
		
		assertEquals(2, getBrowser().getAllActiveWindowHandles().size());
		
		getBrowser().focusWindowByPartialTitle("Yah");
		assertTrue(getBrowser().getCurrentUrl().contains("yahoo"));
		getBrowser().closeAllWindowsNotMatchingPartialUrl("yahoo");
	}
	
	
	@Test 
	public void testFocusWindowByTitleRegex() {
		WebPage webPage = openGoogle(); 
		openNewWindow("https://www.yahoo.com");
		
		assertEquals(2, getBrowser().getAllActiveWindowHandles().size());
		
		getBrowser().focusWindowByPartialTitle(".*Yah.*");
		assertTrue(getBrowser().getCurrentUrl().contains("yahoo"));
		getBrowser().closeAllWindowsNotMatchingPartialUrl("yahoo");
	}
	
	private WebPage openGoogle() {
		getBrowser().getWebDriver().get("http://www.google.com");
		WebPage webPage = WebPage.getInstance(getBrowser(), null);
		WebDriver driver = webPage.getBrowser().getWebDriver().getBase();
		assertEquals(1, getBrowser().getAllActiveWindowHandles().size());
		return webPage;
	}
	
	private void openNewWindow() { 
		openNewWindow(""); 
	}
	private void openNewWindow(String fullurl) {
		WebDriver driver = getBrowser().getWebDriver().getBase();
		((JavascriptExecutor)driver).executeScript(String.format("window.open('%s');", fullurl));
	}
	
	
}
