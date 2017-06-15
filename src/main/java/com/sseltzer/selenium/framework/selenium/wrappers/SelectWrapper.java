package com.sseltzer.selenium.framework.selenium.wrappers;

import java.util.ArrayList;

import org.openqa.selenium.support.ui.Select;

public class SelectWrapper {

	private WebDriverWrapper webDriver;
	private Select select;

	public SelectWrapper(WebDriverWrapper webDriver, WebElementWrapper element) {
		this.webDriver = webDriver;
		select = new Select(element.getBaseObject());
	}

	public SelectWrapper(WebDriverWrapper webDriver, Select select) {
		this.webDriver = webDriver;
		this.select = select;
	}

	public static SelectWrapper convert(WebDriverWrapper webDriver, Select select) {
		return new SelectWrapper(webDriver, select);
	}

	protected Select getBaseObject() {
		return select;
	}

	public void deselectAll() {
		select.deselectAll();
	}

	public void deselectByIndex(int index) {
		select.deselectByIndex(index);
	}

	public void deselectByValue(String value) {
		select.deselectByValue(value);
	}

	public void deselectByVisibleText(String text) {
		select.deselectByVisibleText(text);
	}

	public ArrayList<WebElementWrapper> getAllSelectedOptions() {
		return WebElementWrapper.convert(webDriver, select.getAllSelectedOptions());
	}

	public WebElementWrapper getFirstSelectedOption() {
		return WebElementWrapper.convert(webDriver, select.getFirstSelectedOption());
	}

	public ArrayList<WebElementWrapper> getOptions() {
		return WebElementWrapper.convert(webDriver, select.getOptions());
	}

	public boolean isMultiple() {
		return select.isMultiple();
	}

	public void selectByIndex(int index) {
		select.selectByIndex(index);
	}

	public void selectByValue(String value) {
		select.selectByValue(value);
	}

	public void selectByVisibleText(String text) {
		select.selectByVisibleText(text);
	}
}
