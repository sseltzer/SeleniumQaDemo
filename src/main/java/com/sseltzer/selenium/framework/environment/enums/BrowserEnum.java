	package com.sseltzer.selenium.framework.environment.enums;


/**
 * Simple enumeration to represent the environmental variables. This, like all of the other environment enums
 * also contains the String mappings to the values of the environmental variables used in Eclipse
 * and TeamCity. Each enum maintains the Strings and provide a static function to perform a lookup of the
 * environmental variables to produce the associated enum object. The enum object is used throughout the
 * framework. This provides a decoupling of the environmental variables from the enumeration through the
 * framework. When combined with the EnvironmentStrings.java class, we have a three tiered decoupling.
 * These enumerations are used the framework to allow the external configuration of the tests based on
 * the variables. The Strings contained in each enum come from another enum which was dynamically read
 * from the EnvironmentStrings.properties file. Using this, we need nothing hard coded in the project, we
 * simply read in the variables from the environment and send them here to be compared against our expected
 * values in the .properties file. Depending on the enum returned, we control the test accordingly.
 * <br><br>
 * The BrowserEnum is used in the BrowserFactory to request a Browser based on the enum.
 * @author Sean Seltzer
 *
 */
public enum BrowserEnum {
	NONE(""),
	CHROME("chrome"),
	FIREFOX("firefox"),
	IE("ie"),
	ANDROID("android"),
	IOS("ios");	
	
	private String s;
	
	private BrowserEnum(String s) {
		this.s = s;
	}
	
	@Override
	public String toString() {
		return s;
	}
	
	/**
	 * Perform a reverse search of the enum. Provide a String and it will search through
	 * each enum to find the associated enumeration and return it. If it does not find it,
	 * it will return the NONE object. So if you call find("OperaBrowser") it will return
	 * NONE because there is no implementation of an Opera WebDriver. If you call find("FirefoxBrowser")
	 * it will return the FIREFOX enum.
	 * @param enumStr the String to search for the associated enum.
	 * @return the enumeration mapped to the given string.
	 */
	public static BrowserEnum find(String enumStr) {
		return EnumFinder.find(BrowserEnum.class, enumStr);
	}
}
