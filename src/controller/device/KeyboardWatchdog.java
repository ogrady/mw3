package controller.device;

import game.Configuration;
import game.MetalWarriors;

import java.util.HashMap;
import java.util.Iterator;

import listener.IInputListener;
import listener.IListenable;
import listener.ListenerSet;
import listener.notifier.ParametrizedNotifier;
import logger.LogMessageType;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

/**
 * Watchdog that listenes for keystrokes on the keyboard.<br>
 * Keyup- and keydown-events will be forwarded to all {@link KeyListener} that
 * have registered themselves as listener for this watchdog.
 * 
 * @author Daniel
 * 
 */
@Deprecated
public class KeyboardWatchdog implements IListenable<IInputListener>,
		KeyListener {

	protected ListenerSet<IInputListener> _listeners;
	protected boolean _accepting;
	protected Input _input;
	protected Configuration _configuration;
	protected final HashMap<Integer, ParametrizedNotifier<Boolean, IInputListener>> _notifiers;

	public KeyboardWatchdog(final Configuration configuration) {
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

	@Override
	public ListenerSet<IInputListener> getListeners() {
		return _listeners;
	}

	@Override
	public void keyPressed(final int key, final char c) {
		final ParametrizedNotifier<Boolean, IInputListener> notifier = _notifiers
				.get(key);
		MetalWarriors.logger.print(
				"KeyboardWatchdog received keystroke " + key,
				LogMessageType.INPUT_DEBUG);
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
}
