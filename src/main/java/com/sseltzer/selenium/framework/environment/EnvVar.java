package com.sseltzer.selenium.framework.environment;

import java.util.ArrayList;

public class EnvVar {
	private String name;
	private ArrayList<String> values;
	
	public EnvVar() {
		this("", new ArrayList<String>());
	}
	public EnvVar(String name, ArrayList<String> values) {
		this.name = name;
		this.values = values;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<String> getValues() {
		return values;
	}
	public void setValues(ArrayList<String> values) {
		this.values = values;
	}
}
