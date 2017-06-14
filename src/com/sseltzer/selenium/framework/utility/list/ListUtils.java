
package com.sseltzer.selenium.framework.utility.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *
 *
 * ListUtils.java
 *
 * @author ckiehl Aug 18, 2014
 */
public class ListUtils {
	
	public static <E> ArrayList<List<E>> chunkList(List<E> list, int chunkSize) {
		ArrayList<List<E>> output = new ArrayList<List<E>>();
		int cols = list.size() / chunkSize;
		for (int i = 0; i < list.size(); i += chunkSize) {
			output.add(list.subList(i, i + chunkSize));
		}
		return output;
	}
	
	public static <T> List<T> createListPopulatedWith(T object, int amount) {
		List<T> ts = new ArrayList<T>(); 
		for (int i = 0; i < amount; i++) 
			ts.add(object);
		return ts;
	}
	
	public static <T> List<T> disjunction(List<T> a, List<T> b) {
	    ArrayList<T> list = new ArrayList<T>();
	    Map<T, Integer> mapa = getCardinalityMap(a);
	    Map<T, Integer> mapb = getCardinalityMap(b);
	    Set<T> elts = new HashSet<T>(a);
	    elts.addAll(b);
	    Iterator<T> it = elts.iterator();
	    while(it.hasNext()) {
	        T obj = it.next();
	        for(int i=0,m=((Math.max(getFreq(obj, mapa),getFreq(obj,mapb)))-(Math.min(getFreq(obj,mapa),getFreq(obj,mapb))));i<m;i++) {
	            list.add(obj);
	        }
	    }
	    return list;
	}
	
	
	private static <T> Map<T, Integer> getCardinalityMap(List<T> col) {
	    HashMap<T, Integer> count = new HashMap<T, Integer>();
	    Iterator<T> it = col.iterator();
	    while(it.hasNext()) {
	        T obj = it.next();
	        Integer c = (Integer)(count.get(obj));
	        if(null == c) {
	            count.put(obj, new Integer(1));
	        } else {
	            count.put(obj, new Integer(c.intValue() + 1));
	        }
	    }
	    return count;
	}
	
	private static final <T> int getFreq(final T obj, final Map<T, Integer> freqMap) {
	    try {
	        return ((Integer)(freqMap.get(obj))).intValue();
	    } catch(NullPointerException e) {
	        // ignored
	    } catch(NoSuchElementException e) {
	        // ignored
	    }
	    return 0;
	}
}
