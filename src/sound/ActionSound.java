package sound;

import listener.ICharacterActionListener;
import logger.LogMessageType;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Shape;

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
		// TODO: this still doesn't work as intended: play sound on 100% volume
		// if the source is within the viewport (+10% or so) and slowly fade it
		// out as the source moves farther away
		final Shape vpr = MetalWarriors.instance.getPlayingState()
				.getViewport().getPlayerViewportRect();
		final Shape or = action.getOwner().getHitbox();
		if (vpr.intersects(or)) {
			final float cx = vpr.getCenterX();
			final float cy = vpr.getCenterY();
			final float ox = or.getCenterX();
			final float oy = or.getCenterY();
			final float x = (float) ((ox - cx) / (vpr.getWidth() * 1.5));
			float y = (float) ((oy - cy) / (vpr.getHeight() * 1.5));
			y = 1;
			System.out.println("x:" + x);
			System.out.println("y:" + y);
			_sound.playAt(x, y, 1);
		}

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
