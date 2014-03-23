package environment.collider;

import java.util.ArrayList;
import java.util.List;

import level.Block;
import environment.Actor;
import environment.Positionable;
import environment.character.StationaryShield;
import environment.projectile.ParticleSword;
import environment.projectile.Projectile;

/**
 * Collider for {@link Projectile}s. Destroys blocks and
 * {@link StationaryShield}s from Nitro and damages {@link Actor}s.
 * 
 * @author Daniel
 * 
 */
public class ProjectileCollider extends DefaultCollider<Projectile> {
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
		super(projectile);
		_remainingCollisions = maxCollisions;
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
		dealDamage(collisions);
		uponCollisions(collisions);
		return collisions;
	}

	/**
	 * Used to deal damage to all things it collided with
	 * 
	 * @param collisions
	 *            the list of things the {@link Projectile} collided with
	 */
	protected void dealDamage(final List<Positionable> collisions) {
		for (final Positionable collided : collisions) {
			collided.getCollider().onDamageSourceCollide(_collidable);
		}
	}

	/**
	 * {@link Projectile} can only collide with so many objects before
	 * despawning.<br>
	 * While most {@link Projectile}s can only collide with one object, certain
	 * "breakthrough" bullets will penetrate multiple objects before vanishing.
	 * Such as the {@link ParticleSword}, which hits all objects until it
	 * traveled its max distance.
	 * 
	 * @param collisions
	 *            a list of collisions to substract from the number of remaining
	 *            collisions
	 */
	protected void uponCollisions(final List<Positionable> collisions) {
		_remainingCollisions -= collisions.size();
		if (_remainingCollisions <= 0) {
			// bullets dealt damage to all entities it collided with and then
			// vanishes
			_collidable.destruct();
		}
	}
}
