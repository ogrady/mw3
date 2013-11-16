package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

import environment.Positionable;

/**
 * A viewport determines the portion of the playing field that can be seen. It
 * therefore has a position which is its upper left corner while the width and
 * height are determined via the width and height of the gaming window
 * 
 * @author Daniel
 * 
 */
public class Viewport {
	private final Vector2f _position;
	private final GameContainer _container;

	/**
	 * @return upper left corner of the viewport
	 */
	public Vector2f getPosition() {
		return _position;
	}

	/**
	 * @return width of the viewport which is the width of the window
	 */
	public int getWidth() {
		return _container.getWidth();
	}

	/**
	 * @return height of the viewport which is the height of the window
	 */
	public int getHeight() {
		return _container.getHeight();
	}

	/**
	 * Constructor
	 * 
	 * @param x
	 *            initial x-ps
	 * @param y
	 *            initial y-pos
	 * @param container
	 *            container this game runs in to determine height and width
	 */
	public Viewport(final float x, final float y, final GameContainer container) {
		_container = container;
		_position = new Vector2f(x, y);
	}

	/**
	 * Centers the viewport around a positionable (e.g. the player)
	 * 
	 * @param center
	 *            the positionable to focus
	 */
	public void centerAround(final Positionable center) {
		final float x = center.getPosition().x + center.getHitbox().getWidth()
				/ 2 - getWidth() / 2;
		final float y = center.getPosition().y + center.getHitbox().getHeight()
				/ 2 - getHeight() / 2;
		_position.set(-x, -y);
	}
}
