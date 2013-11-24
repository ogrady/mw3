package environment.character;

import org.newdawn.slick.geom.Vector2f;

import renderer.slick.Slick2DRenderer;
import renderer.slick.mech.NitroRenderer;
import controller.IController;
import controller.keyboard.NitroKeyboardController;
import environment.Player;
import game.Configuration;

/**
 * Each mech consists of a triple of:<br>
 * <ol>
 * <li><strong>Entity</strong> which holds all the attributes</li>
 * <li><strong>Controller</strong> which controls the movements and actions of
 * the entity</li>
 * <li><strong>Renderer</strong> which renders the entity</li>
 * </ol>
 * This class constructs this triple and joins them based on the input values.
 * 
 * @author Daniel
 * 
 */
public class PlayerMechFactory {
	public enum EMech {
		NITRO
	}

	private PlayerMechFactory() {
	}

	/**
	 * Constructs a player with controller and renderer based on the passed
	 * values
	 * 
	 * @param x
	 *            the initial x-position of the player
	 * @param y
	 *            the initial y-position of the player
	 * @param type
	 *            the type used for rendering and controlling (specific mech or
	 *            pilot)
	 * @param conf
	 *            configuration to pass to the controller
	 * @return a contructed player with controller and renderer
	 */
	public static final Player create(final int x, final int y,
			final EMech type, final Configuration conf) {
		Player pl;
		Slick2DRenderer ren;
		IController ctrl;
		switch (type) {
		default: // fallthrough
		case NITRO:
			pl = new Player(new Vector2f(x, y), 0, 0, 5, "");
			ren = new NitroRenderer(pl);
			ctrl = new NitroKeyboardController(pl, conf, (NitroRenderer) ren);
			pl.getListeners().registerListener((NitroRenderer) ren);
			((NitroKeyboardController) ctrl).getListeners().registerListener(
					(NitroRenderer) ren);
			break;
		}
		pl.setController(ctrl);
		pl.setRenderer(ren);
		return pl;
	}
}
