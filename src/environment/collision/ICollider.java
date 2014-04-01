package environment.collision;

import java.util.Collection;

import level.Block;
import environment.IDamageSource;
import environment.Positionable;

/**
 * Interface for objects that handle the collisions
 * 
 * @author Daniel
 * 
 */
public interface ICollider {

	ICollidable getCollidable();

	void handleCollisions();

	/**
	 * Checks for collisions at the current position of the held object
	 * 
	 * @return a list of objects we collide with or an empty list of no
	 *         collisions occur
	 */
	Collection<Positionable> getCollisions();

	/**
	 * Checks whether our collidable collides with some positionable.<br>
	 * When a collidable C0 looks for objects he collides with it should call
	 * the collides-method of C1 to Cn and collect those results to have a
	 * complete list of collisions do some boundry check.<br>
	 * 
	 * @param other
	 *            the object to check for collisions
	 * @return true, if the held collidable and the passed objects collide
	 */
	boolean collides(Positionable other);

	/**
	 * Collide with a positionable
	 * 
	 * @param positionable
	 */
	void onPositionableCollide(Positionable positionable);

	/**
	 * Collide with a damagesource. Usually called from another collider
	 * 
	 * @param dmgsrc
	 */
	void onDamageSourceCollide(IDamageSource dmgsrc);

	/**
	 * Collide with a block
	 * 
	 * @param block
	 */
	void onBlockCollide(Block block);
}
