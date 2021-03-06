package environment.projectile;

import org.newdawn.slick.geom.Vector2f;

import controller.IController;
import environment.Actor;
import environment.IDamageSource;
import environment.Movable;

/**
 * {@link Projectile} are the baseclass for other projectiles. That means it is
 * just a simple bullet that flys into a certain direction by a certain speed,
 * starting from a certain point.<br>
 * Subclasses can employ additional effects, such as the jitter from Havoc's gun
 * and are responsible to determine what happens when the projectile collides
 * with other entities, such as {@link Actor}s or the world.
 *
 * @author Daniel
 *
 */
public class Projectile extends Movable implements IDamageSource {
	protected Vector2f _deltaVector;
	protected float _traveledDistance, _maxTravelDistance;
	protected Actor _source;
	protected float _baseDamage;

	@Override
	public Actor getProducer() {
		return _source;
	}

	@Override
	public float getBaseDamage() {
		return _baseDamage;
	}

	/**
	 * @return the object that spawned the {@link Projectile}. Useful to
	 *         determine checks for friendly fire and such.
	 */
	public Movable getSource() {
		return _source;
	}

	/**
	 * @return the remaining distance to live, which is a convenience-method for
	 *         building the difference between {@link #getMaxTravelDistance()}
	 *         and {@link #getTraveledDistance()}
	 */
	public float getDistanceToLive() {
		return _maxTravelDistance - _traveledDistance;
	}

	/**
	 * The delta-vector determines the current direction and movement of the
	 * projectile.<br>
	 *
	 * @return the delta-vector of the bullet
	 */
	public Vector2f getDeltaVector() {
		return _deltaVector;
	}

	/**
	 * {@link Projectile}s should despawn after they flew a certain distance
	 * without colliding with anything.
	 *
	 * @return the distance the {@link Projectile} travels before despawning
	 */
	public float getMaxTravelDistance() {
		return _maxTravelDistance;
	}

	/**
	 * The {@link Projectile} itself updates this value whenever it's
	 * {@link #move(float, float)} method is being called. Note that the
	 * {@link Projectile} just maintains the value. The {@link IController} of
	 * the bullet is responsible for removing it from the game when it's
	 * "lifespan" expires.
	 *
	 * @return the distance this {@link Projectile} traveled already
	 */
	public float getTraveledDistance() {
		return _traveledDistance;
	}

	/**
	 * As projectiles have a determined direction the fly into we don't need to
	 * use the {@link #move(float, float)}-method where we have to specify x-
	 * and y-movement. We rather just tell the bullet to move itself by applying
	 * it's own delta-vector to it's current position.
	 *
	 * @return see {@link #move(float, float)}
	 */
	public boolean moveOn() {
		_currentPosition.add(_deltaVector);
		_traveledDistance += _deltaVector.length();
		return true;
		// return move(_deltaVector.x, _deltaVector.y);
	}

	@Override
	public boolean move(final float moveFactorX, final float moveFactorY) {
		final boolean result = super.move(moveFactorX, moveFactorY);
		// update the flown distance
		// _traveledDistance += moveFactorX * _xspeed + moveFactorY * _yspeed;
		return result;
	}

	@Override
	public void applyGravity(final float g) {
		// by default, projectiles aren't affected by gravity
	}

	/**
	 * Constructor
	 *
	 * @param position
	 *            starting position (most likely the tip of a gun). Position
	 *            will be copied and therefore has no connection to
	 *            {@link Actor}s when their position is directly passed through
	 *            {@link Actor#getPosition()}
	 * @param deltaVector
	 *            delta-vector that specifies direction and magnitude of the
	 *            movement
	 * @param damage
	 *            damage the {@link Projectile} inflicts upon hitting
	 * @param maxTravelDistance
	 *            distance in pixel after which the projectile despawns
	 * @param source
	 *            the {@link Actor} that created the bullet
	 */
	public Projectile(final Vector2f position, final Vector2f deltaVector,
			final float damage, final float maxTravelDistance,
			final Actor source) {
		super(position.copy(), 5, 5, 0.0f, deltaVector.length());
		_baseDamage = damage;
		_deltaVector = deltaVector;
		_maxTravelDistance = maxTravelDistance;
		_source = source;
	}
}
