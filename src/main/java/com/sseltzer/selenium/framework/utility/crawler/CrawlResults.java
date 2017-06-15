package com.sseltzer.selenium.framework.utility.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 *
 * CrawlResults.java
 * Decorator for the Crawler. Provides convenience 
 * @author ckiehl Jun 13, 2014
 */
public class CrawlResults {
	private Crawler _crawler;
	private String STATUS_OK = "200";

	public CrawlResults(Crawler crawler) {
		this._crawler = crawler;
	}

	public void displayVisitedUrls() {
		for (String url : this._crawler.getVisitedUrls())
			System.out.println("Visited: " + url);
	}

	public void displayBrokenList() {
		for (String error : this.formatErrorResults()) {
			System.out.println(error);
		}
	}

	public void writeVisitedReportToFile(String filename) {
		this.writeReportToFile(filename, this._crawler.getVisitedUrls());
	}

	public void writeErrorReportToFile(String filename) {
		this.writeReportToFile(filename, this.formatErrorResults());
	}

	private void writeReportToFile(String filename, List<String> contents) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(new File("C:\\Users\\ckiehl\\Desktop\\" + filename)));
			for (String url : contents) {
				if (url != null) writer.write(url + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) writer.close();
			} catch (Exception e) {
			}
		}
	}

	private List<String> formatErrorResults() {
		List<String> output = new ArrayList<String>();
		String url, code;
		for (HashMap<String, String> brokenUrl : this._crawler.getBrokenUrls()) {
			url = brokenUrl.get("url");
			code = brokenUrl.get("code");
			if (code.contains("no protocol")) continue;
			// error is hard coded into the HTML
			if (code.equals(STATUS_OK)) output.add(formatError(url, "App Error"));
			else output.add(formatError(url, code));
		}
		return output;
	}

	private String formatError(String url, String errorCode) {
		return String.format("Broken Url: Error Code: %s; Url: %s", errorCode, url);
	}

}
