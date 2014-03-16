package environment.collider;

import java.util.ArrayList;
import java.util.List;

import level.Block;
import environment.Actor;
import environment.IDamageSource;
import environment.Positionable;
import environment.character.StationaryShield;
import environment.projectile.Projectile;

/**
 * Collider for {@link Projectile}s. Destroys blocks and
 * {@link StationaryShield}s from Nitro and damages {@link Actor}s.
 * 
 * @author Daniel
 * 
 */
public class ProjectileCollider extends DefaultCollider<Projectile> {

	/**
	 * Constructor
	 * 
	 * @param projectile
	 *            the {@link Projectile} to handle
	 */
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
		for (final StationaryShield s : StationaryShield.instances) {
			if (collides(s)) {
				collisions.add(s);
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
		// TODO apply damage when an interesting positionable walks into the
		// bullet
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
