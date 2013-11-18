package renderer.slick.projectile;

import org.newdawn.slick.Animation;

import renderer.slick.PositionableRenderer;
import environment.Positionable;

public class BulletRenderer extends PositionableRenderer {
	private final Animation bullet;

	public BulletRenderer(final Positionable renderable) {
		super(renderable);
		bullet = new Animation(loadScaledSpriteSheet("rsc/nitro/bullet.png", 5,
				5, 1), 1000);
		setCurrentAnimation(bullet);
	}

}
