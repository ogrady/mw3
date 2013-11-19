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
		boolean moved = true;
		final float oldX = _position.x;
		final float oldY = _position.y;
		_position.x += moveFactorX * _xspeed;
		_position.y += moveFactorY * _xspeed; // TODO yspeed
		final List<Positionable> collisions = _collider.getCollisions();
		if (!collisions.isEmpty()) {
			for (final Positionable p : collisions) {
				p.getCollider().onPositionableCollide(this);
			}
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
					System.out.println(first);
					_position.y = first.getPosition().y
							+ first.getRenderer().getHeight() - 1;
				}
			}
		}
		return moved;
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
