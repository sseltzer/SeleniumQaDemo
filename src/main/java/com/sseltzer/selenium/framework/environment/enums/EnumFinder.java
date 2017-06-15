package com.sseltzer.selenium.framework.environment.enums;


/**
 * Generic find function to locate enumerations based on String value. This is mainly used
 * for String enum lookup maps. For instances where a String must be tracked through the framework,
 * it is preferred to use an enumeration instead of the String itself to add a layer of abstraction
 * and decouple the data from the framework. Hence, the (currently) four enum maps in the environment
 * package, are used (this is also similar to how Strings are used in the strings package). Instead
 * of referencing the String itself, we can just reference an enum and then the value of the String
 * itself then becomes inconsequential. However, to effectively use this, we need a search function
 * to find the desired enums which represent our Strings. This is where the find function comes in.
 * BrowserEnum, EnvironmentEnum, LoggingEnum, and ServerEnum all represent String properties files that
 * are read and used to configure how the framework behaves. Each runtime environment, TST, DEV, PRD, and
 * local use them to know how to run the framework, not to mention all of the other specific configurations.
 * To disassociate the String values from the framework, the enums are used. To associate the strings with
 * the enum values, they're coded stored in a .properties file, read in with the PropertiesHandler and
 * EnvironmentStrings classes and assigned to the enums. From there, the system properties may be read with
 * EnvironmentHandler, and passed into the find function to return the associated enum representing that String.
 * At no point in the framework is the String actually hard coded. It comes from a .properties file, and read
 * directly from system properties.
 * <br><br>
 * The find function itself works based on the commonality between the enums. All Java enums of course inherit
 * from the Enum class. Unfortunately, we cannot use inheritance on our own. However, Enum inherits from Object
 * and thus, has a toString method which normally returns the String representation of the Enum's hard coded name.
 * However, we can override this in the enum class to return our desired String that the enum represents. Using
 * this, we can take in a class which extends Enum (effectively upcasting our enumerations), along with our String.
 * value. We can loop through all enum constants in the class and call the toString function, again which all
 * of our lookup maps inherit from, and compare that with our given string. If we find a match (ignoring case for
 * the purpose of this framework), we can return that enum. This framework also requires that a NONE enum exist for
 * each of the enum classes since they represent environmental variables. As env vars are not required, what we're
 * looking for may not even exist at all. Instead of using null through the framework which may potentially introduce
 * unchecked exceptions and makes switches more complex, we use a generic NONE enum. As such, in the find function,
 * if the given enum is not found, we try to return the enum representing "NONE" based on Java's automatic enum name
 * to String conversion with Enum.valueOf(Class, String). If NONE does not exist, we return null as a last ditch
 * measure. Thus, following this pattern. Each enum to be used with this function must overload toString, and must
 * have a NONE enum and we can generically handle the searching for all enumerations including enforcing the added
 * NONE functionality which is desired for this framework.
 * 
 * @author Sean Seltzer
 */
public class EnumFinder {

	/**
	 * Generic find function to locate enumerations based on String value. This is mainly used
	 * for String enum lookup maps. For instances where a String must be tracked through the framework,
	 * it is preferred to use an enumeration instead of the String itself to add a layer of abstraction
	 * and decouple the data from the framework. Hence, the (currently) four enum maps in the environment
	 * package, are used (this is also similar to how Strings are used in the strings package). Instead
	 * of referencing the String itself, we can just reference an enum and then the value of the String
	 * itself then becomes inconsequential. However, to effectively use this, we need a search function
	 * to find the desired enums which represent our Strings. This is where the find function comes in.
	 * BrowserEnum, EnvironmentEnum, LoggingEnum, and ServerEnum all represent String properties files that
	 * are read and used to configure how the framework behaves. Each runtime environment, TST, DEV, PRD, and
	 * local use them to know how to run the framework, not to mention all of the other specific configurations.
	 * To disassociate the String values from the framework, the enums are used. To associate the strings with
	 * the enum values, they're coded stored in a .properties file, read in with the PropertiesHandler and
	 * EnvironmentStrings classes and assigned to the enums. From there, the system properties may be read with
	 * EnvironmentHandler, and passed into the find function to return the associated enum representing that String.
	 * At no point in the framework is the String actually hard coded. It comes from a .properties file, and read
	 * directly from system properties.
	 * <br><br>
	 * The find function itself works based on the commonality between the enums. All Java enums of course inherit
	 * from the Enum class. Unfortunately, we cannot use inheritance on our own. However, Enum inherits from Object
	 * and thus, has a toString method which normally returns the String representation of the Enum's hard coded name.
	 * However, we can override this in the enum class to return our desired String that the enum represents. Using
	 * this, we can take in a class which extends Enum (effectively upcasting our enumerations), along with our String.
	 * value. We can loop through all enum constants in the class and call the toString function, again which all
	 * of our lookup maps inherit from, and compare that with our given string. If we find a match (ignoring case for
	 * the purpose of this framework), we can return that enum. This framework also requires that a NONE enum exist for
	 * each of the enum classes since they represent environmental variables. As env vars are not required, what we're
	 * looking for may not even exist at all. Instead of using null through the framework which may potentially introduce
	 * unchecked exceptions and makes switches more complex, we use a generic NONE enum. As such, in the find function,
	 * if the given enum is not found, we try to return the enum representing "NONE" based on Java's automatic enum name
	 * to String conversion with Enum.valueOf(Class, String). If NONE does not exist, we return null as a last ditch
	 * measure. Thus, following this pattern. Each enum to be used with this function must overload toString, and must
	 * have a NONE enum and we can generically handle the searching for all enumerations including enforcing the added
	 * NONE functionality which is desired for this framework.
	 * @param enumClass enum Class to find the String from.
	 * @param enumStr String value to match an enum from the given class.
	 * @return the enum which the given String represents from the given Class.
	 */
	public static <E extends Enum<E>> E find(Class<E> enumClass, String enumStr) {
		E none;
		try {
			none = Enum.valueOf(enumClass, "NONE");
		} catch (IllegalArgumentException e) {
			none = null;
		}
		if (enumStr == null) return none;
		for (E e : enumClass.getEnumConstants()) 
			if (enumStr.equalsIgnoreCase(e.toString())) return e;
		return none;
	}
}
