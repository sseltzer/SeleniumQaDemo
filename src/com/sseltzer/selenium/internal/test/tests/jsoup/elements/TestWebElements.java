
package com.sseltzer.selenium.internal.test.tests.jsoup.elements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sseltzer.selenium.framework.jsoup.elements.WebElements;


/**
 *
 *
 * TestWebElements.java
 *
 * @author ckiehl Aug 20, 2014
 */
public class TestWebElements {
	
	private static String testHtml = ""
		+"<!DOCTYPE html>"
		+"<html>"
		+ "<head>"
		+  "<meta charset=\"UTF-8\">"
		+  "<title>Test Data</title>"
		+ "</head>"
		+ "<body>"
		+  "<h1>This is a Page Title</h1>"
		+  "<p>"
		+  	"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod "
		+  	"tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, "
		+  	"<a href=\"www.google.com\">quis </a>nostrud exercitation ullamco laboris nisi "
		+  	"ut aliquip ex ea commodo consequat."
		+ 	"</p>"
		+	"<ul>"
		+		"<li><a href=\"www.yahoo.com\">yahoo</a></li>"
		+		"<li><a href=\"www.gmail.com\">gmail</a></li>"
		+		"<li><a href=\"www.hotmail.com\">hotmail</a></li>"
		+		"<li><a href=\"www.somemail.com\">somemail</a></li>"
		+		"<li><a href=\"www.aol.what\">aol</a></li>"
		+	"</ul>   "
		+ "</body>"
		+ "</html>";
	
	private static Elements data; 
	
	@BeforeClass
	public static void setUp() throws IOException {
		Document d = Jsoup.parse(testHtml);
		System.out.println(d);
		data = d.select("li a");
	}
	
	@Test 
	public void testDropRemovesLeadingElementsOfList() {
		WebElements elements = new WebElements(data);
		assertEquals("yahoo", elements.get(0).text());
		assertEquals("gmail", elements.get(1).text());
		
		WebElements elements2 = elements.drop(1); 
		assertNotEquals("yahoo", elements2.get(0).text());
		assertEquals("gmail", elements2.get(0).text());
	}
	
	@Test 
	public void testDropRemovesXNumberOfElements() {
		WebElements elements = new WebElements(data);
		assertEquals(5, elements.size());
		assertEquals(4, elements.drop(1).size());
		assertEquals(3, elements.drop(2).size());
		assertEquals(2, elements.drop(3).size());
		assertEquals(1, elements.drop(4).size());
		assertEquals(0, elements.drop(5).size());
	}
	
	@Test 
	public void testDropWithIndexGreaterThanListSizeReturnsEmptyList() {
		WebElements elements = new WebElements(data);
		assertEquals(5, elements.size());
		assertEquals(0, elements.drop(100).size());
	}
	
	@Test 
	public void testDropWithIndexSmallerThanListSizeReturnsFullList() {
		WebElements elements = new WebElements(data);
		assertEquals(5, elements.size());
		assertEquals(5, elements.drop(0).size());
		assertEquals(5, elements.drop(-20).size());
	}
	
	@Test(expected=IndexOutOfBoundsException.class) 
	public void testTakeReturnsLeadingElementsOfListExcludingElementOutsideIndex() {
		WebElements elements = new WebElements(data);
		
		//beginning elms
		assertEquals("yahoo", elements.get(0).text());
		assertEquals("gmail", elements.get(1).text());
		// elms at end
		assertEquals("aol", elements.get(elements.size()-1).text());
		assertEquals("somemail", elements.get(elements.size()-2).text());
		
		WebElements elements2 = elements.take(2); 
		// beginning still present
		assertEquals("yahoo", elements2.get(0).text());
		assertEquals("gmail", elements2.get(1).text());

		// But tail is now gone
		assertEquals("aol", elements2.get(elements.size()-1).text());
		assertEquals("somemail", elements2.get(elements.size()-2).text());
	}
	
	@Test 
	public void testTakePullsINumberOfElements() {
		WebElements elements = new WebElements(data);
		assertEquals(5, elements.size());
		assertEquals(1, elements.take(1).size());
		assertEquals(2, elements.take(2).size());
		assertEquals(3, elements.take(3).size());
		assertEquals(4, elements.take(4).size());
		assertEquals(5, elements.take(5).size());
	}
	
	@Test 
	public void testTakeWithIndexGreaterThanListSizeReturnsFullList() {
		WebElements elements = new WebElements(data);
		assertEquals(5, elements.size());
		assertEquals(5, elements.take(100).size());
	}
	
	@Test 
	public void testTakeWithIndexSmallerThanListSizeReturnsEmptyList() {
		WebElements elements = new WebElements(data);
		assertEquals(5, elements.size());
		assertEquals(0, elements.take(0).size());
	}
	
	@Test 
	public void testLastReturnsLastElementInList() {
		WebElements elements = new WebElements(data);
		assertEquals("aol", elements.get(elements.size()-1).text());
		assertEquals("aol", elements.last().text());

		assertEquals("yahoo", elements.take(1).last().text());
	}
	
	@Test(expected=NoSuchElementException.class) 
	public void testLastOnEmptyListThrowsNoSuchElement() {
		WebElements elements = new WebElements(data).take(0);
		elements.last();
	}
	
	@Test
	public void testInitReturnsAllListElementsExcludingTheLast() {
		WebElements elements = new WebElements(data);
		assertEquals("aol", elements.last().text());
		assertEquals("somemail", elements.init().last().text());
	}
	
	@Test
	public void testTailReturnsNewListExcludingFirstElement() {
		WebElements elements = new WebElements(data);
		assertEquals("yahoo", elements.head().text());
		assertEquals("gmail", elements.tail().head().text());
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testTailOnEmptyListThrowsUnsupportedOpException() {
		WebElements elements = new WebElements(data);
		elements.take(0).tail();
	}
	
		
	
}
