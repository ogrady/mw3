package renderer.slick;

import environment.Positionable;
import game.Viewport;

import org.newdawn.slick.Graphics;

public class SelfRenderer extends PositionableRenderer {
	public SelfRenderer(Positionable _self) {
		super(_self);
	}

	@Override
	public void render(Graphics _g, Viewport _vp) {
		_g.drawRect(_vp.getWidth() / 2 - renderable.getHitbox().getWidth() / 2, _vp.getHeight() / 2 - renderable.getHitbox().getHeight() / 2, renderable.getHitbox().getWidth(), renderable.getHitbox().getHeight());
	}

}
