
package com.sseltzer.selenium.framework.jsoup.elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Function;
import com.sseltzer.selenium.framework.jsoup.collections.RList;
import com.sseltzer.selenium.framework.jsoup.collections.SoupCollection;
import com.sseltzer.selenium.framework.jsoup.collections.RList.Cons;
import com.sseltzer.selenium.framework.jsoup.collections.RList.Nil;
import com.sseltzer.selenium.framework.jsoup.functions.GroupFilter;
import com.sseltzer.selenium.framework.jsoup.functions.GroupMapper;

/**
 *
 *
 * WebElementsGroup.java
 *
 * @author ckiehl Aug 25, 2014
 */
public class WebElementsGroup implements 
		SoupCollection<WebElementsGroup, WebElements>, Iterable<WebElements> { 
	
	private RList<WebElements> elementGroups;
	
	public WebElementsGroup(List<WebElements> elements) {
		WebElements[] elms = elements.toArray(new WebElements[elements.size()]);
		this.elementGroups = RList.of(elms);
	}
	
	public WebElementsGroup(RList<WebElements> elements) {
		this.elementGroups = elements;
	}
	
	public WebElements flatten() {
		List<WebElement> flattenedElements = new ArrayList<WebElement>(); 
		for (WebElements e : this.elementGroups)
			flattenedElements.addAll(e.toList());
		return new WebElements(flattenedElements);
	}
	
	public <F> RList<F> map(Function<WebElements, F> f){
		return this.map(this.elementGroups, f);
	}
	
	private <F> RList<F> map(RList<WebElements> rList, Function<WebElements, F> f) {
		if (rList.isEmpty()) return new Nil<F>(); 
		else return new Cons<F>(f.apply(rList.head()), map(rList.tail(), f));
	}
	
	public GroupMapper map() {
		return new GroupMapper(this.elementGroups);
	}
	
	public GroupFilter filter() {
		return new GroupFilter(this.elementGroups);
	}

	public Iterator<WebElements> iterator() {
		return this.elementGroups.iterator();
	}

	public WebElementsGroup take(Integer i) {
		return new WebElementsGroup(this.elementGroups.take(i));
	}

	public WebElementsGroup drop(Integer i) {
		return new WebElementsGroup(this.elementGroups.drop(i));
	}

	public WebElementsGroup init() {
		return new WebElementsGroup(this.elementGroups.init());
	}

	public WebElementsGroup tail() {
		return new WebElementsGroup(this.elementGroups.tail()); 
	}

	public WebElements head() {
		return this.elementGroups.head(); 
	}

	public WebElements last() {
		return this.elementGroups.last();
	}

	public WebElements get(Integer i) {
		return this.elementGroups.get(i);
	}

	public int size() {
		return this.elementGroups.size(); 
	}

	public boolean isEmpty() {
		return this.elementGroups.isEmpty();
	}

	public List<WebElements> toList() {
		return this.elementGroups.toList();
	}
	
}
