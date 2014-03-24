package listener;

import util.IBitmask;

/**
 * Listeners that listen for changes in a {@link IBitmask}
 * 
 * @author Daniel
 * 
 * @param <E>
 *            Enum that can be put into the bitmask
 */
public interface IEnumBitmaskListener<E extends Enum<?>> extends IListener {
	/**
	 * Called when the bitmask changes
	 * 
	 * @param mask
	 *            the mask that changed
	 */
	void onChange(IBitmask<E> mask);
}
