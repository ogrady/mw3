package util;

/**
 * A map of bits that can easily be prompted whether it contains a certain value.<br>
 * The input values should be powers of 2 (1,2,4,8,16,...) as we use bitwise addition to put them in the map.<br>
 * Looking up whether a value is contained in the map boils down to checking whether that bit is set.<br>
 * Useful for setting flags. 
 * @author Daniel
 *
 */
public class Bitmask {
	private int bits;
	
	/**
	 * Add a value to the map (idempotent)
	 * @param _val value to add
	 */
	public void add(int _val) {
		bits |= _val;
	}
	
	/**
	 * Remove a value from the map (idempotent)
	 * @param _val value to remove
	 */
	public void remove(int _val) {
		bits &= ~_val;
	}
	
	/**
	 * Checks whether a given value is contained in the map
	 * @param _val true, if the corresponding bit is set
	 * @return
	 */
	public boolean has(int _val) {
		return (bits & _val) != 0;
	}
}
