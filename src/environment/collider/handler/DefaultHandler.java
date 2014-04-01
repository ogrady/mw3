package environment.collider.handler;

import java.util.Collection;

import environment.Positionable;

/**
 * Just discards the passed list and does nothing
 * 
 * @author Daniel
 * 
 */
public class DefaultHandler extends
		CollisionHandler<Positionable, Positionable> {

	@Override
	public void handle(final Positionable me,
			final Collection<Positionable> collisions) {

	}
}
