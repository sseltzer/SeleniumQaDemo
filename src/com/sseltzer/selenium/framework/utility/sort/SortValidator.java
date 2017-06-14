package com.sseltzer.selenium.framework.utility.sort;

/**
 * Interface for validating whether or not a String s1 and String s2 are in
 * alphabetical order (ascending or descending)
 * 
 * @author ckiehl
 * 
 */
public abstract class SortValidator {

	public abstract boolean isSorted(String s1, String s2);

	private static int compare(String s1, String s2) {
		return s1.toLowerCase().compareTo(s2.toLowerCase());
	}

	private static class AscendingValidator extends SortValidator {
		@Override
		public boolean isSorted(String s1, String s2) {
			return (compare(s1, s2) <= 0);
		}
	}

	private static class DescendingValidator extends SortValidator {
		@Override
		public boolean isSorted(String s1, String s2) {
			System.out.println(compare(s1, s2));
			return (compare(s1, s2) >= 0);
		}
	}

	public static SortValidator ascending() {
		return SortType.ASCENDING.getValidator();
	}

	public static SortValidator descending() {
		return SortType.DESCENDING.getValidator();
	}

	private enum SortType {
		ASCENDING(new AscendingValidator()), 
		DESCENDING(new DescendingValidator());
		private SortValidator validator;

		private SortType(SortValidator validator) {
			this.validator = validator;
		}

		public SortValidator getValidator() {
			return validator;
		}
	}
}
