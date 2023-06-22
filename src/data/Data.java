package data;

import java.sql.SQLException;
import java.util.*;

import agent.DbAccess;
import agent.Example;
import agent.QUERY_TYPE;
import agent.TableData;
import agent.TableSchema;
import exceptions.EmptySetException;
import exceptions.NoValueException;
import exceptions.OutOfRangeSampleSize;

/**
 * The Data class models the set of transactions (or tuples)
 */
public class Data {
	private List<Example> data;
	private int numberOfExamples;
	private List<Attribute> attributeSet;

	/**
	 * Constructor for the Data class.
	 * 
	 * It initializes the data list by getting all distinct transactions from the
	 * table using the TableData class, and sets the number of examples to the size
	 * of the data list. It then initializes the attribute set by iterating through
	 * the columns of the table schema and adding either a ContinuousAttribute or
	 * DiscreteAttribute object to the attribute set depending on whether the column
	 * is numeric or not. The ContinuousAttribute objects are initialized with the
	 * minimum and maximum values of the column obtained using the TableData class.
	 * 
	 * 
	 * @param tableName string representing the name of the table from the database.
	 * @param db        database access object which owns the 'tableName' table.
	 * @throws SQLException
	 * @throws NoValueException
	 * @throws EmptySetException
	 */
	public Data(String tableName, DbAccess db) throws SQLException, NoValueException, EmptySetException {
		TableSchema table = new TableSchema(db, tableName);
		TableData tAttribute = new TableData(db);

		// Fill all distinct data examples
		data = tAttribute.getDistinctTransactions(tableName);

		// number of Examples:
		numberOfExamples = data.size();

		// explanatory Set

		attributeSet = new LinkedList<Attribute>();

		// If there is a digit: Continuous Attribute:
		for (int i = 0; i < table.getNumberOfAttributes(); i++) {
			if (table.getColumn(i).isNumber()) {
				// Casting the Object values into Double
				double min = (double) tAttribute.getAggregateColumnValue(tableName, table.getColumn(i), QUERY_TYPE.MIN);
				double max = (double) tAttribute.getAggregateColumnValue(tableName, table.getColumn(i), QUERY_TYPE.MAX);
				attributeSet.add(new ContinuousAttribute(table.getColumn(i).getColumnName(), i, min, max));
			} else {
				// Change the Set into array of Strings to be able to be added as dicrete
				// attribute
				Set<Object> stringSet = tAttribute.getDistinctColumnValues(tableName, table.getColumn(i));
				String[] stringArray = stringSet.toArray(new String[stringSet.size()]);
				attributeSet.add(new DiscreteAttribute(table.getColumn(i).getColumnName(), i, stringArray));
			}
		}
	}

	/**
	 * This function returns the number of examples provided.
	 * 
	 * @return An integer value, which is the value of the variable
	 *         'numberOfExamples'.
	 */
	public int getNumberOfExamples() {
		return numberOfExamples;
	}

	/**
	 * This function returns the number of attributes in a set.
	 * 
	 * @return the size of the set: 'attributeSet', which represents the number of
	 *         attributes in the set.
	 */
	public int getNumberOfAttributes() {
		return attributeSet.size();
	}

	/**
	 * The function returns a list of attributes.
	 * 
	 * @return A List of Attribute objects named "attributeSet" is being returned.
	 */
	public List<Attribute> getAttributeSchema() {
		return attributeSet;
	}

	/**
	 * This function returns the attribute value of a specific example in the List
	 * 'data'.
	 * 
	 * @param exampleIndex   The index of the example in the data list for which we
	 *                       want to retrieve the attribute value.
	 * @param attributeIndex The identified of the attribute for which we want to
	 *                       retrieve the value.
	 * @return a 'Object' which represents the value of the attribute at the
	 *         specified 'attributeIndex' for the example at the specified
	 *         'exampleIndex'.
	 */
	public Object getAttributeValue(int exampleIndex, int attributeIndex) {
		return data.get(exampleIndex).get(attributeIndex);
	}

