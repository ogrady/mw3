package listener;

import game.Configuration;
import game.Viewport;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import renderer.IRenderer;

/**
 * Should be implemented by classes that wish to listen for game-events
 * 
 * @author Daniel
 * 
 */
public interface IGameListener extends IListener {
	/**
	 * Fired when the config is (re)loaded. Values from the config (like the
	 * keybinding) could have changed in the meanwhile and listeners might be
	 * interested in this change.
	 * 
	 * @param conf
	 *            the newly loaded configuration
	 */
	public void onLoadConfig(Configuration conf);

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
	public void onTick(Input input, int delta);

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
	public void onRender(Graphics g, Viewport vp);
}
