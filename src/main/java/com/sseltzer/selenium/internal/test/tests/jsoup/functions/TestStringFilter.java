
package com.sseltzer.selenium.internal.test.tests.jsoup.functions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sseltzer.selenium.framework.jsoup.collections.RList;
import com.sseltzer.selenium.framework.jsoup.collections.RStringList;

/**
 *
 *
 * StringFilter.java
 *
 * @author ckiehl Sep 8, 2014
 */
public class TestStringFilter {
	
	@Test
	public void testStringMatching() {
		RStringList items = new RStringList(RList.of("apple", "pear", "banana"));
		RList<String> filtered = items.filter().stringMatching("apple");
		assertEquals(1, filtered.size());
		
		RList<String> filtered2 = items.filter().stringMatching(".*a.*");
		assertEquals(3, filtered2.size());
		
		RList<String> filtered3 = items.filter().stringMatching(".*p.*");
		assertEquals(2, filtered3.size());
		
		RList<String> filtered4 = items.filter().stringMatching("a");
		assertEquals(0, filtered4.size());
	}
	
	@Test
	public void testStringNotMatching() {
		RStringList items = new RStringList(RList.of("apple", "pear", "banana"));
		RList<String> filtered = items.filter().stringNotMatching("apple");
		assertEquals(2, filtered.size());
		
		RList<String> filtered2 = items.filter().stringNotMatching(".*a.*");
		assertEquals(0, filtered2.size());
		
		RList<String> filtered3 = items.filter().stringNotMatching(".*p.*");
		assertEquals(1, filtered3.size());
		
		RList<String> filtered4 = items.filter().stringNotMatching("a");
		assertEquals(3, filtered4.size());
	}
	
	@Test
	public void testStringContaining() {
		RStringList items = new RStringList(RList.of("apple", "pear", "banana"));
		RList<String> filtered = items.filter().stringContaining("apple");
		assertEquals(1, filtered.size());
		
		RList<String> filtered2 = items.filter().stringContaining("a");
		assertEquals(3, filtered2.size());
		
		RList<String> filtered3 = items.filter().stringContaining("p");
		assertEquals(2, filtered3.size());
		
		RList<String> filtered4 = items.filter().stringContaining("asd");
		assertEquals(0, filtered4.size());
	}
	
	@Test
	public void testStringNotContaining() {
		RStringList items = new RStringList(RList.of("apple", "pear", "banana"));
		RList<String> filtered = items.filter().stringNotContaining("apple");
		assertEquals(2, filtered.size());
		
		RList<String> filtered2 = items.filter().stringNotContaining("a");
		assertEquals(0, filtered2.size());
		
		RList<String> filtered3 = items.filter().stringNotContaining("p");
		assertEquals(1, filtered3.size());
		
		RList<String> filtered4 = items.filter().stringNotContaining("asd");
		assertEquals(3, filtered4.size());
	}
	
}
