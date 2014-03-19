package environment;

import game.Configuration;
import game.MetalWarriors;
import game.Viewport;

import java.util.ArrayList;
import java.util.List;

import listener.IGameListener;
import logger.LogMessageType;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import util.ListenableEnumBitmask;
import controller.IControllable;
import controller.IController;

/**
 * Object that may move over the playing field. It has a certain speed and can
 * be affected by gravity. Typical examples are parts of the map that move but
 * do not interact in any other way (like platforms)
 * 
 * @author Daniel
 * 
 */
public abstract class Movable extends Positionable implements IMassObject,
		IControllable, IGameListener {
	public static final ArrayList<Movable> instances = new ArrayList<Movable>();
	protected float _xspeed;
	protected float _yspeed;
	protected IController _controller;
	protected ListenableEnumBitmask<MovableState> _state;

	/**
	 * @return a bitmask where the states of the object can be get or set. This
	 *         map can be probed via {@link ListenableEnumBitmask#has(Enum)}
	 */
	public ListenableEnumBitmask<MovableState> getState() {
		return _state;
	}

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
		_yspeed = speed;
		_state = new ListenableEnumBitmask<MovableState>();
		Movable.instances.add(this);
		MetalWarriors.instance.getListeners().registerListener(this);
	}

	@Override
	public void destruct() {
		super.destruct();
		MetalWarriors.instance.getListeners().unregisterListener(this);
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
		boolean rightCollision = false;
		boolean leftCollision = false;
		boolean upCollision = false;
		boolean downCollision = false;
		final float oldPositionX = _currentPosition.x;

		_currentPosition.x += moveFactorX * _xspeed;

		final List<Positionable> xCollisions = _collider.getCollisions();

		// Move right.
		if (moveFactorX > 0) {
			for (final Positionable p : xCollisions) {
				rightCollision = true;
				p.getCollider().onPositionableCollide(this);
				final float leftEdgeColider = p.getHitbox().getX();
				if (_currentPosition.x + _width > leftEdgeColider) {
					_currentPosition.x = leftEdgeColider - _width;
				}
			}
		}

		// Move left.
		if (moveFactorX < 0) {
			for (final Positionable p : xCollisions) {
				leftCollision = true;
				p.getCollider().onPositionableCollide(this);
				final float rightEdgeColider = p.getHitbox().getX()
						+ p.getHitbox().getWidth();
				if (_currentPosition.x <= rightEdgeColider) {
					_currentPosition.x = rightEdgeColider + 1;
				}
			}
		}

		final float newPositionX = _currentPosition.x;
		_currentPosition.x = oldPositionX;
		_currentPosition.y += moveFactorY * _yspeed;

		final List<Positionable> yCollisions = _collider.getCollisions();

		// Moved up.
		if (moveFactorY < 0) {
			for (final Positionable p : yCollisions) {
				upCollision = true;
				p.getCollider().onPositionableCollide(this);
				final float lowerEdgeColider = p.getHitbox().getY()
						+ p.getHitbox().getHeight();
				if (_currentPosition.y <= lowerEdgeColider) {
					_currentPosition.y = lowerEdgeColider + 1;
				}
			}
		}

		// Moved down.
		if (moveFactorY > 0) {
			for (final Positionable p : yCollisions) {
				downCollision = true;
				p.getCollider().onPositionableCollide(this);
				final float upperEdgeColider = p.getHitbox().getY();
				if (_currentPosition.y + _height > upperEdgeColider) {
					_currentPosition.y = upperEdgeColider - _height;
				}
			}
		}

		_currentPosition.x = newPositionX;

		if (leftCollision || rightCollision || upCollision || downCollision) {
			MetalWarriors.logger.print("MoveFactorX: " + moveFactorX
					+ " MoveFactorY: " + moveFactorY
					+ (leftCollision ? " Left" : "")
					+ (rightCollision ? " Right" : "")
					+ (upCollision ? " Up" : "")
					+ (downCollision ? " Down" : "") + " Collision happened!",
					LogMessageType.PHYSICS_DEBUG);
		}
		return true;
	}

	@Override
	public void onLoadConfig(final Configuration conf) {
	}

	@Override
	public void onTick(final Input input, final int delta) {
		_controller.update(input, delta);
		if (_renderer != null) {
			_renderer.getCurrentAnimation().update(delta);
		}
	}

	@Override
	public void onRender(final Graphics g, final Viewport vp) {
		_renderer.render(g, vp);
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
