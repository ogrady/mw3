package listener;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Listenersets hold the specific listeners for listenables
 * 
 * @author Daniel
 * 
 * @param <L>
 *            listeners this set holds
 */
public class ListenerSet<L extends IListener> extends CopyOnWriteArrayList<L> {
	private static final long serialVersionUID = 1L;

	/**
	 * Using this method guarantees that the listener is inserted only once
	 * 
	 * @param listener
	 *            listener that will be inserted if it is not already in the
	 *            list
	 */
	public void registerListener(final L listener) {
		if (!contains(listener)) {
			add(listener);
		}
	}

	/**
	 * Removes the passed listener if it is registered
	 * 
	 * @param listener
	 *            listener to remove
	 */
	public void unregisterListener(final L listener) {
		remove(listener);
	}

	/**
	 * Removes all listeners
	 */
	public void unregisterAll() {
		clear();
	}

	/**
	 * Notifies all registered listeners via a certain notificator
	 * 
	 * @param notificator
	 *            notificator to use
	 */
	public void notify(final INotifier<L> notificator) {
		final Iterator<L> it = this.iterator();
		while (it.hasNext()) {
			notificator.notify(it.next());
		}
	}
}
