package environment.collider.collector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import environment.Positionable;
import environment.collision.verifier.CollisionVerifier;
import environment.collision.verifier.IntersectionVerifier;
import environment.collision.verifier.NotselfVerifier;

/**
 * {@link CollisionCollector}s gather the objects a {@link Positionable}
 * currently collides with.<br>
 * There can be different collectors: such that they...
 * <ul>
 * <li>...don't collide with anything</li>
 * <li>...collide with everything, the positionable touches</li>
 * <li>...omit certain objects from collisions (instances of the own team etc.)</li>
 * <li>...</li>
 * </ul>
 * This is achieved by applying {@link CollisionVerifier}s to them, which serve
 * as criterion for whether a collision occurs or not. Their only job is to<br>
 * So the only job of collectors is to <strong>collect</strong> those collisions
 * and return them. Handling takes place elsewhere!
 * <p>
 * The reason why each collector only takes care of one collection is to be able
 * to apply different criterions to different collectors. E.g. a collider could
 * be interested in the collisions of its {@link Positionable} and the blocks,
 * as well as with other {@link Positionable}s. For both lists, we would employ
 * an {@link IntersectionVerifier}, to check for basic collision. But the list
 * that contains the {@link Positionable}s also needs a {@link NotselfVerifier},
 * as he would constantly collide with itself. Probing the map doesn't need this
 * check and would therefore add complexity that is not needed. So instead we
 * have different collectors for both those lists and every collector holds its
 * own verifiers. The results of both collectors can be combined within the
 * collider, to have both: the collisions with other {@link Positionable} and
 * with the map.
 * </p>
 * <p>
 * The general course of the collection goes like this:
 * <ol>
 * <li>the collector holds one structure that contain interesting objects (list,
 * Quadtree, database-table, whatever we are storing our objects in).
 * <li>this list of collision-candidates is passed to the interchangeable
 * {@link CollisionVerifier}s one by one to check for actual collision. Based on
 * how the verifiers determines collision, some objects will pass this test,
 * while others don't. Multiple verifiers can be employed to narrow down the
 * resulting collisions even further</li>
 * <li>based on this selection, a final list of objects, with which our object
 * collided is constructed and returned</li>
 * </p>
 * Also, note the difference between active and passive collisions:<br>
 * active: our object ran into something<br>
 * passive: something ran into our object -> probably call to onFoobar()-method
 * 
 * @author Daniel
 * 
 * @param <E>
 *            type of the collection, this collector can check against
 */
public class CollisionCollector<E extends Positionable> {
	private final Collection<E> _candidates;
	private final ArrayList<CollisionVerifier> _verifiers;

	/**
	 * Constructor
	 */
	public CollisionCollector(final Collection<E> candidates,
			final CollisionVerifier... verifiers) {
		_candidates = candidates;
		_verifiers = new ArrayList<CollisionVerifier>();
		addVerifiers(verifiers);
	}

	/**
	 * Adds new verifiers to the {@link CollisionCollector}
	 * 
	 * @param verifiers
	 */
	public void addVerifiers(final CollisionVerifier... verifiers) {
		for (final CollisionVerifier v : verifiers) {
			_verifiers.add(v);
		}
	}

	/**
	 * Checks if one specific {@link Positionable} satisfies all employed
	 * {@link CollisionVerifier}s
	 * 
	 * @param me
	 *            the own {@link Positionable}
	 * @param other
	 *            the other {@link Positionable} whose collision with "me" is in
	 *            question
	 * @return if all {@link CollisionVerifier}s are satisfied (= the two
	 *         objects collided)
	 */
	protected boolean collides(final Positionable me, final Positionable other) {
		int i = 0;
		while (i < _verifiers.size() && _verifiers.get(i).passes(me, other)) {
			i++;
		}
		return i >= _verifiers.size();
	}

	/**
	 * <p>
	 * Collects all relevant collisions. This means, the collector itself runs
	 * through interesting lists and puts interesting entities into a collection
	 * it returns. That's not necessarily a list of all intersecting objects!
	 * </p>
	 * Interesting lists are available through static lists, for example
	 * {@link Positionable#instances}. Later on, this could be replaced with
	 * Quadtrees or similar structures.
	 * 
	 * @param me
	 *            the entity we check for collisions with its environment
	 * @return the list of objects, this collector deemed interesting
	 */

	/**
	 * Collects all collisions from the candidate-list by applying the verifiers
	 * to it. The results will be gathered in the passed {@link HashSet}, so we
	 * can chain multiple {@link CollisionCollector}s and let them all put their
	 * results into the same result-set, instead of generating one on their own
	 * and uniting those sets later on.
	 * 
	 * @param me
	 *            our entity
	 * @param collisions
	 *            {@link HashSet} the collisions will be put in by reference
	 */
	public void collectionCollisions(final Positionable me,
			final HashSet<Positionable> collisions) {
		for (final E candidate : _candidates) {
			if (collides(me, candidate)) {
				collisions.add(candidate);
			}
		}
	}
}
