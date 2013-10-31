package environment;

import org.newdawn.slick.geom.Vector2f;

public class Player extends Entity {

	public Player(Vector2f _position, float _width, float _height, float _speed, String _description) {
		super(_position, _width, _height, _speed, _description);
	}

	@Override
	public void onCollide(Positionable _collider) {

	}

}
