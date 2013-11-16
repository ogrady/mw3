package environment;

import org.newdawn.slick.geom.Vector2f;

public class Player extends Entity {

	public Player(Vector2f position, float width, float height, float speed, String description) {
		super(position, width, height, speed, description);
	}

	@Override
	public void onCollide(Positionable collider) {

	}

}
