package com.sseltzer.selenium.framework.strings;

import java.util.ArrayList;

public class ApplicationStrings {
	
	private ArrayList<String> frameworkStrings;
	private ArrayList<String> internalStrings;
	private ArrayList<String> publicStrings;
	
	public ArrayList<String> getFrameworkStrings() {
		return frameworkStrings;
	}
	public void setFrameworkStrings(ArrayList<String> frameworkStrings) {
		this.frameworkStrings = frameworkStrings;
	}
	public ArrayList<String> getInternalStrings() {
		return internalStrings;
	}
	public void setInternalStrings(ArrayList<String> internalStrings) {
		this.internalStrings = internalStrings;
	}
	public ArrayList<String> getPublicStrings() {
		return publicStrings;
	}
	public void setPublicStrings(ArrayList<String> publicStrings) {
		this.publicStrings = publicStrings;
	}
	
	public boolean isEmpty() {
		if (frameworkStrings == null || frameworkStrings.isEmpty()) return true;
		if (internalStrings == null  || internalStrings.isEmpty())  return true;
		if (publicStrings == null    || publicStrings.isEmpty())    return true;
		return false;
	}
}
