package renderer.slick.mech;

import org.newdawn.slick.Animation;

import renderer.slick.PositionableRenderer;
import environment.Positionable;

public class NitroRenderer extends PositionableRenderer {
	private final Animation walking, jumping;

	public NitroRenderer(final Positionable _pos) {
		super(_pos);
		final float factor = 2;
		walking = new Animation(loadScaledSpriteSheet("rsc/nitro/walking.png",
				43, 48, factor), 100);
		jumping = new Animation(loadScaledSpriteSheet("rsc/nitro/flying.png",
				56, 47, factor), 100);
		setCurrentAnimation(walking);
	}

	public void turnLeft() {
		direction = -1;
	}

	public void turnRight() {
		direction = 1;
	}

	public void jump() {
		setCurrentAnimation(jumping);
	}
}
