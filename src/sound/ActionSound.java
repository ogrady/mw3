package sound;

import listener.ICharacterActionListener;
import logger.LogMessageType;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import environment.character.CharacterAction;
import game.MetalWarriors;

/**
 * Sound that is played whenever a certain {@link CharacterAction} is executed.
 * For that the {@link ActionSound} registers as
 * {@link ICharacterActionListener} and waits for the execution of the
 * {@link CharacterAction}.
 *
 * @author Daniel
 *
 */
public class ActionSound implements ICharacterActionListener {
	private final Sound _sound;

	public ActionSound(final String path) {
		_sound = loadSound(path);
	}

	@Override
	public void onEnded(final CharacterAction action) {
	}

	@Override
	public void onExecute(final CharacterAction action) {
		_sound.play(0.1f, 20);
	}

	/**
	 * Attempts to load a sound from a given path. Stops the game if loading
	 * fails. TODO: look for a more graceful method, like using an empty sound,
	 * or whatever
	 * 
	 * @param path
	 *            path wher the soundfile is
	 * @return the loaded file
	 */
	public static Sound loadSound(final String path) {
		Sound s = null;
		try {
			s = new Sound(path);
		} catch (final SlickException e) {
			MetalWarriors.logger.print("Couldn't load sound from " + path
					+ ". Shutting down.", LogMessageType.GENERAL_ERROR);
			System.err.println("Couldn't load sound from " + path);
			System.exit(1);
		}
		return s;
	}
}
