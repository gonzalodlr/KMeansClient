package data;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * The DiscreteAttribute class represents an attribute with a set of discrete
 * values and provides
 * methods for getting the values, the number of distinct values, and the
 * frequency of a specific value
 * in a given data set. It corresponds to a discrete 'categorical' attribute.
 * This class implement the Iterable interface
 */
public class DiscreteAttribute extends Attribute implements Iterable<String> {
	private TreeSet<String> values;

	/**
	 * The constructor for the 'DiscreteAttribute' class. It is used to create a new
	 * instance of the 'DiscreteAttribute' class with the specified name, index, and
	 * set of discrete values.
	 * 
	 * @param name   as a string representing the name of the attribute
	 * @param index  as an integer representing the identifier of the tuple
	 * @param values initializes a 'TreeSet' object named `values` and adds each
	 *               string value from the input array to the 'values' set.
	 */
	public DiscreteAttribute(String name, int index, String[] values) {
		super(name, index);
		this.values = new TreeSet<>();
		for (String value : values) {
			this.values.add(value);
		}
	}

	/**
	 * The function returns a TreeSet of Strings that correspond to the values of
	 * the attribute.
	 * 
	 * @return a 'TreeSet' of 'String' objects named 'values' is being returned.
	 */
	public TreeSet<String> getValues() {
		return values;
	}

	/**
	 * This function returns the number of distinct values in a collection.
	 * 
	 * @return the size of the 'values' collection.
	 */
	public int getNumberOfDistinctValues() {
		return values.size();
	}

	/**
	 * This Java function calculates the frequency (number of ocurrences) of a given
	 * string value in a set of integers based on a specific attribute in a Data
	 * list of examples.
	 * 
	 * @param data   The data object contains information about the examples, such
	 *               as the attributes and their values for each instance.
	 * @param idList A set of integers representing the IDs of the data instances to
	 *               be searched for a specific attribute value.
	 * @param v      is a String variable representing the attribute value that we
	 *               want to count the frequency of in the given set of ids.
	 * @return The method is returning an integer value which represents the
	 *         number of occurences of a given string value 'v' in a set of data
	 *         identified by a set of integer IDs 'idList'.
	 */
	public int frequency(Data data, Set<Integer> idList, String v) {
		int count = 0;
		for (Integer i : idList) {
			if (v.equals(data.getAttributeValue(i, getIndex()))) {
				count++;
			}
		}
		return count;
	}

	/**
	 * This function returns an iterator for a collection of String values.
	 * 
	 * @return An iterator over a collection of strings is being returned.
	 */
	@Override
	public Iterator<String> iterator() {
		return values.iterator();
	}
}