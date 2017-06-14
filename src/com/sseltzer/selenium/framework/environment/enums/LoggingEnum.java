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
 * The LoggingEnum is used to tell the logging framework which mode it should operate in. The "logging"
 * variable can be set to "savelog" to put the logging framework into save mode where it will initiate
 * a logging session to the LogTester framework, then run each test saving the JSON to disk. The default
 * behavior is validation if the logging variable is not set to savelog. In this mode, it will initiate
 * a session in the LogTester framework, then perform a validation against the baseline JSON files.
 * 
 * @author Sean Seltzer
 *
 */
public enum LoggingEnum {
	NONE(""),
	SAVELOG("savelog");
	
	private String s;
	
	private LoggingEnum(String s) {
		this.s = s;
	}
	
	@Override
	public String toString() {
		return s;
	}
	
	public static LoggingEnum find(String enumStr) {
		return EnumFinder.find(LoggingEnum.class, enumStr);
	}
}
