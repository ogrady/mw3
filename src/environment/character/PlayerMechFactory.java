package environment.character;

import environment.Player;
import game.Configuration;

import org.newdawn.slick.geom.Vector2f;

import controller.IController;
import controller.keyboard.NitroKeyboardController;
import renderer.slick.Slick2DRenderer;
import renderer.slick.mech.NitroRenderer;

public class PlayerMechFactory {
	public enum EMech {
		NITRO
	}

	public PlayerMechFactory() {
	}
	
	public static final Player create(int _x, int _y, EMech _type, Configuration _conf) {
		Player pl;
		Slick2DRenderer ren;
		IController ctrl;
		switch(_type) {
		default:
		case NITRO:
			pl = new Player(new Vector2f(_x,_y), 0, 0, 5, "");
			ren = new NitroRenderer(pl);
			ctrl = new NitroKeyboardController(pl, _conf, (NitroRenderer) ren);
		}
		pl.setController(ctrl);
		pl.setRenderer(ren);
		return pl;
	}
}
