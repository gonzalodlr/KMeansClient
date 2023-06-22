package mining;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import exceptions.OutOfRangeSampleSize;
import data.Data;

/**
 * The KMeansMiner class implements the K-means clustering algorithm for data
 * mining.
 */
public class KMeansMiner {
	private ClusterSet C;

	/**
	 * This is a constructor for the KMeansMiner class that takes an integer
	 * parameter 'k'. It creates a new ClusterSet object with 'k' clusters and
	 * assigns it to the static variable 'C'.
	 * 
	 * @param k number of clusters to be generated.
	 */
	public KMeansMiner(int k) {
		C = new ClusterSet(k);
	}

	/**
	 * Constructor of KMeansMiner for de-serialisation the centroids It reads an
	 * object from a file with the given fileName using ObjectInputStream and
	 * assigns to the static variable 'C', which is a ClusterSet object.
	 * 
	 * @param fileName string with the path name of the file which contains the
	 *                 centroids
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public KMeansMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
		C = (ClusterSet) in.readObject();
		in.close();
	}

	/**
	 * This Java function saves an object C, which corresponds to the centroids we
	 * discover, to a file with the given fileName using ObjectOutputStream.
	 * 
	 * @param fileName The parameter "fileName" is a String variable that represents
	 *                 the name of the file where the object "C" will be saved.
	 */
	public void save(String fileName) throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
		out.writeObject(C);
		out.close();
	}

	/**
	 * The function returns a ClusterSet object.
	 * 
	 * @return returns an object of type 'ClusterSet'.
	 */
	public ClusterSet getC() {
		return C;
	}

	/**
	 * This function implements the k-means clustering algorithm to cluster data
	 * examples.
	 * 
	 * <br>
	 * STEPS: <br>
	 * 1. Random choice of centroids for k clusters. <br>
	 * 2. Assignment of each row of the matrix at to the closest centroid example.
	 * <br>
	 * 3. Calculation of the new centroid for each cluster. <br>
	 * 4. Repeat 2 and 3 until two consecutive iterations return equal centroids.
	 * 
	 * @param data an object of type Data, which will be the data list of examples.
	 * @return The method is returning an integer value which represents the number
	 *         of iterations performed during the k-means clustering algorithm.
	 */
	public int kmeans(Data data) throws OutOfRangeSampleSize {
		int numberOfIterations = 0;
		// STEP 1
		C.initializeCentroids(data);
		boolean changedCluster;
		do {
			numberOfIterations++;
			// STEP 2
			changedCluster = false;
			for (int i = 0; i < data.getNumberOfExamples(); i++) {
				Cluster nearestCluster = C.nearestCluster(data.getItemSet(i));
				Cluster oldCluster = C.currentCluster(i);
				boolean currentChange = nearestCluster.addData(i);
				if (currentChange) {
					changedCluster = true;
				}
				// remove tuple from old cluster
				if (currentChange && oldCluster != null) {
					oldCluster.removeTuple(i);
				}
			}
			// STEP 3
			C.updateCentroids(data);
		} while (changedCluster);

		return numberOfIterations;
	}
}