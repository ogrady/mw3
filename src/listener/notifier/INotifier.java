package listener.notifier;

import listener.IListener;

public interface INotifier<L extends IListener> {
	void notify(L listener);
}
