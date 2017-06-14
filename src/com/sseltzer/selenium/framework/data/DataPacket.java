package com.sseltzer.selenium.framework.data;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;

/**
 * The DataPacket object represents a single test case worth of data. This object contains a collection of
 * DataUnits; which are key value pairs. For example, 5 of these, with different keys, might contain a 
 * grouping of five different tags for input boxes with their respective data. Essentially, the DataPacket
 * container holds an ArrayList that may be added to or will retrieve values, however it is intended to be
 * very simple and therefore does not extend ArrayList nor does it provide functionality to remove data. The
 * purpose of this object is to merely hold a collection of DataUnits to represent a test case, and nothing
 * more. This class however implements Iterable to conveniently iterate through test case data. This is useful
 * when using the keys as tags, and the values as data, as you simply have to iterate through and call a generic
 * function with the key as the tag and value as either the expected value or input data.
 * <br><br>
 * It is important to note that this class does not filter duplicates. It must be done before insertion time.
 * The contains function is provided to check for duplicate keys, however the functionality of DataPacket is
 * very straightforward and includes no logic of substance. In fact, this happens in the DataSet.add function.
 * The purpose of this is to allow DataSet to make a determination of whether or not to make a new DataPacket
 * based on the existence of a duplicate key. Duplicate keys denote a new DataPacket to the DataAggregator, which
 * allows the DataAggregator to self organize its data. The end user of DataAggregator will not need to modify
 * a DataPacket directly, as all data should be given straight to the DataAggregator so it may take advantage of
 * the self organization functionality.
 * @author Sean Seltzer
 *
 */
public class DataPacket implements Iterable<DataUnit> {
	private ArrayList<DataUnit> units = null;

	public DataPacket() {
		units = new ArrayList<DataUnit>();
	}

	/**
	 * Add a KVP to the DataPacket. This will create a new DataUnit and retain it.
	 * @param key is the String key of the data.
	 * @param value is the String value of the data.
	 */
	public void add(String key, String value) {
		units.add(new DataUnit(key, value));
	}

	/**
	 * Determines whether or not the particular key is mapped to a value in the DataPacket.
	 * This will return true if some DataUnit key matches the given key.
	 * @param key is the key to check the DataUnits for.
	 * @return Boolean value of whether or not the key is contained within a DataUnit.
	 */
	public boolean contains(String key) {
		for (DataUnit unit : units) if (unit.getKey().equals(key)) return true;
		return false;
	}

	/**
	 * Search all data units for a given key. This will return the first DataUnit for which the
	 * provided key matches the given key. The DataPacket object does not filter duplicate keys 
	 * therefore two keys may exist that have the same value. However, DataSet.add does check for
	 * duplicates before adding. So unless DataPacket is being used without a DataSet, then this
	 * is not an issue. 
	 * @param key is the key associated with the KVP.
	 * @return returns the value of the KVP or an empty string if the requested key does not exist.
	 */
	public String get(String key) {
		return getUnit(key).getValue();
	}

	public String get(DataLocator locator) {
		return getUnit(locator).getValue();
	}

	public DataUnit getUnit(String key) {
		for (DataUnit unit : units) if (unit.getKey().equals(key)) return unit;
		return new DataUnit(key, StringUtils.EMPTY); // This is the reason why partial sets work.
	}

	public DataUnit getUnit(DataLocator locator) {
		return getUnit(locator.getKey());
	}

	/**
	 * Provides a copy of the ArrayList of DataUnits herein.
	 * @return Returns a copy of the collection of DataUnits.  
	 */
	public ArrayList<DataUnit> getUnits() {
		return new ArrayList<DataUnit>(units);
	}

	/**
	 * This will clear the collection of DataUnits causing the DataPacket to be empty.
	 */
	public void clear() {
		units.clear();
	}

	/**
	 * Allows Iterable functionality, so enhanced for loops may be used with DataPacket to iterate
	 * through the collection of DataUnits.
	 */
	public Iterator<DataUnit> iterator() {
		return units.iterator();
	}

	public String jsonify() {
		StringBuilder jsonStr = new StringBuilder();
		for (int i = 0; i < units.size(); ++i) {
			jsonStr.append("      ");
			jsonStr.append("\"" + i + "\" : {\n");
			jsonStr.append(units.get(i).jsonify());
			jsonStr.append("      ");
			jsonStr.append("},\n");
		}
		jsonStr.deleteCharAt(jsonStr.lastIndexOf(","));
		return jsonStr.toString();
	}
}
