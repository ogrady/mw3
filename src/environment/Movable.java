package environment;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import controller.IControllable;
import controller.IController;

/**
 * Object that may move over the playing field. It has a certain speed and can
 * be affected by gravity.
 * 
 * @author Daniel
 * 
 */
public abstract class Movable extends Positionable implements IMassObject,
		IControllable {
	public static final ArrayList<Movable> instances = new ArrayList<Movable>();
	protected float _xspeed;
	protected float _yspeed;
	protected IController _controller;

	/**
	 * @return speed at which the object moves horizontally (walking)
	 */
	public float getXSpeed() {
		return _xspeed;
	}

	/**
	 * @return speed at which the object moves vertically (jumping, gravity)
	 */
	public float getYSpeed() {
		return _yspeed;
	}

	/**
	 * Constructor
	 * 
	 * @param position
	 *            initial position
	 * @param width
	 *            initial width
	 * @param height
	 *            initial height
	 * @param speed
	 *            horizontal speed
	 */
	public Movable(final Vector2f position, final float width,
			final float height, final float speed) {
		super(position, width, height);
		_xspeed = speed;
		Movable.instances.add(this);
	}

	@Override
	public void destruct() {
		super.destruct();
		Movable.instances.remove(this);
	}

	@Override
	public void applyGravity(final float g) {
		// yspeed += _g;
		move(0, 1);
	}

	/**
	 * Moves into a direction by a certain magnitude.<br>
	 * The final movement is determined by the parameters times the speed in the
	 * direction.<br>
	 * Fails when collisions occur.
	 * 
	 * @param moveFactorX
	 *            times the x-speed
	 * @param moveFactorY
	 *            times the y-speed
	 * @return whether or not the movement was successful (true, if no
	 *         collisions occurred)
	 */
	// TODO abstract und inhalt in unterklasse //
	public boolean move(final float moveFactorX, final float moveFactorY) {
		boolean moved = true;
		final float oldX = _position.x;
		final float oldY = _position.y;
		_position.x += moveFactorX * _xspeed;
		_position.y += moveFactorY * _xspeed; // TODO yspeed
		final ArrayList<Positionable> collisions = getCollisions();
		if (!collisions.isEmpty()) {
			moved = false;
			if (oldX != _position.x) {
				/*final Positionable first = collisions.get(0);
				if (oldX < first.getPosition().x) {
					_position.x = first.getPosition().x - _width;
				} else if (oldX > first.getPosition().x) {
					_position.x = first.getPosition().x + first.getWidth();
				}*/
			}
			if (oldY != _position.y) {
				final Positionable first = collisions.get(0);
				// movement from top
				if (oldY < first.getPosition().y) {
					// - 1 to prevent touching
					_position.y = first.getPosition().y - _height - 1;
				}
				// movement from below
				else if (oldY > first.getPosition().y) {
					_position.y = first.getPosition().y + first.getHeight();
				}
			}
			/*position.x = oldX;
			position.y = oldY;
			moved = false;*/
		}
		return moved;
	}

	@Override
	public void setController(final IController controller) {
		_controller = controller;
	}

	@Override
	public IController getController() {
		return _controller;
	}
}
