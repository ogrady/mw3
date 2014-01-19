package environment.collider;

import java.util.ArrayList;
import java.util.List;

import level.Block;
import environment.Actor;
import environment.IDamageSource;
import environment.Positionable;
import environment.projectile.Projectile;

public class ProjectileCollider extends DefaultCollider<Projectile> {

	public ProjectileCollider(final Projectile projectile) {
		super(projectile);
	}

	@Override
	public List<Positionable> getCollisions() {
		final ArrayList<Positionable> collisions = new ArrayList<Positionable>();
		for (final Block b : Block.instances) {
			if (b.isSolid() && collides(b)) {
				collisions.add(b);
				b.destroy();
			}
		}
		for (final Actor a : Actor.instances) {
			if (a != _collidable.getSource() && collides(a)) {
				collisions.add(a);
			}
		}
		if (collisions.size() > 0) {
			for (final Positionable collided : collisions) {
				collided.getCollider().onDamageSourceCollide(_collidable);
			}
			// bullets deal damage to all entities it collided with and then
			// vanishes
			_collidable.destruct();
		}
		return collisions;
	}

	@Override
	public void onPositionableCollide(final Positionable positionable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDamageSourceCollide(final IDamageSource dmgsrc) {
		// bullets are by default immune to other damage-sources

	}

	@Override
	public void onBlockCollide(final Block block) {
		// blocks don't shift into bullets

	}

}
