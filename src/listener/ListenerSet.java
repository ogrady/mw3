package listener;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListenerSet<L extends IListener> extends CopyOnWriteArrayList<L> {
	private static final long serialVersionUID = 1L;
	
	public void registerListener(L listener) {
		if(!this.contains(listener)) {
			this.add(listener);
		}
	}
	
	public void unregisterListener(L listener) {
		this.remove(listener);
	}
	
	public void unregisterAll() {
		this.clear();
	}
	
	public void notify(INotifier<L> notificator) {
		Iterator<L> it = this.iterator();
		while(it.hasNext()) {
			notificator.notify(it.next());
		}
	}
}
