package com.sseltzer.selenium.framework.strings.maps;

import com.sseltzer.selenium.framework.strings.ApplicationStrings;
import com.sseltzer.selenium.framework.strings.StringLoader;

/**
 * This enumeration is intended to provide a mapping to each of the Strings in
 * the respective properties file. For a Typical .properties file, we might have
 * a handler that we create using a ResourceBundle, then use that handler
 * through the entire application to grab Strings when we need them. This
 * approach is fine, except it tightly couples the KVP mapping of the file to
 * the application. The alternative to this, is what we do here. Create an
 * enumeration which represents the strings, but without requiring the key to be
 * written throughout the framework when a String is desired. This also provides
 * us a convenient way to use dot notation and the auto complete functions of
 * the IDE to quickly locate what we want. Now that we have a decoupling of
 * application to KVP, we still need to map this class to the keys in the
 * .properties class. This is undesirable. The alternative is to not require
 * keys at all, but instead to autoload them. The caveat is that we still need
 * to reference the enums to an exact string in the file. This is done by the
 * <b>ordering<b>. The order of these enums must reflect the order of the
 * strings in the .properties file. So, element 0 in this file must have its
 * String located as the first element in the .properties file. <br>
 * <br>
 * The disadvantage here is that now when one is updated, the other must be
 * updated with the exact ordering. This is not that big of an issue, since when
 * you add a String to one you must add it to the other anyways. <br>
 * <br>
 * The advantage here is that we have now implemented two fold decoupling. The
 * application is decoupled from the .properties files entirely and the map
 * itself is decoupled from the keys. Simply add a new String to the end of the
 * .properties file, and add a new enum to the end of this list, and it will
 * immediately be available to the rest of the application with zero effort. The
 * .properties files are numbered, with a prefix and follow the convention
 * PREFIX.00, PREFIX.01, ..., PREFIX.nn <br>
 * <br>
 * The PublicErrorStrings file contains Strings that are used in form filling
 * error messages in ErrorManager when a JUnit assert fails, this is especially
 * used throughout JUnitTester.
 * 
 * @author Sean Seltzer
 *
 */
public enum PublicErrorStrings {
	URL_MATCHES, URL_CONTAINS, URL_NOT_CONTAINS, ELEMENT_NOT_EXIST, ELEMENTS_EXIST, ELEMENT_NOT_PRESENT, ELEMENT_NO_LONGER, TITLE_MATCHES, TITLE_CONTAINS, CSS_CONTAINS, DATA_PAGE, TEXT_MATCHES, TEXT_CONTAINS, TEXT_CONTAINS_ONE_OF, TEXT_SAME, TEXT_BLANK, COUNT_MISMATCH, ELEMENT_ATTR_NOT_EXIST, ELEMENT_ATTR_MISMATCH, ELEMENT_ATTR_ILLEGAL, ELEMENT_VISIBLE, ELEMENT_NOT_VISIBLE, INVALID_VPREFID, INVALID_CONTENTZONE, JUNIT_TEST, ELEMENT_NOT_EXIST_AT_ELEMENT, ELEMENT_NOT_CLICKABLE, ELEMENT_STALE, MOVE_OUT_OF_BOUNDS, INVALID_STATE, NO_WINDOW, NO_FRAME, TIMEOUT, UNKNOWN, BAD_TAG_NAME, UNSUPPORTED_OPERATION, FAILED_FORM_FILL, IMPROPER_SORT, INVALID_BREAKPOINT, ATTRIBUTE_NOT_BLANK, UNKNOWN_VIDEO_TYPE, CUSTOM_MESSAGE;

	private String message = null;

	private static int loadIndex = 0;
	private static ApplicationStrings appStrings = null;

	private PublicErrorStrings() {
		this.message = retrieveStr();
	}

	private String retrieveStr() {
		if (appStrings == null)
			appStrings = StringLoader.getApplicationStrings();
		return appStrings.getPublicStrings().get(loadIndex++);
	}

	@Override
	public String toString() {
		return message;
	}

}