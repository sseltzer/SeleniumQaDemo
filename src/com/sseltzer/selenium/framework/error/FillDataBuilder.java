package com.sseltzer.selenium.framework.error;

import java.util.ArrayList;

import com.sseltzer.selenium.framework.selenium.mapping.PageObject;

/**
 * The FillDataBuilder object is a simple convenience object which provides a cleaner
 * way to build data for form filled error strings. This object implements method chaining
 * and utilizes variable length argument list static functions to create an ArrayList of
 * Strings to provide to ErrorManager. Once a create function is called, the FillDataBuilder
 * is returned containing the provided Strings in its ArrayList and provides an additional
 * add and get functions to add to or retrieve the internal ArrayList.
 * <br><br>
 * This turns:
 * {@literal
 * ArrayList<String> list = new ArrayList<String>();
 * list.add(String);
 * list.add(String);
 * list.add(String);
 * list.add(String);
 * 
 * Into:
 * FillDataBuilder.create(String, String, String, String);
 * or
 * FillDataBuilder data = FillDataBuilder.create(String, String);
 * if (conditional) data.add(String);
 * else             data.add(String);
 * 
 * etc
 * }
 * 
 * @author Sean Seltzer
 *
 */
public class FillDataBuilder {

	private ArrayList<String> fillData;

	public FillDataBuilder() {
		fillData = new ArrayList<String>();
	}

	/**
	 * Returns a new FillDataBuilder object with an empty internal ArrayList<String>.
	 * @return a new FillDataBuilder object with an empty internal ArrayList<String>.
	 */
	public static FillDataBuilder create() {
		return new FillDataBuilder();
	}

	/**
	 * Returns a new FillDataBuilder object with the given String added to the internal ArrayList<String>. 
	 * @param data string to add to the fill data.
	 * @return a new FillDataBuilder object with the given String added to the internal ArrayList<String>.
	 */
	public static FillDataBuilder create(String data) {
		return FillDataBuilder.create().add(data);
	}

	/**
	 * Returns a new FillDataBuilder object with the given variable length argument list added to the internal ArrayList<String>.
	 * @param dataList variable length argument list of String data to be entered into the fill data. 
	 * @return a new FillDataBuilder object with the given variable length argument list added to the internal ArrayList<String>.
	 */
	public static FillDataBuilder create(String... dataList) {
		FillDataBuilder builder = FillDataBuilder.create();
		for (String data : dataList) builder = builder.add(data);
		return builder;
	}

	public static FillDataBuilder create(int... dataList) {
		FillDataBuilder builder = FillDataBuilder.create();
		for (int data : dataList) builder = builder.add(String.valueOf(data));
		return builder;
	}

	/**
	 * Returns a new FillDataBuilder object with the given variable length argument list added to the internal ArrayList<String>.
	 * @param dataList variable length argument list of PageObject data to be entered into the fill data as toString is called on each PageObject. 
	 * @return a new FillDataBuilder object with the given variable length argument list added to the internal ArrayList<String>.
	 */
	public static FillDataBuilder create(PageObject... dataList) {
		FillDataBuilder builder = FillDataBuilder.create();
		for (PageObject data : dataList) builder = builder.add(data.toString());
		return builder;
	}

	/**
	 * Provides the ability to add a String to the internal ArrayList<String>.
	 * @param data String data to be added to the internal ArrayList<String>.
	 * @return self reference to allow object chaining.
	 */
	public FillDataBuilder add(String data) {
		fillData.add(data);
		return this;
	}

	/**
	 * Returns the internal ArrayList<String> for parsing. This does not return a clone.
	 * @return the internal ArrayList<String> for parsing. This does not return a clone.
	 */
	public ArrayList<String> get() {
		return fillData;
	}

	public FillDataBuilder clear() {
		fillData.clear();
		return this;
	}
}
