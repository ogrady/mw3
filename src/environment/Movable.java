package environment;

import java.util.ArrayList;

import level.Block;

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
		if (!getCollisions().isEmpty()) {
			System.out.println(getCollisions().size());
			position.x = oldX;
			position.y = oldY;
			moved = false;
		}
		return moved;
	}

	/**
	 * Checks the whole list of {@link Positionable}s for collisions with the
	 * object
	 * 
	 * @return a list of {@link Positionable}s we collide with
	 */
	protected ArrayList<Positionable> getCollisions() {
		final ArrayList<Positionable> collisions = new ArrayList<Positionable>();
		for (final Positionable p : Positionable.instances) {
			if (p != this && collides(p)) {
				collisions.add(p);
			}
		}
		return collisions;
	}

	/**
	 * Checks whether we collide with another {@link Positionable}. That happens
	 * whenever their hitboxes intersect
	 * 
	 * @param _other
	 *            other {@link Positionable} to check against
	 * @return true, if the hitboxes of the two objects intersect
	 */
	protected boolean collides(final Positionable _other) {
		// TODO currently only checking for blocked Blocks. this works fine for
		// movement but not suited for other things like bullet-mech collision
		return _other instanceof Block && ((Block) _other).isSolid()
				&& getHitbox().intersects(_other.getHitbox());
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
