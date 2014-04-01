package controller.projectile;

import org.newdawn.slick.Input;

import controller.IControllable;
import controller.IController;
import environment.projectile.Projectile;

/**
 * {@link GenericProjectileController}s are rather simple controllers. Normally
 * they are not even dependent on player-input but rather tell their controlled
 * projectile to keep on flying. Some controllers might want to manipulate the
 * delta-vector of their bullet to simulate intersting movement, such as
 * jittered ballistics or even homing missiles.
 * 
 * 
 * @author Daniel
 * 
 */
public class GenericProjectileController implements IController {
	private Projectile _projectile;

	/**
	 * Contructor
	 * 
	 * @param projectile
	 *            projectile to control
	 */
	public GenericProjectileController(final Projectile projectile) {
		setControllable(projectile);
	}

	@Override
	public IControllable getControllable() {
		return _projectile;
	}

	@Override
	public void setControllable(final IControllable controllable) {
		if (controllable instanceof Projectile) {
			_projectile = (Projectile) controllable;
		} else {
			throw new IllegalArgumentException(
					String.format(
							"controllables for %s must be of class 'Projectile'. An instance of %s was passed instead.",
							getClass().getSimpleName(), controllable.getClass()
									.getSimpleName()));
		}
	}

	@Override
	public void update(final Input input, final int delta) {
		_projectile.moveOn();
		_projectile.getCollider().handleCollisions();
		if (_projectile.getTraveledDistance() > _projectile
				.getMaxTravelDistance()) {
			_projectile.destruct();
		}
	}

}
