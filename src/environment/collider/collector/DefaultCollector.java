package environment.collider.collector;

import java.util.ArrayList;
import java.util.Collection;

import level.Block;
import environment.Actor;
import environment.Positionable;
import environment.collision.validator.IntersectionVerifier;

/**
 * Collects collisions with {@link Block}s and {@link Actor}, based on and
 * {@link IntersectionVerifier}
 * 
 * @author Daniel
 * 
 */
public class DefaultCollector extends CollisionCollector<Positionable> {
	public DefaultCollector() {
		addValidator(new IntersectionVerifier());
	}

	@Override
	public Collection<Positionable> collectCollisions(final Positionable me) {
		final ArrayList<Positionable> collisions = new ArrayList<Positionable>();
		for (final Block b : Block.instances) {
			if (b.isSolid() && collides(me, b)) {
				collisions.add(b);
			}
		}
		for (final Actor a : Actor.instances) {
			if (a != me && collides(me, a)) {
				collisions.add(a);
			}
		}
		return collisions;
	}
}
