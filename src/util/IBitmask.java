package util;

/**
 * Interface for bitmasks. Bitmasks are sets that grant fast probing to see if a
 * value is contained in the mask.
 * 
 * @author Daniel
 * 
 * @param <T>
 *            the type of values that can be stored in the mask
 */
public interface IBitmask<T> {

	/**
	 * Add a value to the map (idempotent)
	 * 
	 * @param val
	 *            value to add
	 */
	public void add(final T val);

	/**
	 * Remove a value from the map (idempotent)
	 * 
	 * @param val
	 *            value to remove
	 */
	public void remove(final T val);

	/**
	 * Checks whether a given value is contained in the map
	 * 
	 * @param val
	 *            true, if the corresponding bit is set
	 * @return
	 */
	public boolean has(final T val);

	/**
	 * Resets the {@link Bitmask} to the initial state (0, no bits set)
	 */
	public void reset();

	/**
	 * Resets the {@link Bitmask} and puts one single value in
	 * 
	 * @param val
	 *            the single value to put in
	 */
	public void set(final T val);

}
