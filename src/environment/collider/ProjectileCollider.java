package environment.collider;

import java.util.ArrayList;
import java.util.List;

import level.Block;
import environment.Actor;
import environment.IDamageSource;
import environment.Positionable;
import environment.projectile.Projectile;

public class ProjectileCollider extends DefaultCollider {
	private final Projectile _projectile;

	public ProjectileCollider(final Projectile projectile) {
		super(projectile);
		_projectile = projectile;
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
			if (a != _projectile.getSource() && collides(a)) {
				collisions.add(a);
			}
		}
		return collisions;
	}

	@Override
	public void onPositionableCollide(final Positionable positionable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDamageSourceCollide(final IDamageSource dmgsrc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBlockCollide(final Block block) {
		// TODO Auto-generated method stub

	}

}
