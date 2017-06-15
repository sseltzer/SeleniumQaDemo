package com.sseltzer.selenium.framework.selenium.webpage.base;

import com.sseltzer.selenium.framework.environment.EnvironmentHandler;
import com.sseltzer.selenium.framework.selenium.mapping.PageObject;
import com.sseltzer.selenium.framework.selenium.wrappers.ByWrapper;
import com.sseltzer.selenium.framework.selenium.wrappers.LocatorType;
import com.sseltzer.selenium.framework.selenium.wrappers.WebDriverWrapper;
import com.sseltzer.selenium.framework.verification.validation.ArgumentValidator;

/**
 * Will determine which type of tag is being retrieved from the page. The existence of a special character
 * within the tag will denote CSS. If a special character is not found, then the tag is marked as being an
 * ID.
 * <br><br>
 * Special characters currently include: 
 * <ul>
 *   <li>'.'
 *   <li>'['
 *   <li>'('
 *   <li>':'
 *   <li>' '
 * </ul>
 * 
 * @author Sean Seltzer
 */
public class TagDetector {

	private static final String cssFlagStr = "._[_*_(_:_ ";
	private static final String[] cssFlags = cssFlagStr.split("_");

	/**
	 * Will determine which type of tag is being retrieved from the page. The existence of a special character
	 * within the tag will denote CSS. If a special character is not found, then the tag is marked as being an
	 * ID.
	 * <br><br>
	 * Special characters currently include: 
	 * <ul>
	 *   <li>'.'
	 *   <li>'['
	 *   <li>'('
	 *   <li>':'
	 *   <li>' '
	 * </ul>
	 * @param tag is the String of the selector to auto-detect the type of tag from. 
	 * @return The TagType enum which may be switched to determine what type of call is required
	 * to interact with that tag. 
	 * @
	 */
	public static TagType detectTagType(String tag) {
		ArgumentValidator.create().validate(tag, "tag");
		if (EnvironmentHandler.isMobileTest()) {
			if (tag.startsWith("//")) return TagType.XPATH;
			if (tag.contains(":id")) return TagType.ID;
			return TagType.NAME;
		}
		for (String cssFlag : cssFlags)
			if (tag.contains(cssFlag)) return TagType.CSS;
		return TagType.ID;
	}

	public static ByWrapper detectBy(WebDriverWrapper webDriver, PageObject pageElement) {
		ArgumentValidator.create().validate(webDriver, "webDriver").validate(pageElement, "pageElement");
		ByWrapper by;
		switch (detectTagType(pageElement.toString())) {
			default:
			case ID:
				by = LocatorType.ID.getByWrapper(webDriver, pageElement);
				break;
			case CSS:
				by = LocatorType.CSS.getByWrapper(webDriver, pageElement);
				break;
			case XPATH:
				by = LocatorType.XPATH.getByWrapper(webDriver, pageElement);
				break;
			case NAME:
				by = LocatorType.NAME.getByWrapper(webDriver, pageElement);
				break;
		}
		return by;
	}

	/**
	 * Is a simple enum to distinguish between the ID and CSS when auto-detecting tag types.
	 * @author Sean Seltzer
	 *
	 */
	public enum TagType {
		ID, CSS, XPATH, NAME
	}

}
