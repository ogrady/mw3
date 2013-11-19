package util;

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
}
