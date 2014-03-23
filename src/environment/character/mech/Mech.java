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
	private static final int[] ANGLES = { 0, 30, 50, 68, 90, 112, 130, 150, 180 };
	protected CharacterAction _specialAttack, _primaryAttack, _secondaryAttack,
			_jump, _block, _unblock, _itemUse, _armUp, _armDown;
	protected int _armPosition;

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
	 * into. So having an arm-state of 7 could mean having the arm lifted by 20°
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
		final double angle = Math.toRadians(ANGLES[getArmPosition()]);
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
		_unblock = new EmptyCharacterAction();
		_armUp = new CharacterAction(Const.MECH_ARM_ROTATION_DELAY) {

			@Override
			protected void execute() {
				_armPosition = Math.max(_armPosition - 1, 0);
			}
		};
		_armDown = new CharacterAction(Const.MECH_ARM_ROTATION_DELAY) {

			@Override
			protected void execute() {
				_armPosition = Math.min(_armPosition + 1, 8);

			}
		};
	}

	/**
	 * Sends the tick to all registered actions, to decrease their remaining
	 * delay accordingly.
	 * 
	 * @param delta
	 *            the passed milliseconds since the last tick
	 */
	public void tickActions(final long delta) {
		_primaryAttack.getDelay().tick(delta);
		_secondaryAttack.getDelay().tick(delta);
		_specialAttack.getDelay().tick(delta);
		_block.getDelay().tick(delta);
		_unblock.getDelay().tick(delta);
		_jump.getDelay().tick(delta);
		_itemUse.getDelay().tick(delta);
		_armUp.getDelay().tick(delta);
		_armDown.getDelay().tick(delta);
	}

	/**
	 * Asks the {@link Mech} to perform the primary-attack-action (shooting in
	 * most cases)
	 * 
	 * @return false, if the action could not be performed, because the timer
	 *         has not expired yet
	 */
	public boolean primaryAttack() {
		return _primaryAttack.perform();
	}

	/**
	 * Asks the {@link Mech} to perform the special-attack-action (e.g. for
	 * {@link Nitro}: spawning a shield)
	 * 
	 * @return false, if the action could not be performed, because the timer
	 *         has not expired yet
	 */
	public boolean specialAttack() {
		return _specialAttack.perform();
	}

	/**
	 * Asks the {@link Mech} to perform the secondary-attack-action (a
	 * melee-attack in most cases)
	 * 
	 * @return false, if the action could not be performed, because the timer
	 *         has not expired yet
	 */
	public boolean secondaryAttack() {
		return _secondaryAttack.perform();
	}

	/**
	 * Asks the {@link Mech} to perform the block-action. Successfully executing
	 * it will cause the {@link Mech} to go into {@link MovableState#BLOCKING}
	 * -state.
	 * 
	 * @return false, if the {@link Mech} could not block for any reasons
	 */
	public boolean block() {
		final boolean blocking = _block.perform();
		if (blocking) {
			_state.add(MovableState.BLOCKING);
		}
		return blocking;
	}

	/**
	 * Asks the {@link Mech} to perform the unblock-action. Successfully
	 * executing it will cause the {@link Mech} to be no longer in
	 * {@link MovableState#BLOCKING}-state.
	 * 
	 * @return false, if the {@link Mech} could not unblock for any reasons
	 */
	public boolean unblock() {
		final boolean unblocked = _unblock.perform();
		if (unblocked) {
			_state.remove(MovableState.BLOCKING);
		}
		return unblocked;
	}

	/**
	 * Asks the {@link Mech} to perform the jump-action. Successfully executing
	 * it will cause the {@link Mech} to go into {@link MovableState#JUMPING}
	 * -state.
	 * 
	 * @return false, if the {@link Mech} could not jump (not being able to jump
	 *         at all, already jumping, being restricted because of blocking
	 *         walls etc.)
	 */
	public boolean jump() {
		final boolean jumping = _jump.perform();
		if (jumping) {
			_state.add(MovableState.JUMPING);
		}
		return jumping;
	}

	/**
	 * Asks the {@link Mech} to perform the item-action.
	 * 
	 * @return false, if no item could be used (no item equipped or timer of
	 *         equipped item not expired yet)
	 */
	public boolean useItem() {
		return _itemUse.perform();
	}
}
