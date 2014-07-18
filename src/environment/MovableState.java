package environment;

import util.EnumBitmask;

/**
 * Several states a {@link Movable} can have. Multiple states can be active at
 * once (e.g. {@link #FLYING} and {@link #MOVING}), which are stored in a
 * {@link EnumBitmask} inside the {@link Movable}.
 *
 * @author Daniel
 *
 */
public enum MovableState {
	/**
	 * The mech is unmoving.
	 */
	STANDING,
	/**
	 * The mech is in a state of motion.
	 */
	MOVING,
	/**
	 * The mech is in a state of motion in the direction which gravity dictates.
	 */
	FALLING,
	/**
	 * The mech is in a state of motion in a direction away from the ground.
	 */
	FLYING,
	/**
	 * The mech is in a state, where its position is not influenced by gravity.
	 */
	HOVERING,
	/**
	 * The mech is in a state, where it is either in an upward motion. Transists
	 * into {@link #FALLING}
	 */
	JUMPING,
	/**
	 * The mech defends itself.
	 */
	BLOCKING,
	/**
	 * The mech is currently performing its special attack
	 */
	SPECIAL;

	/**
	 * Converts a bitmask to a readable string.
	 *
	 * @param enumBitMask
	 *            The bitmask to be converted to a string.
	 * @return A string with all the names of this enum the representing bitmask
	 *         has set to 1.
	 */
	public static String bitMaskToString(final int enumBitMask) {
		int bitMask = enumBitMask;
		boolean firstEntry = true;
		final MovableState[] states = MovableState.values();
		String result = new String();

		for (final MovableState state : states) {
			if ((bitMask & 1) == 1) {
				if (!firstEntry) {
					result += ", ";
				}
				firstEntry = false;
				result += state.toString();
			}

			bitMask = bitMask >> 1;
		}

		return result;
	}
}
