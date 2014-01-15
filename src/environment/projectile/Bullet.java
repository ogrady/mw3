package environment.projectile;

import game.MetalWarriors;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import renderer.slick.projectile.BulletRenderer;
import controller.projectile.GenericProjectileController;

public class Bullet extends Projectile {

	public Bullet(final Vector2f position, final Vector2f deltaVector) {
		super(position, deltaVector, 100);
		setRenderer(new BulletRenderer(this));
		setController(new GenericProjectileController(this));
		MetalWarriors.instance.getListeners().registerListener(this);
	}

	@Override
	public void onTick(final Input input, final int delta) {
		super.onTick(input, delta);
		// System.out.println(getPosition());
	}

}
