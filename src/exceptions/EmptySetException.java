package exceptions;

/**
 * The class "EmptySetException" is a custom exception class in Java
 * that extends the built-in "Exception" class and is used to handle errors
 * related to an empty set.
 */
public class EmptySetException extends Exception {

	/**
	 * Constructor for the exception 'EmptySetException' class that takes
	 * a String parameter 'msg'. Calls the constructor parent class with 'msg'
	 * parameter, which sets the error message for the exception. This allow the
	 * exception to be thrown with a custom message error that can provide more
	 * information about the especific error that is being ocurred.
	 * 
	 * @param msg String representing the message error.
	 */
	public EmptySetException(String msg) {
		super(msg);
	}
}