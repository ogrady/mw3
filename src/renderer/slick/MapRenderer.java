package renderer.slick;

import game.Viewport;
import level.World;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Renderer for the world. Renders the {@link World} and an image in the
 * background.
 *
 * @author Daniel
 *
 */
public class MapRenderer extends Slick2DRenderer {
	public static final float PARALLAX_FACTOR = 0.01f;
	private final World _renderable;
	private Image _backgroundImage;

	/**
	 * Contructor
	 *
	 * @param renderable
	 *            the world to render
	 */
	public MapRenderer(final World renderable) {
		_renderable = renderable;
		try {
			_backgroundImage = new Image("rsc/map/background.jpg");
		} catch (final SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(final Graphics g, final Viewport vp) {
		_renderable.getBlockWidth();
		_renderable.getWidth();
		vp.getWidth();
		_renderable.getBlockHeight();
		_renderable.getHeight();
		vp.getHeight();

		final float x = vp.getPosition().getX();
		final float y = vp.getPosition().getY();
		final int w = vp.getWidth();
		final int h = vp.getHeight();
		_backgroundImage.draw(-x - w + x * PARALLAX_FACTOR, -y - h + y
				* PARALLAX_FACTOR);
		_renderable.getTiledMap().render(0, 0);

	}
}
