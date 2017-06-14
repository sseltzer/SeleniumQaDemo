
package com.sseltzer.selenium.internal.test.tests.jsoup.functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.sseltzer.selenium.framework.jsoup.elements.WebElements;
import com.sseltzer.selenium.framework.jsoup.functions.WebElmFilter;
import com.sseltzer.selenium.framework.jsoup.toolkit.ParallelWebTools;
import com.sseltzer.selenium.framework.jsoup.webpages.SoupPage;
import com.sseltzer.selenium.internal.test.tests.jsoup._test_data.TestHtmlDoc;

/**
 *
 *
 * TestWebElmFilter.java
 * 
 * Test Data looks like: 
 * 		...
 * 		"<li><a href=\"www.yahoo.com\">yahoo</a></li>"
 * 		"<li><a href=\"www.aol.what\">aol</a></li>"
 * 		"<li><a href=\"www.gmail.com\">gmail</a></li>"
 * 		"<li><a href=\"www.hotmail.com\">hotmail</a></li>"
 * 		"<li><a href=\"www.somemail.com\">somemail</a></li>"
 * 		....
 * @author ckiehl Sep 8, 2014
 */
public class TestWebElmFilter {
	
	private WebElmFilter wFilter;
	
	@Before
	public void setUp() {
		SoupPage page = ParallelWebTools.parseString(TestHtmlDoc.getContents());
		WebElements elements = page.select("li a");
		wFilter = elements.filter();
	}
	
	@Test
	public void testFilterTextContaining() {
		WebElements elms = wFilter.textContaining("mail");
		assertEquals(3, elms.size());
		assertFalse(elms.map().toText().contains("aol"));
		assertFalse(elms.map().toText().contains("yahoo"));
	}
	
	@Test
	public void testFilterTextNotContaining() {
		WebElements elms = wFilter.textNotContaining("mail");
		assertEquals(2, elms.size());
		assertTrue(elms.map().toText().contains("aol"));
		assertTrue(elms.map().toText().contains("yahoo"));
	}
	
	@Test
	public void testFilterTextMatching() {
		WebElements elms = wFilter.textMatching(".*mail");
		assertEquals(3, elms.size());
		assertTrue(elms.map().toText().contains("gmail"));
		assertFalse(elms.map().toText().contains("aol"));
		assertFalse(elms.map().toText().contains("yahoo"));
	}
	
	@Test 
	public void testFilterTextNotMatching() {
		WebElements elms = wFilter.textNotMatching(".*mail");
		assertEquals(2, elms.size());
		assertFalse(elms.map().toText().contains("gmail"));
		assertTrue(elms.map().toText().contains("aol"));
		assertTrue(elms.map().toText().contains("yahoo"));
	}
	
	@Test 
	public void testFilterAttrMatching() {
		WebElements elms = wFilter.attrMatching("href", ".*.com");
		assertEquals(4, elms.size());
		assertFalse(elms.map().toAttr("href").contains("www.aol.what"));
	}
	
	@Test 
	public void testFilterAttrNotMatching() {
		WebElements elms = wFilter.attrNotMatching("href", ".*.com");
		assertEquals(1, elms.size());
		assertTrue(elms.map().toAttr("href").contains("www.aol.what"));
	}
	
	@Test 
	public void testFilterAttrContaining() {
		WebElements elms = wFilter.attrContaining("href", ".com");
		assertEquals(4, elms.size());
		assertFalse(elms.map().toAttr("href").contains("www.aol.what"));
	}
	
	@Test 
	public void testFilterAttrNotContaining() {
		WebElements elms = wFilter.attrNotContaining("href", ".com");
		assertEquals(1, elms.size());
		assertTrue(elms.map().toAttr("href").contains("www.aol.what"));
	}
	
	
	
	
}
