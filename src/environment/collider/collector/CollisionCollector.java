package environment.collider.collector;

import java.util.ArrayList;
import java.util.Collection;

import environment.Positionable;
import environment.collision.validator.CollisionVerifier;
import environment.projectile.Projectile;

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
 * Their only job is to <strong>collect</strong> those collisions and return
 * them. Handling takes place in another place!
 * <p>
 * The general course of the collection goes like this:
 * <ol>
 * <li>the collector picks out structures that contain interesting objects
 * (lists, Quadtrees, database-tables, whatever we are storing our objects in).
 * This can employ a preselection, e.g. omiting lists of uninteresting objects
 * (collecting collisions for {@link Projectile} will probably not even consider
 * the list of other {@link Projectile}s for collisions).</li>
 * <li>the collected interesting objects are passed to the interchangeable
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
 */
public abstract class CollisionCollector<E extends Positionable> {
	private final ArrayList<CollisionVerifier> _verifiers;

	/**
	 * Constructor
	 */
	public CollisionCollector() {
		_verifiers = new ArrayList<CollisionVerifier>();
	}

	/**
	 * Protected, as only subclasses should add additional verifiers from within
	 * themselves. No collector should be contructed and altered afterwards. To
	 * employ additional validators, you have to derive this class and do it in
	 * the constructor.
	 * 
	 * @param verifier
	 *            the new verifier. Will only be added if not null
	 */
	public void addValidator(final CollisionVerifier verifier) {
		if (verifier != null) {
			_verifiers.add(verifier);
		}
	}

	/**
	 * @return the {@link CollisionVerifier}s, currently used to check for
	 *         collisions
	 */
	public ArrayList<CollisionVerifier> getValidator() {
		return _verifiers;
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
	public boolean collides(final Positionable me, final Positionable other) {
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
	public abstract Collection<E> collectCollisions(Positionable me);

}
