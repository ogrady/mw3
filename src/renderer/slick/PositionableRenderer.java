package renderer.slick;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

import environment.Positionable;
import game.Viewport;

public class PositionableRenderer extends Slick2DRenderer {
	protected Positionable renderable;

	public PositionableRenderer(final Positionable _renderable) {
		renderable = _renderable;
	}

	@Override
	public void render(final Graphics _g, final Viewport _vp) {
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
}
