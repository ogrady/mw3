package listener;

import environment.Actor;
import environment.IDamageSource;

/**
 * {@link Actor}s as the main actors of the game are throwing special events
 * when interacting with the world in some kind.
 *
 * @author Daniel
 *
 */
public interface IActorListener extends IListener {
	/**
	 * Thrown when an {@link Actor} receives damage. Might be followed by
	 * {@link IActorListener#onDie()}.
	 *
	 * @param src
	 *            source that dealt the damage
	 * @param amount
	 *            amount of damage that was dealt
	 */
	default void onTakeDamage(final IDamageSource src, final int amount) {
	}

	/**
	 * Thrown when the {@link Actor} dies. Note that you will most likely
	 * receive {@link IActorListener#onTakeDamage(IDamageSource, int)} right
	 * before receiving this event if the entity did not die from other means
	 * like admin-kills or such.
	 */
	default void onDie() {
	}

	/**
	 * Thrown when the {@link Actor} is spawned on the battlefield
	 */
	default void onSpawn() {
	}

	/**
	 * Thrown when the {@link Actor}'s health is restored to 100%
	 */
	default void onFullHeal() {
	}
}
