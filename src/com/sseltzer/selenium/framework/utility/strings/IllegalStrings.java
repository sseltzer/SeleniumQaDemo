package com.sseltzer.selenium.framework.utility.strings;

/**
 *
 *
 * IllegalChars.java
 *
 * @author ckiehl May 14, 2014
 */
public class IllegalStrings {

	public static final String[] SPECIAL_CHARACTERS = arrayify("~!@#$%^&*()<>?/:;{}[]\\=\"");

	public static String[] arrayify(String str) {
		String[] array = new String[str.length()];
		for (int i = 0; i < str.length(); ++i) array[i] = String.valueOf(str.charAt(i));
		return array;
	}

	public static String[] appendSpecial(String prefix) {
		String[] special = SPECIAL_CHARACTERS;
		for (int i = 0; i < special.length; ++i) special[i] = prefix + special[i];
		return special;
	}
}
