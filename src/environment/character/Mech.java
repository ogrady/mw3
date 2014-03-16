package environment.character;

import org.newdawn.slick.geom.Vector2f;

import environment.Actor;

/**
 * This is the intermediate class between {@link Actor}s and specialized mechs
 * (Nitro, Havoc, Spider, ...). Its purpose is to have another abstraction layer
 * to add new features that apply to all mechs, but not for all {@link Actor}s.
 * 
 * @author Daniel
 * 
 */
public abstract class Mech extends Actor {
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
	}
}
