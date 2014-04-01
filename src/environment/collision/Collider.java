package environment.collision;

import java.util.Collection;

import level.Block;
import environment.IDamageSource;
import environment.Positionable;
import environment.collider.collector.CollisionCollector;
import environment.collider.collector.DefaultCollector;
import environment.collider.handler.CollisionHandler;
import environment.collider.handler.DefaultHandler;
import environment.collision.validator.CollisionVerifier;

/**
 * Colliders are composite-objects, consisting of:
 * <ul>
 * <li><strong>a {@link Positionable}:</strong> the objects, this
 * {@link Collider} takes care of ("our" entity)</li>
 * <li><strong>a {@link CollisionCollector}</strong> which collects all
 * collisions that occur at a given time with our entity. This is done by
 * employing different {@link CollisionVerifier}s within the collector to
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
 * collector</li>
 * </ul>
 * 
 * @author Daniel
 * 
 */
public class Collider<P extends Positionable> implements ICollider {
	protected P _collidable;
	protected CollisionCollector<Positionable> _collector;
	protected CollisionHandler<Positionable, Positionable> _handler;

	/**
	 * Constructor empoys {@link DefaultCollector} and {@link DefaultHandler}.
	 * Can be replaced in constructors of subclasses.
	 * 
	 * @param positionable
	 *            our entity
	 */
	public Collider(final P positionable,
			final CollisionCollector<Positionable> collector,
			final CollisionHandler<Positionable, Positionable> handler) {
		_collidable = positionable;
		_collector = collector;
		_handler = handler;
	}

	@Override
	public void handleCollisions() {
		_handler.handle(_collidable, _collector.collectCollisions(_collidable));
	}

	@Override
	public Collection<Positionable> getCollisions() {
		return _collector.collectCollisions(_collidable);
	}

	@Override
	public ICollidable getCollidable() {
		return _collidable;
	}

	@Override
	public boolean collides(final Positionable other) {
		return _collector.collides(_collidable, other);
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
