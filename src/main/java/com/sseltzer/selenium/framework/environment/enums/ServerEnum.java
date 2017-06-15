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
 * map a String of the domain to the target server. For example, for a production website there is a main .com
 * url. However the website is distributed across four servers; in the case of valpak.com there is vpcom1, vpcom2
 * vpcom3, and vpcom4. So going to PRD without a server number is just the regular www.valpak.com website, but
 * specifying a server number will direct the domain to one of the servers such as vpcom1.valpak.com.  
 * 
 * @author Sean Seltzer
 *
 */
public enum ServerEnum {
	NONE(""),
	SERVER1("1"),
	SERVER2("2"),
	SERVER3("3"),
	SERVER4("4");
	
	private String s;
	
	private ServerEnum(String s) {
		this.s = s;
	}
	
	@Override
	public String toString() {
		return s;
	}

	/**
	 * Perform a reverse search of the enum. Provide a String and it will search through
	 * each enum to find the associated enumeration and return it. If it does not find it,
	 * it will return the NONE object. So if you call find("5") it will return
	 * NONE because there is no 5th server. If you call find("2")
	 * it will return the SERVER2 enum.
	 * @param enumStr the String to search for the associated enum.
	 * @return the enumeration mapped to the given string.
	 */
	public static ServerEnum find(String enumStr) {
		return EnumFinder.find(ServerEnum.class, enumStr);
	}
}
