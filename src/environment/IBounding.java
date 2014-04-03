package environment;

import org.newdawn.slick.geom.Shape;

/**
 * To be implemented by anything that has some sort of hitbox in form of a
 * {@link Shape}, containing the element.
 * 
 * @author Daniel
 * 
 */
public interface IBounding {
	/**
	 * @return a {@link Shape} containing the element for collision checking
	 */
	Shape getHitbox();
}
