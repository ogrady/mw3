package renderer.slick;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import environment.Positionable;
import game.Viewport;

/**
 * Renderers for positionable objects that can not move should extend this
 * class.
 * 
 * @author Daniel
 * 
 */
abstract public class PositionableRenderer<P extends Positionable> extends
		Slick2DRenderer {
	protected P _renderable;
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
	public PositionableRenderer(final P renderable) {
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
}
