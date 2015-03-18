package listener;

import game.Viewport;
import level.World;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import renderer.IRenderer;

public interface IPlayingStateListener extends IListener {
	/**
	 * The game itself sleeps for a certain amount of time and then wakes up to
	 * update all objects again (which repeats until the game is closed).<br>
	 * Objects can register themselves with the game to be notified whenever the
	 * games wakes up and tells them how many milliseconds have passed since
	 * then.
	 *
	 * @param input
	 *            the inputobject from this tick
	 * @param delta
	 *            the milliseconds passed since the last time
	 */
	default void onTick(final Input input, final int delta) {
	}

	/**
	 * Called from the game whenever another render-cycle begins.<br>
	 * It passes the rendering-plane to all entities for them to draw themselves
	 * (probably via their {@link IRenderer}) on that plane.
	 *
	 * @param g
	 *            the plane to draw on
	 * @param vp
	 *            the viewport the player currently sees
	 */
	default void onRender(final Graphics g, final Viewport vp) {
	}

	default void onLoadMap(final World map) {
	}
}
