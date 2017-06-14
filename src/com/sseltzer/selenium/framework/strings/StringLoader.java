package com.sseltzer.selenium.framework.strings;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sseltzer.selenium.framework.error.ErrorManager;

public class StringLoader {
	
	private static String FILE = "/com/sseltzer/selenium/framework/strings/properties/FrameworkStrings.json";
	
	public static final String READ_ERROR = "Could not read file: ";
	public static final String EMPTY_ERROR = "String file is empty: ";
	public static final String CLOSE_ERROR = "Could not close: ";
	
	private static ApplicationStrings appStrings = null;
	public static ApplicationStrings getApplicationStrings() {
		if (appStrings == null) load();
		return appStrings;
	}
	
	private static void load() {
		File file = new File(StringLoader.class.getResource(FILE).getPath());
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			appStrings = mapper.readValue(file, ApplicationStrings.class);
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			ErrorManager.throwAndDump(READ_ERROR, e);
		} catch (JsonMappingException e) {
			ErrorManager.throwAndDump(READ_ERROR, e);
		} catch (IOException e) {
			ErrorManager.throwAndDump(READ_ERROR, e);
		}
		if (appStrings == null || appStrings.isEmpty()) ErrorManager.throwAndDump(EMPTY_ERROR + FILE, null);
	}
}
