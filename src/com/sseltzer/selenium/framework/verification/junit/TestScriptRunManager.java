package com.sseltzer.selenium.framework.verification.junit;

public class TestScriptRunManager {
	private static FailMode failMode = FailMode.HALT;
	private static boolean aborted = false;

	public static void setFailMode(FailMode failMode) {
		TestScriptRunManager.failMode = failMode;
	}

	public static FailMode getFailMode() {
		return failMode;
	}

	public static void abort() {
		aborted = true;
	}

	public static boolean isAborted() {
		return aborted;
	}

	public static void reset(FailMode context) {
		switch (context) {
			case HALT:
				if (failMode == FailMode.HALT) aborted = false;
				break;
			case SCRIPT:
				if (failMode == FailMode.SCRIPT) aborted = false;
				break;
			case CONTINUE:
			default:
				if (failMode == FailMode.CONTINUE) aborted = false;
				break;
		}
	}

	public enum FailMode {
		HALT, SCRIPT, CONTINUE
	}
}
