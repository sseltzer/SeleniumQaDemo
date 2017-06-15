package com.sseltzer.selenium.framework.file.disk;

import java.io.File;

import com.sseltzer.selenium.framework.file.common.TimestampGenerator;
import com.sseltzer.selenium.framework.verification.junit.TestScriptRunManager;
import com.sseltzer.selenium.framework.verification.junit.TestScriptRunManager.FailMode;

public class GitFilePackage extends DiskPackage {

	private TimestampGenerator tGen;

	private String currentDirectory;
	private File newDirectory = null;

	public GitFilePackage(String rootPath, String prefix) {
		super(rootPath);
		tGen = new TimestampGenerator(prefix);
		currentDirectory = "current";
	}

	private File createTemp() {
		return create(tGen.generateNew());
	}

	public String getCurrentDirectory() {
		return currentDirectory;
	}

	public void writeFile(String fileName, String data) {
		if (TestScriptRunManager.isAborted()) return;
		if (newDirectory == null) newDirectory = createTemp();
		super.writeFile(newDirectory.getAbsolutePath() + File.separator + fileName, data);
	}

	public void abort() {
		delete(newDirectory);
		newDirectory = null;
		if (TestScriptRunManager.getFailMode() == FailMode.HALT) TestScriptRunManager.abort();
	}
}
