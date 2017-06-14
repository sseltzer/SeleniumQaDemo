
package com.sseltzer.selenium.internal.test.tests.utility.strings;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sseltzer.selenium.framework.utility.strings.WordUtils;

/**
 *
 *
 * TestWordUtils.java
 *
 * @author ckiehl Aug 15, 2014
 */
public class TestWordUtils {

	@Test
	public void firstWordShouldReturnFirstWordFromInputString() {
		String example = "Hello world! How are ya?"; 
		assertEquals("Hello", WordUtils.firstWord(example));
	}
}
