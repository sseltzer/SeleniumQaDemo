
package com.sseltzer.selenium.internal.test.tests.jsoup.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.sseltzer.selenium.framework.jsoup.collections.RList;

/**
 *
 *
 * RlistTest.java
 *
 * @author ckiehl Sep 2, 2014
 */
public class RlistTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test 
	public void testRecursiveGetReturnsExpectedElement() {
		RList<Integer> ints = RList.of(1,2,3,4,5,6,7,8,9,10);
		assertEquals(new Integer(1),  ints.get(0));
		assertEquals(new Integer(2),  ints.get(1));
		assertEquals(new Integer(3),  ints.get(2));
		assertEquals(new Integer(4),  ints.get(3));
	}
	
	@Test
	public void testGetThrowsOutOfBoundsOnSmallInput() {
		RList<Integer> ints = RList.of(1,2,3,4,5,6,7,8,9,10);
		exception.expect(IndexOutOfBoundsException.class);
		ints.get(-1);
	}
	
	@Test
	public void testGetThrowsOutOfBoundsOnLargeInput() {
		RList<Integer> ints = RList.of(1,2,3,4,5,6,7,8,9,10);
		exception.expect(IndexOutOfBoundsException.class);
		ints.get(100);
	}
	
	@Test 
	public void testDropReturnsListExcludingNElements() {
		RList<Integer> ints = RList.of(1,2,3,4,5,6,7,8,9,10);
		assertTrue(ints.drop(100).isEmpty());
		assertEquals(9, ints.drop(1).size());
		assertEquals(8, ints.drop(2).size());
		assertEquals(7, ints.drop(3).size());
		
		assertEquals(2, (int)ints.drop(1).head());
		assertEquals(3, (int)ints.drop(2).head());
		assertEquals(4, (int)ints.drop(3).head());
	}
	
	@Test 
	public void testOriginalListRemainsAfterDrop() {
		RList<Integer> ints = RList.of(1,2,3,4,5,6,7,8,9,10);
		assertTrue(ints.size() == 10);
		RList<Integer> ints2 = ints.drop(5);
		assertEquals(5, ints.drop(5).size());
		assertTrue(ints.size() == 10);
	}
	
	@Test 
	public void testTakeReturnsTopOfListUpToN() {
		RList<Integer> ints = RList.of(1,2,3,4,5,6,7,8,9,10);
		assertTrue(ints.take(0).isEmpty());
		assertEquals(1, ints.take(1).size());
		assertEquals(2, ints.take(2).size());
		assertEquals(3, ints.take(3).size());
		assertEquals(4, ints.take(4).size());
		assertEquals(ints.size(), ints.take(100).size());
		
		assertEquals(1, (int)ints.take(1).get(0));
		assertEquals(2, (int)ints.take(2).get(1));
		assertEquals(3, (int)ints.take(3).get(2));
	}
	
	@Test 
	public void testLastReturnsFinalElementInList() {
		RList<Integer> ints = RList.of(1,2,3,4,5,6,7,8,9,10);
		assertEquals(new Integer(10), ints.last());
		assertEquals(new Integer(4), ints.take(4).last());
	}
	
	@Test 
	public void testInitReturnsAllItemsInListExcludingLast() {
		RList<Integer> ints = RList.of(1,2,3,4,5,6,7,8,9,10);
		assertEquals(9, ints.init().size());
		assertEquals(9, ints.init().size());
	}
	
	@Test 
	public void testOriginalListRemainsAfterTake() {
		RList<Integer> ints = RList.of(1,2,3,4,5,6,7,8,9,10);
		assertTrue(ints.size() == 10);
		RList<Integer> ints2 = ints.take(5);
		assertTrue(ints.size() == 10);
	}
	
	@Test 
	public void testIteratorWalksEntireList() {
		RList<Integer> ints = RList.of(1,2,3,4,5,6,7,8,9,10);
		List<Integer> expected = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		int index = 0;
		for (Integer currentInt : ints) {
			assertEquals(expected.get(index), currentInt);
			index += 1;
		}
	}
	
	@Test 
	public void testMapAppliesFuncAndReturnsNewList() {
		RList<Integer> ints = RList.of(1,2,3,4,5,6,7,8,9,10);
		RList<Integer> newInts = ints.map(new Function<Integer, Integer>() {
			public Integer apply(Integer i) { return i * 100; }
		});
		// old list intact:
		assertEquals(new Integer(1), ints.get(0));
		assertEquals(new Integer(2), ints.get(1));
		// new list transformed: 
		assertEquals(new Integer(100), newInts.get(0));
		assertEquals(new Integer(200), newInts.get(1));
	}
	
	@Test 
	public void testMapEmptyListReturnsEmptyList() {
		RList<Integer> list1 = RList.of();
		RList<Integer> list2 = list1.map(new Function<Integer, Integer>() {
			public Integer apply(Integer i) { return i * 100;	}
		});
		assertTrue(list1.isEmpty());
		assertTrue(list2.isEmpty());
	}
	
	@Test 
	public void testFilterRetainsElementsMatchingPredicate() {
		RList<Integer> ints = RList.of(1,2,3,4,5,6,7,8,9,10);
		RList<Integer> filtered = ints.filter(new Predicate<Integer>() {
			public boolean apply(Integer i) { return i < 6; }
		});
		assertEquals(5, filtered.size());
		assertEquals(new Integer(5), filtered.last());
	}
	
	@Test 
	public void testForRangeReturnsNonInclusiveRangeOfIntegers() {
		RList<Integer> ints = RList.forRange(0, 10);
		assertEquals(10, ints.size());
		assertEquals(new Integer(0), ints.get(0)); 
		assertEquals(new Integer(9), ints.get(9));
		
		RList<Integer> intsRev = RList.forRange(10, 0);
		assertEquals(10, intsRev.size());
		assertEquals(new Integer(10), intsRev.get(0)); 
		assertEquals(new Integer(1), intsRev.get(9));
		
		RList<Integer> ints2 = RList.forRange(-10, 10);
		assertEquals(20, ints2.size());
		assertEquals(new Integer(-10), ints2.get(0)); 
		assertEquals(new Integer(9), ints2.get(19));
		
		RList<Integer> ints2Rev = RList.forRange(10, -10);
		assertEquals(20, ints2Rev.size());
		assertEquals(new Integer(10), ints2Rev.get(0)); 
		assertEquals(new Integer(-9), ints2Rev.get(19));
		
		RList<Integer> ints3 = RList.forRange(20);
		assertEquals(20, ints3.size());
		assertEquals(new Integer(0), ints3.get(0)); 
		assertEquals(new Integer(19), ints3.get(19));
	}
}
