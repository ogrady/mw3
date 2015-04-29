package environment.character.mech;

import listener.IAnimationListener;
import listener.IStationaryShieldListener;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

import renderer.slick.CollisionRenderer;
import renderer.slick.HitboxRenderer;
import renderer.slick.mech.NitroRenderer;
import sound.NitroSoundManager;
import util.Const;
import environment.Hitbox;
import environment.MovableState;
import environment.Positionable;
import environment.character.CharacterAction;
import environment.character.StationaryShield;
import environment.projectile.ParticleSword;
import environment.projectile.Projectile;
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
	protected int _armPosition;
	private static final int[] ANGLES = { 0, 30, 50, 68, 90, 112, 130, 150, 180 };

	public Nitro(final Vector2f position, final String description) {
		super(position, 72, 90, 5, description);
		_maxLife = Const.NITRO_HP;
		_currentLife = Const.NITRO_HP;
		// look straight
		_armPosition = 4;

		// we need empty actions for this, as per default an
		// EmptyCharacterAction would be used, which always returns false on
		// perform() and therefore we couldn't block at all.
		setCharacterAction(CharacterActionName.BLOCK, new CharacterAction(this,
				0) {

			@Override
			protected void execute() {

			}
		});
		setCharacterAction(CharacterActionName.UNBLOCK, new CharacterAction(
				this, 0) {

			@Override
			protected void execute() {

			}
		});
		setCharacterAction(CharacterActionName.SPECIAL_ATTACK,
				new CharacterAction(this, Const.NITRO_SHIELD_DELAY,
						MovableState.BLOCKING) {
			@Override
			protected void execute() {
				if (_activeShields < Const.NITRO_MAX_SHIELD_COUNT) {
					_activeShields++;
					final StationaryShield s = new StationaryShield(
							_currentPosition);
					MetalWarriors.instance.getPlayingState()
					.getListeners().registerListener(s);
					s.getListeners().registerListener(Nitro.this);
				}
			}
		});
		setCharacterAction(CharacterActionName.PRIMARY_ATTACK,
				new CharacterAction(this, Const.NITRO_SMG_DELAY,
						MovableState.BLOCKING) {
			@Override
			protected void execute() {
				Vector2f exitPoint = getArmJoint();
				if (getDirection() <= 0) {
					exitPoint = exitPoint.sub(new Vector2f(
							Const.NITRO_SMG_DIAMETER, 0));
				}

				exitPoint = exitPoint.add(getFireline().scale(
						getArmLength()));
				new SMGBullet(exitPoint, Nitro.this.getFireline()
						.scale(Const.NITRO_SMG_SPEED), Nitro.this);
			}
		});
		setCharacterAction(CharacterActionName.SECONDARY_ATTACK,
				new CharacterAction(this, Const.NITRO_SWORD_DELAY,
						MovableState.BLOCKING) {

			@Override
			protected void execute() {
				final Vector2f exitpoint = getPosition().copy();
				if (getDirection() > 0) {
					exitpoint.x += getWidth();
				} else {
					exitpoint.x -= ParticleSword.SWORDSIZE;
				}
				getArmJoint().add(getFireline().scale(getArmLength()));
				new ParticleSword(Nitro.this);
			}
		});
		setCharacterAction(CharacterActionName.ARM_UP, new CharacterAction(
				this, Const.MECH_ARM_ROTATION_DELAY) {

			@Override
			protected void execute() {
				_armPosition = Math.max(_armPosition - 1, 0);
			}
		});
		setCharacterAction(CharacterActionName.ARM_DOWN, new CharacterAction(
				this, Const.MECH_ARM_ROTATION_DELAY) {

			@Override
			protected void execute() {
				_armPosition = Math.min(_armPosition + 1, 8);
			}
		});
		setCharacterAction(CharacterActionName.JUMP, new CharacterAction(this,
				0) {

			@Override
			protected void execute() {
				move(0, -2);
				_state.add(MovableState.FLYING);
			}
		});

		new NitroSoundManager(this);
		final NitroRenderer renderer = new NitroRenderer(this);
		// TODO remove those subrenderers once the
		// "nitro is falling through the floor like an idiot"-bug is resolved
		renderer.getSubrenderers().add(new HitboxRenderer<Positionable>(this));
		renderer.getSubrenderers().add(
				new CollisionRenderer<Positionable>(this));
		setRenderer(renderer);
		renderer.getSpecialAnimation().getListeners()
		.registerListener(new IAnimationListener() {
			@Override
			public void onEnded() {
				_state.remove(MovableState.SPECIAL);
			}
		});
	}

	/**
	 * Mechs can rotate their arms to determine the direction they shoot in.<br>
	 * The arm can have eight different states.
	 * <p>
	 * 0 - being vertically up<br>
	 * 4 - being straight forward<br>
	 * 8 - being vertically down<br>
	 * </p>
	 * Having 3 states in between each.<br>
	 * So between each arm-position lies an angle of 20 degrees.<br>
	 * The state of the arm says nothing about the direction the mech is facing
	 * into. So having an arm-state of 7 could mean having the arm lifted by 20
	 * degrees from the ground and facing left or right. The direction can still
	 * be obtained from {@link #getDirection()}
	 *
	 * @return a number between 0 and 9, representing the current angle of the
	 *         arm
	 */
	public int getArmPosition() {
		return _armPosition;
	}

	/**
	 * The fireline is the vector, that determines the direction in which the
	 * Mechs {@link Projectile}s will fly. This is a normalised vector and
	 * therefore only determines the direction, not the magnitude, nor the
	 * position. Each time this method is called, a new vector will be created.
	 * So there is no way to manipulate the internal vector and no need to copy
	 * that vector before using it.
	 *
	 * @return the vector that determines the direction of shot
	 *         {@link Projectile}
	 */
	public Vector2f getFireline() {
		final double angle = Math.toRadians(ANGLES[getArmPosition()]);
		return new Vector2f((float) Math.sin(angle) * getDirection(),
				(float) -Math.cos(angle));
	}

	/**
	 * Rotates the arm up by 20 degrees
	 */
	@Override
	public void armUp() {
		getCharacterAction(CharacterActionName.ARM_UP).perform();
	}

	/**
	 * Rotates the arm down by 20 degrees
	 */
	@Override
	public void armDown() {
		getCharacterAction(CharacterActionName.ARM_DOWN).perform();
	}

	/**
	 * @return length of the arm, from joint to tip, to determine the exit-point
	 *         for bullets
	 */
	public int getArmLength() {
		return (int) (28 * Const.SCALE_FACTOR);
	}

	/**
	 * @return the point around which the arm rotates
	 */
	public Vector2f getArmJoint() {
		float relativeX;

		// Take into consideration direction of the mech since the position of
		// the joint is not directly in the center
		if (getDirection() > 0) {
			relativeX = 16 * Const.SCALE_FACTOR;
		} else {
			relativeX = getWidth() - 16 * Const.SCALE_FACTOR;
		}
		return getPosition().copy().add(
				new Vector2f(relativeX, 13 * Const.SCALE_FACTOR));
	}

	/**
	 * @return combined hitbox of mech and arm
	 */
	@Override
	public Hitbox getHitbox() {
		Hitbox mech = super.getHitbox();

		final Vector2f armLocation = getArmJoint();
		Shape arm;
		float angle;
		angle = (float) Math.toRadians(ANGLES[getArmPosition()] - 90);

		if (getDirection() > 0) {
			arm = new Rectangle(armLocation.x, armLocation.y, getArmLength(),
					20);
		} else {
			arm = new Rectangle(armLocation.x - getArmLength(), armLocation.y,
					getArmLength(), 20);
			angle *= -1;
		}
		arm = arm.transform(Transform.createRotateTransform(angle,
				armLocation.x, armLocation.y));
		//return mech.union(arm)[0];
		mech.addShape(arm);

		return mech;
	}

	@Override
	public void onDestruct(final StationaryShield shield) {
		_activeShields--;
	}
}
