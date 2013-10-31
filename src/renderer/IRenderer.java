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
	 * @param _g graphics to draw on
	 * @param _vp viewport the game currently has
	 */
	void render(Graphics _g, Viewport _vp);
}
