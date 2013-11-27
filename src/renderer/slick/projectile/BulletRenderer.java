package renderer.slick.projectile;

import renderer.slick.ObservableAnimation;
import renderer.slick.PositionableRenderer;
import environment.Positionable;

public class BulletRenderer extends PositionableRenderer {
	private final ObservableAnimation bullet;

	public BulletRenderer(final Positionable renderable) {
		super(renderable);
		bullet = new ObservableAnimation(loadScaledSpriteSheet("rsc/nitro/bullet.png", 5,
				5, 1), 1000);
		setCurrentAnimation(bullet);
	}

}
