
package com.sseltzer.selenium.internal.test.tests.utility.strings;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sseltzer.selenium.framework.utility.strings.StringUtils;

/**
 *
 *
 * TestStringUtils.java
 *
 * @author ckiehl Aug 15, 2014
 */
public class TestStringUtils {

	@Test 
	public void containsDigitShouldReturnTrueIfNumberInString() {
		String hasNum1 = "8.5 miles away"; 
		String hasNum2 = "at end of string 124"; 
		String hasNum3 = "In the Middl3 of String"; 
		String hasNum4 = "886"; 
		String noNumStr = "Nothinn here"; 
		String noNumStr2 = "~!@#$%^&*()_+"; 
		assertTrue(StringUtils.containsDigit(hasNum1));
		assertTrue(StringUtils.containsDigit(hasNum2));
		assertTrue(StringUtils.containsDigit(hasNum3));
		assertTrue(StringUtils.containsDigit(hasNum4));
		assertFalse(StringUtils.containsDigit(noNumStr));
		assertFalse(StringUtils.containsDigit(noNumStr2));
	}
	
}
