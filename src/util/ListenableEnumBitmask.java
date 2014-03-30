package util;

import listener.IEnumBitmaskListener;
import listener.IListenable;
import listener.ListenerSet;
import listener.notifier.INotifier;

/**
 * A bitmask that can notifies its listeners whenever its values change.
 * 
 * @author Daniel
 * 
 * @param <E>
 */
public class ListenableEnumBitmask<E extends Enum<?>> extends EnumBitmask<E>
		implements IListenable<IEnumBitmaskListener<E>> {
	private final ListenerSet<IEnumBitmaskListener<E>> _listeners;

	/**
	 * Constructor
	 */
	public ListenableEnumBitmask() {
		super();
		_listeners = new ListenerSet<IEnumBitmaskListener<E>>();
	}

	/**
	 * Notifies the listeners if a new value was added
	 */
	@Override
	public void add(final E value) {
		final boolean isNew = !has(value);
		_map.put(value, true);
		if (isNew) {
			_listeners.notify(new INotifier<IEnumBitmaskListener<E>>() {
				@Override
				public void notify(final IEnumBitmaskListener<E> listener) {
					listener.onAdd(ListenableEnumBitmask.this, value);
				}
			});
		}
	}

	/**
	 * Notifies the listeners if a value was added
	 */
	@Override
	public void remove(final E value) {
		final boolean had = has(value);
		_map.put(value, false);
		if (had) {
			_listeners.notify(new INotifier<IEnumBitmaskListener<E>>() {
				@Override
				public void notify(final IEnumBitmaskListener<E> listener) {
					listener.onRemove(ListenableEnumBitmask.this, value);
				}
			});
		}
	}

	@Override
	public ListenerSet<IEnumBitmaskListener<E>> getListeners() {
		return _listeners;
	}
}
