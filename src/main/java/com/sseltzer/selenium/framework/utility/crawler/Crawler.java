package com.sseltzer.selenium.framework.utility.crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;
import com.sseltzer.selenium.framework.error.exceptions.PublicException;

/**
 * Crawls all links on Valpak.com and reports any error (e.g. 404, 500,
 * application error, etc..).
 * 
 * 
 * @author ckiehl
 */
/*
 * Set a maximum recursion depth for the crawler. This thing will run until it runs out of
 * Internet, memory, or stack frames (whichever comes first). So, the maxDepth allows you
 * to bound it to something reasonable.
 * */
public class Crawler {
	private static WebClient webClient = null;

	private List<String> visitedUrls;
	private List<HashMap<String,String>> brokenUrlsAndCodes;
	
	private String primaryDomainToCrawl;
	private List<String> blackList;
	private Integer maxDepth; 
	
	private final Integer STATUS_OK = 200;
	
	private static final List<String> blackListDefaults = Arrays.asList(
		"sort=", 
		"radius=",	
		"#respond",
		"advertise",
		"blog",
		"plus.google",
		"facebook",
		"twitter"
	);
			
	
	public Crawler() {
		this(Collections.<String> emptyList(), Integer.MAX_VALUE);
	}
	
	public Crawler(Integer maxDepth) {
		this(Collections.<String> emptyList(), maxDepth);
	}
	
	public Crawler(List<String> blackList) {
		this(blackList, Integer.MAX_VALUE);
	}
	//TODO Resolve Deprecation
	@SuppressWarnings("deprecation")
	public Crawler(List<String> blackList, Integer maxDepth) {
		this.blackList = buildMasterBlacklist(blackList);
		this.maxDepth = maxDepth;
		if (webClient == null) {
			webClient = new WebClient(BrowserVersion.FIREFOX_45);
			this.configureOptions(webClient);
			this.initializeContainers();
		}
	}
	
	private List<String> buildMasterBlacklist(List<String> blackList) {
		// combines the default blacklist with the user supplied blacklist 
		Set<String> fullBlackList = new HashSet<String>(blackList);
		fullBlackList.addAll(blackListDefaults);
		return new ArrayList<String>(fullBlackList);
	}

	private void initializeContainers() {
		visitedUrls = new ArrayList<String>();
		brokenUrlsAndCodes = new ArrayList<HashMap<String,String>>();
	}

	private void configureOptions(WebClient webClient) {
		// Turn off all of the WebClient features we don't need (for speed)
		WebClientOptions options = webClient.getOptions();
		options.setThrowExceptionOnFailingStatusCode(false);
		options.setThrowExceptionOnScriptError(false);
		options.setJavaScriptEnabled(false);
		options.setAppletEnabled(false);
		options.setCssEnabled(false);
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
	}

	public List<String> getVisitedUrls() {
		return this.visitedUrls;
	}
	
	public List<HashMap<String,String>> getBrokenUrls() {
		return this.brokenUrlsAndCodes;
	}
	
	public void displayVisited() {
		for (String s : this.visitedUrls) 
			System.out.println("Visited: " + s);
	}
	
	private static String createPrimaryDomainRegex(String url) {
		// used to contrain the crawler to the seed url's host domain
		try {
			return String.format(".*%s.*", new URL(url).getHost());
		} catch (MalformedURLException e) {
			throw new FrameworkException(e);
		}
	}

	public CrawlResults crawl(String seed) {
		try {
			this.primaryDomainToCrawl = createPrimaryDomainRegex(seed);
			HtmlPage page = webClient.getPage(seed);
			if (page.getWebResponse().getStatusCode() != STATUS_OK) 
				brokenUrlsAndCodes.add(mapValues(seed, statusCodeAsString(page)));
			else {
				LinkedHashSet<String> urlsToCheck = getRelevantUrls(page, new LinkedHashSet<String>());
				crawl(urlsToCheck);
			}
		} catch (MalformedURLException e) {
			throw new PublicException(e);
		} catch (FailingHttpStatusCodeException e) {
			throw new PublicException(e);
		} catch (IOException e) {
			throw new PublicException(e);
		}
		return new CrawlResults(this);
	}

	private void crawl(LinkedHashSet<String> urlsToCheck) {
		// Helper function for the crawler
		// Basic flow:
		// 1. Grab first url in list
		// 2. Open it
		// 3. log if valid
		// 4. Back to 1 until done.
		System.out.println(maxDepth);
		if (maxDepth-- == 0)
			return;
		if (urlsToCheck.size() == 0)
			return;
		String currentUrl = this.popFirst(urlsToCheck);
		System.out.println("Checking: " + currentUrl);
		try {
			HtmlPage page = webClient.getPage(currentUrl);
			visitedUrls.add(currentUrl);
			if (isBadStatusCode(page)) 
				brokenUrlsAndCodes.add(mapValues(currentUrl, statusCodeAsString(page)));
			urlsToCheck.addAll(getRelevantUrls(page, urlsToCheck));
			crawl(urlsToCheck);
		} catch (Exception e) {
			brokenUrlsAndCodes.add(mapValues(currentUrl, e.getMessage()));
			crawl(urlsToCheck);
		}
	}
	
	private String popFirst(LinkedHashSet<String> urlSet) {
		String head = urlSet.iterator().next();
		urlSet.remove(head);
		return head;
	}
	
	private boolean isBadStatusCode(HtmlPage page) {
		int status = page.getWebResponse().getStatusCode();
		String responseData = page.getWebResponse().getContentAsString().toLowerCase();
		return (
			status != STATUS_OK 
			|| responseData.matches("^404 error")
			|| responseData.matches("^application error")
		); 
	}
	
	private String statusCodeAsString(HtmlPage page) {
		return Integer.toString(page.getWebResponse().getStatusCode());
	}

	private LinkedHashSet<String> getRelevantUrls(HtmlPage page, LinkedHashSet<String> urlsToVisit)  {
		// Prevents the crawler from looping off into infinity by filtering out any urls that have 
		// either been checked previously, aren't relevant to vpdev (e.g. yahoo.com), or are radius,
		// sort links, or Grocery Coupons (which are too numerous to cover and/or covered by Selenium tests)
		try {
			String url;
			for (HtmlAnchor anchor : page.getAnchors()) {
				url = page.getFullyQualifiedUrl(anchor.getHrefAttribute()).toString();
				if (isValidAnchorType(url, urlsToVisit)) urlsToVisit.add(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return urlsToVisit;
	}
	
	private boolean isValidAnchorType(String url, LinkedHashSet<String> urlsToVisit) {
		return (
//				url.contains("savings?cid")
//				&& url.contains("vpdev")
				url.matches(this.primaryDomainToCrawl)
				&& !visitedUrls.contains(url) 
				&& !urlsToVisit.contains(url) 
				&& url.length() > 6
				&& !inBlackList(url));
	}
	
	private boolean inBlackList(String url) {
		boolean isPresent = false; 
		for (String criterion : blackList) 
			if (url.contains(criterion)) isPresent = true;
		return isPresent;
	}

	@SuppressWarnings("serial")
	private HashMap<String, String> mapValues(final String url, final String responseCode) {
		return new HashMap<String, String>() {{
			put("url", url); 
			put("code", responseCode);
		}};
	}
	
}
