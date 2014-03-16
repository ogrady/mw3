package exception;

/**
 * Thrown when something with the map went wrong: loading it from the file or
 * illegal access to the map.
 * 
 * @author Daniel
 * 
 */
public class MapException extends Exception {
	private static final long serialVersionUID = 1L;

	public MapException(final String message) {
		super(message);
	}
}
