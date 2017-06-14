
package com.sseltzer.selenium.internal.test.tests.utility.sort;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.sseltzer.selenium.framework.utility.sort.SortUtils;

/**
 *
 *
 * TestSortUtils.java
 *
 * @author ckiehl Aug 16, 2014
 */
public class TestSortUtils {
	
	private static List<String> decending = Arrays.asList("z", "y", "x", "w" );
	private static List<String> acending = Arrays.asList("a", "b", "c", "d" );
	private static List<String> mixed = Arrays.asList("x", "b", "t", "d" );

	@Test 
	public void testIsSortedDecendingReturnsTrueOnDecendingVals() {
		assertTrue(SortUtils.isSortedDecending(decending));
		assertFalse(SortUtils.isSortedDecending(acending));
	}
	
	@Test 
	public void testIsSortedAcendingReturnsTrueOnAcecendingVals() {
		assertTrue(SortUtils.isSortedAscending(acending));
		assertFalse(SortUtils.isSortedDecending(acending));
	}
	
	@Test 
	public void testIsSortedReturnsFasleOnMixedVals() {
		assertFalse(SortUtils.isSortedAscending(mixed));
		assertFalse(SortUtils.isSortedDecending(mixed));
	}
	
	@Test 
	public void testIsSortedReturnsTrueAmpersandedVals() {
		List<String> asdf = Arrays.asList("C&", "CA");
		assertTrue(SortUtils.isSortedAscending(asdf));
	}
}
