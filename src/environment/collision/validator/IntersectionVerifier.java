package environment.collision.validator;

import environment.Positionable;

/**
 * {@link Positionable}s pass this verifier if their hitbox intersects with the
 * passed "me" hitbox
 * 
 * @author Daniel
 * 
 */
public class IntersectionVerifier extends CollisionVerifier {

	@Override
	public boolean passes(final Positionable me, final Positionable other) {
		return me.getHitbox().intersects(other.getHitbox());
	}
}
