package com.sseltzer.selenium.framework.file.disk;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;
import com.sseltzer.selenium.framework.verification.junit.TestScriptRunManager;
import com.sseltzer.selenium.framework.verification.junit.TestScriptRunManager.FailMode;

public class TestDiskManagers {

	//@Ignore
	@Test
	public void test() {
		TimestampedPackage lm = DiskManager.getScreenshotManager();
		lm.create();
		lm.create();
		lm.delete();
	}

	@Ignore
	@Test
	public void testWriteLogToLatestDirectory() {
		TimestampedPackage lpm = DiskManager.getScreenshotManager();
		lpm.writeFile("test0.json", "test0");
		lpm.writeFile("test1.json", "test1");
		lpm.writeFile("test2.json", "test2");
	}

	@Ignore
	@Test
	public void testAbort() {
		TimestampedPackage lpm = DiskManager.getScreenshotManager();
		lpm.writeFile("test0.json", "test0");
		lpm.writeFile("test1.json", "test1");
		lpm.abort();
	}

	@Ignore
	@Test
	public void testAbortContinue() {
		TestScriptRunManager.setFailMode(FailMode.CONTINUE);
		TimestampedPackage lpm = DiskManager.getScreenshotManager();
		lpm.writeFile("test0.json", "test0");
		lpm.writeFile("test1.json", "test1");
		lpm.abort();
		lpm.writeFile("test2.json", "test2");
		TestScriptRunManager.reset(FailMode.SCRIPT);
	}

	@Ignore
	@Test
	public void testFailureWithNextTestExecution() {
		TimestampedPackage lpm = DiskManager.getScreenshotManager();
		lpm.writeFile("test0.json", "test0");
		lpm.writeFile("test1.json", "test1");
		lpm.abort();
		lpm.writeFile("test2.json", "test2");
	}

	@Ignore
	@Test
	public void zContinue() {
		testFailureWithNextTestExecution();
		if (TestScriptRunManager.isAborted()) fail("Test aborted.");
		TimestampedPackage lpm = DiskManager.getScreenshotManager();
		lpm.writeFile("test3.json", "test3");
		lpm.writeFile("test4.json", "test4");
		lpm.writeFile("test5.json", "test5");
	}

	@Ignore
	@Test
	public void testFailure() {
		TimestampedPackage lpm = DiskManager.getScreenshotManager();
		try {
			lpm.writeFile("test0.json", "test0");
			lpm.writeFile("test1.json", "test1");
			throw new FrameworkException();
		} catch (Exception e) {
			lpm.abort();
			throw new FrameworkException(e);
		}
	}

	@Ignore
	@Test
	public void zzContinue() {
		if (TestScriptRunManager.isAborted()) fail("Test aborted.");
		TimestampedPackage lpm = DiskManager.getScreenshotManager();
		lpm.writeFile("test3.json", "test3");
		lpm.writeFile("test4.json", "test4");
		lpm.writeFile("test5.json", "test5");
	}
}
