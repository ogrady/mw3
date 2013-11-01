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
	protected float xspeed;
	protected float yspeed;
	protected IController controller;

	/**
	 * @return speed at which the object moves horizontally (walking)
	 */
	public float getXSpeed() {
		return xspeed;
	}

	/**
	 * @return speed at which the object moves vertically (jumping, gravity)
	 */
	public float getYSpeed() {
		return yspeed;
	}

	public Movable(final Vector2f _position, final float _width,
			final float _height, final float _speed) {
		super(_position, _width, _height);
		xspeed = _speed;
		Movable.instances.add(this);
	}

	@Override
	public void destruct() {
		super.destruct();
		Movable.instances.remove(this);
	}

	@Override
	public void applyGravity(final float _g) {
		// yspeed += _g;
		move(0, 1);
	}

	/**
	 * Moves into a direction by a certain magnitude.<br>
	 * The final movement is determined by the parameters times the speed in the
	 * direction.<br>
	 * Fails when collisions occur.
	 * 
	 * @param x
	 *            times the x-speed
	 * @param y
	 *            times the y-speed
	 * @return whether or not the movement was successful (true, if no
	 *         collisions occurred)
	 */
	public boolean move(final float x, final float y) {
		boolean moved = true;
		final float oldX = position.x;
		final float oldY = position.y;
		position.x += x * xspeed;
		position.y += y * xspeed; // TODO yspeed
		final ArrayList<Positionable> collisions = getCollisions();
		if (!collisions.isEmpty()) {
			moved = false;
			if (oldX != position.x) {

			}
			if (oldY != position.y) {
				final Positionable first = collisions.get(0);
				// movement from top
				if (oldY < first.getPosition().y) {
					position.y = first.getPosition().y - height;
				}
				// movement from below
				else if (oldY > first.getPosition().y) {
					position.y = first.getPosition().y + first.getHeight();
				}
			}
			/*position.x = oldX;
			position.y = oldY;
			moved = false;*/
		}
		return moved;
	}

	@Override
	public void setController(final IController _controller) {
		controller = _controller;
	}

	@Override
	public IController getController() {
		return controller;
	}
}
