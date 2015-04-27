package renderer.slick.mech;

import org.newdawn.slick.Graphics;

import renderer.slick.HitboxRenderer;
import environment.character.mech.Nitro;
import game.Viewport;

/**
 * <p>
 * For debug purposes only; Combines {@link NitroRenderer} with {@link HitboxRenderer}
 * </p>
 *
 * @author Fabian
 *
 */
public class NitroHitboxRenderer extends NitroRenderer {
	private final HitboxRenderer hitboxRenderer;
	
	public NitroHitboxRenderer(final Nitro pos) {
		super(pos);

		hitboxRenderer = new HitboxRenderer(pos);
	}

	@Override
	public void render(final Graphics g, final Viewport vp) {
		super.render(g,vp);

		hitboxRenderer.render(g,vp);
	}
}
