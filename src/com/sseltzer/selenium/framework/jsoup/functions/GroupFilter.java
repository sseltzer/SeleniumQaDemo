
package com.sseltzer.selenium.framework.jsoup.functions;

import com.google.common.base.Predicate;
import com.sseltzer.selenium.framework.jsoup.collections.RList;
import com.sseltzer.selenium.framework.jsoup.elements.WebElements;
import com.sseltzer.selenium.framework.jsoup.elements.WebElementsGroup;

/**
 *
 *
 * GroupFilter.java
 *
 * @author ckiehl Aug 25, 2014
 */
public class GroupFilter {
	
	private RList<WebElements> elementsGroup;

	public GroupFilter(RList<WebElements> list) {
		this.elementsGroup = list;
	}
	
	public WebElementsGroup sizeLessThan(final int size) {
		RList<WebElements> es = this.elementsGroup.filter(new Predicate<WebElements>() {
			public boolean apply(WebElements e) { return e.size() < size; }
		});
		return new WebElementsGroup(es);
	}
	
	public WebElementsGroup sizeEqualTo(final int size) {
		RList<WebElements> es = this.elementsGroup.filter(new Predicate<WebElements>() {
			public boolean apply(WebElements e) { return e.size() == size; }
		});
		return new WebElementsGroup(es);
	}
	
	public WebElementsGroup sizeGreaterThan(final int size) {
		RList<WebElements> es = this.elementsGroup.filter(new Predicate<WebElements>() {
			public boolean apply(WebElements e) { return e.size() > size; }
		});
		return new WebElementsGroup(es);
	}

}
