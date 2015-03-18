package listener;

import environment.character.CharacterAction;

public interface ICharacterActionListener extends IListener {
	/**
	 * Called, when the {@link CharacterAction} has ended. Makes no guarantees
	 * about the execution. It could have been executed and it could also have
	 * been skipped, if the preconditions weren't met (timer not expired etc.)
	 *
	 * @param action
	 *            the {@link CharacterAction} that has ended
	 */
	default void onEnded(final CharacterAction action) {
	}

	/**
	 * Posthook for the execution of a {@link CharacterAction}. That is, someone
	 * called {@link CharacterAction#perform()} and all preconditions were met.
	 * Then {@link CharacterAction#execute()} is being called to do the action
	 * itself, THEN the listeners are notified via this method.
	 *
	 * @param action
	 *            the performed {@link CharacterAction}
	 */
	default void onExecute(final CharacterAction action) {
	}
}
