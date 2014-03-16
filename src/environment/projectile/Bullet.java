package environment.projectile;

import org.newdawn.slick.geom.Vector2f;

import renderer.slick.projectile.BulletRenderer;
import util.Const;
import controller.projectile.GenericProjectileController;
import environment.Movable;
import environment.collider.ProjectileCollider;
import game.MetalWarriors;

/**
 * The projectile Nitro fires from his SMG. Has a smooth ballistics and range of
 * {@value #DISTANCE} before it despawns.<br>
 * Collides with solid walls, mechs and players.
 * 
 * @author Daniel
 * 
 */
public class Bullet extends Projectile {

	public Bullet(final Vector2f position, final Vector2f deltaVector,
			final Movable source) {
		super(position, deltaVector, Const.NITRO_SMG_DMG,
				Const.NITRO_SMG_DISTANCE, source);
		setRenderer(new BulletRenderer(this));
		setController(new GenericProjectileController(this));
		setCollider(new ProjectileCollider(this));
		MetalWarriors.instance.getListeners().registerListener(this);
	}
}
