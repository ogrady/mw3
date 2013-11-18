package environment.projectile;

import org.newdawn.slick.geom.Vector2f;

import environment.Entity;

public class Bullet extends Entity {

	public Bullet(final Vector2f position) {
		super(position, 0, 0, 3, null);
	}

}
