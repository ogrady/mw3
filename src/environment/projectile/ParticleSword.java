package environment.projectile;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import renderer.slick.Slick2DRenderer;
import util.Const;
import controller.projectile.GenericProjectileController;
import environment.character.mech.Nitro;
import environment.collider.ProjectileCollider;
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
	public static final int SWORDSIZE = 30;

	public ParticleSword(final Vector2f start, final Vector2f delta,
			final Nitro source) {
		super(start, delta, Const.NITRO_SWORD_DMG, source.getHeight()
				- SWORDSIZE, source);
		_currentPosition = start;
		setWidth(SWORDSIZE);
		setHeight(SWORDSIZE);
		setController(new GenericProjectileController(this));
		setCollider(new ProjectileCollider(this, Integer.MAX_VALUE));
		// the whole renderer is for debugging purposes and will be replaced
		// with a defaultrenderer
		setRenderer(new Slick2DRenderer() {

			@Override
			public void render(final Graphics g, final Viewport vp) {
				final Vector2f pos = ParticleSword.this._currentPosition;
				final float w = ParticleSword.this.getWidth();
				final float h = ParticleSword.this.getHeight();

				g.fillRect(pos.x, pos.y, w, h);
			}

			@Override
			public void update(final long delta) {
			}
		});
		MetalWarriors.instance.getListeners().registerListener(this);
	}
}
