package renderer.slick;

import org.newdawn.slick.Graphics;

import environment.character.StationaryShield;
import game.Viewport;

/**
 * Renderer for Nitros {@link StationaryShield}s.
 * 
 * @author Daniel
 * 
 */
public class StationaryShieldRenderer extends Slick2DRenderer {
	private final StationaryShield _shield;

	/**
	 * Constructor
	 * 
	 * @param shield
	 *            the logical {@link StationaryShield}
	 */
	public StationaryShieldRenderer(final StationaryShield shield) {
		_shield = shield;
	}

	@Override
	public void render(final Graphics g, final Viewport vp) {
		// TODO for Greg
		g.drawRect(_shield.getPosition().x, _shield.getPosition().y,
				_shield.getWidth(), _shield.getHeight());
	}

}
