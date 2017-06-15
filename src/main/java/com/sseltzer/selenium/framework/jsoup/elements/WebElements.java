
package com.sseltzer.selenium.framework.jsoup.elements;

import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sseltzer.selenium.framework.jsoup.collections.RList;
import com.sseltzer.selenium.framework.jsoup.collections.SoupCollection;
import com.sseltzer.selenium.framework.jsoup.functions.WebElmFilter;
import com.sseltzer.selenium.framework.jsoup.functions.WebElmMapper;


/**
 *
 *
 * WebElements.java
 *
 * @author ckiehl Aug 20, 2014
 */
public class WebElements implements 
		SoupCollection<WebElements, WebElement>, Iterable<WebElement> {
	
	private final RList<WebElement> elements; 
	
	public WebElements() { this.elements = RList.of(); }
	
	public WebElements(RList<WebElement> elements) { this.elements = elements; }

	public WebElements(List<WebElement> elements) {
		WebElement[] es = elements.toArray(new WebElement[elements.size()]); 
		this.elements = RList.of(es); 
	}
	
	public WebElements(Elements elements) {
		WebElement[] elms = new WebElement[elements.size()];
		int i = 0;
		for (Element e : elements)
			elms[i++] = new WebElement(e);
		this.elements = RList.of(elms);
	}
	
	public WebElements tail() {
		return new WebElements(this.elements.tail());
	}
	public WebElements take(Integer i) {
		return new WebElements(this.elements.take(i));
	}
	public WebElements drop(Integer i) {
		return new WebElements(this.elements.drop(i));
	}
	public WebElements init() {
		return new WebElements(this.elements.init());
	}
	
	public WebElement get(Integer i) {
		return elements.get(i);
	}
	public WebElement head() {
		return elements.head();
	}
	public WebElement last() {
		return elements.last();
	}

	public boolean isEmpty() {
		return elements.isEmpty();
	}

	public int size() {
		return elements.size();
	}

	public Iterator<WebElement> iterator() {
		return this.elements.iterator();
	}
	
	public WebElmMapper map() {
		return new WebElmMapper(this.elements);
	}
	
	public WebElmFilter filter() {
		return new WebElmFilter(this.elements);
	}
	
	public List<WebElement> toList() {
		return this.elements.toList();
	}
	
	public String url() {
		return this.elements.get(0).baseUri();
	}
}









