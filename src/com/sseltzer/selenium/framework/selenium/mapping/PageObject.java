package com.sseltzer.selenium.framework.selenium.mapping;

/**
 * The PageObject is a container that provides a reference to an element on a web page. The String
 * contained within provides either a JavaScript ID or css selector that may reference one or more
 * page elements. A PageObject may be an input, div, button, or any other kind of HTML reference. It
 * may also reference a collection of the above. The PageObject is currently a simple container, but
 * as all mappings are required to use it, it may be easily extended in the future.
 * 
 * @author Sean Seltzer
 *
 */
public class PageObject {

	private String tag;

	public PageObject(String tag) {
		this.tag = tag;
	}

	public String toString() {
		return tag;
	}
}
