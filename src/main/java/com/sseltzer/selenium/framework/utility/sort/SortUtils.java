
package com.sseltzer.selenium.framework.utility.sort;

import java.util.List;

import com.google.common.collect.Ordering;

/**
 *
 *
 * SortUtil.java
 *
 * @author ckiehl Aug 16, 2014
 */
public class SortUtils {

	public static <T extends Comparable<? super T>> Boolean isSortedDecending(List<T> inputList) {
		return Ordering.natural().reverse().isOrdered(inputList);
	}
	
	public static <T extends Comparable<? super T>> Boolean isSortedAscending(List<T> inputList) {
		return Ordering.natural().isOrdered(inputList);
	}
}
