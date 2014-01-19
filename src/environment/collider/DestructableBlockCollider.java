package environment.collider;

import level.Block;
import environment.IDamageSource;

public class DestructableBlockCollider extends DefaultCollider<Block> {

	public DestructableBlockCollider(final Block block) {
		super(block);
	}

	@Override
	public void onDamageSourceCollide(final IDamageSource dmgsrc) {
		_collidable.destroy();
	}

}
