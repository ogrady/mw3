package listener.notifier;

import listener.IListener;

/**
 * Notifiers, which also have a method to pass an additional parameter of any
 * type.
 * 
 * @author Daniel
 * 
 * @param <T>
 *            the type of the parameter
 * @param <L>
 *            the type of {@link IListener}s, this Notifier notifies
 */
public abstract class ParametrizedNotifier<T, L extends IListener> implements
		INotifier<L> {

	@Override
	public void notify(final L listener) {
	}

	public abstract void notify(final L listener, T parameter);

}
