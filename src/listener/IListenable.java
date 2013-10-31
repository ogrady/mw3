package listener;

public interface IListenable<L extends IListener> {
	ListenerSet<L> getListeners();
}
