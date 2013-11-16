package environment.collider;

/**
 * Collidables have to ability to collide with other abilities. The effect of
 * those collisions will be determined by the current collider. Collidables can
 * dynamically exchange their collider to have different effects upon
 * collisions.<br>
 * E.g. some mechs might have different behaviour when hitting a wall based on
 * their internal state (ballista might launch itself into a wall to destroy it
 * where just walking into it has no effect)
 * 
 * @author Daniel
 * 
 */
public interface ICollidable {
	/**
	 * @return the current collider
	 */
	ICollider getCollider();

	/**
	 * @param newCollider
	 *            the new collider
	 */
	void setCollider(ICollider newCollider);
}
