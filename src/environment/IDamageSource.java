package environment;

/**
 * Damagesources can be various things, such as bullets, swords, explosions or
 * even a mech when launching itself into an object.
 * 
 * @author Daniel
 * 
 */
public interface IDamageSource {
	/**
	 * @return the base damage the source inflicts which can be further reduced
	 *         by shields
	 */
	float getBaseDamage();
}
