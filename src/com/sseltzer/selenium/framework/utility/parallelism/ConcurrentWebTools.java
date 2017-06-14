
package com.sseltzer.selenium.framework.utility.parallelism;

import java.util.Arrays;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;

/**
 *
 *
 * ConcurrentActions.java
 *
 * @author ckiehl Jul 31, 2014
 */
public class ConcurrentWebTools {
	
	private static Parallel parallel = new Parallel(250);  
	
	public static <T, E> List<T> apply(List<E> list, Function<E, T> f) throws Exception {
		return parallel.apply(list, f);
	}
	
	public static Document openAndParse(String urls) throws FrameworkException {
		return openAndParse(Arrays.asList(urls)).get(0);
	}
	
	public static List<Document> openAndParse(List<String> urls) throws FrameworkException {
		try {
			return parseResponses(loadResponses(urls));
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}
	
	public static Response loadResponses(final String url) throws FrameworkException {
		return loadResponses(Arrays.asList(url)).get(0);
	}
	
	public static List<Response> loadResponses(final List<String> urls) throws FrameworkException {
		try {
			return _loadResponses(urls);
		} catch (Exception e ) {
			throw new FrameworkException(e);
		}
	}
	
	public static List<Response> _loadResponses(final List<String> urls) throws Exception{
		return parallel.apply(urls, new Function<String, Response>() {
			public Response apply(String url) throws Exception {
				return Jsoup.connect(url)
						.timeout(60000)
						.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
						.referrer("http://www.google.com")
						.ignoreHttpErrors(true)
						.ignoreContentType(true)
						.execute();
			}}
		);
	}
	
	public static Document parseResponses(Response response) throws FrameworkException {
		try {
			return parseResponses(Arrays.asList(response)).get(0);
		} catch (Exception e ) {
			throw new FrameworkException(e);
		} 
	}
	
	public static List<Document> parseResponses(List<Response> responses) throws Exception {
		return parallel.apply(responses, new Function<Response, Document>() {
			public Document apply(Response response) throws Exception {
				return response.parse();
			}}
		);
	}
}
