package environment.collision;

import environment.Positionable;
import environment.collider.collector.DefaultCollector;
import environment.collider.handler.DefaultHandler;

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
		super(positionable, new DefaultCollector(), new DefaultHandler());
	}
}
