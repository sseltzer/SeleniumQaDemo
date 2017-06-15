
package com.sseltzer.selenium.framework.utility.strings;
/**
 *
 *
 * StringUtils.java
 *
 * @author ckiehl Aug 15, 2014
 */
public class StringUtils {

	public static boolean containsDigit(String string) {
		return string.matches(".*\\d.*");
	}
}
