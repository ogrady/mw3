package renderer.slick;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import environment.Positionable;
import game.Viewport;

public class HitboxRenderer<P extends Positionable> extends
		PositionableRenderer<P> {

	public HitboxRenderer(final P renderable) {
		super(renderable);
	}

	/**
	 * Renders the positionable at its current position. Flips the animation if
	 * the direction isinversed (= -1)
	 */
	@Override
	public void render(final Graphics g, final Viewport vp) {
		g.setColor(Color.white);
		for(Shape s : _renderable.getHitbox().getShapes()) {
			g.draw(s);
		}
	}

}
