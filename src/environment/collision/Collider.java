package environment.collision;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import level.Block;
import environment.IDamageSource;
import environment.Positionable;
import environment.collider.collector.CollisionCollector;
import environment.collider.handler.CollisionHandler;
import environment.collider.handler.DefaultHandler;
import environment.collision.validator.CollisionVerifier;

/**
 * Colliders are composite-objects, consisting of:
 * <ul>
 * <li><strong>a {@link Positionable}:</strong> the objects, this
 * {@link Collider} takes care of ("our" entity)</li>
 * <li><strong>a {@link CollisionCollector}s</strong> which collect all
 * collisions that occur at a given time with our entity. This is done by
 * employing different {@link CollisionVerifier}s within the collectors to
 * dynamically add or remove criterions for collisions (see there for further
 * documentation)</li>
 * <li><strong>a {@link CollisionHandler}</strong> that handles the collisions
 * the collector yielded
 * </ul>
 * So a collider can:
 * <ul>
 * <li>yield a list of entities, our entitiy currently collides with (for
 * movement-checks e.g.)</li>
 * <li>handle the collisions (aka notifying the objects we collided and so on)</li>
 * <li>handle notifications of other objects our entity collided with (see
 * former bullet point)</li>
 * <li>be dynamically changable by replacing the handler, or replacing the
 * collectors</li>
 * </ul>
 * 
 * @author Daniel
 * 
 */
public class Collider<P extends Positionable> implements ICollider {
	protected P _collidable;
	protected List<CollisionCollector<? extends Positionable>> _collectors;
	protected CollisionHandler<Positionable, Positionable> _handler;

	/**
	 * Constructor
	 * 
	 * @param positionable
	 *            our entity
	 */
	public Collider(final P positionable) {
		_collectors = new ArrayList<CollisionCollector<? extends Positionable>>();
		_collidable = positionable;
		_handler = new DefaultHandler();
	}

	/**
	 * Adds another collector for gathering the collisions
	 * 
	 * @param collector
	 *            the new {@link CollisionCollector}
	 */
	protected void addCollector(
			final CollisionCollector<? extends Positionable> collector) {
		_collectors.add(collector);
	}

	@Override
	public void handleCollisions() {
		_handler.handle(_collidable, getCollisions());
	}

	@Override
	public Collection<Positionable> getCollisions() {
		final HashSet<Positionable> collisions = new HashSet<Positionable>();
		for (final CollisionCollector<? extends Positionable> collector : _collectors) {
			collector.collectionCollisions(_collidable, collisions);
		}
		return collisions;
	}

	@Override
	public ICollidable getCollidable() {
		return _collidable;
	}

	@Override
	public void onPositionableCollide(final Positionable positionable) {
	}

	@Override
	public void onDamageSourceCollide(final IDamageSource dmgsrc) {
	}

	@Override
	public void onBlockCollide(final Block block) {

	}
}
