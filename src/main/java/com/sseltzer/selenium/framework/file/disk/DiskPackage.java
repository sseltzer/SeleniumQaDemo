package com.sseltzer.selenium.framework.file.disk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;

import com.sseltzer.selenium.framework.error.exceptions.FrameworkException;

public class DiskPackage {

	private String rootPath;

	public DiskPackage(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getRootPath() {
		return rootPath;
	}

	public File create(String name) {
		File file = new File(rootPath + name);
		if (!file.mkdir()) System.out.println("could not make folder");
		return file;
	}

	public void delete(String name) {
		delete(new File(name));
	}

	public void delete(File file) {
		recursiveDelete(file);
	}

	private void recursiveDelete(File file) {
		if (!file.exists()) return;
		for (File f : file.listFiles()) f.delete();
		for (String d : getSubdirectories(file)) delete(file.getAbsolutePath() + File.separator + d);
		file.delete();
	}

	public String[] getSubdirectories(String path) {
		return getSubdirectories(new File(path));
	}

	public String[] getSubdirectories(File file) {
		return file.list(new FilenameFilter() {
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
	}

	public void writeFile(String fileName, String data) {
		File file = new File(fileName);
		FileWriter fileWriter = null;
		try {
			if (file.exists()) file.delete();
			fileWriter = new FileWriter(file);
			fileWriter.write(data);
		} catch (Exception e) {
			throw new FrameworkException(e);
		} finally {
			try {
				if (fileWriter != null) fileWriter.close();
			} catch (Exception e) {
				throw new FrameworkException(e);
			}
		}
	}
	public void writeFile(String fileName, byte[] data) {
		File file = new File(fileName);
		FileOutputStream fos = null;
		try {
			if (file.exists()) file.delete();
			file.createNewFile();
			fos = new FileOutputStream(file);
			fos.write(data);
		} catch (Exception e) {
			throw new FrameworkException(e);
		} finally {
			try {
				if (fos != null) fos.close();
			} catch (Exception e) {
				throw new FrameworkException(e);
			}
		}
	}
}
