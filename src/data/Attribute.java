package data;

import java.io.Serializable;

/**
 * The Attribute class is an abstract class that contains a name and index
 * field, as well as methods for getting the name and index.
 */
public abstract class Attribute implements Serializable{
	private String name;
	private int index;

	/**
	 * Constructor method for Attribute class that takes in two parameters:
	 * 
	 * @param name  as a string representing the name of the attribute
	 * @param index as an integer representing the identifier of the tuple
	 */
	public Attribute(String name, int index) {
		this.name = name;
		this.index = index;
	}

	/**
	 * The function returns the name of the attribute.
	 * 
	 * @return the string value 'name'.
	 */
	public String getName() {
		return name;
	}

	/**
	 * The function returns the value of the variable "index", which corresponds to
	 * the attribute index
	 * 
	 * @return the integer value which is stored
	 *         in the variable 'index'.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * This function overrides the default toString() method to return the name of
	 * the object as a string.
	 * 
	 * @return the value of the 'name' variable of the 'Attribute' as a string.
	 */
	@Override
	public String toString() {
		return name;
	}
}
