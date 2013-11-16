package environment;

import java.util.ArrayList;

import level.Block;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import renderer.IRendereable;
import renderer.slick.Slick2DRenderer;

/**
 * Object that can be positioned somewhere on the playing field and has a
 * certain width and height for collisions which will be determined by the
 * renderers current frame
 * 
 * @author Daniel
 */
public abstract class Positionable implements IRendereable<Slick2DRenderer> {
	public static final ArrayList<Positionable> instances = new ArrayList<Positionable>();

	protected Vector2f _position;
	protected float _width, _height;
	protected Slick2DRenderer _renderer;

	/**
	 * @param _width
	 *            new width of the object
	 */
	public void setWidth(final int width) {
		_width = width;
	}

	/**
	 * @param _height
	 *            new height of the object
	 */
	public void setHeight(final int height) {
		_height = height;
	}

	/**
	 * @return the current width of the object
	 */
	public float getWidth() {
		return _width;
	}

	/**
	 * @return the current height of the object
	 */
	public float getHeight() {
		return _height;
	}

	/**
	 * @return current position of the object. That is the upper left corner of
	 *         the object!
	 */
	public Vector2f getPosition() {
		return _position;
	}

	/**
	 * @return hitbox for collisions
	 */
	public Shape getHitbox() {
		return new Rectangle(_position.x, _position.y, _width, _height);
	}

	@Override
	public Slick2DRenderer getRenderer() {
		return _renderer;
	}

	@Override
	public void setRenderer(final Slick2DRenderer renderer) {
		_renderer = renderer;
	}

	/**
	 * Constructor
	 * 
	 * @param position
	 *            initial position
	 * @param width
	 *            width of the hitbox
	 * @param height
	 *            height of the hitbox
	 */
	public Positionable(final Vector2f position, final float width,
			final float height) {
		_position = position;
		_width = width;
		_height = height;
		Positionable.instances.add(this);
		// hitbox = new Re
		// new Rectangle(_position.x, _position.y, _width, _height);
	}

	public void destruct() {
		Positionable.instances.remove(this);
	}

	/**
	 * Checks the whole list of {@link Positionable}s for collisions with the
	 * object
	 * 
	 * @return a list of {@link Positionable}s we collide with
	 */
	public ArrayList<Positionable> getCollisions() {
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
	 * @param other
	 *            other {@link Positionable} to check against
	 * @return true, if the hitboxes of the two objects intersect
	 */
	protected boolean collides(final Positionable other) {
		// TODO currently only checking for blocked Blocks. this works fine for
		// movement but not suited for other things like bullet-mech collision
		return other instanceof Block && ((Block) other).isSolid()
				&& getHitbox().intersects(other.getHitbox());
	}

	abstract public void onCollide(Positionable collider);
}
