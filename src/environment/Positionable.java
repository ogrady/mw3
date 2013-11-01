package environment;

import java.util.ArrayList;

import level.Block;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import renderer.IRendereable;
import renderer.slick.Slick2DRenderer;

/**
 * Object that can be positioned somewhere on the playing field and has a
 * certain width and height for collisions
 * 
 * @author Daniel
 */
public abstract class Positionable implements IRendereable<Slick2DRenderer> {
	public static final ArrayList<Positionable> instances = new ArrayList<Positionable>();

	protected Vector2f position;
	protected float width, height;
	protected Slick2DRenderer renderer;

	/**
	 * @param _width
	 *            new width of the object
	 */
	public void setWidth(final int _width) {
		width = _width;
	}

	/**
	 * @param _height
	 *            new height of the object
	 */
	public void setHeight(final int _height) {
		height = _height;
	}

	/**
	 * @return the current width of the object
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @return the current height of the object
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @return current position of the object. That is the upper left corner of
	 *         the object!
	 */
	public Vector2f getPosition() {
		return position;
	}

	/**
	 * @return hitbox for collisions
	 */
	public Rectangle getHitbox() {
		return new Rectangle(position.x, position.y, width, height);
	}

	@Override
	public Slick2DRenderer getRenderer() {
		return renderer;
	}

	@Override
	public void setRenderer(final Slick2DRenderer _renderer) {
		renderer = _renderer;
	}

	/**
	 * Constructor
	 * 
	 * @param _position
	 *            initial position
	 * @param _width
	 *            width of the hitbox
	 * @param _height
	 *            height of the hitbox
	 */
	public Positionable(final Vector2f _position, final float _width,
			final float _height) {
		position = _position;
		width = _width;
		height = _height;
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

	abstract public void onCollide(Positionable _collider);
}
