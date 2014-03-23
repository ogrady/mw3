package listener.notifier;

import listener.IListener;
import controller.keyboard.KeyboardWatchdog;

/**
 * Notifiers are one-method-interfaces to simulate method-pointers. Used for
 * notifying listeners of events. To be thread-safe, we would have to use
 * iterators to run through the list of listeners for every event-call. Using
 * Notifiers, we can define one method that does the loop with the iterator and
 * just pass it a Notifier-object that defines in its {@link #notify(IListener)}
 * -method, which listener-method should be called.
 * <p>
 * For an example of usage, see {@link KeyboardWatchdog}, where key-codes are
 * mapped on several Notifiers and just the appropriate Notifier is applied to
 * all listeners in {@link KeyboardWatchdog#keyPressed(int, char)} and
 * {@link KeyboardWatchdog#keyReleased(int, char)}.
 * </p>
 * 
 * @author Daniel
 * 
 * @param <L>
 *            the type of {@link IListener} this Notifier notifies
 */
public interface INotifier<L extends IListener> {
	void notify(L listener);
}
