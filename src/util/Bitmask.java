package util;

/**
 * A map of bits that can easily be prompted whether it contains a certain
 * value.<br>
 * The input values should be powers of 2 (1,2,4,8,16,...) as we use bitwise
 * addition to put them in the map.<br>
 * Looking up whether a value is contained in the map boils down to checking
 * whether that bit is set.<br>
 * Useful for setting flags.
 * 
 * @author Daniel
 * 
 */
public class Bitmask implements IBitmask<Integer> {
	protected int _bits;

	/**
	 * Add a value to the map (idempotent)
	 * 
	 * @param val
	 *            value to add
	 */
	@Override
	public void add(final Integer val) {
		_bits |= val;
	}

	/**
	 * Remove a value from the map (idempotent)
	 * 
	 * @param val
	 *            value to remove
	 */
	@Override
	public void remove(final Integer val) {
		_bits &= ~val;
	}

	/**
	 * Checks whether a given value is contained in the map
	 * 
	 * @param val
	 *            true, if the corresponding bit is set
	 * @return
	 */
	@Override
	public boolean has(final Integer val) {
		return (_bits & val) != 0;
	}

	/**
	 * Resets the {@link Bitmask} to the initial state (0, no bits set)
	 */
	@Override
	public void reset() {
		_bits = 0;
	}

	/**
	 * Resets the {@link Bitmask} and puts one single value in
	 * 
	 * @param val
	 *            the single value to put in
	 */
	@Override
	public void set(final Integer val) {
		reset();
		add(val);
	}
}
