package environment.collider;

import environment.Actor;
import environment.IDamageSource;

/**
 * Colliders for {@link Actor} that take damage upon colliding with
 * {@link IDamageSource}s
 * 
 * @author Daniel
 * 
 */
public class ActorCollider extends DefaultCollider<Actor> {
	public ActorCollider(final Actor positionable) {
		super(positionable);
	}

	@Override
	public void onDamageSourceCollide(final IDamageSource dmgsrc) {
		_collidable.takeDamage(dmgsrc, (int) dmgsrc.getBaseDamage());
	}
}
