package environment.collision.validator;

import environment.Positionable;
import environment.collider.collector.CollisionCollector;
import environment.collision.ICollider;

/**
 * Checks for the collision of two {@link Positionable}s under certain
 * criterions. In most cases, this will be simple intersection. But multiple
 * verifiers can be combined to determine more complex collision-scenarios: for
 * example, if two objects should not collide more than once and already
 * collided before. You would then employ an {@link IntersectionVerifier} and a
 * {@link OnceVerifier} in the {@link CollisionCollector}<br>
 * You can pair up any {@link CollisionCollector} (which does the preselection
 * by choosing the lists he gathers the candidates from) with any
 * {@link CollisionVerifier}.
 * 
 * @author Daniel
 * 
 */
abstract public class CollisionVerifier {
	/**
	 * Checks, whether two entities collide
	 * 
	 * @param me
	 *            the own entity, passed down from the {@link ICollider}
	 * @param other
	 *            the entity to check against
	 * @return
	 */
	public abstract boolean passes(Positionable me, Positionable other);
}
