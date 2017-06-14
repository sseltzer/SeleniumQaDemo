package com.sseltzer.selenium.framework.selenium.wrappers;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

public abstract class SearchContextBridge {
	protected abstract WebDriver getWebDriver();

	protected abstract Object getBaseObject();

	protected abstract Class<?> getBaseObjectClass();

	public abstract WebElementWrapper findElement(ByWrapper by, TimingOptions timingOptions);

	public abstract ArrayList<WebElementWrapper> findElements(ByWrapper by, TimingOptions timingOptions);

	protected abstract WebElementWrapper findElementCore(ByWrapper by);

	protected abstract ArrayList<WebElementWrapper> findElementsCore(ByWrapper by);
}