	/**
	 * This function returns a string representation of a list of examples.
	 * 
	 * @return A representation of the data objects, where each example is numbered
	 *         and followed by its string representation, separated by a newline
	 *         character.
	 */
	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < getNumberOfExamples(); i++) {
			Example example = data.get(i);
			str += (i + 1) + ":" + example.toString() + "\n";
		}
		return str;
	}

	/**
	 * This function returns a Tuple object representing an Example object's
	 * attribute values, where each attribute is either a DiscreteItem or a
	 * ContinuousItem.
	 * 
	 * @param index The index parameter is an integer value representing the index
	 *              of the example in the data set from which we want to retrieve
	 *              the item set.
	 * @return returns a `Tuple` object that models as a sequence of Attribute-value
	 *         pairs the i in date.
	 */
	public Tuple getItemSet(int index) {
		Tuple tuple = new Tuple(getNumberOfAttributes());
		Example example = data.get(index);
		for (int i = 0; i < getNumberOfAttributes(); i++) {
			Attribute attribute = attributeSet.get(i);
			Object value = example.get(i);
			if (attribute instanceof DiscreteAttribute) {
				tuple.add(new DiscreteItem((DiscreteAttribute) attribute, (String) value), i);
			} else if (attribute instanceof ContinuousAttribute) {
				tuple.add(new ContinuousItem((ContinuousAttribute) attribute, (double) value), i);
			}
		}

		return tuple;
	}

	/**
	 * The function "sampling" returns an array of k random indexes from a data list
	 * example, with error handling for out of range sample sizes.
	 * 
	 * @Notes if the number of 'k' interactions is outside of the range of examples,
	 *        it will throw a OutOfRangeSampleSize exception.
	 * @param k The number of centroids to be selected randomly from the data.
	 * @return The method is returning an array of integers, which are the indexes
	 *         of the randomly chosen centroids.
	 */
	public int[] sampling(int k) throws OutOfRangeSampleSize {
		if (k <= 0) {
			throw new OutOfRangeSampleSize("Error:\tk <= 0\n");
		} else if (k >= getNumberOfExamples()) {
			throw new OutOfRangeSampleSize("Error:\tk >= " + getNumberOfExamples() + "\n");
		}

		int[] centroidIndexes = new int[k];
		// choose k random different centroids in data.
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		for (int i = 0; i < k; i++) {
			boolean found;
			int c;
			do {
				found = false;
				c = rand.nextInt(getNumberOfExamples());
				// verify that centroid[c] is not equal to a centroide already stored in
				// CentroidIndexes
				for (int j = 0; j < i; j++) {
					if (compare(centroidIndexes[j], c)) {
						found = true;
						break;
					}
				}
			} while (found);
			centroidIndexes[i] = c;
		}
		return centroidIndexes;
	}

	/**
	 * The function compares two rows of data based on their attributes.
	 * 
	 * @param i The index of the first row to compare in the data list.
	 * @param j The index of the second row to compare in the data list.
	 * @return returns true if the two data examples contains the same value, false
	 *         otherwise.
	 */
	private boolean compare(int i, int j) {
		for (int k = 0; k < getNumberOfAttributes(); k++) {
			if (!data.get(i).get(k).equals(data.get(j).get(k))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This function returns the calling of a method based on the type of the
	 * attribute.
	 * 
	 * @param clusteredData A set of integers representing the indices of the
	 *                      instances that belong to a particular cluster.
	 * @param attribute     The "attribute" parameter is an object of the
	 *                      "Attribute" class, which is a superclass of
	 *                      "DiscreteAttribute" and "ContinuousAttribute" classes.
	 * @return The method 'computePrototype' returns an object that represents the
	 *         prototype of a cluster, based on the given set of clustered data and
	 *         attribute. The specific type of object returned depends on the type
	 *         of attribute passed as a parameter. If the attribute is discrete, the
	 *         method returns an object of type 'String'. If the attribute is
	 *         continuous, the method returns an object of type 'Double'.
	 */
	public Object computePrototype(Set<Integer> clusteredData, Attribute attribute) {
		if (attribute instanceof DiscreteAttribute) {
			return computePrototype(clusteredData, (DiscreteAttribute) attribute);
		}
		if (attribute instanceof ContinuousAttribute) {
			return computePrototype(clusteredData, (ContinuousAttribute) attribute);
		}
		return null;
	}

	/**
	 * This function computes the prototype of a continuous attribute based on the
	 * frequency of its values in a given set of IDs. Determines the most frequently
	 * needed value for attributes in the subset of attributes identified by idList.
	 * 
	 * @param idList    A set of integers representing the indices of examples in a
	 *                  dataset.
	 * @param attribute a ContinuousAttribute object representing the attribute for
	 *                  which the prototype value needs to be computed.
	 * @return The method returns a Double object, which represents the prototype
	 *         value of a continuous attribute computed from a list of examples. If
	 *         the computation is successful, the prototype value is returned as a
	 *         scaled Double value. If the computation fails, the method returns
	 *         null.
	 */
	public double computePrototype(Set<Integer> idList, ContinuousAttribute attribute) {
		double sum = 0;
		int count = 0;

		for (Integer index : idList) {
			Example example = data.get(index);
			for (int i = 0; i < getNumberOfAttributes(); i++) {
				Object value = example.get(i);
				if (value instanceof Double) {
					double value1 = (double) value;
					ContinuousItem item = new ContinuousItem(attribute, value1);

					if (item != null) {
						sum += (double) item.getValue();
						count++;
					}
				}
			}
		}

		if (count > 0) {
			double prototypeValue = sum / count;
			return attribute.getScaledValue(prototypeValue);
		}

		return 0;
	}

	/**
	 * This function computes the prototype of a discrete attribute based on the
	 * frequency of its values in a given set of IDs. Determines the most frequently
	 * needed value for attributes in the subset of attributes identified by idList.
	 * 
	 * @param idList    A set of integers representing the IDs of the instances in a
	 *                  dataset.
	 * @param attribute A DiscreteAttribute object that represents a discrete
	 *                  attribute in a dataset. It contains a set of possible values
	 *                  for the attribute.
	 * @return A string representing the most frequently prototype value for the
	 *         given set of IDs and discrete attribute.
	 */
	public String computePrototype(Set<Integer> idList, DiscreteAttribute attribute) {
		String prototype = null;
		int maxFrequency = -1;
		for (String value : attribute.getValues()) {
			int frequency = attribute.frequency(this, idList, value);
			if (frequency > maxFrequency) {
				maxFrequency = frequency;
				prototype = value;
			}
		}
		return prototype;
	}
}