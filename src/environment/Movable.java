package environment;

import game.Configuration;
import game.MetalWarriors;

import java.util.ArrayList;
import java.util.List;

import listener.IGameListener;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import util.EnumBitmask;
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
		IControllable, IGameListener {
	public static final ArrayList<Movable> instances = new ArrayList<Movable>();
	protected float _xspeed;
	protected float _yspeed;
	protected IController _controller;
	protected EnumBitmask<MovableState> _state;

	/**
	 * @return a bitmask where the states of the object can be get or set. This
	 *         map can be probed via {@link EnumBitmask#has(Enum)}
	 */
	public EnumBitmask<MovableState> getState() {
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
		_state = new EnumBitmask<MovableState>();
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
		boolean collision = false;
		final float oldPositionX = _currentPosition.x;
		
		_currentPosition.x += moveFactorX * _xspeed;
		
		final List<Positionable> xCollisions = _collider.getCollisions();
		
		// Move right.
		if(moveFactorX > 0)
		{
			collision = true;
			for(final Positionable p : xCollisions) {
				p.getCollider().onPositionableCollide(this);
				float leftEdgeColider = p.getPosition().getX();
				if(_currentPosition.x + _width > leftEdgeColider - 1) {
					_currentPosition.x = leftEdgeColider - _width - 1;
				}
			}
		}
		
		// Move left.
		if(moveFactorX < 0)
		{
			collision = true;
			for(final Positionable p : xCollisions) {
				p.getCollider().onPositionableCollide(this);
				float rightEdgeColider = p.getPosition().getX() + p.getHeight();
				if(_currentPosition.x < rightEdgeColider) {
					_currentPosition.x = rightEdgeColider + 1;
				}
			}
		}
		
		final float newPositionX = _currentPosition.x;
		_currentPosition.x = oldPositionX;
		_currentPosition.y += moveFactorY * _yspeed;
		
		final List<Positionable> yCollisions = _collider.getCollisions();
		
		// Moved up.
		if(moveFactorY < 0) {
			collision = true;
			for(final Positionable p : yCollisions) {
				p.getCollider().onPositionableCollide(this);
				float lowerEdgeColider = p.getPosition().getY() + p.getHeight();
				if(_currentPosition.y < lowerEdgeColider) {
					_currentPosition.y = lowerEdgeColider + 1;
				}
			}
		}
		
		
		// Moved down.
		if(moveFactorY > 0) {
			collision = true;
			for(final Positionable p : yCollisions) {
				p.getCollider().onPositionableCollide(this);
				float upperEdgeColider = p.getPosition().getY();
				if(_currentPosition.y + _height > upperEdgeColider - 1) {
					_currentPosition.y = upperEdgeColider - _height - 1;
				}
			}
		}
		
		_currentPosition.x = newPositionX;
		
		System.out.println("MoveFactorX: " + moveFactorX + " MoveFactorY: " + moveFactorY + " Collision: " +  collision);
		
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
	public void setController(final IController controller) {
		_controller = controller;
	}

	@Override
	public IController getController() {
		return _controller;
	}
}
