package renderer.slick;

import game.Viewport;
import level.World;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MapRenderer extends Slick2DRenderer {
	private final World _renderable;
	private Image _backgroundImage;

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
		/*g.drawImage(_backgroundImage, vp.getPosition().x / 100000
				- _backgroundImage.getWidth() / 2, vp.getPosition().y / 100000
				- _backgroundImage.getHeight() / 2);*/
		g.drawImage(_backgroundImage, 0 - vp.getPosition().x,
				0 - vp.getPosition().y);
		_renderable.getTiledMap().render(0, 0);

	}
}
