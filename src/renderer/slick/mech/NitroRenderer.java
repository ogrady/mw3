package renderer.slick.mech;

import listener.IEntityListener;

import org.newdawn.slick.Animation;

import renderer.slick.MovableRenderer;
import environment.Actor;
import environment.IDamageSource;

public class NitroRenderer extends MovableRenderer implements IEntityListener {
	private final Animation walking, jumping;

	public NitroRenderer(final Actor pos) {
		super(pos);
		final float factor = 2;
		walking = new Animation(loadScaledSpriteSheet("rsc/nitro/walking.png",
				43, 48, factor), 100);
		jumping = new Animation(loadScaledSpriteSheet("rsc/nitro/flying.png",
				56, 47, factor), 100);
		setCurrentAnimation(walking);
	}

	@Override
	public void onLeftButton(final boolean down) {
		_direction = -1;

	}

	@Override
	public void onRightButton(final boolean down) {
		_direction = 1;
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
		setCurrentAnimation(jumping);
	}

	@Override
	public void onSpecialActionButton(final boolean down) {

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

	}

	@Override
	public void onSpawn() {
		System.out.println("there we go");
	}

	@Override
	public void onFullHeal() {
	}
}
