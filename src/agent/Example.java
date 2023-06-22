package agent;

import java.util.ArrayList;
import java.util.List;

/**
 * The Example class implements the Comparable interface and contains methods
 * for adding objects to a list, getting objects from the list, comparing
 * Example objects, and getting the list as a string representation.
 */
public class Example implements Comparable<Example> {
	private List<Object> example = new ArrayList<Object>();

	/**
	 * This function adds an object to a list called "example".
	 * 
	 * @param o The parameter "o" is an object that will be added to the "example"
	 *          collection. The type of the object can be any class since the
	 *          parameter is of type "Object", which is a superclass of all
	 *          other classes in Java.
	 */
	public void add(Object o) {
		example.add(o);
	}

	/**
	 * This function returns the element at the specified index in the "example"
	 * object.
	 * 
	 * @param i The parameter "i" is an integer representing the index of the
	 *          element to be retrieved from the "example" object.
	 * @return The method is returning the element at the specified index 'i' from
	 *         the 'example' object. The type of the returned object is not
	 *         specified in the code snippet.
	 */
	public Object get(int i) {
		return example.get(i);
	}

	/**
	 * This function compares the elements of two Example objects and returns an
	 * integer indicating their relative order.
	 * 
	 * @param ex The parameter "ex" is an object of the class "Example" that is
	 *           being compared to the current object.
	 * @return The method is returning an integer value. If the objects in the
	 *         "example" list of the input parameter "ex" are not equal to the
	 *         objects in the "example" list of the current object (this).
	 *         If objects in both lists are equal, the method retuns 0
	 *         If the object of the lists are less, the method retuns -1
	 *         If the object of the lists are mayor, the method retuns 1
	 */
	public int compareTo(Example ex) {

		int i = 0;
		for (Object o : ex.example) {
			if (!o.equals(this.example.get(i)))
				return ((Comparable) o).compareTo(example.get(i));
			i++;
		}
		return 0;
	}

	/**
	 * This function returns a string representation of the objects in the "example"
	 * array.
	 * 
	 * @return A string representation of the objects in the "example" array,
	 *         separated by spaces.
	 */
	public String toString() {
		String str = "";
		for (Object o : example)
			str += o.toString() + " ";
		return str;
	}
}