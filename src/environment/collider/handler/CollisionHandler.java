package environment.collider.handler;

import java.util.Collection;

import level.Block;
import environment.IDamageSource;
import environment.Positionable;
import environment.collider.collector.CollisionCollector;

/**
 * Handles collisions that were determined before.
 * 
 * @author Daniel
 * 
 * @param <P>
 */
abstract public class CollisionHandler<M extends Positionable, P extends Positionable> {
	/**
	 * Handles the passed collisions.
	 * 
	 * @param me
	 *            our own {@link Positionable}, the other objects collided with
	 * @param collisions
	 *            a list of objects, our object collided with, determined by a
	 *            {@link CollisionCollector}.
	 */
	abstract public void handle(M me, Collection<P> collisions);

	public void onPositionableCollide(final Positionable positionable) {
	}

	public void onDamageSourceCollide(final IDamageSource dmgsrc) {
	}

	public void onBlockCollide(final Block block) {
	}

}
