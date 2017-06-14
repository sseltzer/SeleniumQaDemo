
package com.sseltzer.selenium.framework.jsoup.functions;

import com.google.common.base.Function;
import com.sseltzer.selenium.framework.jsoup.collections.RList;
import com.sseltzer.selenium.framework.jsoup.collections.RStringList;
import com.sseltzer.selenium.framework.jsoup.elements.WebElement;

/**
 *
 *
 * WebElmMapper.java
 *
 * @author ckiehl Sep 5, 2014
 */
public class WebElmMapper extends Mapper {
	
	private RList<WebElement> elms;

	public WebElmMapper(RList<WebElement> elements) {
		this.elms = elements;
	}
	
	public RStringList toText() {
		return new RStringList(this.elms.map(new Function<WebElement, String>() {
			public String apply(WebElement e) { return e.text(); }
		}));
	}
	
	public RStringList toAttr(final String attrName) {
		return new RStringList(this.elms.map(new Function<WebElement, String>() {
			public String apply(WebElement e) { return e.attr(attrName); }
		}));
	}
}
