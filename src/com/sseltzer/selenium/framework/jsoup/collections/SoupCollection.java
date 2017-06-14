
package com.sseltzer.selenium.framework.jsoup.collections;

import java.util.List;


/**
 *
 *
 * FunctionalCollection.java
 *
 * @author ckiehl Aug 20, 2014
 */
public interface SoupCollection<T, V> {
	
	public T take(Integer i);
	
	public T drop(Integer i); 
	
	public T init(); 
	
	public T tail(); 
	
	public V head();
	
	public V last();
	
	public V get(Integer i );
	
	public int size(); 
	
	public boolean isEmpty();
	
	public List<V> toList();
	
}
