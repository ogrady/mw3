package environment.character;

import org.newdawn.slick.geom.Vector2f;

import environment.Entity;

public abstract class Mech extends Entity {
	public Mech(Vector2f _position, float _width, float _height, float _speed, String _description) {
		super(_position, _width, _height, _speed, _description);
	}
}
