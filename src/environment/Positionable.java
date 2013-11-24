package environment;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import renderer.DefaultRenderer;
import renderer.IRendereable;
import renderer.slick.Slick2DRenderer;
import environment.collider.DefaultCollider;
import environment.collider.ICollidable;
import environment.collider.ICollider;

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
public abstract class Positionable implements IRendereable<Slick2DRenderer>,
		ICollidable {
	public static final ArrayList<Positionable> instances = new ArrayList<Positionable>();

	protected Vector2f _currentPosition;
	protected float _width, _height;
	protected Slick2DRenderer _renderer;
	protected ICollider _collider;

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
	 * @return hitbox for collisions
	 */
	public Shape getHitbox() {
		return new Rectangle(_currentPosition.x, _currentPosition.y, _width,
				_height);
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
		_collider = new DefaultCollider(this);
		_renderer = new DefaultRenderer();
		Positionable.instances.add(this);
	}

	public void destruct() {
		Positionable.instances.remove(this);
	}

	@Override
	public ICollider getCollider() {
		return _collider;
	}

	@Override
	public void setCollider(final ICollider newCollider) {
		_collider = newCollider;
	}
}
