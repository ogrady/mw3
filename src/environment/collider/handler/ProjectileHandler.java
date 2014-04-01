package environment.collider.handler;

import java.util.Collection;

import environment.Positionable;
import environment.collision.ICollider;
import environment.projectile.Projectile;

/**
 * Calles the {@link ICollider#onDamageSourceCollide(environment.IDamageSource)}
 * of all collided objects
 * 
 * @author Daniel
 * 
 */
public class ProjectileHandler extends
		CollisionHandler<Positionable, Positionable> {
	@Override
	public void handle(final Positionable me,
			final Collection<Positionable> collisions) {
		final Projectile proj = (Projectile) me;
		for (final Positionable p : collisions) {
			p.getCollider().onDamageSourceCollide(proj);
		}
	}
}
