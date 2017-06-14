package com.sseltzer.selenium.framework.verification.string;

import java.util.ArrayList;
import java.util.List;

import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPage;
import com.sseltzer.selenium.framework.verification.junit.TestScript;

/**
 * Tests that replacement characters (These guys: http://bit.ly/1k0sEKY) do not
 * appear on the website. At the time of writing, they were littered all over
 * the place.
 */
public class UnicodeTester extends TestScript {
	public static final String UNICODE_ERROR_CHAR = "\uFFFD";
	public static final String ESCAPED_ERROR_CHAR = "&#65533;";

	public static void verifyNoUnicodeErrorsOnPage(WebPage webPage) {
		String source = getPageSource(webPage);
		List<String> errors = collectUnicodeErrors(source);
		if (!errors.isEmpty()) {
			throw new FrameworkException(formatErrors(errors));
		}
	}
	
	private static String getPageSource(WebPage webPage) {
		return webPage.getBrowser().getPageSource();
	}
	
	public static List<String> collectUnicodeErrors(String source) {
		List<String> errors = new ArrayList<String>(); 
		for (String line : source.split("\n")) {
			if (line.contains(UNICODE_ERROR_CHAR) || line.contains(ESCAPED_ERROR_CHAR))
				errors.add(line.trim());
		}
		return errors;
	}
	
	public static String formatErrors(List<String> errors) {
		String output = "\n\nUnicode Errors found on page:";
		for (String error : errors) 
			output += "\n  Line:  " + error; 
		output += "\n\n";
		return output;
	}
}
