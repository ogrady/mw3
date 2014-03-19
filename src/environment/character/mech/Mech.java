package environment.character.mech;

import org.newdawn.slick.geom.Vector2f;

import environment.Actor;
import environment.MovableState;
import environment.character.CharacterAction;
import environment.character.EmptyCharacterAction;

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
			_jump, _block, _itemUse;

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
	}

	public void tick(final long delta) {
		_primaryAttack.getDelay().tick(delta);
		_secondaryAttack.getDelay().tick(delta);
		_specialAttack.getDelay().tick(delta);
		_block.getDelay().tick(delta);
		_jump.getDelay().tick(delta);
		_itemUse.getDelay().tick(delta);
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
