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
	public void onEnded(CharacterAction action);
}
