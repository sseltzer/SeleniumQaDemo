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
 * The ServerEnum is used in the EnvironmentConfig to map the EnvironmentEnum to the server number to represent
 * the actual target server configurations. These are then used in the objects which extend Website in order to
 * map a String of the domain to the target server. For example, there is a dev, tst, and prd enum. For valpak.com
 * dev maps to vpdev.valpak.com, tst maps to vptst.valpak.com, and prd maps to http://www.valpak.com. When used
 * with ServerEnum in the EnvironmentConfig, the exact server can be mapped.
 * 
 * @author Sean Seltzer
 *
 */
public enum EnvironmentEnum {
		NONE(""),
		LOCAL("local"),
		DEV("dev"),
		DEV1("dev1"),
		DEV2("dev2"),
		TST("tst"),
		PRD("prd"),
		PRD1("prd1"),
		PRD2("prd2"),
		PRD3("prd3"),
		PRD4("prd4"),
		PRDG1("prdG1"),
		PRDG2("prdG2"),
		PRDG3("prdG3"),
		PRDG4("prdG4");

		private String s;
		
		private EnvironmentEnum(String s) {
			this.s = s;
		}
		
		@Override
		public String toString() {
			return s;
		}
		
		public static EnvironmentEnum find(String enumStr) {
			return EnumFinder.find(EnvironmentEnum.class, enumStr);
		}
}
