package util;

/**
 * {@link EnumBitmask}s are {@link Bitmask}, who take members from Enums as
 * input-values. The ordinal-number of those Enums will be used as power of 2,
 * as a convienience to store enum-values efficiently.
 * 
 * @author Daniel
 * 
 * @param <E>
 *            the Enum that can be put into the {@link EnumBitmask}
 */
public class EnumBitmask<E extends Enum<?>> extends Bitmask {
	public void add(final E value) {
		add((int) Math.pow(2, value.ordinal()));
	}

	public void remove(final E value) {
		remove((int) Math.pow(2, value.ordinal()));
	}

	public boolean has(final E value) {
		return has((int) Math.pow(2, value.ordinal()));
	}

	public boolean hasNot(final E value) {
		return !has(value);
	}

	public void set(final E value) {
		set((int) Math.pow(2, value.ordinal()));
	}
}
