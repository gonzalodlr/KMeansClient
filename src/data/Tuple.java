package data;

import java.io.Serializable;

/**
 * The Tuple class contains methods for creating and manipulating an array of
 * Item objects, calculating distances between tuples, and calculating the
 * average distance of items in a cluster from the centroid. The tuple class
 * represents a tuple as a sequence of attribute-value pairs.
 */
public class Tuple implements Serializable{
	private Item[] tuple;

	/**
	 * The constructor method for the 'Tuple' class.
	 * It initialise array of 'Item' objects called 'tuple' with a length equal
	 * to the "size" parameter.
	 * 
	 * @param size integer representing the size of our tuple array.
	 */
	public Tuple(int size) {
		tuple = new Item[size];
	}

	/**
	 * The function returns the length of an array called "tuple".
	 * 
	 * @return The method is returning the length of the `tuple` array.
	 */
	public int getLength() {
		return tuple.length;
	}

	/**
	 * The function returns the item at the specified index 'i' in an array called
	 * "tuple".
	 * 
	 * @param i The parameter "i" is an integer representing the index of the
	 *          element to be retrieved from the "tuple" array.
	 * @return an `Item` object at the index 'i' of the 'tuple' array.
	 */
	public Item get(int i) {
		return tuple[i];
	}

	/**
	 * The function assigns an "Item" object to a specific index in an array called
	 * "tuple".
	 * 
	 * @param item The "item" parameter is an object of the class "Item" that is
	 *             being added to an array called "tuple" at a specific index "i".
	 * @param i    The parameter "i" is an integer that represents the index at
	 *             which the "item" object will be added to the "tuple" array.
	 */
	public void add(Item item, int i) {
		tuple[i] = item;
	}

	/**
	 * This Java function determines the distance between the tuple referred by obj
	 * and the current tuple (referred by this). The distance is obtained as the sum
	 * of the distances between items at equal positions in the two tuples. Use
	 * double distance(Object a) of Item.
	 * 
	 * @param obj The parameter "obj" is an object of the class "Tuple".
	 * @return The method is returning a double value, which represents the total
	 *         distance between the current Tuple object and the Tuple object passed
	 *         as a parameter.
	 */
	public double getDistance(Tuple obj) {
		double distance = 0.0;
		for (int i = 0; i < tuple.length; i++) {
			distance += tuple[i].distance(obj.get(i));
		}
		return distance;
	}

	/**
	 * The function calculates the average distance between the current tuple and
	 * those obtained from the rows of the data examples matrix having an index in
	 * 'clusterData'.
	 * 
	 * @param data          an object of the Data class that contains the exmples.
	 * @param clusteredData an array of integers representing the indices of the
	 *                      data examples that belong to a particular cluster.
	 * @return The method is returning the average distance of the items in the
	 *         clusteredData array from the centroid of the cluster they belong to.
	 */
	public double avgDistance(Data data, int clusteredData[]) {
		double p = 0.0, sumD = 0.0;
		for (int i = 0; i < clusteredData.length; i++) {
			double d = getDistance(data.getItemSet(clusteredData[i]));
			sumD += d;
		}
		p = sumD / clusteredData.length;
		return p;
	}
}
