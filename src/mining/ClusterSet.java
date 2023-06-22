package mining;

import java.io.Serializable;

import exceptions.OutOfRangeSampleSize;
import data.Data;
import data.Tuple;

/**
 * The ClusterSet class represents a set of clusters (determined by k-means) and
 * provides methods for initializing centroids, finding the nearest cluster to a
 * given tuple, updating centroids, and generating string representations of the
 * clusters.
 */
public class ClusterSet implements Serializable {
	private Cluster[] C;
	private int i;

	/**
	 * The constructor of the 'ClusteSet' class. Initialise a new 'ClusterSet'
	 * object with an array of 'k' 'Cluster' objects and sets the 'i' variable,
	 * which correspond to the length, to 0.
	 * 
	 * @param k integer representing the number of cluster to be added.
	 */
	public ClusterSet(int k) {
		C = new Cluster[k];
		i = 0;
	}

	/**
	 * The function adds a Cluster object to an array of Cluster objects.
	 * 
	 * @param c The parameter "c" is an object of the class "Cluster" that has to be
	 *          added into our array of objects of the class "Cluster" called "C".
	 *          The index "i" is incremented after adding the object 'c'.
	 */
	public void add(Cluster c) {
		C[i++] = c;
	}

	/**
	 * The function returns the Cluster object at the specified index.
	 * 
	 * @param i The parameter "i" is an integer representing the index of the
	 *          cluster to be retrieved from an array or list of clusters.
	 * @return The method 'get' is returning a 'Cluster' object at the index 'i'
	 *         from an array 'C'.
	 */
	public Cluster get(int i) {
		return C[i];
	}

	/**
	 * This function initializes the centroids of a clustering algorithm using a
	 * random sampling method of the data.
	 * 
	 * @param data The data parameter is an object of the Data class, which contains
	 *             the data list of examples that we want to cluster.
	 */
	public void initializeCentroids(Data data) throws OutOfRangeSampleSize {

		int[] centroidIndexes = data.sampling(C.length);

		for (int i = 0; i < centroidIndexes.length; i++) {
			Tuple centroid = data.getItemSet(centroidIndexes[i]);
			Cluster c = new Cluster(centroid);
			add(c);
		}
	}

	/**
	 * The function finds the nearest cluster to a given tuple based on the distance
	 * between the tuple and the centroids of the clusters.
	 * 
	 * @param tuple The parameter "tuple" is a data exmple or tuple that needs to be
	 *              assigned to the nearest cluster based on the distance between
	 *              the tuple and the centroids of the clusters.
	 * @return The method is returning the closer cluster to a given tuple.
	 */
	public Cluster nearestCluster(Tuple tuple) {
		double minDistance = Double.MAX_VALUE; // C[0].getCentroid().getDistance(tuple);
		Cluster nearestCluster = null;
		for (int i = 0; i < C.length; i++) {
			double distance = tuple.getDistance(C[i].getCentroid());
			if (distance < minDistance) {
				minDistance = distance;
				nearestCluster = C[i];
			}
		}
		return nearestCluster;
	}

	/**
	 * The function returns the cluster that contains a given ID or null if it is
	 * not found.
	 * 
	 * @param id The parameter "id" is an integer representing the index of a data
	 *           example that we want to find the cluster for.
	 * @return The method 'currentCluster' returns a 'Cluster' object that contains
	 *         the given 'id'. If no such cluster is found, it returns 'null'.
	 */
	public Cluster currentCluster(int id) {
		for (int i = 0; i < C.length; i++) {
			if (C[i].contain(id)) {
				return C[i];
			}
		}
		return null;
	}

	/**
	 * This function calculates the new centroid foreach clusterin.
	 * 
	 * @param data list that contains the data examples that are being used to
	 *             compute the centroids.
	 */
	public void updateCentroids(Data data) {
		for (int i = 0; i < C.length; i++) {
			C[i].computeCentroid(data);
		}
	}

	/**
	 * This function returns a string made from each centroid of the cluster set.
	 * 
	 * @return A string representation of the non-null elements in an array of
	 *         objects.
	 */
	public String toString() {
		String str = "";
		for (int i = 0; i < C.length; i++) {
			if (C[i] != null) {
				str += i + ":" + C[i].toString() + "\n";
			}
		}
		return str;
	}

	/**
	 * This function returns a string representation of a Data object.
	 * 
	 * @param data the data list of examples.
	 * @return A string representation describing the state of each cluster in 'C'
	 */
	public String toString(Data data) {
		String str = "";
		for (int i = 0; i < C.length; i++) {
			if (C[i] != null) {
				str += i + ":" + C[i].toString(data) + "\n";
			}
		}
		return str;
	}
}