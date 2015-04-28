package renderer.slick;

import game.Viewport;
import level.Block;
import level.World;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Renderer for the world for testing. Renders like a {@link MapRenderer} but
 * also displays the hitbox of solid blocks.
 *
 * @author Daniel
 *
 */
public class HitboxMapRenderer extends MapRenderer {
	private final World _m;

	/**
	 * Contructor
	 *
	 * @param renderable
	 *            the world to render
	 */
	public HitboxMapRenderer(final World renderable) {
		super(renderable);
		_m = renderable;
	}

	@Override
	public void render(final Graphics g, final Viewport vp) {
		super.render(g, vp);
		g.setColor(Color.white);
		final Block[][] blocks = _m.getBlocks();
		for (final Block[] outer : blocks) {
			for (final Block inner : outer) {
				if (inner.isSolid()) {
					g.drawRect(inner.getPosition().x, inner.getPosition().y,
							inner.getWidth(), inner.getHeight());
				}
			}
		}
	}
}
