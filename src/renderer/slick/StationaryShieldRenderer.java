package renderer.slick;

import org.newdawn.slick.Animation;
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
	private final Animation _animation;

	/**
	 * Constructor
	 * 
	 * @param shield
	 *            the logical {@link StationaryShield}
	 */
	public StationaryShieldRenderer(final StationaryShield shield) {
		_shield = shield;
		_animation = new Animation(loadScaledSpriteSheet(
				"rsc/nitro/stationary_shield.png", 16, 32, 2), 70);
	}

	@Override
	public void render(final Graphics g, final Viewport vp) {
		_animation.draw(_shield.getPosition().x, _shield.getPosition().y);
	}

}
