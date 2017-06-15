package com.sseltzer.selenium.framework.data;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The DataSet object represents a set of test cases. This object contains a set of DataPackets which in turn
 * contain a set of DataUnits. While DataPacket is a collection of key value pairs to represent a single test,
 * the DataSet will contain multiple DataPackets. Each DataPacket will contain duplicate KVPs. This allows the
 * DataAggregator to self organize. When DataAggregator is given a series of data, it is routed to the correct
 * DataSet object which will then start adding them to the last DataPacket object. When a duplicate key is
 * detected, this will cause DataSet to create a new DataPacket and add the duplicate key to it instead. All
 * subsequent data is always added to the last packet until a duplicate is detected, and a new packet is created,
 * replacing it as the last packet.
 * <br>
 * <br>For Example:
 * <pre>
 * 1. DataAggregator data = DataAggregator.getInstance();
 * 2. data.add("setName", "firstName", "John");
 * 3. data.add("setName", "lastName",  "Smith");
 * 4. data.add("setName", "firstName", "Jane");
 * 5. data.add("setName", "lastName",  "Smith");
 * 6. data.add("setName", "firstName", "Jimmy");
 * 7. data.add("setName", "lastName",  "Smith");
 * </pre>
 * In the above example, firstName is added to the first DataPacket (which is created empty to provide a 
 * "last packet" to add to). Since "lastName" does not conflict with "firstName", it too is added to the
 * same DataPacket. Upon reaching the line 4, "firstName" exists in the packet from the first line. 
 * The DataSet will then create a new DataPacket which will then become the last packet. "firstName" is
 * added to this new packet. On line 5, again, no "lastName" exists in this new packet. On line 6 "firstName"
 * is encountered again which will cause a new packet to be created, and repeat the cycle.
 * <br><br>
 * This self organizing concept, combined with the ability to iterate easily allows for tests to be created
 * iteratively. using the above example:
 * <br>
 * <pre>
 * 8.  for (DataPacket packet : data.getSet("setName")) {
 * 9.    String firstNameVal = packet.get("firstName");
 * 10.   String lastNameVal  = packet.get("lastName");
 * 11.   System.out.println(firstNameVal + " " + lastNameVal);
 * 12. }
 * </pre>
 * In this example, the set name may be requested from DataAggregator which will provide the set which was
 * mapped to in lines 2 - 7.This set will contain three packets which contain the KVP mappings that were inserted.
 * Now that a DataSet is obtained, an enhanced for loop may be called to iterate through the DataPacket objects
 * contained within. Each DataPacket will contain the keys that were inserted. Calling get using the keys,
 * "firstName" and "lastName" then will provide "John Smith", "Jane Smith", and "Jimmy Smith" which may be used
 * in an iterative test.
 * <br><br>
 * An important note to make is that if a key is requested that does not exist in the DataPacket, an empty string
 * is returned; "". This allows partial data to be inserted for test cases. For example, if a firstName, lastName
 * and email are provided for 5 sets of data, but the last set only provides a lastName; it is assumed that the
 * firstName and email are blank. This facilitates iterative tests where each DataPacket may not be structured
 * exactly the same as the last. Some fields may be left blank (not populated in the DataAggregator), and upon
 * requested while iterating, will return a blank which will be used in the test.
 * @author Sean Seltzer
 *
 */
public class DataSet implements Iterable<DataPacket> {
	private ArrayList<DataPacket> packets = null;

	public DataSet() {
		packets = new ArrayList<DataPacket>();
		packets.add(new DataPacket());
	}

	/**
	 * This copy constructor is used to duplicate the DataSet when a call is made to retrieve it.
	 * @param dataSet is the DataSet to be copied.
	 */
	public DataSet(DataSet dataSet) {
		packets = new ArrayList<DataPacket>(dataSet.getPackets());
	}

	/**
	 * The add function will take a key and a value which will add them to the DataSet. The DataSet will
	 * always add the data to the last packet in the collection. If a collision is detected between data
	 * and data currently in the last packet - that is, if an attempt is made to insert a key into a DataPacket
	 * which already contains that key, a new DataPacket will be created and all subsequent data will be added
	 * to the new packet until another collision is detected. Once DataSet determines if it needs to (and does)
	 * create a new DataPacket, DataPacket.add(String, String) is called to add the key to the packet.
	 * @param key is the key of the KVP to be retained in the DataPacket.
	 * @param value is the value of the KVP to be retained in the DataPacket.
	 * @return The index of the DataPacket that the key was added to is returned.
	 */
	public int add(String key, String value) {
		if (packets.get(packets.size() - 1).contains(key)) packets.add(new DataPacket());
		packets.get(packets.size() - 1).add(key, value);
		return (packets.size() - 1);
	}

	/**
	 * Retrieves a given key from a DataPacket at the given index in the DataSet. This will correspond to
	 * the order of data entered. For example, if five sets of data are entered, and the third one is required
	 * the index will be 2. This should be seldom used as DataLocators may be used, in addition to Iterable.
	 * @param index is the index of the DataPacket in the DataSet which should correspond to the order the data was entered.
	 * @param key is the key of the KVP.
	 * @return Returns the value of the KVP for the key in the specified DataPacket.
	 */
	public String get(int index, String key) {
		return packets.get(index).get(key);
	}

	/**
	 * Gets the DataPacket object at the given index. This will correspond to the order of data entered. For 
	 * example, if five sets of data are entered, and the third one is required the index will be 2.
	 * @param index is the index of the DataPacket in the DataSet which should correspond to the order the data was entered.
	 * @return Returns the DataPacket at the given index.
	 */
	public DataPacket getPacket(int index) {
		return packets.get(index);
	}

	/**
	 * This will return a copy of the DataPacket collection.
	 * @return Returns a copy of the DataPacket collection.
	 */
	public ArrayList<DataPacket> getPackets() {
		return new ArrayList<DataPacket>(packets);
	}

	/**
	 * This will clear the collection of DataPackets causing the DataPacket to be empty.
	 */
	public void clear() {
		for (DataPacket packet : packets)
			packet.clear();
		packets.clear();
		packets.add(new DataPacket());
	}

	/**
	 * Allows Iterable functionality, so enhanced for loops may be used with DataSet to iterate
	 * through the collection of DataPackets.
	 */
	public Iterator<DataPacket> iterator() {
		return packets.iterator();
	}

	public String jsonify() {
		StringBuilder jsonStr = new StringBuilder();
		for (int i = 0; i < packets.size(); ++i) {
			jsonStr.append("    ");
			jsonStr.append("\"" + i + "\" : {\n");
			jsonStr.append(packets.get(i).jsonify());
			jsonStr.append("    ");
			jsonStr.append("},\n");
		}
		jsonStr.deleteCharAt(jsonStr.lastIndexOf(","));
		return jsonStr.toString();
	}
}
