package environment.collision;

import java.util.ArrayList;
import java.util.List;

import level.Block;
import environment.IDamageSource;
import environment.Positionable;

/**
 * The nevercollider never collides with anything. This can be used for blocks
 * which are simply empty (which have to have some kind of collider)
 * 
 * @author Daniel
 * 
 */
public class NeverCollider implements ICollider {
	private final ICollidable _collidable;

	public NeverCollider(final ICollidable collidable) {
		_collidable = collidable;
	}

	@Override
	public ICollidable getCollidable() {
		return _collidable;
	}

	@Override
	public List<Positionable> getCollisions() {
		return new ArrayList<Positionable>();
	}

	@Override
	public boolean collides(final Positionable other) {
		return false;
	}

	@Override
	public void onPositionableCollide(final Positionable positionable) {
	}

	@Override
	public void onDamageSourceCollide(final IDamageSource dmgsrc) {
	}

	@Override
	public void onBlockCollide(final Block block) {
	}

	@Override
	public void handleCollisions() {

	}

}
