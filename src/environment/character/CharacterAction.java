package environment.character;

import java.util.HashSet;
import java.util.Iterator;

import listener.ICharacterActionListener;
import listener.IListenable;
import listener.ListenerSet;
import listener.notifier.INotifier;
import util.Countdown;
import environment.Actor;
import environment.MovableState;

/**
 * {@link CharacterAction}s are exchangeable operations of any entity in the
 * game. Those actions can be anything an entity could do: jump, shoot, block
 * etc. When they are executed, the action will first check whether it can
 * already be executed. If so, it will do the desired action and reset the
 * delay.<br>
 * As they are interchangeable objects, the base-class can define
 * basic-functionality and delay and subclasses can set other actions.<br>
 * By default, {@link CharacterAction}s are being prevented from being performed
 * when the performing {@link Actor} is {@link MovableState#DYING} or
 * {@link MovableState#DEAD}.
 *
 * @author Daniel
 *
 */
public abstract class CharacterAction implements
IListenable<ICharacterActionListener> {
	private final Countdown _delay;
	private final ListenerSet<ICharacterActionListener> _listeners;
	private final Actor _owner;
	protected HashSet<MovableState> _forbiddenStates;

	/**
	 * @return the owner of the {@link CharacterAction} to which this action is
	 *         tied to
	 */
	public Actor getOwner() {
		return _owner;
	}

	@Override
	public ListenerSet<ICharacterActionListener> getListeners() {
		return _listeners;
	}

	/**
	 * The set of states the {@link Actor} must not be in to execute the action.
	 * By default, {@link MovableState#DYING} and {@link MovableState#DEAD} are
	 * forbidden. They can be removed or other states can be added by
	 * manipulating this set.
	 *
	 * @return a set of {@link MovableState}s the {@link Actor} can't be in when
	 *         executing this action. In fact, being in one of them cancels the
	 *         execution of the action. See {@link #isStateValid()} for further
	 *         detail.
	 */
	public HashSet<MovableState> getForbiddenStates() {
		return _forbiddenStates;
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
	 * @param performer
	 *            the performing {@link Actor}
	 * @param delay
	 *            the delay for this action
	 * @param forbidden
	 *            an optional list of forbidden states this action can't be
	 *            executed in. Basically just a convenience method for using
	 *            {@link #getForbiddenStates()} after initialisation. See there
	 *            for more info.
	 */
	public CharacterAction(final Actor performer, final long delay,
			final MovableState... forbidden) {
		_forbiddenStates = new HashSet<MovableState>();
		_listeners = new ListenerSet<ICharacterActionListener>();
		_delay = new Countdown(delay);
		_owner = performer;
		_forbiddenStates.add(MovableState.DYING);
		_forbiddenStates.add(MovableState.DEAD);
		for (final MovableState ms : forbidden) {
			_forbiddenStates.add(ms);
		}
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
		if (_delay.isTimedOut() && isStateValid()) {
			execute();
			_delay.reset();
			done = true;
			_listeners.notify(new INotifier<ICharacterActionListener>() {
				@Override
				public void notify(final ICharacterActionListener listener) {
					listener.onExecute(CharacterAction.this);
				}
			});
		}
		return done;
	}

	public void stop() {
		_listeners.notify(new INotifier<ICharacterActionListener>() {
			@Override
			public void notify(final ICharacterActionListener listener) {
				listener.onEnded(CharacterAction.this);
			}
		});
	}

	/**
	 * Checks whether the state of the {@link Actor} is valid. This is the case
	 * iff it's current state-mask holds not a single one of the forbidden
	 * states.
	 *
	 * @return true, iff the {@link Actor} is allowed to execute the action
	 *         statewise
	 */
	private boolean isStateValid() {
		boolean valid = true;
		final Iterator<MovableState> it = _forbiddenStates.iterator();
		while (valid && it.hasNext()) {
			valid = !_owner.getState().has(it.next());

		}
		return valid;
	}

	/**
	 * This method is called when {@link #perform()} was called after the timer
	 * has run out.<br>
	 * In here, anything this action should do is done.
	 */
	abstract protected void execute();
}
