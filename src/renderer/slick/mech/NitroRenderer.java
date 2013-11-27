package renderer.slick.mech;

import listener.IEntityListener;

import org.newdawn.slick.Animation;

import renderer.slick.MovableRenderer;
import environment.Actor;
import environment.IDamageSource;

public class NitroRenderer extends MovableRenderer<Actor> implements
		IEntityListener {
	private final Animation walking, jumping, broken, shielded;

	public NitroRenderer(final Actor pos) {
		super(pos);
		final float factor = 2;
		walking = new Animation(loadScaledSpriteSheet("rsc/nitro/walking.png",
				36, 45, factor), 60);
		jumping = new Animation(loadScaledSpriteSheet("rsc/nitro/flying.png",
				40, 47, factor), 60);
		broken = new Animation(loadScaledSpriteSheet("rsc/nitro/broken.png",
				47, 48, factor), 60);
		shielded = new Animation(loadScaledSpriteSheet(
				"rsc/nitro/shielded.png", 40, 46, factor), 60);
		shielded.setLooping(false);
		this.setIdle();
	}

	protected void setIdle() {
		this.setCurrentAnimation(walking);
		walking.stop();
	}

	@Override
	public void onLeftButton(final boolean down) {
		if (!down) {
			this.setIdle();
		} else {
			if (this._current == walking) {
				walking.start();
			} else {
				setCurrentAnimation(walking);
			}
			_direction = -1;
		}
	}

	@Override
	public void onRightButton(final boolean down) {
		if (!down) {
			this.setIdle();
		} else {
			if (this._current == walking) {
				walking.start();
			} else {
				setCurrentAnimation(walking);
			}
			_direction = 1;
		}
	}

	@Override
	public void onUpButton(final boolean down) {
	}

	@Override
	public void onDownButton(final boolean down) {

	}

	@Override
	public void onItemButton(final boolean down) {

	}

	@Override
	public void onBlockButton(final boolean down) {
	}

	@Override
	public void onSecondaryAttackButton(final boolean down) {

	}

	@Override
	public void onJumpButton(final boolean down) {
		if (!down) {
			setIdle();
		} else {
			setCurrentAnimation(jumping);
		}
	}

	@Override
	public void onSpecialActionButton(final boolean down) {
		if (!down) {
			setIdle();
		} else {
			setCurrentAnimation(shielded);
		}
	}

	@Override
	public void onPrimaryAttackButton(final boolean down) {

	}

	@Override
	public void onSelectButton(final boolean down) {

	}

	@Override
	public void onStartButton(final boolean down) {

	}

	@Override
	public void onTakeDamage(final IDamageSource src, final int amount) {

	}

	@Override
	public void onDie() {
		setCurrentAnimation(broken);
	}

	@Override
	public void onSpawn() {
		System.out.println("there we go");
	}

	@Override
	public void onFullHeal() {
	}
}
