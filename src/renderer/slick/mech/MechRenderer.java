package renderer.slick.mech;

import java.util.HashMap;

import listener.IActorListener;

import org.newdawn.slick.geom.Vector2f;

import renderer.slick.MovableRenderer;
import renderer.slick.ObservableAnimation;
import environment.character.mech.Mech;

/**
 * Renderers for mechs. They have animations for the basic movements and states
 * (have to be filled in in the subclasses) and maintain a hashmap of images for
 * the armposition.
 * 
 * @author Daniel
 * 
 */
abstract public class MechRenderer extends MovableRenderer<Mech> implements
		IActorListener {
	protected HashMap<Integer, ObservableAnimation> _armPositions;
	protected ObservableAnimation _walking, _jumping, _broken, _shielded,
			_flyingPrelude, _arm, _special, _primaryAttack, _secondaryAttack;

	/**
	 * @return the map that contains the animations for all arm-positions
	 */
	public HashMap<Integer, ObservableAnimation> getArmPositions() {
		return _armPositions;
	}

	/**
	 * @return the animation for walking
	 */
	public ObservableAnimation getWalkingAnimation() {
		return _walking;
	}

	/**
	 * @return the animation for jumping
	 */
	public ObservableAnimation getJumpingAnimation() {
		return _jumping;
	}

	/**
	 * @return the animation for when the mech is broken
	 */
	public ObservableAnimation getBrokenAnimation() {
		return _broken;
	}

	/**
	 * @return the animation for when the {@link Mech} is using his shield
	 */
	public ObservableAnimation getShieldedAnimation() {
		return _shielded;
	}

	/**
	 * @return the animation for when the {@link Mech} starts flying
	 */
	public ObservableAnimation getFlyingPreludeAnimation() {
		return _flyingPrelude;
	}

	/**
	 * @return the current arm-animation
	 */
	public ObservableAnimation getArmAnimation() {
		return _arm;
	}

	/**
	 * @return the animation for when the {@link Mech} is performing its special
	 *         movement
	 */
	public ObservableAnimation getSpecialAnimation() {
		return _special;
	}

	/**
	 * @return the animation for the primary attack
	 */
	public ObservableAnimation getPrimaryAttack() {
		return _primaryAttack;
	}

	/**
	 * @return the animation for the secondary attack
	 */
	public ObservableAnimation getSecondaryAttack() {
		return _secondaryAttack;
	}

	/**
	 * Constructor
	 * 
	 * @param renderable
	 */
	public MechRenderer(final Mech mech) {
		super(mech);
		_armPositions = new HashMap<Integer, ObservableAnimation>();
	}

	/**
	 * @return length of the arm, from joint to tip, to determine the exit-point
	 *         for bullets
	 */
	abstract public int getArmLength();

	/**
	 * @return the point around which the arm rotates
	 */
	abstract public Vector2f getArmJoint();

}
