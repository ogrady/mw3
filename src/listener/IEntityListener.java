package listener;

import environment.IDamageSource;

/**
 * Entities as the main actors of the game are throwing special events when
 * interacting with the world in some kind.
 * 
 * @author Daniel
 * 
 */
public interface IEntityListener extends IListener {
	/**
	 * Thrown when an entity receives damage. Might be followed by
	 * {@link IEntityListener#onDie()}.
	 * 
	 * @param src
	 *            source that dealt the damage
	 * @param amount
	 *            amount of damage that was dealt
	 */
	void onTakeDamage(IDamageSource src, int amount);

	/**
	 * Thrown when the entity dies. Note that you will most likely receive
	 * {@link IEntityListener#onTakeDamage(IDamageSource, int)} right before
	 * receiving this event if the entitiy did not die from other means like
	 * admin-kills or such.
	 */
	void onDie();

	/**
	 * Thrown when the entity is spawned on the battlefield
	 */
	void onSpawn();

	/**
	 * Thrown when the entity's health is restored to 100%
	 */
	void onFullHeal();
}
