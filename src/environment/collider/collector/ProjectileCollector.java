package environment.collider.collector;

import java.util.Collection;

import environment.Positionable;
import environment.character.StationaryShield;
import environment.collision.validator.OnceVerifier;
import environment.collision.validator.SelfprotectValidator;
import environment.projectile.Projectile;

/**
 * Like the {@link DefaultCollector}, but employs a {@link OnceVerifier} in
 * addition, as entities should be hit only once by {@link Projectile}s. It also
 * takes {@link StationaryShield}s into account as well.
 * 
 * @author Daniel
 * 
 */
public class ProjectileCollector extends DefaultCollector {
	public ProjectileCollector() {
		addValidator(new OnceVerifier());
		addValidator(new SelfprotectValidator());
	}

	@Override
	public Collection<Positionable> collectCollisions(final Positionable me) {
		// lists the defaultcollector considered + stationary shields
		final Collection<Positionable> collisions = super.collectCollisions(me);
		for (final StationaryShield s : StationaryShield.instances) {
			if (collides(me, s)) {
				collisions.add(s);
			}
		}
		return collisions;
	}
}
