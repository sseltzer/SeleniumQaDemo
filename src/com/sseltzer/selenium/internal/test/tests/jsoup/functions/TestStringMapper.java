
package com.sseltzer.selenium.internal.test.tests.jsoup.functions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sseltzer.selenium.framework.jsoup.collections.RList;
import com.sseltzer.selenium.framework.jsoup.functions.StringMapper;

/**
 *
 *
 * TestStringMapper.java
 *
 * @author ckiehl Sep 8, 2014
 */
public class TestStringMapper {
	
	@Test
	public void testMapperRetrunsNewTransformedList() {
		String[] strings = {"hElLO","wOrlD"};
		StringMapper sMapper = new StringMapper(RList.of(strings));
		
		RList<String> upper = sMapper.toUpperCase();
		assertEquals("HELLO", upper.head());
		assertEquals("WORLD", upper.get(1));
		
		StringMapper sMapper2 = new StringMapper(upper);
		RList<String> lower = sMapper2.toLowerCase();
		assertEquals("hello", lower.head());
		assertEquals("world", lower.get(1));
		
		// original list not changed
		assertEquals("HELLO", upper.head());
		
		StringMapper sMapper3 = new StringMapper(lower);
		RList<String> replaced = sMapper3.replace("h", "j");
		assertEquals("jello", replaced.head());
		assertEquals("world", replaced.get(1));
	}
}
