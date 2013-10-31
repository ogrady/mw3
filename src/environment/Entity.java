package environment;

import org.newdawn.slick.geom.Vector2f;

public abstract class Entity extends Movable {
	protected String description;

	public Entity(Vector2f _position, float _width, float _height, float _speed, String _description) {
		super(_position, _width, _height, _speed);
		description = _description != null ? _description : getClass().getSimpleName();
	}
}
