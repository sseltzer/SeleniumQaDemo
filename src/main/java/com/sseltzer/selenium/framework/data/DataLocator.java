package com.sseltzer.selenium.framework.data;

/**
 * The DataLocator object is used as a pointer to locate a specific piece of data in the DataLocator.
 * The primary purpose of this is to abstract the data specifics from the test. When data is inserted
 * the must be used in one or more tests, data may be stored and the locator retrieved. The locator
 * may then be stored statically and retained through several tests. No test needs to know the specific
 * details of the data, only that if the DataLocator is provided to DataAggregator that the desired
 * data will be returned. For example, a useful aspect of this, is that a test script runtime for
 * Valpak.com, the Welcome page search geography string "Clearwater, FL" is stored, and the DataLocator
 * retained statically. Five subsequent tests may then use DataLocator to get the data, none of which
 * need to know anything about the value "Clearwater, FL". This can then change, or a set of geographies
 * used which will simplify testing.
 * <br><br>
 * Note, that DataLocator is simply a container that will reference data. It may not be modified, only
 * used at a later time to retrieve the data it specifically references.  
 * @author Sean Seltzer
 *
 */
public class DataLocator {

	private String setName = null;
	private int packetNum = -1;
	private String key = null;

	/**
	 * A DataLocator is created when adding data to the DataAggregator. The setName will correspond
	 * to the Map in DataAggregator, the packetNum is the index of the DataPacket in the DataSet collection,
	 * and the key is used to iterate through the DataPacket to find the value.
	 * @param setName is the setName from DataAggregator which maps to the DataSet.
	 * @param packetNum is the index of the DataPacket in the DataSet collection.
	 * @param key is the key in the KVP used to iterate through the DataPacket to find the DataUnit.
	 */
	public DataLocator(String setName, int packetNum, String key) {
		this.setName = setName;
		this.packetNum = packetNum;
		this.key = key;
	}

	/**
	 * Returns the setName from DataAggregator which maps to the DataSet.
	 * @return Returns the setName from DataAggregator which maps to the DataSet.
	 */
	public String getSetName() {
		return setName;
	}

	/**
	 * Returns the index of the DataPacket in the DataSet collection.
	 * @return Returns the index of the DataPacket in the DataSet collection.
	 */
	public int getPacketNum() {
		return packetNum;
	}

	/**
	 * Returns the key in the KVP used to iterate through the DataPacket to find the DataUnit.
	 * @return Returns the key in the KVP used to iterate through the DataPacket to find the DataUnit.
	 */
	public String getKey() {
		return key;
	}

}
