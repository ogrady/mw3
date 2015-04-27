package renderer.slick.projectile;

import renderer.slick.ObservableAnimation;
import renderer.slick.PositionableRenderer;
import environment.Movable;
import util.Const;

public class BulletRenderer extends PositionableRenderer<Movable> {
	private static final ObservableAnimation BULLET = new ObservableAnimation(
			loadScaledSpriteSheet("rsc/nitro/bullet.png", Const.NITRO_SMG_DIAMETER, Const.NITRO_SMG_DIAMETER, 1.5f), 1000);

	public BulletRenderer(final Movable renderable) {
		super(renderable);
		setCurrentAnimation(BULLET);
	}
}
