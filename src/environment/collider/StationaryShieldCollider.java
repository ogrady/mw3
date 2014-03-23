package environment.collider;

import environment.IDamageSource;
import environment.character.StationaryShield;

/**
 * Collider for {@link StationaryShield}s from Nitro. Those only collide with
 * damage-sources, while other entities can pass through them.
 * 
 * @author Daniel
 * 
 */
public class StationaryShieldCollider extends DefaultCollider<StationaryShield> {
	private final StationaryShield _shield;

	/**
	 * Constrcutor
	 * 
	 * @param shield
	 *            the logical {@link StationaryShield}
	 */
	public StationaryShieldCollider(final StationaryShield shield) {
		super(shield);
		_shield = shield;
	}

	@Override
	public void onDamageSourceCollide(final IDamageSource dmgsrc) {
		_shield._hp -= dmgsrc.getBaseDamage();
		if (_shield._hp <= 0) {
			_shield.destruct();
		}
	}
}