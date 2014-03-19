package environment;

import java.util.ArrayList;

import listener.IActorListener;
import listener.IListenable;
import listener.ListenerSet;
import listener.notifier.INotifier;

import org.newdawn.slick.geom.Vector2f;

/**
 * Actors are movable objects that can have an additional descriton string.<br>
 * As a fallback, the simple class name is used as description if null is
 * passed.<br>
 * They are the main actors in the game, such as the pilots and mechs and
 * maintain health.
 * 
 * @author Daniel
 * 
 */
abstract public class Actor extends Movable implements
		IListenable<IActorListener> {
	public static final ArrayList<Actor> instances = new ArrayList<Actor>();
	protected ListenerSet<IActorListener> _entityListeners;
	protected String _description;
	protected int _maxLife, _currentLife;

	/**
	 * Constructor
	 * 
	 * @param position
	 *            initial position
	 * @param width
	 *            width in pixels
	 * @param height
	 *            height in pixels
	 * @param speed
	 *            movement speed
	 * @param description
	 *            description string
	 */
	public Actor(final Vector2f position, final float width,
			final float height, final float speed, final String description) {
		super(position, width, height, speed);
		_entityListeners = new ListenerSet<IActorListener>();
		_description = description != null ? description : getClass()
				.getSimpleName();
		instances.add(this);
	}

	/**
	 * Makes the {@link Actor} reduce its hitpoints by the incoming amount of
	 * damage and notify its listeners of this event.<br>
	 * When its hitpoints reach zero, he will also broadcast an onDie-event.
	 * 
	 * @param src
	 *            the source of damage
	 * @param amount
	 *            the amount of damage
	 */
	public void takeDamage(final IDamageSource src, final int amount) {
		_currentLife = Math.max(0, _currentLife - amount);
		_entityListeners.notify(new INotifier<IActorListener>() {

			@Override
			public void notify(final IActorListener listener) {
				listener.onTakeDamage(src, amount);
			}
		});
		if (_currentLife == 0) {
			_entityListeners.notify(new INotifier<IActorListener>() {

				@Override
				public void notify(final IActorListener listener) {
					listener.onDie();
				}
			});
		}
	}

	@Override
	public void destruct() {
		super.destruct();
		instances.remove(this);
		_entityListeners.unregisterAll();
	}

	@Override
	public ListenerSet<IActorListener> getListeners() {
		return _entityListeners;
	}
}
