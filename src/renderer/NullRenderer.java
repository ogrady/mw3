package renderer;

import game.Viewport;
import level.Block;

import org.newdawn.slick.Graphics;

import renderer.slick.Slick2DRenderer;

/**
 * {@link NullRenderer}s don't render anything at all. They are the basic
 * renderers that are employed for new renderable objects, to avoid
 * NullPointers.<br>
 * In most cases, this renderer will be replaced with a more specific renderer
 * in the constructor of the renderable. But in some cases it might be desirable
 * to have objects in the game, but be not rendered. For example destroyed
 * {@link Block} s.
 * 
 * @author Daniel
 * 
 */
public class NullRenderer extends Slick2DRenderer {

	public NullRenderer() {
	}

	@Override
	public void render(final Graphics g, final Viewport vp) {
	}

	@Override
	public int getWidth() {
		return 0;
	}

	@Override
	public int getHeight() {
		return 0;
	}

}
