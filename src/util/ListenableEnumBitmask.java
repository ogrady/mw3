package util;

import listener.IEnumBitmaskListener;
import listener.IListenable;
import listener.ListenerSet;
import listener.notifier.INotifier;

public class ListenableEnumBitmask<E extends Enum<?>> extends EnumBitmask<E>
		implements IListenable<IEnumBitmaskListener> {
	private final ListenerSet<IEnumBitmaskListener> _listeners;
	private final INotifier<IEnumBitmaskListener> _notifier;

	public ListenableEnumBitmask() {
		super();
		_listeners = new ListenerSet<IEnumBitmaskListener>();
		_notifier = new INotifier<IEnumBitmaskListener>() {
			@Override
			public void notify(final IEnumBitmaskListener listener) {
				listener.onChange(ListenableEnumBitmask.this);
			}
		};
	}

	@Override
	public void add(final E value) {
		final int old = _bits;
		add((int) Math.pow(2, value.ordinal()));
		if (old != _bits) {
			_listeners.notify(_notifier);
		}
	}

	@Override
	public void remove(final E value) {
		final int old = _bits;
		remove((int) Math.pow(2, value.ordinal()));
		if (old != _bits) {
			_listeners.notify(_notifier);
		}
	}

	@Override
	public ListenerSet<IEnumBitmaskListener> getListeners() {
		return _listeners;
	}

}
