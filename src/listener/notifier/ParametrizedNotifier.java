package listener.notifier;

import listener.IListener;

public abstract class ParametrizedNotifier<T, L extends IListener> implements
		INotifier<L> {

	@Override
	public void notify(final L listener) {
	}

	public abstract void notify(final L listener, T parameter);

}
