
package com.sseltzer.selenium.framework.jsoup.urls;

import com.google.common.collect.ImmutableMap;
import com.sseltzer.selenium.framework.environment.EnvironmentHandler;

/**
 *
 *
 * ValpakUrl.java
 *
 * @author ckiehl Sep 2, 2014
 */

public class ValpakUrl {
private static ImmutableMap<String, String> envCache = loadSystemEnv();
	
	private static final ImmutableMap<String, String> envMap = 
		ImmutableMap.<String, String>builder()
		.put("",   "https://vpdev.valpak.com")
		.put("dev",  "https://vpdev.valpak.com")
		.put("dev1",  "https://vpdev1.valpak.com")
		.put("dev2",  "https://vpdev2.valpak.com")
		.put("tst",  "https://vptst.valpak.com")
		.put("prd",  "https://www.valpak.com")
		.put("prd1", "https://vpcom1.valpak.com:8943")
		.put("prd2", "https://vpcom2.valpak.com:8943")
		.put("prd3", "https://vpcom3.valpak.com:8943")
		.put("prd4", "https://vpcom4.valpak.com:8943")
		.build();
	
	private static ImmutableMap<String, String> loadSystemEnv() {
		return ImmutableMap.of("env", EnvironmentHandler.getEnvironment().toString());
	}
	
	public static void setMockEnv(ImmutableMap<String,String> mockEnv) {
		envCache = mockEnv;
	}
	
	public static String from(String url) {
		String env = envCache.get("env"); 
		return envMap.get(env) + url;
	}
	
	
}
