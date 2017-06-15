package com.sseltzer.selenium.framework.file.disk;

import java.io.File;

import com.sseltzer.selenium.framework.environment.EnvironmentHandler;
import com.sseltzer.selenium.framework.file.common.TimestampGenerator;
import com.sseltzer.selenium.framework.verification.junit.TestScriptRunManager;
import com.sseltzer.selenium.framework.verification.junit.TestScriptRunManager.FailMode;

public class TimestampedPackage extends DiskPackage {

	private TimestampGenerator tGen;

	private File rootDir = null;
	private File writeDirectory = null;

	public TimestampedPackage(String rootPath, String prefix) {
		super(rootPath);
		tGen = new TimestampGenerator(prefix);
		rootDir = new File(rootPath);
	}

	public File create() {
		String buildNumber = EnvironmentHandler.getBuildNumber();
		if (buildNumber == null || buildNumber.equals("")) buildNumber = "local_";
		else buildNumber = EnvironmentHandler.getImagePath() + "_Build_" + buildNumber + "_";
		return create(buildNumber + tGen.generateNew());
	}

	public void delete() {
		delete(getRootPath() + getLatestDirectoryName());
	}

	public String getLatestDirectoryName() {
		String[] directories = getSubdirectories(rootDir);
		if (directories.length < 1) return null;
		String latest = directories[0];
		for (String s : directories) latest = (latest.compareTo(s) < 0) ? s : latest;
		return latest;
	}

	public void writeFile(String fileName, String data) {
		if (TestScriptRunManager.isAborted()) return;
		if (writeDirectory == null) writeDirectory = create();
		super.writeFile(writeDirectory.getAbsolutePath() + File.separator + fileName, data);
	}
	public void writeFile(String fileName, byte[] data) {
		if (TestScriptRunManager.isAborted()) return;
		if (writeDirectory == null) writeDirectory = create();
		super.writeFile(writeDirectory.getAbsolutePath() + File.separator + fileName, data);
	}

	public void abort() {
		delete(writeDirectory);
		writeDirectory = null;
		if (TestScriptRunManager.getFailMode() == FailMode.HALT) TestScriptRunManager.abort();
	}
}
