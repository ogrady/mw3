package environment.character.mech;

import org.newdawn.slick.geom.Vector2f;

import renderer.slick.mech.NitroRenderer;
import util.Const;
import environment.character.CharacterAction;
import environment.character.StationaryShield;
import environment.projectile.SMGBullet;
import game.MetalWarriors;

/**
 * <p>
 * <img src=
 * "http://static.giantbomb.com/uploads/scale_small/13/137417/2295678-metal_warriors__u__00001.png"
 * >
 * </p>
 * <p>
 * Equipped with an SMG, particle sword and jetpack. Can block attacks and spawn
 * {@link StationaryShield}s on his position.
 * </p>
 * 
 * @author Daniel
 * 
 */
public class Nitro extends Mech {
	public Nitro(final Vector2f position, final String description) {
		super(position, 0, 0, 5, description);
		setRenderer(new NitroRenderer(this));
		_specialAttack = new CharacterAction(Const.NITRO_SHIELD_DELAY) {
			@Override
			protected void execute() {
				final StationaryShield s = new StationaryShield(
						_currentPosition);
				MetalWarriors.instance.getListeners().registerListener(s);
			}
		};
		_primaryAttack = new CharacterAction(Const.NITRO_SMG_DELAY) {
			@Override
			protected void execute() {
				final NitroRenderer r = (NitroRenderer) _renderer;
				final Vector2f exitPoint = r.getArmJoint().add(
						getFireline().scale(r.getArmLength()));
				new SMGBullet(exitPoint, Nitro.this.getFireline().scale(
						Const.NITRO_SMG_SPEED), Nitro.this);
			}
		};
	}
}
