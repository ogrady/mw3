package renderer.slick.mech;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import renderer.slick.PositionableRenderer;
import environment.Positionable;
import game.Viewport;

public class NitroRenderer extends PositionableRenderer {
	Animation walkingRight, walkingLeft;

	public NitroRenderer(final Positionable _pos) {
		super(_pos);
		SpriteSheet sprite;
		try {
			sprite = new SpriteSheet(new SpriteSheet("rsc/nitro/walking.png",
					43, 63, 0).getScaledCopy(2), 86, 126);
			walkingRight = new Animation(sprite, 100);
			walkingLeft = new Animation(new SpriteSheet(sprite.getFlippedCopy(
					true, false), 86, 123), 100);
			setCurrentAnimation(walkingRight);
		} catch (final SlickException e) {
			e.printStackTrace();
		}
	}

	public void turnLeft() {
		setCurrentAnimation(walkingLeft);
	}

	public void turnRight() {
		setCurrentAnimation(walkingRight);
	}

	@Override
	public void render(final Graphics _g, final Viewport _vp) {
		// current.draw(_vp.getWidth() / 2 - renderable.getHitbox().getWidth() /
		// 2, _vp.getHeight() / 2 - renderable.getHitbox().getHeight() / 2);
		// _g.drawAnimation(current, _vp.getWidth() / 2 -
		// renderable.getHitbox().getWidth() / 2, _vp.getHeight() / 2 -
		// renderable.getHitbox().getHeight() / 2);
		_g.drawAnimation(current, renderable.getPosition().x,
				renderable.getPosition().y);
	}
}
