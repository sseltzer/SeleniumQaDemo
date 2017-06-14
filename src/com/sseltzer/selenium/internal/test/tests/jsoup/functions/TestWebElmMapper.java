
package com.sseltzer.selenium.internal.test.tests.jsoup.functions;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sseltzer.selenium.framework.jsoup.elements.WebElements;
import com.sseltzer.selenium.framework.jsoup.functions.WebElmMapper;
import com.sseltzer.selenium.framework.jsoup.toolkit.ParallelWebTools;
import com.sseltzer.selenium.framework.jsoup.webpages.SoupPage;
import com.sseltzer.selenium.framework.utility.list.ListUtils;
import com.sseltzer.selenium.internal.test.tests.jsoup._test_data.TestHtmlDoc;

/**
 *
 *
 * TestMapper.java
 * 
 * Test Data looks like: 
 * 
 * 		...
 * 		"<li><a href=\"www.yahoo.com\">yahoo</a></li>"
 * 		"<li><a href=\"www.aol.what\">aol</a></li>"
 * 		"<li><a href=\"www.gmail.com\">gmail</a></li>"
 * 		"<li><a href=\"www.hotmail.com\">hotmail</a></li>"
 * 		"<li><a href=\"www.somemail.com\">somemail</a></li>"
 * 		....
 * 
 * @author ckiehl Aug 25, 2014
 */
public class TestWebElmMapper {
	
	private WebElmMapper wMap;
	
	@Before
	public void setUp() {
		SoupPage page = ParallelWebTools.parseString(TestHtmlDoc.getContents());
		WebElements elements = page.select("li a");
		wMap = elements.map();
	}
	
	@Test
	public void testMapToTextReturnsListOfTextExtractedFromWebElements() {
		List<String> expected = Arrays.asList("yahoo", "aol", "gmail", "hotmail", "somemail");
		List<String> textElements = wMap.toText().toList();
		assertEquals(0, ListUtils.disjunction(expected, textElements).size());
	}
	
	@Test
	public void testMapToAttrReturnsListOfAttrsExtractedFromWebElements() {
		List<String> expected = Arrays.asList(
			"www.yahoo.com", "www.aol.what", "www.gmail.com", 
			"www.hotmail.com", "www.somemail.com");
		List<String> attrs = wMap.toAttr("href").toList();
		assertEquals(0, ListUtils.disjunction(expected, attrs).size());
	}
	
	
	
}
