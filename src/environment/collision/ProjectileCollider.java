package environment.collision;

import java.util.Collection;

import level.Block;
import environment.Actor;
import environment.Positionable;
import environment.character.StationaryShield;
import environment.collider.collector.CollisionCollector;
import environment.collider.handler.ProjectileHandler;
import environment.collision.verifier.IntersectionVerifier;
import environment.collision.verifier.NotselfVerifier;
import environment.collision.verifier.OnceVerifier;
import environment.collision.verifier.SelfprotectValidator;
import environment.projectile.Projectile;

/**
 * Collider for {@link Projectile}s. Destroys blocks and
 * {@link StationaryShield}s from Nitro and damages {@link Actor}s.
 *
 * @author Daniel
 *
 */
public class ProjectileCollider extends Collider<Projectile> {
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
		_handler = new ProjectileHandler();
		_remainingCollisions = maxCollisions;
		addCollector(new CollisionCollector<Block>(Block.solidBlocks,
				new IntersectionVerifier(), new OnceVerifier()));
		addCollector(new CollisionCollector<Actor>(Actor.instances,
				new IntersectionVerifier(), new NotselfVerifier(),
				new SelfprotectValidator(), new OnceVerifier()));
		addCollector(new CollisionCollector<StationaryShield>(
				StationaryShield.instances, new IntersectionVerifier(),
				new OnceVerifier()));
	}

	@Override
	public void handleCollisions() {
		final Collection<Positionable> collisions = getCollisions();
		_handler.handle(_collidable, collisions);
		_remainingCollisions -= collisions.size();
		if (_remainingCollisions <= 0) {
			_collidable.destruct();
		}
	}
}
