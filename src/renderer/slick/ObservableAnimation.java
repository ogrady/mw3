package renderer.slick;

import listener.IListenable;
import listener.ListenerSet;
import listener.notifier.INotifier;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class ObservableAnimation extends Animation implements
		IListenable<IAnimationListener> {
	private final ListenerSet<IAnimationListener> _listeners;

	public ObservableAnimation(final SpriteSheet sheet, final int duration) {
		super(sheet, duration);
		_listeners = new ListenerSet<IAnimationListener>();
	}

	public void addListener(final IAnimationListener listener) {
		_listeners.add(listener);
	}

	public void removeListener(final IAnimationListener listener) {
		_listeners.remove(listener);
	}

	public void clearListeners() {
		_listeners.unregisterAll();
	}

	@Override
	public ListenerSet<IAnimationListener> getListeners() {
		return _listeners;
	}

	@Override
	public Image getCurrentFrame() {
		final Image image = super.getCurrentFrame();
		if (getFrame() == getFrameCount() - 1) {
			_listeners.notify(new INotifier<IAnimationListener>() {
				@Override
				public void notify(final IAnimationListener listener) {
					listener.onEnded();
				}
			});
		}
		return image;
	}
}
