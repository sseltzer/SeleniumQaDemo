package com.sseltzer.selenium.framework.environment.enums;

public enum MobileDeviceEnum {
	NEXUS_7         ("Nexus 7",          BrowserEnum.ANDROID),
	GALAXY_NOTE_3   ("SAMSUNG-SM-N900A", BrowserEnum.ANDROID),
	HTC_ONE         ("HTC6500LVW",       BrowserEnum.ANDROID),
	GALAXY_S4       ("SAMSUNG-SGH-I337", BrowserEnum.ANDROID),
	GALAXY_S5       ("SAMSUNG-SM-G900V", BrowserEnum.ANDROID),
	IOS_PAD_2		("iPad 2",           BrowserEnum.IOS),
	IOS_IPHONE_6    ("iPhone 6",         BrowserEnum.IOS),
	IOS_Apple_Watch ("Apple Watch",      BrowserEnum.IOS);
	
	private String deviceName;
	private BrowserEnum b;
	
	private MobileDeviceEnum(String deviceName, BrowserEnum b) {
		this.deviceName = deviceName;
		this.b = b;
	}
	
	public BrowserEnum getBrowser() {
		return b;
	}
	
	@Override
	public String toString() {
		return deviceName;
	}

	public static MobileDeviceEnum find(String deviceName) {
		if (deviceName == null) throw new RuntimeException("Mobile device name not specified. Add a 'mobiledevice' environmental variable.");
		MobileDeviceEnum device = EnumFinder.find(MobileDeviceEnum.class, deviceName);
		if (device == null) throw new RuntimeException("mobiledevice provided is invalid. Please use a correct device or add the device to the MobileDeviceEnum.");
		return device;
	}
}
