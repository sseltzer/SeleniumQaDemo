
package com.sseltzer.selenium.internal.test.tests.jsoup.functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.sseltzer.selenium.framework.jsoup.collections.RList;
import com.sseltzer.selenium.framework.jsoup.toolkit.ParallelWebTools;
import com.sseltzer.selenium.framework.jsoup.webpages.SoupPage;
import com.sseltzer.selenium.internal.test.tests.jsoup._test_data.TestHtmlDoc;

/**
 *
 *
 * TestFilter.java
 *
 * @author ckiehl Aug 25, 2014
 */
public class TestFilter {
	
	@Test 
	public void testFilterTextContainingRetainsItemsMatchingTextCriteria() {
		SoupPage page = ParallelWebTools.parseString(TestHtmlDoc.getContents());
		List<String> listElements = page.select("li").map().toText().toList();
		assertEquals("yahoo", listElements.get(0));
		assertEquals("aol",   listElements.get(1));
		assertEquals("gmail", listElements.get(2));
		
		List<String> filtered = page.select("li")
				.filter().textContaining("mail")
				.map().toText().toList();
		
		assertEquals("gmail", filtered.get(0));
		assertEquals("hotmail", filtered.get(1));
		assertEquals("somemail", filtered.get(2));
	}
	
	@Test 
	public void testFilterTextContainingDropsItemsNotMatchingTextCriteria() {
		SoupPage page = ParallelWebTools.parseString(TestHtmlDoc.getContents());
		List<String> listElements = page.select("li").map().toText().toList();
		
		assertTrue(listElements.contains("aol"));
		List<String> filtered = page.select("li")
				.filter().textContaining("mail")
				.map().toText().toList();
		assertFalse(filtered.contains("aol"));
	}
	
	@Test 
	public void testFilterTextMatchingRetainsItemsExactlyMatchingTextCriteria() {
		SoupPage page = ParallelWebTools.parseString(TestHtmlDoc.getContents());
		RList<String> listElements = page.select("li").map().toText();
		
//		assertTrue(listElements.contains("gmail"));
//		assertTrue(listElements.contains("yahoo"));
//		assertTrue(listElements.contains("aol"));
		
//		List<String> filtered = page.select("li")
//				.filter().textMatching("gmail")
//				.map().toText().toList();
//		assertTrue(filtered.contains("gmail"));
//		assertFalse(filtered.contains("yahoo"));
//		assertFalse(filtered.contains("aol"));
	}
	
	@Test 
	public void testFilterAttrMatchingDropsItemsNotExactMatching() {
		SoupPage page = ParallelWebTools.parseString(TestHtmlDoc.getContents());
		List<String> listElements = page.select("li").map().toText().toList();

		assertTrue(listElements.contains("hotmail"));
		assertTrue(listElements.contains("yahoo"));
		assertTrue(listElements.contains("aol"));
		
		List<String> filtered = page.select("li a")
				.filter().attrMatching("href", "www.hotMAIL.com")
				.map().toText().toList();
		
		assertEquals(0, filtered.size()); // Nothing matched
	}
	
	@Test 
	public void testFilterAttrMatchingRetainsItemsExactMatching() {
		SoupPage page = ParallelWebTools.parseString(TestHtmlDoc.getContents());
		List<String> listElements = page.select("li").map().toText().toList();

		assertTrue(listElements.contains("hotmail"));
		assertTrue(listElements.contains("yahoo"));
		assertTrue(listElements.contains("aol"));
		
		List<String> filtered = page.select("li a")
				.filter().attrMatching("href", "www.hotmail.com")
				.map().toText().toList();
		
		assertEquals(1, filtered.size());
		assertTrue(filtered.contains("hotmail"));
	}
	
	@Test 
	public void testFilterAttrContainingRetainsAllItemsMatching() {
		SoupPage page = ParallelWebTools.parseString(TestHtmlDoc.getContents());
		
		List<String> listElements = page.select("li").map().toText().toList();
		assertEquals(5, listElements.size());
		
		List<String> filtered = page.select("li a")
				.filter().attrContaining("href", "www")
				.map().toText().toList();
		assertEquals(5, filtered.size()); // 'www' matched in every url
	}
	
}
