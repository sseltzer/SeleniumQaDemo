
package com.sseltzer.selenium.internal.test.tests.utility.screen;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 *
 *
 * ScreenGrab.java
 *
 * @author ckiehl Sep 30, 2014
 */
public class ScreenGrab {

	
	public static void takeSnapshot() {
		try {
			Robot r = new Robot();
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			BufferedImage img = r.createScreenCapture(new Rectangle(0, 0, screen.width, screen.height));
			if (System.getProperty("os.name").contains("Win")) 
				ImageIO.write(img, "png", new File("C:/selenium_upload/" + System.currentTimeMillis() +".png"));
			else {
				
				ImageIO.write(img, "png", new File("/app/continuousintegration/selenium/images/" + System.currentTimeMillis()+".png"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
