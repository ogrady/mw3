package test;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import util.magicwand.MagicWand;

public class MagicWandTest extends BasicGame {
	private Image _img;
	private Shape _shape;

	public MagicWandTest() {
		super("Magic Wand Test");
	}

	@Override
	public void render(final GameContainer arg0, final Graphics arg1)
			throws SlickException {
		arg1.drawImage(_img, 0, 0);
		arg1.draw(_shape);

	}

	@Override
	public void init(final GameContainer arg0) throws SlickException {
		for (int j = 0; j < 100; j++) {
			_img = new Image("rsc/test/MagicWand1.png");
			_shape = new MagicWand().getBoundingShape(_img);
		}
		System.out.println("computed hitbox has " + _shape.getPointCount()
				+ " points.");
	}

	@Override
	public void update(final GameContainer arg0, final int arg1)
			throws SlickException {
	}

	public static void main(final String[] args) throws SlickException {
		final AppGameContainer apc = new AppGameContainer(new MagicWandTest());
		apc.setShowFPS(false);
		apc.start();

	}

}
