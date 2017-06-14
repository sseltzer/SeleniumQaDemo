package com.sseltzer.selenium.framework.data;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import com.sseltzer.selenium.framework.selenium.mapping.PageObject;

/**
 * DataAggregator is implemented as a Singleton object (partially due to the requirement that objects
 * be static in order to persist between JUnit tests). The DataAggregator is an interface in which to
 * store and retrieve data easily. Three values are needed to store data: an arbitrary setName to uniquely
 * identify which data set the data belongs to, a key to retrieve the data, and the value of the data. The
 * setName can be anything, but should be something relevant to the collection, such as "signup" for
 * membership form data. The key should generally correspond to the element on the page which the data belongs
 * to, so iterative tests can be taken advantage of. The value should just be the value of the key, again to
 * take advantage of iterative tests. The DataAggregator knows nothing of any implementation and only holds
 * data, therefore it may be used to store anything. The setName and key are arbitrary and it only matters
 * that they are unique (the key may be duplicates for multiple data sets; more on that later). As such,
 * the DataAggregator may store any data. For example, a page title will not correspond to an element tag,
 * therefore ("title", "title", "Valpak.com") for example may be used. A DataLocator is returned at insertion
 * time, so the locator may be used as the parameter for DataAggregator.get later, therefore the actual test
 * need not know anything about the data, setName, or key. However, the key may be important and upon entering
 * data, instead of using a DataLocator, a DataSet may be iterated through and a specific key requested from
 * a DataPacket to get a specific piece of data. Using this, the DataAggregator then become flexible as it
 * may be used to store data statically which may be inserted once and retained through a set of tests. The
 * retrieval method is simplistic but flexible and allows numerous duplicate tests to be condensed into a single
 * test, greatly reducing testing complexity, duplication, and maintenance headache. In addition, if a PageObject
 * is used as a key, we abstract the key's value altogether. If the page changes and the key must change, once
 * we change the PageObject, the DataAggregator automatically adjusts. The correct value is then used in the test.
 * So only the mapping must change, and neither the DataAggregator, nor the test itself must change. 
 * <br><br>
 * The DataAggregator object contains a map of String keys to DataSet values. When a new key is provided, a
 * new DataSet is created. This is 1:1, named set to DataSet. The DataSet then contains a set of test data to
 * conduct a single test with multiple sets of data. The DataSet contains a collection of DataPackets which 
 * represent a single set of test data. The DataPacket contains a collection of DataUnits, which is simply a
 * Key Value Pair (KVP). The three strings needed to insert something into the DataAggregator are again
 * (String setName, String key, String value). However the setName and key may use PageObjects directly for
 * convenience. Once these parameters are provided. The DataAggregator will retrieve the DataSet based on
 * the setName. If it does not exist, it creates it. It will add the KVP to the DataSet. The DataSet will then
 * attempt to add the KVP to the last DataPacket. If the DataPacket contains a duplicate key, it is assumed
 * that this data represents a new DataPacket. A new one will be created and the KVP will be added to the new
 * DataPacket which will store it as a DataUnit. After the add, a DataLocator will be returned which may
 * optionally be used by the caller; again, however, the caller may choose to retrieve it directly, or iterate
 * through the data. This is done merely for convenience, and to abstract the data itself from the test case.
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
 * In the above example, a new DataSet is created for "setName". Then, firstName is added to the first 
 * DataPacket (which is created empty to provide a "last packet" to add to). Since "lastName" does not 
 * conflict with "firstName", it too is added to the same DataPacket. Upon reaching the line 4, "firstName" 
 * exists in the packet from the first line. The DataSet will then create a new DataPacket which will then 
 * become the last packet. "firstName" is added to this new packet. On line 5, again, no "lastName" exists 
 * in this new packet. On line 6 "firstName" is encountered again which will cause a new packet to be created, 
 * and repeat the cycle.
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
public class DataAggregator implements Iterable<DataSet> {

	private static DataAggregator globalData = null;
	private Hashtable<String, DataSet> collection = null;

	private DataAggregator() {
		collection = new Hashtable<String, DataSet>();
	}

	/**
	 * Singleton getInstance to get the DataAggregator object. A Singleton is used to persist the data
	 * between test cases. 
	 * @return The DataAggregator object retained between test cases.
	 */
	public static DataAggregator getInstance() {
		if (globalData == null) globalData = new DataAggregator();
		return globalData;
	}

	/**
	 * This will check the collection to see if it contains a DataSet that corresponds to the setName.
	 * If not, it will add a new empty object. It will then get the corresponding DataSet and add the
	 * KVP to it. It will then create a DataLocator object based on the setName, the packetNumber returned
	 * from DataSet.add, and the key. The DataLocator will tell the DataAggregator exactly where to find
	 * the data, so it may be used later for retrieval.
	 * @param setName is the arbitrary name of the set to add the KVP to.
	 * @param key is the arbitrary name of the key to associate with the data.
	 * @param value is the data we are storing.
	 * @return Returns a DataLocator object which may be used with DataAggregator.get to locate this data.
	 */
	public DataLocator add(String setName, String key, String value) {
		if (!collection.containsKey(setName)) collection.put(setName, new DataSet());
		int packetNum = collection.get(setName).add(key, value);
		return new DataLocator(setName, packetNum, key);
	}

	/**
	 * This will check the collection to see if it contains a DataSet that corresponds to the setName.
	 * If not, it will add a new empty object. It will then get the corresponding DataSet and add the
	 * KVP to it. It will then create a DataLocator object based on the setName, the packetNumber returned
	 * from DataSet.add, and the key. The DataLocator will tell the DataAggregator exactly where to find
	 * the data, so it may be used later for retrieval.
	 * @param setName is the arbitrary name of the set to add the KVP to.
	 * @param key is the arbitrary name of the key to associate with the data.
	 * @param value is the data we are storing.
	 * @return Returns a DataLocator object which may be used with DataAggregator.get to locate this data.
	 */
	public DataLocator add(PageObject setName, String key, String value) {
		return add(setName.toString(), key, value);
	}

	/**
	 * This will check the collection to see if it contains a DataSet that corresponds to the setName.
	 * If not, it will add a new empty object. It will then get the corresponding DataSet and add the
	 * KVP to it. It will then create a DataLocator object based on the setName, the packetNumber returned
	 * from DataSet.add, and the key. The DataLocator will tell the DataAggregator exactly where to find
	 * the data, so it may be used later for retrieval.
	 * @param setName is the arbitrary name of the set to add the KVP to.
	 * @param key is the arbitrary name of the key to associate with the data.
	 * @param value is the data we are storing.
	 * @return Returns a DataLocator object which may be used with DataAggregator.get to locate this data.
	 */
	public DataLocator add(String setName, PageObject key, String value) {
		return add(setName, key.toString(), value);
	}

	/**
	 * This will check the collection to see if it contains a DataSet that corresponds to the setName.
	 * If not, it will add a new empty object. It will then get the corresponding DataSet and add the
	 * KVP to it. It will then create a DataLocator object based on the setName, the packetNumber returned
	 * from DataSet.add, and the key. The DataLocator will tell the DataAggregator exactly where to find
	 * the data, so it may be used later for retrieval.
	 * @param setName is the arbitrary name of the set to add the KVP to.
	 * @param key is the arbitrary name of the key to associate with the data.
	 * @param value is the data we are storing.
	 * @return Returns a DataLocator object which may be used with DataAggregator.get to locate this data.
	 */
	public DataLocator add(PageObject setName, PageObject key, String value) {
		return add(setName.toString(), key.toString(), value);
	}

	/**
	 * This will pull the setName, packetNumber, and key from the DataLocator and return the data that
	 * was inserted at the time of the creation of the DataLocator. The use of this is to completely
	 * abstract the data from the test itself, needing only a DataLocator to reference data instead of
	 * requiring information about the data and how it was stored.
	 * @param locator is the locator that was created at add time which points to the desired data.
	 * @return Returns the value which was inserted into the DataAggregator at the time of creation of the DataLocator.
	 */
	public String get(DataLocator locator) {
		return get(locator.getSetName(), locator.getPacketNum(), locator.getKey());
	}

	/**
	 * Allows the retrieval of a value based on the setName it was added to, a known index of the
	 * DataSet DataPacket it was added to (corresponds to the order of insertion), and the key associated
	 * with the KVP.
	 * @param setName is the arbitrary name of the set to add the KVP to.
	 * @param index is the index of the DataPacket inside DataSet which corresponds to the insertion order.
	 * @param key is the arbitrary name of the key to associate with the data.
	 * @return Returns the value of the KVP from the setName and packetNumber.
	 */
	public String get(String setName, int index, String key) {
		return collection.get(setName).get(index, key);
	}

	/**
	 * Allows the retrieval of a value based on the setName it was added to, a known index of the
	 * DataSet DataPacket it was added to (corresponds to the order of insertion), and the key associated
	 * with the KVP.
	 * @param setName is the arbitrary name of the set to add the KVP to.
	 * @param index is the index of the DataPacket inside DataSet which corresponds to the insertion order.
	 * @param key is the arbitrary name of the key to associate with the data.
	 * @return Returns the value of the KVP from the setName and packetNumber.
	 */
	public String get(PageObject setName, int index, String key) {
		return get(setName.toString(), index, key);
	}

	/**
	 * Allows the retrieval of a value based on the setName it was added to, a known index of the
	 * DataSet DataPacket it was added to (corresponds to the order of insertion), and the key associated
	 * with the KVP.
	 * @param setName is the arbitrary name of the set to add the KVP to.
	 * @param index is the index of the DataPacket inside DataSet which corresponds to the insertion order.
	 * @param key is the arbitrary name of the key to associate with the data.
	 * @return Returns the value of the KVP from the setName and packetNumber.
	 */
	public String get(String setName, int index, PageObject key) {
		return get(setName, index, key.toString());
	}

	/**
	 * Allows the retrieval of a value based on the setName it was added to, a known index of the
	 * DataSet DataPacket it was added to (corresponds to the order of insertion), and the key associated
	 * with the KVP.
	 * @param setName is the arbitrary name of the set to add the KVP to.
	 * @param index is the index of the DataPacket inside DataSet which corresponds to the insertion order.
	 * @param key is the arbitrary name of the key to associate with the data.
	 * @return Returns the value of the KVP from the setName and packetNumber.
	 */
	public String get(PageObject setName, int index, PageObject key) {
		return get(setName.toString(), index, key.toString());
	}

	/**
	 * Retrieves a DataSet object by name.
	 * @param setName is the name of the set in the map.
	 * @return Returns the DataSet associated with the setName.
	 */
	public DataSet getSet(String setName) {
		return collection.get(setName);
	}

	/**
	 * Empties the DataAggregator. This will clear all DataPackets of DataUnits, then DataSet of DataPackets,
	 * then the DataAggregator of DataSets; completely dumping all retained data.
	 */
	public void clear() {
		for (DataSet dataSet : this) dataSet.clear();
		collection.clear();
	}

	/**
	 * Allows Iterable functionality, so enhanced for loops may be used with DataAggregator to iterate
	 * through the collection of DataSets.
	 */
	public Iterator<DataSet> iterator() {
		ArrayList<DataSet> set = new ArrayList<DataSet>();
		Set<String> keys = collection.keySet();
		for (String key : keys) set.add(new DataSet(collection.get(key)));
		return set.iterator();
	}

	public String jsonify() {
		StringBuilder jsonStr = new StringBuilder("{\n");
		for (String key : collection.keySet()) {
			jsonStr.append("  \"" + key + "\" : {\n");
			jsonStr.append(collection.get(key).jsonify());
			jsonStr.append("  },\n");
		}
		jsonStr.deleteCharAt(jsonStr.lastIndexOf(",")).append("}");
		return jsonStr.toString();
	}
}