package environment;

import org.newdawn.slick.geom.Vector2f;

/**
 * Entities are movable objects that can have an additional descriton string.<br>
 * As a fallback, the simple class name is used as description if null is passed
 * 
 * @author Daniel
 * 
 */
public abstract class Entity extends Movable {
	protected String _description;

	public Entity(final Vector2f position, final float width,
			final float height, final float speed, final String description) {
		super(position, width, height, speed);
		_description = description != null ? description : getClass()
				.getSimpleName();
	}
}
