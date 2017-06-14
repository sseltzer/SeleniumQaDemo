package com.sseltzer.selenium.framework.data;

import com.sseltzer.selenium.framework.selenium.mapping.PageObject;

/**
 * The DataUnit object represents the smallest unit of data that is maintained for the framework data
 * structure. This is a String/String key value pair object that allows you to maintain a mapping of
 * tag name to data. Typically, DataUnit is not used directly, as DataAggregator, DataSet, and DataPacket
 * will manage DataUnits. Those object contain convenience functions to pull values from DataUnits at a
 * higher level in addition to being Iterable.
 * @author Sean Seltzer
 *
 */
public class DataUnit {
	private String key;
	private String value;

	/**
	 * Creates the KVP with the given key and value.
	 * @param key is the key to return the value.
	 * @param value is the data to be stored.
	 */
	public DataUnit(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Returns the key for this KVP.
	 * @return Returns the key for this KVP.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Lazy utility method to effectively just create a "new PageObject(key)".
	 * @return a PageObject which represents this key.
	 */
	public PageObject getKeyAsPageObject() {
		return new PageObject(key);
	}

	/**
	 * Returns the value for this KVP.
	 * @return Returns the value for this KVP.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Lazy utility method to effectively just create a "new PageObject(key)".
	 * @return a PageObject which represents this value.
	 */
	public PageObject getValueAsPageObject() {
		return new PageObject(value);
	}

	/**
	 * Lazy utility method to effectively just create a "Integer.parseInt(value)".
	 * @return an Integer which represents this value.
	 */
	public int getValueAsInteger() {
		return Integer.parseInt(value);
	}

	public String jsonify() {
		return "        \"" + key + "\" : " + "\"" + value + "\"\n";
	}
}
