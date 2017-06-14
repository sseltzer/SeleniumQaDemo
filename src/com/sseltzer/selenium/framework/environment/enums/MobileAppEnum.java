package com.sseltzer.selenium.framework.environment.enums;

public enum MobileAppEnum {
	LEGACY      ("legacy"),
	AR          ("reality"),
	SKYWAY      ("skyway"),
	APPLE_WATCH ("watch");
	
	private String s;
	
	private MobileAppEnum(String s) {
		this.s = s;
	}
	
	@Override
	public String toString() {
		return s;
	}

	public static MobileAppEnum findFromPath(String path) {
		if (path == null) throw new RuntimeException("Please provide a 'mobileapp' env var that contains legacy, reality, skyway, or watch.");
		if (path.contains(LEGACY.toString())) return LEGACY;
		if (path.contains(AR.toString())) return AR;
		if (path.contains(SKYWAY.toString())) return SKYWAY;
		if (path.contains(APPLE_WATCH.toString())) return APPLE_WATCH;
		throw new RuntimeException("Could not identify app type from path: " + path + " \n. Please ensure 'mobileapp' env var contains legacy, reality, skyway, or watch.");
	}
}
