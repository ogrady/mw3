package environment;

import org.newdawn.slick.geom.Vector2f;

public class Player extends Actor {

	public Player(final Vector2f position, final float width,
			final float height, final float weight, final float speed, final String description) {
		super(position, width, height, speed, weight, description);
	}
}
