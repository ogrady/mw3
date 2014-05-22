package environment.collision;

import level.Block;
import environment.Actor;
import environment.Positionable;
import environment.collider.collector.CollisionCollector;
import environment.collision.verifier.IntersectionVerifier;
import environment.collision.verifier.NotselfVerifier;

/**
 * The defaultcollider does basic checking whether he collides with something or
 * not:<br>
 * whenever his collidables hitbox intersects with the hitbox of another
 * collidable they collide.<br>
 * All other methods are empty and can be overridden by subclasses.
 *
 * @author Daniel
 *
 */
public class DefaultCollider<P extends Positionable> extends Collider<P> {
	public DefaultCollider(final P positionable) {
		super(positionable);
		addCollector(new CollisionCollector<Block>(Block.solidBlocks,
				new IntersectionVerifier()));
		// TODO get the fucking map in here somehow more clean and implement
		// this in general (quadtree still contains some invisible blocks)
		/*addCollector(new QuadTreeCollector<Block>(World.last.getQuadtree(),
				new IntersectionVerifier()));*/
		addCollector(new CollisionCollector<Actor>(Actor.instances,
				new IntersectionVerifier(), new NotselfVerifier()));
	}
}
