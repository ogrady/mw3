package renderer.slick.projectile;

import renderer.slick.ObservableAnimation;
import renderer.slick.PositionableRenderer;
import environment.Movable;

public class BulletRenderer extends PositionableRenderer<Movable> {
	private final ObservableAnimation bullet;

	public BulletRenderer(final Movable renderable) {
		super(renderable);
		bullet = new ObservableAnimation(loadScaledSpriteSheet(
				"rsc/nitro/bullet.png", 5, 5, 1), 1000);
		setCurrentAnimation(bullet);
	}

}
