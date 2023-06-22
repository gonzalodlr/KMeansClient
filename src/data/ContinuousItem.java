package data;

/**
 * The ContinuousItem class extends the Item class and calculates the distance
 * between two continuous attribute values. It models a pair <Continuous
 * attribute - numeric value >
 */
public class ContinuousItem extends Item {

	/**
	 * This is a constructor for the 'ContinuousItem' class. Creates a new 'Item'
	 * object with the specified attribute and value.
	 * 
	 * @param attribute Continuos Attribute
	 * @param value     Double value
	 */
	public ContinuousItem(ContinuousAttribute attribute, double value) {
		super(attribute, value);
	}

	/**
	 * This Java function determines the distance (in absolute value) between the
	 * scaled value stored in the current item (this.getValue()) and the scaled
	 * value associated with the parameter a. To obtain scaled values make use of
	 * getScaledValue(...)
	 * 
	 * @param a is an object that is being passed into the method. It is used to
	 *          calculate the distance between the current object and the object
	 *          passed in. The method checks if the object passed in is an instance
	 *          of the ContinuousItem class and if so, it calculates the distance
	 *          between the two attribute values.
	 * @return The method is returning the absolute distance between two continuous
	 *         attribute values. If the input object is not an instance of
	 *         ContinuousItem, it returns 0.
	 */
	@Override
	public double distance(Object a) {
		double abs = 0;

		if (a instanceof ContinuousItem) {
			double scaledValue1 = ((ContinuousAttribute) getAttribute()).getScaledValue((double) getValue());
			double scaledValue2 = ((ContinuousAttribute) ((ContinuousItem) a).getAttribute())
					.getScaledValue((double) ((ContinuousItem) a).getValue());
			abs = Math.abs(scaledValue1 - scaledValue2);
			return abs;
		}
		return abs;
	}
}
