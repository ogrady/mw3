package environment.character.mech;

import java.util.HashMap;
import java.util.Iterator;

import listener.ICharacterActionListener;

import org.newdawn.slick.Input;
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
	public enum CharacterActionName {
		PRIMARY_ATTACK, SECONDARY_ATTACK, SPECIAL_ATTACK, JUMP, BLOCK, UNBLOCK, USE_ITEM, ARM_UP, ARM_DOWN
	}

	/**
	 * This contains a mapping of {@link CharacterActionName} to
	 * {@link CharacterAction}s. Useful for iterating over ALL
	 * {@link CharacterAction}s and for accessing specific actions via a
	 * {@link CharacterActionName} as key, instead of having a getter / setter
	 * for each of them
	 */
	private final HashMap<CharacterActionName, CharacterAction> _characterActions;

	/**
	 * Replaces an old {@link CharacterAction} with a new one. All listeners
	 * from the old action will be transfered to the new one.<br>
	 * That is useful when an action removes a certain state after ending. E.g.
	 * the action for special attack could have a listener, that removes the
	 * {@link MovableState#SPECIAL} state after the action was executed. When
	 * replacing the special-action with another action, we still want to
	 * preserve the mechanism of automatically removing the state, once the
	 * action has ended.
	 *
	 * @param key
	 *            the {@link CharacterActionName} of the {@link CharacterAction}
	 *            the passed action should be mapped to. NULL will be ignored
	 * @param value
	 *            the new {@link CharacterAction} we want to inject into the
	 *            {@link Mech}. NULL will be ignored
	 */
	protected void setCharacterAction(final CharacterActionName key,
			final CharacterAction value) {
		if (key != null && value != null) {
			final CharacterAction currentAction = _characterActions.get(key);
			if (currentAction != null) {
				final Iterator<ICharacterActionListener> it = currentAction
						.getListeners().iterator();
				while (it.hasNext()) {
					value.getListeners().registerListener(it.next());
				}
				currentAction.getListeners().unregisterAll();
			}
			_characterActions.put(key, value);
		}
	}

	/**
	 * Getter for a specific {@link CharacterAction}, identified by its name
	 *
	 * @param key
	 *            the name of the action
	 * @return the identified action. Is never null and at least an
	 *         {@link EmptyCharacterAction}
	 */
	public CharacterAction getCharacterAction(final CharacterActionName key) {
		assert _characterActions.get(key) != null;
		return _characterActions.get(key);
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
		_characterActions = new HashMap<CharacterActionName, CharacterAction>(
				CharacterActionName.values().length);
		// make sure every character action has at least an empty action. Those
		// don't do anything and can be replaced in the subclasses.
		for (final CharacterActionName key : CharacterActionName.values()) {
			setCharacterAction(key, new EmptyCharacterAction());
		}
	}

	abstract public void armUp();
	abstract public void armDown();

	/**
	 * Asks the {@link Mech} to perform the primary-attack-action (shooting in
	 * most cases)
	 *
	 * @return false, if the action could not be performed, because the timer
	 *         has not expired yet
	 */
	public boolean primaryAttack() {
		return getCharacterAction(CharacterActionName.PRIMARY_ATTACK).perform();
	}

	/**
	 * Asks the {@link Mech} to perform the special-attack-action (e.g. for
	 * {@link Nitro}: spawning a shield)
	 *
	 * @return false, if the action could not be performed, because the timer
	 *         has not expired yet
	 */
	public boolean specialAttack() {
		final boolean performing = getCharacterAction(
				CharacterActionName.SPECIAL_ATTACK).perform();
		if (performing) {
			_state.add(MovableState.SPECIAL);
		}
		return performing;
	}

	/**
	 * Asks the {@link Mech} to perform the secondary-attack-action (a
	 * melee-attack in most cases)
	 *
	 * @return false, if the action could not be performed, because the timer
	 *         has not expired yet
	 */
	public boolean secondaryAttack() {
		return getCharacterAction(CharacterActionName.SECONDARY_ATTACK)
				.perform();
	}

	/**
	 * Asks the {@link Mech} to perform the block-action. Successfully executing
	 * it will cause the {@link Mech} to go into {@link MovableState#BLOCKING}
	 * -state.
	 *
	 * @return false, if the {@link Mech} could not block for any reasons
	 */
	public boolean block() {
		final boolean blocking = getCharacterAction(CharacterActionName.BLOCK)
				.perform();
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
		final boolean unblocked = getCharacterAction(
				CharacterActionName.UNBLOCK).perform();
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
		final boolean jumping = getCharacterAction(CharacterActionName.JUMP)
				.perform();
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
		return getCharacterAction(CharacterActionName.USE_ITEM).perform();
	}

	@Override
	public void onTick(final Input input, final int delta) {
		super.onTick(input, delta);
		// forward the tick to all registered actions, to decrease their delay
		for (final CharacterAction action : _characterActions.values()) {
			action.getDelay().tick(delta);
		}
	}
}
