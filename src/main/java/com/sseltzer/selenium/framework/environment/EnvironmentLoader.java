package com.sseltzer.selenium.framework.environment;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sseltzer.selenium.framework.error.ErrorManager;

public class EnvironmentLoader {
	private static String CONFIG_FILE = "/properties/EnvVarConfig.json";

	private EnvVarConfig configs;
	private HashMap<String, String> envVariables;

	public static final String READ_ERROR = "Could not read EnvVarConfig.";
	public static final String EMPTY_ERROR = "EnvVarConfig is empty.";
	public static final String CLOSE_ERROR = "Could not close EnvVarConfig.";

	public void load() {
		envVariables = new HashMap<String, String>();
		
		File file = new File(getClass().getResource(CONFIG_FILE).getPath());
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			configs = mapper.readValue(file, EnvVarConfig.class);
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			ErrorManager.throwAndDump(READ_ERROR, e);
		} catch (JsonMappingException e) {
			ErrorManager.throwAndDump(READ_ERROR, e);
		} catch (IOException e) {
			ErrorManager.throwAndDump(READ_ERROR, e);
		}
		if (configs == null || configs.getConfigs().size() < 1) ErrorManager.throwAndDump(EMPTY_ERROR, null);
		for (EnvVar e : configs.getConfigs()) envVariables.put(e.getName(), readVariable(e.getName()));
	}
	
	public void reload() {
		envVariables.clear();
		load();
	}

	private static String readVariable(String variable) {
		String ret = System.getProperty(variable);
		if (ret != null) return ret;
		ret = System.getProperty(variable.toLowerCase());
		if (ret != null) return ret;
		ret = System.getenv(variable);
		if (ret != null) return ret;
		ret = System.getenv(variable.toLowerCase());
		if (ret != null) return ret;
		return ret;
	}

	public String getVariable(String key) {
		if (envVariables == null) load();
		return envVariables.get(key);
	}
}
