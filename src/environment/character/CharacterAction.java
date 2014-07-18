package environment.character;

import listener.ICharacterActionListener;
import listener.IListenable;
import listener.ListenerSet;
import listener.notifier.INotifier;
import util.Countdown;

/**
 * {@link CharacterAction}s are exchangeable operations of any entity in the
 * game. Those actions can be anything an entity could do: jump, shoot, block
 * etc. When they are executed, the action will first check whether it can
 * already be executed. If so, it will do the desired action and reset the
 * delay.<br>
 * As they are interchangeable objects, the base-class can define
 * basic-functionality and delay and subclasses can set other actions.
 *
 * @author Daniel
 *
 */
public abstract class CharacterAction implements
		IListenable<ICharacterActionListener> {
	private final Countdown _delay;
	private final ListenerSet<ICharacterActionListener> _listeners;

	@Override
	public ListenerSet<ICharacterActionListener> getListeners() {
		return _listeners;
	}

	/**
	 * Timer to display the remaining delay until this {@link CharacterAction}
	 * can be performed again
	 *
	 * @return the timer to display the remaining delay
	 */
	public Countdown getDelay() {
		return _delay;
	}

	/**
	 * Constructor
	 *
	 * @param delay
	 *            the delay for this action
	 */
	public CharacterAction(final long delay) {
		_listeners = new ListenerSet<ICharacterActionListener>();
		_delay = new Countdown(delay);
	}

	/**
	 * Tries to do the action. E.g. a JumpAction would probably try to make the
	 * associated character jump.<br>
	 * But only if the timer has already run out. If not, the call will simply
	 * be ignored. Else, the {@link #execute()}-method will be called.
	 *
	 * @return whether the action could be performed or not (= timer was still
	 *         running)
	 *
	 */
	public boolean perform() {
		boolean done = false;
		if (_delay.isTimedOut()) {
			execute();
			_delay.reset();
			done = true;
		}
		return done;
	}

	public void stop() {
		new INotifier<ICharacterActionListener>() {
			@Override
			public void notify(final ICharacterActionListener listener) {
				listener.onEnded(CharacterAction.this);

			}
		};
	}

	/**
	 * This method is called when {@link #perform()} was called after the timer
	 * has run out.<br>
	 * In here, anything this action should do is done.
	 */
	abstract protected void execute();
}
