package com.sseltzer.selenium.framework.utility.external;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 * RobotFactory.java
 *
 * @author ckiehl Jul 9, 2014
 */
public final class ControllerFactory {
	
	public static AppController newDefaultCloseController() {
		List<Integer> closeInstructions = Arrays.asList(
				KeyEvent.VK_ESCAPE);
		return new AppController(closeInstructions);
	}

	public static AppController newPrintDialogController() {
		List<Integer> closeInstructions = Arrays.asList(
				KeyEvent.VK_ESCAPE);
		return new AppController(closeInstructions);
	}
	
	public static AppController newCallDialogController() {
		List<Integer> closeInstructions = Arrays.asList(
				KeyEvent.VK_ESCAPE, KeyEvent.VK_ESCAPE);
		return new AppController(closeInstructions);
	}

	public static AppController newOutlookController() {
		List<Integer> closeInstructions = Arrays.asList(
				KeyEvent.VK_ESCAPE, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);
		return new AppController(closeInstructions);
	}

}
