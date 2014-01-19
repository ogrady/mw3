package environment.collider;

import java.util.ArrayList;
import java.util.List;

import level.Block;
import environment.Actor;
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
public class DefaultCollider<P extends Positionable> implements ICollider {
	protected P _collidable;

	public DefaultCollider(final P positionable) {
		_collidable = positionable;
	}

	@Override
	public List<Positionable> getCollisions() {
		final ArrayList<Positionable> collisions = new ArrayList<Positionable>();
		for (final Block b : Block.instances) {
			if (b.isSolid() && collides(b)) {
				collisions.add(b);
			}
		}
		for (final Actor a : Actor.instances) {
			if (a != _collidable && collides(a)) {
				collisions.add(a);
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
		return _collidable.getHitbox().intersects(other.getHitbox());
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
