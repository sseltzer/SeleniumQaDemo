
package com.sseltzer.selenium.internal.test.tests.jsoup._test_data;


/**
 *
 *
 * TestHtmlDoc.java
 *
 * @author ckiehl Aug 25, 2014
 */
public class TestHtmlDoc {
	
	private static String testHtml = ""
			+"<!DOCTYPE html>"
			+"<html>"
			+ "<head>"
			+  "<meta charset=\"UTF-8\">"
			+  "<title>Test Data</title>"
			+ "</head>"
			+ "<body>"
			+  "<h1>This is a Page Title</h1>"
			+  "<p>"
			+  	"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod "
			+  	"tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, "
			+  	"<a href=\"www.google.com\">quis </a>nostrud exercitation ullamco laboris nisi "
			+  	"ut aliquip ex ea commodo consequat."
			+ 	"</p>"
			+	"<ul>"
			+		"<li><a href=\"www.yahoo.com\">yahoo</a></li>"
			+		"<li><a href=\"www.aol.what\">aol</a></li>"
			+		"<li><a href=\"www.gmail.com\">gmail</a></li>"
			+		"<li><a href=\"www.hotmail.com\">hotmail</a></li>"
			+		"<li><a href=\"www.somemail.com\">somemail</a></li>"
			+	"</ul>"
			+ "</body>"
			+ "</html>";
	
	public static String getContents() {
		return testHtml;
	}
}
