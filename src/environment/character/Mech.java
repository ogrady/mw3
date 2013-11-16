package environment.character;

import org.newdawn.slick.geom.Vector2f;

import environment.Entity;

public abstract class Mech extends Entity {
	public Mech(Vector2f position, float width, float height, float speed, String description) {
		super(position, width, height, speed, description);
	}
}
