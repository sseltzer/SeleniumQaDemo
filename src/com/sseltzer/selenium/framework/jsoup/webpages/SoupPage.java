
package com.sseltzer.selenium.framework.jsoup.webpages;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.sseltzer.selenium.framework.jsoup.elements.WebElement;
import com.sseltzer.selenium.framework.jsoup.elements.WebElements;
import com.sseltzer.selenium.framework.utility.parallelism.ConcurrentWebTools;

/**
 *
 *
 * WebPage.java
 *
 * @author ckiehl Aug 20, 2014
 */
public class SoupPage {
	
	private final Document _document;

	public SoupPage(Document _document) {
		this._document = _document;
	}
	
	public static SoupPage of(String url) {
		Document doc = ConcurrentWebTools.openAndParse(url);
		return new SoupPage(doc);
	}
	
	public WebElements select(String cssQuery) {
		List<WebElement> output = new ArrayList<WebElement>(); 
		for (Element e: this._document.select(cssQuery))
			output.add(new WebElement(e));
		return new WebElements(output);
	}
	
	public String getUrl() {
		return this._document.baseUri();
	}
	
	@Override 
	public String toString() {
		return this._document.toString();
	}
	
	
}
