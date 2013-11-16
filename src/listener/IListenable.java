package listener;

/**
 * A listenable is an object that can fire certain events.<br>
 * Each listenable supports one kind of listeners of which he can hold a list.<br>
 * By imeplementing the corresponding listener-interface objects can register
 * with the listenable and have their interface-methods called when something
 * intersting happens in the listenable-object.
 * 
 * @author Daniel
 * 
 * @param <L>
 *            the specific listener-interface this listenable supports
 */
public interface IListenable<L extends IListener> {
	ListenerSet<L> getListeners();
}
