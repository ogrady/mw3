package environment.collision;

import environment.Actor;
import environment.IDamageSource;
import environment.MovableState;

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
		int dmg = (int) dmgsrc.getBaseDamage();
		if (_collidable.getState().has(MovableState.BLOCKING)) {
			// puts the relativ position of the source of the damage into -1|1.
			// -1: source is left of our positionable
			// 1: source is right of our positionable
			final float dif = dmgsrc.getProducer().getPosition().x
					- _collidable.getPosition().x;
			final int srcdir = (int) (dif / Math.abs(dif));
			// if we are blocking and facing into the direction the damage came
			// from, reduce the taken damage
			if (_collidable.getDirection() == srcdir) {
				// TODO put this constant somewhere else
				dmg *= 0.8;
			}
		}
		_collidable.takeDamage(dmgsrc, dmg);
	}
}
