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
	 * Called, when an element was added to the mask
	 * 
	 * @param mask
	 *            the mask after adding the new element
	 * @param newElement
	 *            the added element
	 */
	void onAdd(IBitmask<E> mask, E newElement);

	/**
	 * Called, when an element was removed from the mask
	 * 
	 * @param mask
	 *            the mask after removing the element
	 * @param removedElement
	 *            the removed element
	 */
	void onRemove(IBitmask<E> mask, E removedElement);
}
