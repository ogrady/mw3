package renderer.slick;

import logger.LogMessageType;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import renderer.IRenderer;
import environment.Positionable;
import game.MetalWarriors;
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
	 * Constructor<br>
	 * constructs a renderer which is looking to the right side
	 *
	 * @param renderable
	 */
	public PositionableRenderer(final P renderable) {
		_renderable = renderable;
	}

	/**
	 * Renders the positionable at its current position. Flips the animation if
	 * {@link Positionable#getDirection()} is inversed (= -1)
	 */
	@Override
	public void render(final Graphics g, final Viewport vp) {
		final Image frame = _current.getCurrentFrame();
		frame.draw(_renderable.getPosition().x, _renderable.getPosition().y,
				(_renderable.getDirection() - 1) * -frame.getWidth() / 2, 0,
				(_renderable.getDirection() + 1) * frame.getWidth() / 2,
				frame.getHeight());
		for (final IRenderer sub : _subrenderers) {
			sub.render(g, vp);
		}
	}

	/**
	 * As in {@link Slick2DRenderer#setCurrentAnimation(Animation)} but also
	 * adjusts the width and height of the rendered object according to the
	 * current frame of the current animation
	 */
	@Override
	public void setCurrentAnimation(final ObservableAnimation newCurrent) {
		super.setCurrentAnimation(newCurrent);
		MetalWarriors.logger.print(
				"setting current animation to " + newCurrent,
				LogMessageType.GFX_DEBUG);
	}
}
