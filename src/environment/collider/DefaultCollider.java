package environment.collider;

import java.util.ArrayList;
import java.util.List;

import level.Block;
import environment.IDamageSource;
import environment.Positionable;

/**
 * The defaultcollider does basic checking whether he collids with something or
 * not:<br>
 * whenever his collidables hitbox intersects with the hitbox of another
 * collidable they collide.<br>
 * All other methods are empty and can be overridden by subclasses.
 * 
 * @author Daniel
 * 
 */
public class DefaultCollider implements ICollider {
	protected Positionable _collidable;

	public DefaultCollider(final Positionable positionable) {
		_collidable = positionable;
	}

	@Override
	public List<Positionable> getCollisions() {
		final ArrayList<Positionable> collisions = new ArrayList<Positionable>();
		for (final Positionable p : Positionable.instances) {
			if (p.getCollider().collides(_collidable)) {
				collisions.add(p);
			}
		}
		return collisions;
	}

	@Override
	public ICollidable getCollidable() {
		return _collidable;
	}

	@Override
	public boolean collides(final Positionable other) {
		return other != _collidable
				&& _collidable.getHitbox().intersects(other.getHitbox());
	}

	@Override
	public void onPositionableCollide(final Positionable positionable) {
	}

	@Override
	public void onDamageSourceCollide(final IDamageSource dmgsrc) {
	}

	@Override
	public void onBlockCollide(final Block block) {

	}
}
