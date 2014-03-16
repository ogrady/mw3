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
	STANDING, MOVING, FALLING, FLYING, JUMPING
}
