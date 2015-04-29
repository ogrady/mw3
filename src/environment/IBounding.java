package environment;

/**
 * To be implemented by anything that has some sort of hitbox in form of one or more
 * {@link Shape}, containing the element.
 * 
 * @author Daniel
 * 
 */
public interface IBounding {
	/**
	 * @return an array of {@link Shape} containing the element for collision checking
	 */
	Hitbox getHitbox();
}
