
package com.sseltzer.selenium.framework.jsoup.collections;

import com.sseltzer.selenium.framework.jsoup.functions.StringFilter;
import com.sseltzer.selenium.framework.jsoup.functions.StringMapper;

/**
 *
 *
 * RStringList.java
 *
 * @author ckiehl Sep 4, 2014
 */
public class RStringList extends RList<String> {
	
	private RList<String> elms;
	
	public RStringList(RList<String> elms) {
		this.elms = elms;
	}

	@Override
	public StringMapper map() {
		return new StringMapper(this);
	}
	
	@Override 
	public StringFilter filter() {
		return new StringFilter(this);
	}

	@Override
	public String head() { 
		return this.elms.head(); 
	}

	@Override
	public RList<String> tail() { 
		return this.elms.tail(); 
	}
	
	@Override 
	public boolean isEmpty() {
		return this.elms.isEmpty();
		
	}
	
	
	
}
