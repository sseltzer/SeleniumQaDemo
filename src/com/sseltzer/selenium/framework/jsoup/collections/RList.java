
package com.sseltzer.selenium.framework.jsoup.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.sseltzer.selenium.framework.jsoup.functions.Filter;
import com.sseltzer.selenium.framework.jsoup.functions.Mapper;


/**
 *
 * Immutable Persistent LinkedList implementation for 
 * use with the JSoup framework. 
 * 
 * @author ckiehl Sep 2, 2014
 */

public abstract class RList<E> implements Iterable<E> {

	public abstract E head();
	public abstract RList<E> tail();
	public boolean isEmpty() { return this instanceof Nil;	};

	public boolean contains(E elm) {
		for (E e : this) 
			if (e.equals(elm)) return true;
		return false;
	}
	
	public RList<E> cons(E e) {
		return new Cons<E>(e, this);
	}
	
	public E get(int i) {
		if (i < 0) 
			throw new IndexOutOfBoundsException();
		try { 
			return get(this, i); 
		} catch (UnsupportedOperationException e) {
			throw new IndexOutOfBoundsException();
		}
	}
	
	private E get(RList<E> es, int i) {
		if (i == 0) return es.head(); 
		else return get(es.tail(), i - 1);
	}
	
	public RList<E> drop(int i) {
		RList<E> these = this; 
		int count = i;
		while (!these.isEmpty() && count > 0) {
			these = these.tail();
			count -= 1;
		}
		return these;
	}
	
	public RList<E> take(int n) {
		if (isEmpty() || n <= 0) return new Nil<E>();
		Cons<E> h = new Cons<E>(this.head(), new Nil<E>());
		Cons<E> t = h;
		RList<E> rest = this.tail();
		int i = 1; 
		while (!rest.isEmpty() && i < n) {
			for (E e : h) System.out.println(e);
			System.out.println();
			i += 1;
			Cons<E> nx = new Cons<E>(rest.head(), new Nil<E>());
			t.tail = nx;
			t = nx;
			rest = rest.tail();
		}
		return h;
	}
	
	public RList<E> init() {
		return this.take(this.size() - 1);
	}
	
	public E last() {
		if (this.isEmpty()) throw new NoSuchElementException(); 
		E last = this.head();
		for (E e : this) 
			last = e;
		return last;
	}
		
	public int size() {
		return count(this, 0);
	}
	
	private int count(RList<E> es, int count) {
		if (es.isEmpty()) return count; 
		else return count(es.tail(), count + 1);
	}

	public Iterator<E> iterator() {
		return new nodeIterator<E>(this);
	}
	
	public static class Nil<E> extends RList<E> {
		public E head() { 
			throw new UnsupportedOperationException("Head of empty List"); 
		}
		public RList<E> tail() { 
			throw new UnsupportedOperationException("Tail of empty List"); 
		}
	}
	
	public static class Cons<E> extends RList<E>{
		private final E head;
		protected RList<E> tail;
		
		public Cons(E head, RList<E> list) {
			this.head = head; 
			this.tail = list;
		}
		public E head() { return this.head; }
		public RList<E> tail() { return this.tail; }
	}
	
	private static class nodeIterator<E> implements Iterator<E> {
		
		private RList<E> items; 
		
		public nodeIterator(RList<E> toIter) {
			this.items = toIter;
		}

		public boolean hasNext() { 
			if (items instanceof Nil) return false;
			else return true;
		}

		public E next() {
			E e = this.items.head();
			this.items = this.items.tail();
			return e;
		}

		public void remove() {
			throw new UnsupportedOperationException("Mutation not supported >:(");
		}
	}
	
	public <F> Mapper map() { return null; };
	
	public <F> RList<F> map(Function<E, F> f){
		return this.map(this, f);
	}
	
	private <F> RList<F> map(RList<E> rList, Function<E, F> f) {
		if (rList.isEmpty()) return new Nil<F>(); 
		else return new RStringList.Cons<F>(f.apply(rList.head()), map(rList.tail(), f));
	}
	
	public <F> Filter filter() {return null; };
	
	public RList<E> filter(Predicate<E> p){
		return this.filter(this, p);
	}
	
	private RList<E> filter(RList<E> list, Predicate<E> p) {
		if (list.isEmpty()) return new Nil<E>(); 
		else if (p.apply(list.head())) return new Cons<E>(list.head(), filter(list.tail(), p));
		else return filter(list.tail(), p);
	}
	
	public static <E> RList<E> of(final E... items) {
		RList<E> out = new Nil<E>(); 
		for (int i = items.length -1; i >= 0; i--) 
			out = out.cons(items[i]);
		return out;
	}
	
	public static <E> RList<E> populate(final E e, int amount) {
		RList<E> out = new Nil<E>(); 
		for (int i = 0; i < amount; i++)
			out = out.cons(e);
		return out;
	}
	
	public static RList<Integer> forRange(int range) {
		return forRange(0, range);
	}
	
	public static RList<Integer> forRange(int start, int stop) {
		if (stop == start) return new Nil<Integer>();
		else return new Cons<Integer>(start, forRange(start < stop ? start + 1 : start - 1, stop));
	}
	
	public List<E> toList() {
		return toList(this, new ArrayList<E>());
	}
	
	private List<E> toList(RList<E> list, ArrayList<E> output) {
		if (list.isEmpty()) return output; 
		else {
			output.add(list.head());
			return toList(list.tail(), output);
		}
	}
}
