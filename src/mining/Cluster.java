package mining;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import data.Data;
import data.Tuple;

/**
 * The 'Cluster' class represents a cluster of centroids and contains methods
 * for computing the centroid, adding and removing data centroids, and
 * generating a string
 * representation of the cluster.
 */
public class Cluster implements Serializable {
	private Tuple centroid;
	private Set<Integer> clusteredData;

	/**
	 * This is a constructor for the 'Cluster' class.
	 * 
	 * @Notes Initializes the 'clusteredData' instance variable to a new 'HashSet'
	 *        object.
	 * 
	 * @param centroid initializes the 'centroid' instance variable to the value of
	 *                 the 'centroid' that takes a 'Tuple' object as a parameter.
	 */
	public Cluster(Tuple centroid) {
		this.centroid = centroid;
		clusteredData = new HashSet<Integer>();
	}

	/**
	 * The function returns a Tuple representing the centroid.
	 * 
	 * @return A tuple representing the centroid.
	 */
	public Tuple getCentroid() {
		return centroid;
	}

	/**
	 * This function updates the centroid of a cluster based on the data and
	 * clustered data.
	 * 
	 * @param data The 'data' parameter is an object of the 'Data' class, which
	 *             likely contains a set of examples that are being used to compute
	 *             the centroid.
	 */
	public void computeCentroid(Data data) {
		for (int i = 0; i < centroid.getLength(); i++) {
			centroid.get(i).update(data, clusteredData);
		}
	}

	/**
	 * The function adds an integer ID to a collection of clustered data and returns
	 * a boolean indicating if the operation was successful.
	 * 
	 * @param id The parameter "id" is an integer value that represents the index of
	 *           the example to be added into the "clusteredData" collection. The
	 *           method returns a boolean value indicating if the operation
	 *           successful or not.
	 * @return A boolean value, the result of the 'add'
	 *         method being called on the 'clusteredData' object with the 'id'
	 *         parameter passed as an argument.
	 */
	public boolean addData(int id) {
		return clusteredData.add(id);
	}

	/**
	 * The function checks if a given ID is contained in a clustered data structure
	 * and returns a boolean
	 * value.
	 * 
	 * @param id The parameter "id" is an integer value that represents the
	 *           identifier of the example that we want to check if it exists in the
	 *           "clusteredData" collection. The method
	 *           "contains" returns a boolean value indicating if the
	 *           "clusteredData" collection contains the
	 *           specified "id"
	 * @return A boolean value indicating if the integer parameter 'id' is contained
	 *         in the
	 *         'clusteredData' set.
	 */
	public boolean contain(int id) {
		return clusteredData.contains(id);
	}

	/**
	 * This function removes a tuple from a clustered data structure based on its
	 * ID.
	 * 
	 * @param id The parameter "id" is an integer value that represents the
	 *           identifier of the tuple that
	 *           needs to be removed from the "clusteredData" collection.
	 */
	public void removeTuple(int id) {
		clusteredData.remove(id);
	}

	/**
	 * This function returns a string representation of the centroid.
	 * 
	 * @return A string representation of the centroid, which includes the distinct
	 *         values of the centroid
	 */
	public String toString() {
		String str = "Centroid=(";
		for (int i = 0; i < centroid.getLength(); i++) {
			str += centroid.get(i) + " ";
		}
		str += ")";
		return str;
	}

	/**
	 * The function returns a string representation of the centroid and examples in
	 * a given data set.
	 * 
	 * @param data The input data that contains the examples to be clustered.
	 * @return A string representation of the data, including the centroid and
	 *         examples with their
	 *         distances from the centroid and average distance.
	 */
	public String toString(Data data) {
		String str = toString();
		str += "\nExamples:\n";
		// Cast set<integer> to array of integers
		Object[] objectArray = clusteredData.toArray();

		int[] array = new int[objectArray.length];
		for (int i = 0; i < objectArray.length; i++) {
			array[i] = (int) objectArray[i];
		}
		for (int i = 0; i < array.length; i++) {
			str += "[";
			for (int j = 0; j < data.getNumberOfAttributes(); j++) {
				str += data.getAttributeValue(array[i], j) + " ";
			}
			str += "] dist=" + getCentroid().getDistance(data.getItemSet(array[i])) + "\n";

		}
		str += "\nAvgDistance=" + getCentroid().avgDistance(data, array) + "\n";
		return str;
	}
}
