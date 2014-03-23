package renderer.slick;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import renderer.IRenderer;

/**
 * Renderer for slick games on a 2D surface {@link Graphics}.<br>
 * Used as abstraction layer to be able to replace the presentation.
 * 
 * @author Daniel
 * 
 */
abstract public class Slick2DRenderer implements IRenderer {
	protected ObservableAnimation _current;

	/**
	 * @return the currently looped animation. Whenever the renderer receives an
	 *         update it will loop this animation a bit further
	 */
	public ObservableAnimation getCurrentAnimation() {
		return _current;
	}

	/**
	 * Sets the current animation.<br>
	 * It also restarts the former animation so it can restart when being used
	 * again.
	 * 
	 * @param newCurrent
	 *            new current animation
	 */
	protected void setCurrentAnimation(final ObservableAnimation newCurrent) {
		final Animation old = newCurrent;
		_current = newCurrent;
		if (_current != old && old != null) {
			old.restart();
		}
	}

	/**
	 * @return the width of the object determined by the current frame of the
	 *         current animation
	 */
	public int getWidth() {
		return _current.getWidth();
	}

	/**
	 * @return the height of the object determined by the current frame of the
	 *         current animation
	 */
	public int getHeight() {
		return _current.getHeight();
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

	/**
	 * Updates the currently running animation by some milliseconds
	 * 
	 * @param delta
	 *            the milliseconds passed since the last tick
	 */
	public void update(final long delta) {
		getCurrentAnimation().update(delta);
	}

}
