
package com.sseltzer.selenium.framework.jsoup.toolkit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Connection.Response;

import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;
import com.sseltzer.selenium.framework.jsoup.webpages.SoupPage;
import com.sseltzer.selenium.framework.jsoup.webpages.SoupPageGroup;
import com.sseltzer.selenium.framework.utility.parallelism.Function;
import com.sseltzer.selenium.framework.utility.parallelism.Parallel;

import org.jsoup.Jsoup;

/**
 *
 *
 * ConcurrentActions.java
 *
 * @author ckiehl Jul 31, 2014
 */
public class ParallelWebTools {
	
	private static Parallel parallel = new Parallel(0);  
	
	public static <T, E> List<T> apply(List<E> list, Function<E, T> f) throws Exception {
		return parallel.apply(list, f);
	}
	
	public static SoupPage openAndParse(String urls) {
		return openAndParse(Arrays.asList(urls)).get(0);
	}
	
	public static SoupPageGroup openAndParse(List<String> urls) {
		return parseResponses(loadResponses(urls));
	}
	
	public static Response loadResponse(final String url) throws RuntimeException {
		return loadResponses(Arrays.asList(url)).get(0);
	}
	
	public static List<Response> loadResponses(final List<String> urls) throws RuntimeException {
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
	
	public static SoupPage parseResponse(Response response) throws FrameworkException {
		return parseResponses(Arrays.asList(response)).get(0);
	}
	
	public static SoupPageGroup parseResponses(List<Response> responses) throws RuntimeException {
		return new SoupPageGroup(parallel.apply(responses, new Function<Response, SoupPage>() {
			public SoupPage apply(Response response) throws Exception {
				return new SoupPage(response.parse());
			}}
		));
	}
	
	public static SoupPageGroup parseHtmlFiles(List<String> paths) throws RuntimeException {
		List<SoupPage> pages = new ArrayList<SoupPage>();
		for (String path : paths) 
			pages.add(parseHtmlFile(path));
		return new SoupPageGroup(pages);
	}
	
	public static SoupPage parseHtmlFile(String path) throws RuntimeException {
		File file = new File(path);
		try {
			return new SoupPage(Jsoup.parse(file, "UTF-8", "http://www.example.com"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static SoupPage parseString(String html) throws RuntimeException {
		return new SoupPage(Jsoup.parse(html));
	}
	
	public static SoupPage parseString(String html, String baseUrl) throws RuntimeException {
		return new SoupPage(Jsoup.parse(html, baseUrl));
	}

}




