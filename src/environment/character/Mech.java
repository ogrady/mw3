package environment.character;

import org.newdawn.slick.geom.Vector2f;

import environment.Actor;

public abstract class Mech extends Actor {
	public Mech(Vector2f position, float width, float height, float speed, String description) {
		super(position, width, height, speed, description);
	}
}
