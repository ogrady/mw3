package renderer.slick;

import java.util.Collection;

import org.newdawn.slick.Graphics;

import environment.Positionable;
import game.Viewport;

/**
 * For debugging-purposes ONLY. Will probably slow the game down considerably as
 * it collects all collisions a second time.
 *
 * @author Daniel
 *
 * @param <P>
 */
public class CollisionRenderer<P extends Positionable> extends
		PositionableRenderer<P> {

	public CollisionRenderer(final P renderable) {
		super(renderable);
	}

	@Override
	public void render(final Graphics g, final Viewport p) {
		final Collection<Positionable> collisions = _renderable.getCollider()
				.getCollisions();
		for (final Positionable pos : collisions) {
			g.fill(pos.getHitbox());
		}
	}

}
