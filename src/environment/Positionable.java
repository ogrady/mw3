package environment;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import renderer.NullRenderer;
import renderer.IRendereable;
import renderer.slick.Slick2DRenderer;
import environment.collision.DefaultCollider;
import environment.collision.ICollidable;
import environment.collision.ICollider;

/**
 * Object that can be positioned somewhere on the playing field and has a
 * certain width and height for collisions which will be determined by the
 * renderers current frame. Every positionable can collide with others and
 * therefore has a {@link DefaultCollider} upon creation which can be exchanged
 * for more fitting colliders in subclasses. Typial instances are blocks for the
 * map which are placed once but do not move afterwards.
 *
 * @author Daniel
 */
public abstract class Positionable implements IBounding,
		IRendereable<Slick2DRenderer>, ICollidable {
	public static final ArrayList<Positionable> instances = new ArrayList<Positionable>();

	protected Vector2f _currentPosition;
	protected float _width, _height;
	protected Slick2DRenderer _renderer;
	protected ICollider _collider;
	protected int _direction;

	/**
	 * @return direction the {@link Positionable} is currently facing to. -1 is
	 *         left, 1 is right
	 */
	public int getDirection() {
		return _direction;
	}

	/**
	 * @param direction
	 *            the new direction the {@link Positionable} is facing. 0 will
	 *            be ignored, positive values will be defaulted to 1, negative
	 *            values to -1
	 */
	public void setDirection(final int direction) {
		if (direction != 0) {
			_direction = direction / Math.abs(direction);
		}
	}

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
		return _currentPosition;
	}

	/**
	 * @return center point of the entity. That's the middle of the current
	 *         frame of the current animation.
	 */
	public Vector2f getCenter() {
		return new Vector2f(_currentPosition.x + getWidth() / 2,
				_currentPosition.y + getHeight() / 2);
	}

	/**
	 * @return hitbox for collisions. Per default, a bounding rectangle is
	 *         created the spans over the {@link Positionable}, using its
	 *         position and size.
	 */
	@Override
	public Hitbox getHitbox() {
		return new Hitbox(new Rectangle(getPosition().x, getPosition().y, getWidth() - 1,
				getHeight() - 1));
	}

	@Override
	public ICollider getCollider() {
		return _collider;
	}

	@Override
	public void setCollider(final ICollider newCollider) {
		_collider = newCollider;
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
		_currentPosition = position;
		_width = width;
		_height = height;
		_direction = 1;
		// just to make sure we don't have null pointers for colliders and
		// renderers
		_collider = new DefaultCollider<Positionable>(this);
		_renderer = new NullRenderer();
		Positionable.instances.add(this);
	}

	public void destruct() {
		Positionable.instances.remove(this);
	}

	@Override
	public String toString() {
		return String.format("%s [(%.1f|%.1f), (%.1f|%.1f)]", getClass()
				.getSimpleName(), getPosition().x, getPosition().y,
				getPosition().x + getWidth(), getPosition().y + getHeight());
	}
}
