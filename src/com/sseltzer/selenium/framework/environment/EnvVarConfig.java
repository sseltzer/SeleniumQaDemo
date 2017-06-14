package com.sseltzer.selenium.framework.environment;

import java.util.ArrayList;


public class EnvVarConfig {
	private ArrayList<EnvVar> configs;
	
	public EnvVarConfig() {
		this(new ArrayList<EnvVar>());
	}
	public EnvVarConfig(ArrayList<EnvVar> configs) {
		this.configs = configs;
	}
	
	public ArrayList<EnvVar> getConfigs() {
		return configs;
	}
	public void setConfigs(ArrayList<EnvVar> configs) {
		this.configs = configs;
	}
}
