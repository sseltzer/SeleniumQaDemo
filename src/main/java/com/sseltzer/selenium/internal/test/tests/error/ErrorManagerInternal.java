package com.sseltzer.selenium.internal.test.tests.error;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;

import com.sseltzer.selenium.framework.error.ErrorManager;
import com.sseltzer.selenium.framework.error.FillDataBuilder;
import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;

/**
*
*
* ErrorManagerInternal.java
*
* @author ckiehl Mar 25, 2014
*/
public class ErrorManagerInternal {

	@Test
	public void testElementnotVisibleExceptionIsCaughtAndDisplaysExpectedMessage() {
		try {
			ErrorManager.throwAndDumpCoreException(new ElementNotVisibleException("fake"), FillDataBuilder.create("fakeCSSSelector"));
		} catch (FrameworkException e) {
			assertTrue(e.getMessage().contains("Element expected to be visible"));
		}
	}

	@Test
	public void testThrowCoreStaleExceptiontestIsCaughtAndDisplaysExpectedMessage() {
		try {
			ErrorManager.throwAndDumpCoreException(new StaleElementReferenceException("fake"), FillDataBuilder.create("fakeCSSSelector"));
		} catch (FrameworkException e) {
			assertTrue(e.getMessage().contains("Received Stale Element when trying to interact"));
		}
	}

	@Test
	public void testThrowCoreInvalidStateExceptionIsCaughtAndDisplaysExpectedMessage() {
		try {
			ErrorManager.throwAndDumpCoreException(new InvalidElementStateException("fake"), FillDataBuilder.create("fakeCSSSelector"));
		} catch (FrameworkException e) {
			assertTrue(e.getMessage().contains("Invalid State Exception"));
		}
	}

	@Test
	public void testThrowCoreTargetOutOfBoundsExceptionIsCaughtAndDisplaysExpectedMessage() {
		try {
			ErrorManager.throwAndDumpCoreException(new MoveTargetOutOfBoundsException("fake"), FillDataBuilder.create("fakeCSSSelector"));
		} catch (FrameworkException e) {
			assertTrue(e.getMessage().contains("Move target is outside"));
		}
	}

	@Test
	public void testThrowCoreNoSuchWindowExceptionIsCaughtAndDisplaysExpectedMessage() {
		try {
			ErrorManager.throwAndDumpCoreException(new NoSuchWindowException("fake"), FillDataBuilder.create("fakeCSSSelector"));
		} catch (FrameworkException e) {
			assertTrue(e.getMessage().contains("Could not locate Window"));
		}
	}

	@Test
	public void testThrowCoreNoSuchFrameExceptionIsCaughtAndDisplaysExpectedMessage() {
		try {
			ErrorManager.throwAndDumpCoreException(new NoSuchFrameException("fake"), FillDataBuilder.create("fakeCSSSelector"));
		} catch (FrameworkException e) {
			assertTrue(e.getMessage().contains("Could not locate Frame"));
		}
	}

	@Test
	public void testThrowCoreSuchElementExceptionIsCaughtAndDisplaysExpectedMessage() {
		try {
			ErrorManager.throwAndDumpCoreException(new NoSuchElementException("fake"), FillDataBuilder.create("fakeCSSSelector"));
		} catch (FrameworkException e) {
			assertTrue(e.getMessage().contains("Could not locate element"));
		}
	}

	@Test
	public void testThrowCoreTimeOutExceptionIsCaughtAndDisplaysExpectedMessage() {
		try {
			ErrorManager.throwAndDumpCoreException(new TimeoutException("fake"), FillDataBuilder.create("fakeCSSSelector"));
		} catch (FrameworkException e) {
			assertTrue(e.getMessage().contains("Timed out after"));
		}
	}

	@Test
	public void testThrowCoreGeneralExceptionIsCaughtAndDisplaysExpectedMessage() {
		try {
			ErrorManager.throwAndDumpCoreException(new Exception("Something bad happened!"), FillDataBuilder.create("fakeCSSSelector"));
		} catch (FrameworkException e) {
			assertTrue(e.getMessage().contains("Something bad happened"));
		}
	}
}
