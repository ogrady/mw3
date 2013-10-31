package renderer.slick;

import game.Viewport;
import level.Map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MapRenderer extends Slick2DRenderer {
	private final Map renderable;
	private Image backgroundImage;

	public MapRenderer(final Map _renderable) {
		renderable = _renderable;
		try {
			backgroundImage = new Image("rsc/map/background.jpg");
		} catch (final SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(final Graphics _g, final Viewport _vp) {
		_g.drawImage(backgroundImage, _vp.getPosition().x / 10,
				_vp.getPosition().y / 10);
		renderable.getTiledMap().render(0, 0);
	}
}
