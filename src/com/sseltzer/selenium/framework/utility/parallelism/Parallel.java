
package com.sseltzer.selenium.framework.utility.parallelism;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 *
 *
 * Concurrenly.java
 * 
 * ##About:
 * 
 * Concurrently is a central, reusable access point to a shared ExecutorService.
 * It is fully generic, thus will accept any input E and return any output T.  
 * Specific Executors can be injected via constructor. The default is a FixedThreadPool 
 * with 20 threads. At the time of writing, this gave fairly good throughput for scraping 
 * tasks.
 * 
 * Concurrently is, essentially, a poor man's implementation of a map -- of the higher order
 * kind, not the key/value kind. It applies an function to over a list of items, and returns 
 * a new list of modified items. Now, Java is, of course, not functional, which presents a problem. 
 * All functions need to be transported in Anonymous classes. This can lead to some unsightly code. 
 * However, this, along with all Guava's warnings were all taken into account, and in the end, a 
 * net LOC savings can be established. If you're threading in <1.8, there's no getting around 
 * Callable<T> class bloat. Making it reusable (while gaining additional, lazy eval features) seemed 
 * to be the best choice.
 * 
 * Finally, keeping with the Guava criteria for using these idioms: you can't argue with results. Total 
 * time to check every Category link on the welcome page using this technique is less than 3 seconds 
 * -- you can't even spin up a Selenium instance in that amount of time. And it takes only 6 lines of
 * code to do so. 
 * 
 * ##Usage: 
 * 
 * 
 * ###Construction:
 *  
 * `Concurrently()`  
 * Constructs a pool instance with a default fixed thread size of 20. 
 * 
 * `Concurrently(ExecutorService e) 
 * Constructs a pool with a custom executor (Useful if you want to bound the threads to available 
 * core for CPU bound tasks.) 
 * 
 * ###Method Sumary: 
 *  
 * Concurrently has a single, simple method: `apply`. 
 * 
 * It accepts a List of type E, and a Function of type <E,T>, where E is the input to the function 
 * and T is the return type. The function is then 'applied' to the input and executed inside of the 
 * ExecutorService. 
 * 
 * Example 1. Opening a URL
 * 
 *     public Response parallelOpen(List<String> urls) {
 *       concurrently.appy(urls, new Function<String, Response>() {
 *       	@Override public Response apply(String url) {
 *       		return Jsoup.connect(url).execute();
 *       	} 
 *       }
 *     }
 *	   
 * 2. Usage
 * 
 *     List<Response> responses = parallelOpen(urls); 
 *
 * 
 * @author ckiehl Jul 31, 2014
 */
public class Parallel {
	
	private int throttle; 

	private ExecutorService executor; 
	
	
	public Parallel() { 
		this(Executors.newFixedThreadPool(20), 0);
	}
	
	public Parallel(int throttleAmount) { 
		this(Executors.newFixedThreadPool(20), throttleAmount);
	}
	
	public Parallel(ExecutorService executor, int throttle) { 
		this.executor = executor;
		this.throttle = throttle;
	}
	
	public <T, E> List<T> apply(List<E> list, Function<E, T> f) throws RuntimeException {
		
		List<Future<T>> tasks = new ArrayList<Future<T>>();
		List<T> output = new ArrayList<T>(); 
		
		for (E item : list) {
			tasks.add(executor.submit(new CallableTask<T,E>(f, item)));
			sleep(this.throttle);
		}
		for (Future<T> future : tasks) { 
			try {
				output.add(future.get()); 
			} 	catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return output;
	}
	
	private void sleep(int amount) {
		try {
			Thread.sleep(throttle);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	// Tasks given to an ExecutorService must be of type Runnable or Callable. 
	// This class serves as a wrapper around the client function to allow it to 
	// run in parallel. 
	//
	private class CallableTask<T, E> implements Callable<T> {
		
		private Function<E, T> f;
		private E collection;

		public CallableTask(Function<E, T> f, E collection) {
			this.f = f;
			this.collection = collection;
		}

		public T call() throws Exception {
			return f.apply(collection);
		}
	}
	

	
}












