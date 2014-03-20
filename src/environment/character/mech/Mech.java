package environment.character.mech;

import org.newdawn.slick.geom.Vector2f;

import util.Const;
import environment.Actor;
import environment.MovableState;
import environment.character.CharacterAction;
import environment.character.EmptyCharacterAction;
import environment.projectile.Projectile;

/**
 * <p>
 * This is the intermediate class between {@link Actor}s and specialized mechs
 * (Nitro, Havoc, Spider, ...). Its purpose is to have another abstraction layer
 * to add new features that apply to all mechs, but not for all {@link Actor}s.
 * </p>
 * 
 * <p>
 * They have {@link CharacterAction}s, like jumping, shooting etc., that can be
 * triggered from outside-classes, like their controller.
 * </p>
 * Pilots can be {@link Mech}s too, to have them share the same possible
 * actions.
 * 
 * @author Daniel
 * 
 */
public abstract class Mech extends Actor {
	protected CharacterAction _specialAttack, _primaryAttack, _secondaryAttack,
			_jump, _block, _itemUse, _armUp, _armDown;
	protected int _armPosition;

	/**
	 * Mechs can rotate their arms to determine the direction they shoot in.<br>
	 * The arm can have nine different states.
	 * <p>
	 * 0 - being vertically up<br>
	 * 4 - being straight forward<br>
	 * 9 - being vertically down<br>
	 * </p>
	 * Having 3 states in between each.<br>
	 * So between each arm-position lies an angle of 20 degrees.<br>
	 * The state of the arm says nothing about the direction the mech is facing
	 * into. So having an arm-state of 8 could mean having the arm lifted by 20°
	 * from the ground and facing left or right. The direction can still be
	 * obtained from {@link #getDirection()}
	 * 
	 * @return a number between 0 and 9, representing the current angle of the
	 *         arm
	 */
	public int getArmPosition() {
		return _armPosition;
	}

	/**
	 * Rotates the arm up by 20°
	 */
	public void armUp() {
		_armUp.perform();
	}

	/**
	 * Rotates the arm down by 20°
	 */
	public void armDown() {
		_armDown.perform();
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
		final double angle = Math.toRadians(getArmPosition() * 20);
		return new Vector2f((float) Math.sin(angle) * getDirection(),
				(float) -Math.cos(angle));
	}

	/**
	 * Constructor (@see
	 * {@link Actor#Actor(Vector2f, float, float, float, String)})
	 * 
	 * @param position
	 * @param width
	 * @param height
	 * @param speed
	 * @param description
	 */
	public Mech(final Vector2f position, final float width, final float height,
			final float speed, final String description) {
		super(position, width, height, speed, description);
		// default actions that don't do anything. Have to be replaced in
		// subclasses
		_primaryAttack = new EmptyCharacterAction();
		_secondaryAttack = new EmptyCharacterAction();
		_specialAttack = new EmptyCharacterAction();
		_jump = new EmptyCharacterAction();
		_itemUse = new EmptyCharacterAction();
		_block = new EmptyCharacterAction();
		_armUp = new CharacterAction(Const.MECH_ARM_ROTATION_DELAY) {

			@Override
			protected void execute() {
				_armPosition = Math.max(_armPosition - 1, 0);
			}
		};
		_armDown = new CharacterAction(Const.MECH_ARM_ROTATION_DELAY) {

			@Override
			protected void execute() {
				_armPosition = Math.min(_armPosition + 1, 9);

			}
		};
	}

	public void tick(final long delta) {
		_primaryAttack.getDelay().tick(delta);
		_secondaryAttack.getDelay().tick(delta);
		_specialAttack.getDelay().tick(delta);
		_block.getDelay().tick(delta);
		_jump.getDelay().tick(delta);
		_itemUse.getDelay().tick(delta);
		_armUp.getDelay().tick(delta);
		_armDown.getDelay().tick(delta);
	}

	public void primaryAttack() {
		_primaryAttack.perform();
	}

	public void specialAttack() {
		_specialAttack.perform();
	}

	public void secondaryAttack() {
		_secondaryAttack.perform();
	}

	public void block() {
		_block.perform();
		_state.add(MovableState.BLOCKING);
	}

	public void unblock() {
		_state.remove(MovableState.BLOCKING);
	}

	public void jump() {
		_jump.perform();
		_state.add(MovableState.JUMPING);
	}

	public void useItem() {
		_itemUse.perform();
	}
}
