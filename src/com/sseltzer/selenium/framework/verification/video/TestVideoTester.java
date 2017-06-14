
package com.sseltzer.selenium.framework.verification.video;

import org.junit.Test;

import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;

/**
 *
 *
 * TestVideoTester.java
 *
 * @author ckiehl Aug 7, 2014
 */
public class TestVideoTester {

	@Test(expected=FrameworkException.class) 
	public void testMalformedVideoUrlThrowsException() {
		String malformedUrl = "http://www.youtube.com/embed/watch?v=K07jFJYs7_I"; 
		VideoTester.testVideoPlayable(malformedUrl);
	}
	
	@Test(expected=FrameworkException.class) 
	public void testPrivateYoutubeVideoThrowsException() {
		String privateVid = "https://www.youtube.com/watch?v=69TzwmuVBZU"; 
		VideoTester.testVideoPlayable(privateVid);
	}
	
	@Test(expected=FrameworkException.class) 
	public void testInvalidVimeoUrlThrowsException() {
		String vimeoUrl = "http://vimeo.com/channels/staffpicks/100607f95"; 
		VideoTester.testVideoPlayable(vimeoUrl);
	}
	
	@Test 
	public void testValidVimeoUrlPasses() {
		String vimeoUrl = "http://player.vimeo.com/video/93107965?api=1&player_id=undefined_vimeoPlayer"; 
		VideoTester.testVideoPlayable(vimeoUrl);
	}
	
	@Test 
	public void testValidYoutubeVideoDoesNotThrowException() {
		String validUrl = "http://www.youtube.com/embed/NdzkqG26JoE?enablejsapi=1&modestbranding=1&rel=0&wmode=transparent";
		VideoTester.testVideoPlayable(validUrl);
	}
	
	//TODO: find other youtube test cases
}
