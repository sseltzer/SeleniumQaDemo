/**
 *
 *
 * TestSanityCrawler.java
 *
 * @author ckiehl Apr 24, 2014
 */
package com.sseltzer.selenium.internal.test.tests.verification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.junit.Test;

import com.sseltzer.selenium.framework.utility.crawler.Crawler;

public class TestSanityCrawler {

	@Test
	public void tester() {
		Crawler crawler = new Crawler(1000);
//		String seed = "http://vpdev.valpak.com/redesign/home";
//		String seed = "http://www.valpak.com/coupons/home";
		String seed = "https://www.valpak.com/coupons/coupon-codes/stores?geo=33511&partnerCatId=1";
		try {
			crawler.crawl(seed);
		} catch (Exception e) {
			writeFile(crawler);
		}
//		crawler.displayVisited();
//		crawler.displayBrokenList();
		writeFile(crawler);
	}
	
	private void writeFile(Crawler crawler)  {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(new File("C:\\Users\\ckiehl\\Desktop\\output.txt")));
			for (String url : crawler.getVisitedUrls()) {
				if (url != null) writer.write(url + "\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) writer.close();
			} catch(Exception e) {}
		}
		
		crawler.getVisitedUrls();
	}
}
