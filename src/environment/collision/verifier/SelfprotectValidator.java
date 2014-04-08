package environment.collision.verifier;

import environment.Positionable;
import environment.projectile.Projectile;

/**
 * {@link Positionable}s pass this verifier, if they are not they source of the
 * {@link Projectile}, "me" contains in this case
 * 
 * @author Daniel
 * 
 */
public class SelfprotectValidator extends CollisionVerifier {

	@Override
	public boolean passes(final Positionable me, final Positionable other) {
		final Projectile proj = (Projectile) me;
		return proj.getProducer() != other;
	}
}
