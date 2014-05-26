package environment;

import game.MetalWarriors;
import game.Viewport;

import java.util.ArrayList;
import java.util.Collection;

import listener.IPlayingStateListener;
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
IControllable, IPlayingStateListener {
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
		MetalWarriors.instance.getPlayingState().getListeners()
				.registerListener(this);
	}

	@Override
	public void destruct() {
		super.destruct();
		MetalWarriors.instance.getPlayingState().getListeners()
				.unregisterListener(this);
		Movable.instances.remove(this);
	}

	@Override
	public void applyGravity(final float g) {
		// yspeed += _g;
		move(0, 1, true);
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
	public boolean move(final float moveFactorX, final float moveFactorY) {
		return this.move(moveFactorX, moveFactorY, false);
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
	 * @param ignoreBlocking
	 *            if true, the {@link Movable} will move, even if it is in
	 *            blocking-state. This will be done for gravity-related movement
	 *            within {@link #applyGravity(float)} and can not be called from
	 *            outside the class
	 * @return returns true, if a movement (change of placement) occurred.
	 */
	private boolean move(final float moveFactorX, final float moveFactorY,
			final boolean ignoreBlocking) {
		boolean rightCollision = false;
		boolean leftCollision = false;
		boolean upCollision = false;
		boolean downCollision = false;
		final float oldPositionX = _currentPosition.x;
		final float oldPositionY = _currentPosition.y;

		if (!ignoreBlocking && _state.has(MovableState.BLOCKING)
				|| moveFactorX == 0 && moveFactorY == 0) {
			return false;
		}

		setDirection((int) moveFactorX);

		_currentPosition.x += moveFactorX * _xspeed;

		final Collection<Positionable> xCollisions = _collider.getCollisions();

		// Move right.
		if (moveFactorX > 0) {
			for (final Positionable p : xCollisions) {
				rightCollision = true;
				p.getCollider().onPositionableCollide(this);
				final float leftEdgeColider = p.getHitbox().getX();
				// If the right edge of the object is inside the colliding
				// object, move it back to the left.
				if (_currentPosition.x + _width > leftEdgeColider) {
					_currentPosition.x = leftEdgeColider - _width;
				}
			}

			if (_currentPosition.x != oldPositionX) {
				_state.set(MovableState.MOVING);
			}
		}

		// Move left.
		if (moveFactorX < 0) {
			for (final Positionable p : xCollisions) {
				leftCollision = true;
				p.getCollider().onPositionableCollide(this);
				final float rightEdgeColider = p.getHitbox().getX()
						+ p.getHitbox().getWidth();
				// If the left edge of the object is inside the colliding
				// object, move it back to the right.
				if (_currentPosition.x <= rightEdgeColider) {
					_currentPosition.x = rightEdgeColider + 1;
				}
			}

			if (_currentPosition.x != oldPositionX) {
				_state.set(MovableState.MOVING);
			}
		}

		final float newPositionX = _currentPosition.x;
		_currentPosition.x = oldPositionX;
		_currentPosition.y += moveFactorY * _yspeed;

		final Collection<Positionable> yCollisions = _collider.getCollisions();

		// Moved up.
		if (moveFactorY < 0) {
			for (final Positionable p : yCollisions) {
				upCollision = true;
				p.getCollider().onPositionableCollide(this);
				final float lowerEdgeColider = p.getHitbox().getY()
						+ p.getHitbox().getHeight();
				// If the upper edge of the object is inside the colliding
				// object, move it back down.
				if (_currentPosition.y <= lowerEdgeColider) {
					_currentPosition.y = lowerEdgeColider + 1;
				}
			}

			if (_currentPosition.y != oldPositionY) {
				_state.set(MovableState.FLYING);
				_state.add(MovableState.MOVING);
			}
		}

		// Moved down.
		if (moveFactorY > 0) {
			for (final Positionable p : yCollisions) {
				downCollision = true;
				p.getCollider().onPositionableCollide(this);
				final float upperEdgeColider = p.getHitbox().getY();
				// If the lower edge of the object is inside the colliding
				// object, move it back up.
				if (_currentPosition.y + _height > upperEdgeColider) {
					_currentPosition.y = upperEdgeColider - _height;
				}
			}

			if (_currentPosition.y != oldPositionY) {
				_state.set(MovableState.FALLING);
				_state.add(MovableState.MOVING);
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

		final boolean isMoving = _currentPosition.x != oldPositionX
				|| _currentPosition.y != oldPositionY;

		if (!isMoving) {
			_state.set(MovableState.STANDING);
		}

		MetalWarriors.logger.print(_state.toString(),
				LogMessageType.INPUT_DEBUG);
		return isMoving;
	}

	@Override
	public void onLoadMap(final level.World map) {
	}

	@Override
	public void onTick(final Input input, final int delta) {
		_controller.update(input, delta);
		if (_renderer != null) {
			_renderer.update(delta);
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
