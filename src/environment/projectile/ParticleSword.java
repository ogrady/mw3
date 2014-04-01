package environment.projectile;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import renderer.slick.Slick2DRenderer;
import util.Const;
import controller.projectile.GenericProjectileController;
import environment.character.mech.Nitro;
import environment.collision.ProjectileCollider;
import game.MetalWarriors;
import game.Viewport;

/**
 * Particle sword from {@link Nitro}. Spawns at the current position of
 * {@link Nitro} and is an invisible damagesource with (technically) infinite
 * hits before it despawns. The only thing that actually causes it to despawn is
 * when it traveled its maximum distance (which is {@link Nitro}s height from
 * top to bottom).<br>
 * As it is invisible, its speed has to match the sword-animation to always be
 * at the tip of the sword in the animation.
 * 
 * @author Daniel
 * 
 */
public class ParticleSword extends Projectile {
	/**
	 * this determines the size of the hitbox
	 */
	public static final int SWORDSIZE = 30;
	/**
	 * the speed by which the sword moves (has to be adjusted to match the
	 * animation)
	 */
	private static final Vector2f SPEED = new Vector2f(0, 3);
	private final Vector2f _delta;
	private final Vector2f _offset;
	private final float _toTravel;

	public ParticleSword(final Nitro source) {
		super(new Vector2f(), new Vector2f(), Const.NITRO_SWORD_DMG, source
				.getHeight() - SWORDSIZE, source);
		_offset = new Vector2f();
		_delta = SPEED;
		setWidth(SWORDSIZE);
		setHeight(SWORDSIZE);
		_currentPosition = source.getPosition();
		_toTravel = source.getHeight() - SWORDSIZE;
		setController(new GenericProjectileController(this));
		setCollider(new ProjectileCollider(this, Integer.MAX_VALUE));
		// the whole renderer is for debugging purposes and will be replaced
		// with a defaultrenderer
		setRenderer(new Slick2DRenderer() {

			@Override
			public void render(final Graphics g, final Viewport vp) {
				g.draw(getHitbox());
			}

			@Override
			public void update(final long delta) {
			}
		});
		MetalWarriors.instance.getListeners().registerListener(this);
	}

	@Override
	public Vector2f getPosition() {
		final Vector2f pos = _currentPosition.copy().add(_offset);
		pos.x += _source.getDirection() == 1 ? _source.getWidth() : -SWORDSIZE;
		return pos;
	}

	@Override
	public boolean moveOn() {
		_offset.add(_delta);
		if (_offset.length() >= _toTravel) {
			destruct();
		}
		return true;
	}
}
