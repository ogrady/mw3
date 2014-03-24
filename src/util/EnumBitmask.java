package util;

import java.util.HashMap;

/**
 * {@link EnumBitmask}s are {@link Bitmask}, who take members from Enums as
 * input-values. They consist of a hashmap Enum -> Boolean.
 * 
 * @author Daniel
 * 
 * @param <E>
 *            the Enum that can be put into the {@link EnumBitmask}
 */
public class EnumBitmask<E extends Enum<?>> implements IBitmask<E> {
	protected final HashMap<E, Boolean> _map;

	/**
	 * Constructor
	 */
	public EnumBitmask() {
		_map = new HashMap<E, Boolean>();
	}

	@Override
	public void add(final E value) {
		_map.put(value, true);
	}

	@Override
	public void remove(final E value) {
		_map.put(value, false);
	}

	@Override
	public boolean has(final E value) {
		final Boolean has = _map.get(value);
		return has != null && has;
	}

	public boolean hasNot(final E value) {
		return !has(value);
	}

	@Override
	public void set(final E value) {
		reset();
		add(value);
	}

	@Override
	public void reset() {
		_map.clear();
	}

	@Override
	public String toString() {
		String contents = "";
		for (final E e : _map.keySet()) {
			if (has(e)) {
				contents += e + "\r\n";
			}
		}
		return contents;
	}
}
