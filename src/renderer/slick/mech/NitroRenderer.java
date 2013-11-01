package renderer.slick.mech;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import renderer.slick.PositionableRenderer;
import environment.Positionable;
import game.Viewport;

public class NitroRenderer extends PositionableRenderer {
	Animation walkingRight, walkingLeft, walking;
	int direction;

	public NitroRenderer(final Positionable _pos) {
		super(_pos);
		direction = 1;
		SpriteSheet sprite;
		try {
			sprite = new SpriteSheet(new SpriteSheet("rsc/nitro/walking.png",
					43, 48, 0).getScaledCopy(2), 86, 84);
			walking = new Animation(sprite, 100);
			;
			setCurrentAnimation(walking);
		} catch (final SlickException e) {
			e.printStackTrace();
		}
	}

	public void turnLeft() {
		direction = -1;
	}

	public void turnRight() {
		direction = 1;
	}

	@Override
	public void render(final Graphics _g, final Viewport _vp) {
		final Image frame = current.getCurrentFrame();
		frame.draw(renderable.getPosition().x, renderable.getPosition().y,
				(direction - 1) * -frame.getWidth() / 2, 0, (direction + 1)
						* frame.getWidth() / 2, frame.getHeight());
		for (final Positionable p : renderable.getCollisions()) {
			_g.draw(p.getHitbox());
		}
		_g.draw(renderable.getHitbox());
	}
}
