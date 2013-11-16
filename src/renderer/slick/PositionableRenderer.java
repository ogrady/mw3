package renderer.slick;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import environment.Positionable;
import game.Viewport;

public class PositionableRenderer extends Slick2DRenderer {
	protected Positionable _renderable;
	/**
	 * 1 means looking right, -1 looking left
	 */
	protected int _direction;

	/**
	 * Constructor<br>
	 * constructs a renderer which is looking to the right side
	 * 
	 * @param renderable
	 */
	public PositionableRenderer(final Positionable renderable) {
		_renderable = renderable;
		_direction = 1;
	}

	/**
	 * Renders the positionable at its current position. Flips the animation if
	 * the direction isinversed (= -1)
	 */
	@Override
	public void render(final Graphics g, final Viewport vp) {
		final Image frame = _current.getCurrentFrame();
		frame.draw(_renderable.getPosition().x, _renderable.getPosition().y,
				(_direction - 1) * -frame.getWidth() / 2, 0, (_direction + 1)
						* frame.getWidth() / 2, frame.getHeight());
		for (final Positionable p : _renderable.getCollider().getCollisions()) {
			g.draw(p.getHitbox());
		}
		g.draw(_renderable.getHitbox());
	}

	/**
	 * As in {@link Slick2DRenderer#setCurrentAnimation(Animation)} but also
	 * adjusts the width and height of the rendered object according to the
	 * current frame of the current animation
	 */
	@Override
	public void setCurrentAnimation(final Animation newCurrent) {
		super.setCurrentAnimation(newCurrent);
		_renderable.setWidth(newCurrent.getWidth());
		_renderable.setHeight(newCurrent.getHeight());
	}

	/**
	 * Loads a {@link SpriteSheet} in scaled form
	 * 
	 * @param path
	 *            path to the spritesheet
	 * @param frameWidth
	 *            width of one frame in the original spritesheet
	 * @param frameHeight
	 *            height of one frame in the original spritesheet
	 * @param factor
	 *            factor by which to scale the frames
	 * @return a scaled sheet or null if something fails
	 */
	final protected static SpriteSheet loadScaledSpriteSheet(final String path,
			final int frameWidth, final int frameHeight, final float factor) {
		SpriteSheet sheet = null;
		try {
			sheet = new SpriteSheet(new SpriteSheet(path, frameWidth,
					frameHeight, 0).getScaledCopy(factor),
					(int) (factor * frameWidth), (int) (factor * frameHeight));
		} catch (final SlickException e) {
			e.printStackTrace();
		}
		return sheet;
	}
}
