package com.sseltzer.selenium.internal.test.tests.verification;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPage;
import com.sseltzer.selenium.framework.verification.junit.TestScript;
import com.sseltzer.selenium.framework.verification.string.UnicodeTester;
import com.sseltzer.selenium.internal.test.website.TestWebsite;

/**
 *
 *
 * TestUnicodeTester.java
 *
 * @author ckiehl Jun 13, 2014
 */
public class TestUnicodeTester extends TestScript {

	private static String testData = "<body>\n"
			+ "<p>Hello. I a\uFFFDm a test string</p>\n"
			+ "<p>I contain two&#65533; errors</p>\n";

	@Test(expected = FrameworkException.class)
	public void testUnicodeTester() {
		WebPage webPage = new TestWebsite(getBrowser()).open("printable/UPS-PACKAGING-SERVICES-INC/15835?addressId=1646900&offerId=1504430");
		UnicodeTester.verifyNoUnicodeErrorsOnPage(webPage);
	}

	@Test
	public void testErrorErrosAreFound() {
		assertTrue(testData.contains(UnicodeTester.ESCAPED_ERROR_CHAR));
		assertTrue(testData.contains(UnicodeTester.UNICODE_ERROR_CHAR));
	}

	@Test
	public void testErrorCollector() {
		Integer expectedNumberOfErrors = 2;
		Integer result = UnicodeTester.collectUnicodeErrors(testData).size();
		assertEquals(expectedNumberOfErrors, result);
	}

	@Test
	public void testErrorFormatting() {
		String results = UnicodeTester.formatErrors(UnicodeTester.collectUnicodeErrors(testData));
		String expectedFirstLine = "\n\nUnicode Errors found on page:";
		String expectedSecndLine = "  Line:  " + "<p>Hello. I a\uFFFDm a test string</p>\n";
		assertTrue(results.contains(expectedFirstLine));
		assertTrue(results.contains(expectedSecndLine));
	}
}
