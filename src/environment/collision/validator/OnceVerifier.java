package environment.collision.validator;

import java.util.HashSet;

import environment.Positionable;

/**
 * {@link Positionable}s pass this verifier only once and never again
 * 
 * @author Daniel
 * 
 */
public class OnceVerifier extends CollisionVerifier {
	private final HashSet<Positionable> _collidedBefore;

	public OnceVerifier() {
		_collidedBefore = new HashSet<Positionable>();
	}

	@Override
	public boolean passes(final Positionable me, final Positionable other) {
		// attempts to add -> return value implies success of adding
		return _collidedBefore.add(other);
	}
}
