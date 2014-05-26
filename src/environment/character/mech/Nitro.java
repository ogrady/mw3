package environment.character.mech;

import listener.IAnimationListener;
import listener.IStationaryShieldListener;

import org.newdawn.slick.geom.Vector2f;

import renderer.slick.mech.NitroRenderer;
import util.Const;
import environment.MovableState;
import environment.character.CharacterAction;
import environment.character.StationaryShield;
import environment.projectile.ParticleSword;
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
public class Nitro extends Mech implements IStationaryShieldListener {
	private int _activeShields;

	public Nitro(final Vector2f position, final String description) {
		super(position, 0, 0, 5, description);
		_maxLife = Const.NITRO_HP;
		_currentLife = Const.NITRO_HP;
		_specialAttack = new CharacterAction(Const.NITRO_SHIELD_DELAY) {
			@Override
			protected void execute() {
				if (!_state.has(MovableState.BLOCKING)
						&& _activeShields < Const.NITRO_MAX_SHIELD_COUNT) {
					_activeShields++;
					final StationaryShield s = new StationaryShield(
							_currentPosition);
					MetalWarriors.instance.getPlayingState().getListeners()
							.registerListener(s);
					s.getListeners().registerListener(Nitro.this);
				}
			}
		};
		_primaryAttack = new CharacterAction(Const.NITRO_SMG_DELAY) {
			@Override
			protected void execute() {
				if (!_state.has(MovableState.BLOCKING)) {
					final NitroRenderer r = (NitroRenderer) _renderer;
					final Vector2f exitPoint = r.getArmJoint().add(
							getFireline().scale(r.getArmLength()));
					new SMGBullet(exitPoint, Nitro.this.getFireline().scale(
							Const.NITRO_SMG_SPEED), Nitro.this);
				}
			}
		};
		_secondaryAttack = new CharacterAction(Const.NITRO_SWORD_DELAY) {

			@Override
			protected void execute() {
				final Vector2f exitpoint = getPosition().copy();
				if (getDirection() > 0) {
					exitpoint.x += getWidth();
				} else {
					exitpoint.x -= ParticleSword.SWORDSIZE;
				}
				final NitroRenderer r = (NitroRenderer) _renderer;
				r.getArmJoint().add(getFireline().scale(r.getArmLength()));
				new ParticleSword(Nitro.this);
			}
		};
		_jump = new CharacterAction(0) {

			@Override
			protected void execute() {
				move(0, -2);
			}
		};
		final NitroRenderer nr = new NitroRenderer(this);
		setRenderer(nr);
		nr.getSpecialAnimation().getListeners()
				.registerListener(new IAnimationListener() {
					@Override
					public void onEnded() {
						_state.remove(MovableState.SPECIAL);
					}
				});
	}

	@Override
	public void onDestruct(final StationaryShield shield) {
		_activeShields--;
	}
}
