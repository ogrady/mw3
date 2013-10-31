package listener;

public interface INotifier<L extends IListener> {
	void notify(L listener);
}
