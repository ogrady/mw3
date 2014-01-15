package controller.keyboard;

import java.util.HashMap;
import java.util.Iterator;

import listener.IGameListener;
import listener.IInputListener;
import listener.IListenable;
import listener.ListenerSet;
import listener.notifier.ParametrizedNotifier;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import controller.IControllable;
import controller.IController;
import environment.Movable;
import game.Configuration;
import game.Viewport;

/**
 * Keyboard input which listens for keypresses
 * 
 * @author Daniel
 * 
 */
abstract public class KeyboardController implements
		IListenable<IInputListener>, IGameListener, IController, KeyListener {
	protected ListenerSet<IInputListener> _listeners;
	protected boolean _accepting;
	protected Movable _controllable;
	protected Input _input;
	protected Configuration _configuration;
	protected final HashMap<Integer, ParametrizedNotifier<Boolean, IInputListener>> _notifiers;

	public KeyboardController(final IControllable controllable,
			final Configuration configuration) {
		setControllable(controllable);
		_listeners = new ListenerSet<IInputListener>();
		_configuration = configuration;
		_accepting = true;
		_notifiers = new HashMap<Integer, ParametrizedNotifier<Boolean, IInputListener>>();
		// up
		_notifiers.put(configuration.getInteger(Configuration.UP),
				new ParametrizedNotifier<Boolean, IInputListener>() {

					@Override
					public void notify(final IInputListener listener,
							final Boolean parameter) {
						listener.onUpButton(parameter);
					}
				});
		// down
		_notifiers.put(configuration.getInteger(Configuration.DOWN),
				new ParametrizedNotifier<Boolean, IInputListener>() {

					@Override
					public void notify(final IInputListener listener,
							final Boolean parameter) {
						listener.onDownButton(parameter);
					}
				});
		// left
		_notifiers.put(configuration.getInteger(Configuration.LEFT),
				new ParametrizedNotifier<Boolean, IInputListener>() {

					@Override
					public void notify(final IInputListener listener,
							final Boolean parameter) {
						listener.onLeftButton(parameter);
					}
				});
		// right
		_notifiers.put(configuration.getInteger(Configuration.RIGHT),
				new ParametrizedNotifier<Boolean, IInputListener>() {

					@Override
					public void notify(final IInputListener listener,
							final Boolean parameter) {
						listener.onRightButton(parameter);
					}
				});
		// jump
		_notifiers.put(configuration.getInteger(Configuration.JUMP),
				new ParametrizedNotifier<Boolean, IInputListener>() {

					@Override
					public void notify(final IInputListener listener,
							final Boolean parameter) {
						listener.onJumpButton(parameter);
					}
				});
		// attack 1
		_notifiers.put(configuration.getInteger(Configuration.ATTACK_1),
				new ParametrizedNotifier<Boolean, IInputListener>() {

					@Override
					public void notify(final IInputListener listener,
							final Boolean parameter) {
						listener.onPrimaryAttackButton(parameter);
					}
				});
		// attack 2
		_notifiers.put(configuration.getInteger(Configuration.ATTACK_2),
				new ParametrizedNotifier<Boolean, IInputListener>() {

					@Override
					public void notify(final IInputListener listener,
							final Boolean parameter) {
						listener.onSecondaryAttackButton(parameter);
					}
				});
		// special
		_notifiers.put(configuration.getInteger(Configuration.SPECIAL),
				new ParametrizedNotifier<Boolean, IInputListener>() {

					@Override
					public void notify(final IInputListener listener,
							final Boolean parameter) {
						listener.onSpecialActionButton(parameter);
					}
				});
		// item
		_notifiers.put(configuration.getInteger(Configuration.ITEM),
				new ParametrizedNotifier<Boolean, IInputListener>() {

					@Override
					public void notify(final IInputListener listener,
							final Boolean parameter) {
						listener.onItemButton(parameter);
					}
				});
		// block
		_notifiers.put(configuration.getInteger(Configuration.BLOCK),
				new ParametrizedNotifier<Boolean, IInputListener>() {

					@Override
					public void notify(final IInputListener listener,
							final Boolean parameter) {
						listener.onBlockButton(parameter);
					}
				});
		// select
		_notifiers.put(configuration.getInteger(Configuration.SELECT),
				new ParametrizedNotifier<Boolean, IInputListener>() {

					@Override
					public void notify(final IInputListener listener,
							final Boolean parameter) {
						listener.onSelectButton(parameter);
					}
				});
		// start
		_notifiers.put(configuration.getInteger(Configuration.START),
				new ParametrizedNotifier<Boolean, IInputListener>() {

					@Override
					public void notify(final IInputListener listener,
							final Boolean parameter) {
						listener.onStartButton(parameter);
					}
				});

	}

	@Override
	public IControllable getControllable() {
		return _controllable;
	}

	/**
	 * @throws ClassCastException
	 *             if the controllable is not a {@link Movable}
	 */
	@Override
	public void setControllable(final IControllable controllable) {
		_controllable = (Movable) controllable;
	}

	@Override
	public boolean isAcceptingInput() {
		return _accepting;
	}

	@Override
	public void setInput(final Input input) {
		if (!(_input == input)) {
			if (_input != null) {
				_input.removeKeyListener(this);
			}
			_input = input;
			_input.addKeyListener(this);
		}
	}

	@Override
	public void inputEnded() {
	}

	@Override
	public void inputStarted() {
	}

	public boolean isKeyPressed(final int key) {
		return _input.isKeyDown(key);
	}

	@Override
	public void update(final Input input, final int delta) {
	}

	@Override
	public void onLoadConfig(final Configuration conf) {

	}

	@Override
	public void onTick(final Input input, final int delta) {
	}

	@Override
	public ListenerSet<IInputListener> getListeners() {
		return _listeners;
	}

	@Override
	public void keyPressed(final int key, final char c) {
		final ParametrizedNotifier<Boolean, IInputListener> notifier = _notifiers
				.get(key);
		if (notifier != null) {
			final Iterator<IInputListener> it = _listeners.iterator();
			while (it.hasNext()) {
				notifier.notify(it.next(), true);
			}
		}
	}

	@Override
	public void keyReleased(final int key, final char c) {
		final ParametrizedNotifier<Boolean, IInputListener> notifier = _notifiers
				.get(key);
		if (notifier != null) {
			final Iterator<IInputListener> it = _listeners.iterator();
			while (it.hasNext()) {
				notifier.notify(it.next(), false);
			}
		}
	}

	// not interesting for controllers
	@Override
	public void onRender(final Graphics g, final Viewport vp) {

	}
}
