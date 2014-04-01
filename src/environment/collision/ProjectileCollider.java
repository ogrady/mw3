package environment.collision;

import java.util.Collection;

import environment.Actor;
import environment.Positionable;
import environment.character.StationaryShield;
import environment.collider.collector.ProjectileCollector;
import environment.collider.handler.ProjectileHandler;
import environment.projectile.Projectile;

/**
 * Collider for {@link Projectile}s. Destroys blocks and
 * {@link StationaryShield}s from Nitro and damages {@link Actor}s.
 * 
 * @author Daniel
 * 
 */
public class ProjectileCollider extends Collider<Projectile> {
	private int _remainingCollisions;

	/**
	 * Constructor
	 * 
	 * @param projectile
	 *            the {@link Projectile} to handle
	 * @param maxCollisions
	 *            the number of collisions the {@link Projectile} can suffer
	 *            before it despawns
	 */
	public ProjectileCollider(final Projectile projectile,
			final int maxCollisions) {
		super(projectile, new ProjectileCollector(), new ProjectileHandler());
		_remainingCollisions = maxCollisions;
	}

	@Override
	public void handleCollisions() {
		final Collection<Positionable> collisions = _collector
				.collectCollisions(_collidable);
		_handler.handle(_collidable, collisions);
		_remainingCollisions -= collisions.size();
		if (_remainingCollisions <= 0) {
			_collidable.destruct();
		}
	}
}
