package com.sseltzer.selenium.framework.selenium.wrappers;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sseltzer.selenium.framework.error.ErrorManager;
import com.sseltzer.selenium.framework.error.FillDataBuilder;

public class WebElementWrapper extends SearchContextBridge {
	private WebDriverWrapper webDriver;
	private WebElement webElement;
	private FillDataBuilder fillData;

	public WebElementWrapper(WebDriverWrapper webDriver, WebElement webElement) {
		this.webDriver = webDriver;
		this.webElement = webElement;
		fillData = FillDataBuilder.create(webElement.getTagName());
	}

	public static WebElementWrapper convert(WebDriverWrapper webDriver, WebElement webElement) {
		return new WebElementWrapper(webDriver, webElement);
	}

	public static ArrayList<WebElementWrapper> convert(WebDriverWrapper webDriver, List<WebElement> elements) {
		ArrayList<WebElementWrapper> ret = new ArrayList<WebElementWrapper>();
		for (WebElement element : elements)
			ret.add(new WebElementWrapper(webDriver, element));
		return ret;
	}

	@Override
	protected WebDriver getWebDriver() {
		return webDriver.getBaseObject();
	}

	@Override
	public WebElement getBaseObject() {
		return webElement;
	}

	@Override
	protected Class<?> getBaseObjectClass() {
		return webElement.getClass();
	}

	public void clear() {
		try {
			webElement.clear();
		} catch (Exception e) {
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
	}

	public void click() {
		try {
			webElement.click();
		} catch (Exception e) {
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
	}

	@Override
	protected WebElementWrapper findElementCore(ByWrapper by) {
		try {
			return WebElementWrapper.convert(webDriver, webElement.findElement(by.getBaseObject()));
		} catch (Exception e) {
			FillDataBuilder fillData = FillDataBuilder.create(by.getBaseObject().toString());
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
		// This can't execute, either the element is returned from findElement, or an exception is thrown by the wait function.
		ErrorManager.throwDeadCode();
		return null;
	}

	@Override
	protected ArrayList<WebElementWrapper> findElementsCore(ByWrapper by) {
		try {
			return WebElementWrapper.convert(webDriver, webElement.findElements(by.getBaseObject()));
		} catch (Exception e) {
			FillDataBuilder fillData = FillDataBuilder.create(by.getBaseObject().toString());
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
		// This can't execute, either the element is returned from findElement, or an exception is thrown by the wait function.
		ErrorManager.throwDeadCode();
		return null;
	}

	@Override
	public WebElementWrapper findElement(ByWrapper by, TimingOptions timingOptions) {
		TimingManager.waitForValpakComPageLoad(this);
		return TimingManager.findElement(this, by, timingOptions);
	}

	@Override
	public ArrayList<WebElementWrapper> findElements(ByWrapper by, TimingOptions timingOptions) {
		TimingManager.waitForValpakComPageLoad(this);
		return TimingManager.findElements(this, by, timingOptions);
	}

	public String getAttribute(String name) {
		try {
			return webElement.getAttribute(name);
		} catch (Exception e) {
			FillDataBuilder fillData = FillDataBuilder.create(name);
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
		// This can't execute, either the element is returned from findElement, or an exception is thrown by the wait function.
		ErrorManager.throwDeadCode();
		return null;
	}

	public String getCSSValue(String propertyName) {
		try {
			return webElement.getCssValue(propertyName);
		} catch (Exception e) {
			ErrorManager.throwAndDumpCoreException(e, FillDataBuilder.create(propertyName));
		}
		// This can't execute, either the element is returned from findElement, or an exception is thrown by the wait function.
		ErrorManager.throwDeadCode();
		return null;
	}

	public Point getLocation() {
		try {
			return webElement.getLocation();
		} catch (Exception e) {
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
		// This can't execute, either the element is returned from findElement, or an exception is thrown by the wait function.
		ErrorManager.throwDeadCode();
		return null;

	}
	
	public Dimension getSize() {
		try {
			return webElement.getSize();
		} catch (Exception e) {
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
		// This can't execute, either the element is returned from findElement, or an exception is thrown by the wait function.
		ErrorManager.throwDeadCode();
		return null;
	}

	public String getTagName() {
		try {
			return webElement.getTagName();
		} catch (Exception e) {
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
		// This can't execute, either the element is returned from findElement, or an exception is thrown by the wait function.
		ErrorManager.throwDeadCode();
		return null;

	}

	public String getText() {
		try {
			return webElement.getText();
		} catch (Exception e) {
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
		// This can't execute, either the element is returned from findElement, or an exception is thrown by the wait function.
		ErrorManager.throwDeadCode();
		return null;
	}
	
	public boolean hasAttribute(String attributeName) {
		String attrib = this.getAttribute(attributeName);
		return attrib == null ? false : true;
	}

	public boolean isDisplayed() {
		try {
			return webElement.isDisplayed();
		} catch (Exception e) {
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
		// This can't execute, either the element is returned from findElement, or an exception is thrown by the wait function.
		ErrorManager.throwDeadCode();
		return false;
	}

	public boolean isEnabled() {
		try {
			return webElement.isEnabled();
		} catch (Exception e) {
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
		// This can't execute, either the element is returned from findElement, or an exception is thrown by the wait function.
		ErrorManager.throwDeadCode();
		return false;
	}

	public boolean isSelected() {
		try {
			return webElement.isSelected();
		} catch (Exception e) {
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
		// This can't execute, either the element is returned from findElement, or an exception is thrown by the wait function.
		ErrorManager.throwDeadCode();
		return false;
	}

	public void sendKeys(CharSequence... keysToSend) {
		try {
			webElement.sendKeys(keysToSend);
		} catch (Exception e) {
			ErrorManager.throwAndDumpCoreException(e, FillDataBuilder.create(keysToSend.toString()));
		}
	}

	public boolean isOnScreen() {
		Point location = webElement.getLocation();
		if (location.getX() < 0) return false;
		if (location.getY() < 0) return false;
		Dimension dim = getWebDriver().manage().window().getSize();
		if (location.getX() > dim.getWidth()) return false;
		if (location.getY() > dim.getHeight()) return false;
		return true;
	}

	public void submit() {
		try {
			webElement.submit();
		} catch (Exception e) {
			ErrorManager.throwAndDumpCoreException(e, fillData);
		}
	}
	
	
	
	public boolean isLeftOf(WebElementWrapper that) {
		return this.bottomRight().x < that.topLeft().x; 
	}
	
	public boolean isRightOf(WebElementWrapper that) {
		return this.topLeft().x > that.bottomRight().x; 
	}
	
	public boolean isBelow(WebElementWrapper that) {
		return this.topLeft().y > that.bottomRight().y;
	}
	
	public boolean isAbove(WebElementWrapper that) {
		return this.bottomRight().y <= that.topLeft().y; 
	}
	
	public boolean isInside(WebElementWrapper that) {
		return this.topLeft().y > that.topLeft().y && 
			   this.topLeft().x > that.topLeft().x &&
			   this.bottomRight().y < that.bottomRight().y && 
			   this.bottomRight().x < that.bottomRight().x;  
	}
	
	public Point topLeft() {
		return this.webElement.getLocation();
	}

	public Point bottomRight() {
		int x = this.webElement.getLocation().x; 
		int y = this.webElement.getLocation().y; 
		int bottomX = x + this.webElement.getSize().width;  
		int bottomY = y + this.webElement.getSize().height; 
		return new Point(bottomX, bottomY);
	}
}
