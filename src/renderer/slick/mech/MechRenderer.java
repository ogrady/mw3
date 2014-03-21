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
			_flyingPrelude, _arm;

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
