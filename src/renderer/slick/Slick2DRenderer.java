package renderer.slick;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

import renderer.IRenderer;

/**
 * Renderer for slick games on a 2D surface {@link Graphics}.<br>
 * Used as abstraction layer to be able to replace the presentation.
 * 
 * @author Daniel
 * 
 */
abstract public class Slick2DRenderer implements IRenderer {
	protected Animation _current;

	/**
	 * @return the currently looped animation. Whenever the renderer receives an
	 *         update it will loop this animation a bit further
	 */
	public Animation getCurrentAnimation() {
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
	protected void setCurrentAnimation(final Animation newCurrent) {
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

}
