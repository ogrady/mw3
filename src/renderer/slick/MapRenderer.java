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
		g.drawImage(_backgroundImage, vp.getPosition().x / 10,
				vp.getPosition().y / 10);
		_renderable.getTiledMap().render(0, 0);
	}
}
