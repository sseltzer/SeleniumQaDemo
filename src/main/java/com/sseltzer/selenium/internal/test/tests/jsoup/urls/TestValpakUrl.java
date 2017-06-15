
package com.sseltzer.selenium.internal.test.tests.jsoup.urls;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.sseltzer.selenium.framework.jsoup.urls.ValpakUrl;

/**
 *
 *
 * TestValpakUrl.java
 *
 * @author ckiehl Sep 2, 2014
 */
public class TestValpakUrl {
	

	@Test 
	public void testValpakUrlReturnsCorrectTypeBasedOnEnvVar() {
		ValpakUrl.setMockEnv(ImmutableMap.of("env", ""));
		assertEquals("https://vpdev.valpak.com/", ValpakUrl.from(""));
		
		ValpakUrl.setMockEnv(ImmutableMap.of("env", "dev"));
		assertEquals("https://vpdev.valpak.com/", ValpakUrl.from(""));
		
		ValpakUrl.setMockEnv(ImmutableMap.of("env", "tst"));
		assertEquals("https://vptst.valpak.com/", ValpakUrl.from(""));
		
		ValpakUrl.setMockEnv(ImmutableMap.of("env", "prd"));
		assertEquals("https://www.valpak.com/", ValpakUrl.from(""));
		
		ValpakUrl.setMockEnv(ImmutableMap.of("env", "prd1"));
		assertEquals("https://vpcom1.valpak.com:8943/", ValpakUrl.from(""));
		
		ValpakUrl.setMockEnv(ImmutableMap.of("env", "prd2"));
		assertEquals("https://vpcom2.valpak.com:8943/", ValpakUrl.from(""));
		
		ValpakUrl.setMockEnv(ImmutableMap.of("env", "prd3"));
		assertEquals("https://vpcom3.valpak.com:8943/", ValpakUrl.from(""));
		
		ValpakUrl.setMockEnv(ImmutableMap.of("env", "prd4"));
		assertEquals("https://vpcom4.valpak.com:8943/", ValpakUrl.from(""));
	}
	
}
