package com.sseltzer.selenium.framework.selenium.wrappers;

import com.sseltzer.selenium.framework.selenium.wrappers.TimingManager.TimeoutMode;


/**
 * 
 * Container for custom timing options. TimeoutMode specifies the TimingManager
 * timeout mode of whether or not to perform the timed action immediately (IMMEDIATE),
 * after the polled duration (WAIT), or force the event to occur even if the operation
 * is unavailable even after waiting (FALLBACK). 
 * <br><br>
 * For example, to click a button without waiting, use IMMEDIATE. To click the button
 * after it becomes available on the page, use WAIT. To try to still click the button 
 * on the page even if the button does not load in time use FALLBACK. Certain circumstances
 * may cause the framework WAIT to timeout even if the action is available, hence FALLBACK.
 * One such case is when a page service is 503 (unavailable) on page load, the page might
 * actually be loaded but one single script is stalling Selenium due to the way Selenium
 * receives events from the DOM. The page can timeout loading even if the entire web page
 * is loaded if an external script has not finished. This situation happened on Valpak.com
 * and would cause the entire script to hang before global implicit timeouts were implemented.
 * <br><br>
 * The wait time specified is the maximum time allowed to wait while the poll time is the polling
 * interval to check to see if the action is available. Default is 10s wait time, and 100ms poll time.
 * So if an action is requested with TimingOptions, 10s will be waited, but if the action is available
 * after 500ms, it will be performed at the first available poll check time. ie, last poll was 450,
 * the action becomes available at 500, the next poll time is 550 when the action will be performed.  
 * @author Sean Seltzer
 *
 */
public class TimingOptions {
	
	private TimeoutMode timeoutMode;
	private long waitTime;
	private long pollTime;
	
	public TimingOptions(TimeoutMode timeoutMode, long waitTime, long pollTime) {
		this.timeoutMode = timeoutMode;
		this.waitTime = waitTime;
		this.pollTime = pollTime;
	}
	
	public static TimingOptions IMMEDIATE = new TimingOptions(TimeoutMode.IMMEDIATE, 0, 0);

	public TimeoutMode getTimeoutMode() {
		return timeoutMode;
	}

	public long getWaitTime() {
		return waitTime;
	}

	public long getPollTime() {
		return pollTime;
	}

	public String getWaitTimeAsString() {
		return Long.toString(waitTime);
	}

	public String getPollTimeAsString() {
		return Long.toString(pollTime);
	}
}
