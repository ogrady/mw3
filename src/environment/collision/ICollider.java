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

	/**
	 * @return the {@link ICollidable} this collider takes care of
	 */
	ICollidable getCollidable();

	/**
	 * Constructs a list of occuring collisions and handles them
	 */
	void handleCollisions();

	/**
	 * Checks for collisions at the current position of the held object
	 * 
	 * @return a list of objects we collide with or an empty list of no
	 *         collisions occur
	 */
	Collection<Positionable> getCollisions();

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
