package data;

/**
 * The DiscreteItem class extends the Item class and calculates the distance
 * between two discrete attribute values.
 * 
 * Classe DiscreteItem che estende la classe Item e rappresenta una coppia
 * <Attributo discreto- valore discreto>
 */
public class DiscreteItem extends Item {
	public DiscreteItem(DiscreteAttribute attribute, String value) {
		super(attribute, value);
	}

	/**
	 * The function calculates the distance between two objects based on their
	 * values, returning 0 if they
	 * are equal and 1 if they are not.
	 * 
	 * @param a The parameter "a" is an object of the class "DiscreteItem".
	 * @return The method is returning a double value, either 0.0 or 1.0, depending
	 *         on whether the value of the current object is equal to the value of
	 *         the object passed as a parameter.
	 */
	@Override
	public double distance(Object a) {
		if (getValue().equals(((DiscreteItem) a).getValue()))
			return 0.0;
		else
			return 1.0;
	}
}
