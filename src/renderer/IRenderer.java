package renderer;

import game.Viewport;

import org.newdawn.slick.Graphics;

/**
 * Basic renderer fuctionality for 2D drawing.
 * @author Daniel
 *
 */
public interface IRenderer {
	/**
	 * Renders the renderable on the given graphics-object 
	 * @param g graphics to draw on
	 * @param vp viewport the game currently has
	 */
	void render(Graphics g, Viewport vp);
}
