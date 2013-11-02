package renderer.slick;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import environment.Positionable;
import game.Viewport;

public class PositionableRenderer extends Slick2DRenderer {
	protected Positionable renderable;
	protected int direction;

	public PositionableRenderer(final Positionable _renderable) {
		renderable = _renderable;
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

	/**
	 * Sets the current animation and adjusts the size of the hitbox to the size
	 * of the passed animation.<br>
	 * It also restarts the former animation so it can restart when being used
	 * again. Thus the animation should only contain frames of the same size
	 * 
	 * @param _current
	 *            new current animation
	 */
	protected void setCurrentAnimation(final Animation _current) {
		final Animation old = current;
		current = _current;
		if (current != old && old != null) {
			old.restart();
		}
		renderable.setWidth(current.getWidth());
		renderable.setHeight(current.getHeight());
	}

	final protected static SpriteSheet loadScaledSpriteSheet(
			final String _path, final int _frameWidth, final int _frameHeight,
			final float _factor) {
		SpriteSheet sheet = null;
		try {
			sheet = new SpriteSheet(new SpriteSheet(_path, _frameWidth,
					_frameHeight, 0).getScaledCopy(_factor),
					(int) (_factor * _frameWidth),
					(int) (_factor * _frameHeight));
		} catch (final SlickException e) {
			e.printStackTrace();
		}
		return sheet;
	}
}
