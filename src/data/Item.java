package data;

import java.io.Serializable;
import java.util.Set;

/**
 * The Item class represents an attribute-value pair and provides methods for
 * computing distance and
 * updating the value.
 */
public abstract class Item implements Serializable{
	private Attribute attribute;
	private Object value;

	/**
	 * This is a constructor for the Item class.
	 * It sets the attribute and value instance variables of the Item object to the
	 * values passed in as parameters.
	 * 
	 * @param attribute type Attribute.
	 * @param value     type Object.
	 */
	public Item(Attribute attribute, Object value) {
		this.attribute = attribute;
		this.value = value;
	}

	/**
	 * The function returns an attribute.
	 * 
	 * @return The method is returning an object of the class 'Attribute'.
	 */
	public Attribute getAttribute() {
		return attribute;
	}

	/**
	 * The function returns the value of an object.
	 * 
	 * @return Returning the value of an object.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * This is an abstract method that calculates the distance between two objects
	 * and returns a double value. The implementation will be different for discrete
	 * item and continuous item. The method is abstract, which means that it does
	 * not have an implementation in this.
	 * 
	 * @param a The parameter "a" is an object that is used to calculate the
	 *          distance from the current object. The specific type of object is not
	 *          specified in the method signature, so it can be any
	 *          object that implements this method.
	 * @return A double value representing the distance between the object calling
	 *         the method and the object passed as a parameter. However, since this
	 *         is an abstract method, it does not have and implementation in the
	 *         current class and must be implemented in a subclass.
	 */
	public abstract double distance(Object a);

	/**
	 * This function updates the value of a prototype with a function call
	 * computePrototype(Set<Integer> s, Attribute a).
	 * 
	 * @param data          an object of type Data, which contains the examples
	 * @param clusteredData A set of integers representing the indices of the data
	 *                      examples.
	 */
	public void update(Data data, Set<Integer> clusteredData) {
		value = data.computePrototype(clusteredData, attribute);
	}

	/**
	 * This function returns a string representation of the value.
	 * 
	 * @return A string representation of the value of the object is being returned.
	 */
	@Override
	public String toString() {
		return value.toString();
	}
}