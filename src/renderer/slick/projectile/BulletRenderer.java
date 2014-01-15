package renderer.slick.projectile;

import renderer.slick.ObservableAnimation;
import renderer.slick.PositionableRenderer;
import environment.Movable;

public class BulletRenderer extends PositionableRenderer<Movable> {
	private static final ObservableAnimation BULLET = new ObservableAnimation(
			loadScaledSpriteSheet("rsc/nitro/bullet.png", 5, 5, 1.5f), 1000);

	public BulletRenderer(final Movable renderable) {
		super(renderable);
		setCurrentAnimation(BULLET);
	}
}
