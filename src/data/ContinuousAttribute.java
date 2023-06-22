package data;

/**
 * The ContinuousAttribute class represents a continuous attribute with a
 * minimum and maximum value,
 * and provides a method to scale a given value to a range between 0 and 1. It
 * corresponds to a numeric attribute
 */
public class ContinuousAttribute extends Attribute {
	private double max;
	private double min;

	/**
	 * The constructor class for ContinuousAttribute class:
	 * 
	 * @param name  a string representing the name of the attribute
	 * @param index a integer representing the index of the attribute
	 * @param min   a double representing the minimun value of the attribute
	 * @param max   a double representing the maximun value of the attribute
	 */
	public ContinuousAttribute(String name, int index, double min, double max) {
		super(name, index);
		this.min = min;
		this.max = max;
	}

	/**
	 * The function returns a scaled value of a given input based on the minimum and
	 * maximum values. The normalisation has the interval [0,1] as its condomain.
	 * 
	 * @param v is the value that needs to be scaled.
	 * @return Returns a double value that represents the scaled value of the
	 *         input parameter 'v'. The scaled value is calculated by subtracting
	 *         the minimum value ('min') from 'v' and dividing the result by the
	 *         difference between the maximum value ('max') and the minimum
	 *         value ('min').
	 */
	public double getScaledValue(double v) {
		return (v - min) / (max - min);
	}
}