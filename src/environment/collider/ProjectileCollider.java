package environment.collider;

import java.util.ArrayList;
import java.util.HashSet;
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
	private final HashSet<Positionable> _collidedBefore;

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
		_collidedBefore = new HashSet<Positionable>();
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
		return collisions;
	}

	/**
	 * Used to deal damage to all things it collided with
	 * 
	 * @param collisions
	 *            the list of things the {@link Projectile} collided with
	 */
	protected void dealDamage(final List<Positionable> collisions) {
		int newCollisions = 0;
		for (final Positionable collided : collisions) {
			if (!_collidedBefore.contains(collided)) {
				collided.getCollider().onDamageSourceCollide(_collidable);
				_collidedBefore.add(collided);
				newCollisions++;
			}
		}
		uponCollisions(collisions, newCollisions);
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
	 * @param relevant
	 *            the number of relevant collisions that occured (the set of
	 *            collisions minus the collisions that were handled before, as
	 *            one {@link Projectile} can hit a {@link Positionable} only
	 *            once)
	 */
	protected void uponCollisions(final List<Positionable> collisions,
			final int relevant) {
		_remainingCollisions -= relevant;
		if (_remainingCollisions <= 0) {
			// bullets dealt damage to all entities it collided with and then
			// vanishes
			_collidable.destruct();
		}
	}
}
