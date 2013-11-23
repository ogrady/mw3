package environment;

import listener.IEntityListener;
import listener.IListenable;
import listener.INotifier;
import listener.ListenerSet;

import org.newdawn.slick.geom.Vector2f;

/**
 * Entities are movable objects that can have an additional descriton string.<br>
 * As a fallback, the simple class name is used as description if null is
 * passed.<br>
 * They are the main actors in the game, such as the pilots and mechs and
 * maintain health.
 * 
 * @author Daniel
 * 
 */
abstract public class Entity extends Movable implements
		IListenable<IEntityListener> {
	protected ListenerSet<IEntityListener> entityListeners;
	protected String _description;
	protected int maxLife, currentLife;

	public Entity(final Vector2f position, final float width,
			final float height, final float speed, final String description) {
		super(position, width, height, speed);
		entityListeners = new ListenerSet<IEntityListener>();
		_description = description != null ? description : getClass()
				.getSimpleName();
	}

	public void takeDamage(final IDamageSource src, final int amount) {
		currentLife = Math.max(0, currentLife - amount);
		entityListeners.notify(new INotifier<IEntityListener>() {

			@Override
			public void notify(final IEntityListener listener) {
				listener.onTakeDamage(src, amount);
			}
		});
		if (currentLife == 0) {
			entityListeners.notify(new INotifier<IEntityListener>() {

				@Override
				public void notify(final IEntityListener listener) {
					listener.onDie();
				}
			});
		}
	}

	@Override
	public void destruct() {
		super.destruct();
		entityListeners.unregisterAll();
	}

	@Override
	public ListenerSet<IEntityListener> getListeners() {
		return entityListeners;
	}
}
