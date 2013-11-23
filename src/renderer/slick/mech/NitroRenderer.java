package renderer.slick.mech;

import listener.IEntityListener;

import org.newdawn.slick.Animation;

import renderer.slick.MovableRenderer;
import environment.Entity;
import environment.IDamageSource;

public class NitroRenderer extends MovableRenderer implements IEntityListener {
	private final Animation walking, jumping;

	public NitroRenderer(final Entity pos) {
		super(pos);
		final float factor = 2;
		walking = new Animation(loadScaledSpriteSheet("rsc/nitro/walking.png",
				43, 48, factor), 100);
		jumping = new Animation(loadScaledSpriteSheet("rsc/nitro/flying.png",
				56, 47, factor), 100);
		setCurrentAnimation(walking);
	}

	@Override
	public void onLeftButton() {
		_direction = -1;

	}

	@Override
	public void onRightButton() {
		_direction = 1;
	}

	@Override
	public void onUpButton() {

	}

	@Override
	public void onDownButton() {

	}

	@Override
	public void onItemButton() {

	}

	@Override
	public void onBlockButton() {
	}

	@Override
	public void onSecondaryAttackButton() {

	}

	@Override
	public void onJumpButton() {
		setCurrentAnimation(jumping);
	}

	@Override
	public void onSpecialActionButton() {

	}

	@Override
	public void onPrimaryAttackButton() {

	}

	@Override
	public void onSelectButton() {

	}

	@Override
	public void onStartButton() {

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
