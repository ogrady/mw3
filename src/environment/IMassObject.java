package environment;

/**
 * Every object that can have gravity applied to it should implement this interface
 * @author Daniel
 */
public interface IMassObject {
	/**
	 * Apply a certain vertical pull to the object (positive: down, negative: up)
	 * @param g gravity
	 */
	void applyGravity(float g);
}
