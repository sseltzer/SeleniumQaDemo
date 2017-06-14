
package com.sseltzer.selenium.framework.utility.external;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;
import com.sseltzer.selenium.framework.selenium.wrappers.TimingManager;

/**
 * 
 * Base class for interacting with programs outside of 
 * Selenium's control.
 * 
 * This is likely to change a ton due to my indecisiveness, so 
 * if you're going to use it, you'll want to control object creation 
 * through the factory, rather than through the constructor.     
 * 
 * AppController.java
 *
 * @author ckiehl Jul 9, 2014
 */
public class AppController {
	private Robot _robot;
	private List<Integer> closeInstructions;

	public AppController() throws FrameworkException {
		this(Arrays.asList(KeyEvent.VK_ESCAPE));
	}
	
	public AppController(List<Integer> closeActions) throws FrameworkException {
		try {
			this._robot = new Robot();
			this.closeInstructions = closeActions;
		}	catch (AWTException e) { 
			throw new FrameworkException(e); 
		}
	}
	
	public void close() throws FrameworkException {
		System.out.println("Running Close Commmand");
		for (Integer closeAction : closeInstructions)
			this.pressKey(closeAction);
	}
	
	private void pressKey(final int key) {
		this._robot.keyPress(key);
		TimingManager.waitForSpecifiedDuration(50);
		this._robot.keyRelease(key);
		TimingManager.waitForSpecifiedDuration(100);
	}
	
}
