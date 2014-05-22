package environment.collision;

import level.Block;
import environment.IDamageSource;

/**
 * Collider for destructable {@link Block}s, that will be destroyed once they
 * come into contact with any {@link IDamageSource}.
 *
 * @author Daniel
 *
 */
public class DestructableBlockCollider extends DefaultCollider<Block> {

	/**
	 * Constructor
	 *
	 * @param block
	 *            the {@link Block} that will be destroyed, once this collider
	 *            touches a {@link IDamageSource}
	 */
	public DestructableBlockCollider(final Block block) {
		super(block);
	}

	@Override
	public void onDamageSourceCollide(final IDamageSource dmgsrc) {
		_collidable.destroy();
	}

}
