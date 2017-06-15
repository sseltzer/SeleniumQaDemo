package com.sseltzer.selenium.framework.verification.video;

import org.jsoup.nodes.Document;

import com.sseltzer.selenium.framework.error.ErrorManager;
import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;
import com.sseltzer.selenium.framework.selenium.mapping.PageObject;
import com.sseltzer.selenium.framework.selenium.webpage.base.WebPage;
import com.sseltzer.selenium.framework.utility.parallelism.ConcurrentWebTools;

/**
 * VideoTester
 * @author Sean Seltzer
 *
 */
public abstract class VideoTester {

	private static String ERROR_UNAVAILABLE = "This video is unavailable";
	private static String ERROR_NOT_EXIST = "This video does not exist";
	private static String ERROR_PRIVATE = "This video is private";

	public static void testVideoPlayable(WebPage webPage, PageObject pageObject) {
		String videoSrc = webPage.interactWithElement().getElementAttribute(pageObject, "src");
		verifyVideoNotBroken(videoSrc);
	}
	
	public static void testVideoPlayable(String videoSrc) {
		verifyVideoNotBroken(videoSrc);
	}
	
	public static void verifyVideoNotBroken(String videoUrl) {
		if (videoUrl.contains("embed/watch?v="))
			ErrorManager.throwAndDump("Video Url is malformed");
		if (ConcurrentWebTools.loadResponses(videoUrl).statusCode() != 200) 
			ErrorManager.throwAndDump("Could not load video url"); // Handles Vimeo's 404s ( if present )
		if (videoUrl.contains("vimeo")) return;
		Document soup = ConcurrentWebTools.openAndParse(toWatchUrl(videoUrl));
//		String h1 = soup.select("h1 span").isEmpty()
		if (soup.select("h1 span").isEmpty()) {
			throw new FrameworkException("\n\nEmbedded Video is Unavailable, Private, or does not exist at the linked URL\n\n");
		}
	}

	private static String toWatchUrl(String embeddedUrl) {
		return embeddedUrl.replace("embed/", "watch?v=");
	}
}
