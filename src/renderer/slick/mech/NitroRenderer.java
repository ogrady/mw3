package renderer.slick.mech;

import listener.IEntityListener;

import org.newdawn.slick.Animation;

import renderer.slick.MovableRenderer;
import environment.IDamageSource;
import environment.Movable;

public class NitroRenderer extends MovableRenderer implements IEntityListener {
	private final Animation walking, jumping, broken, shielded;

	public NitroRenderer(final Movable pos) {
		super(pos);
		final float factor = 2;
		walking = new Animation(loadScaledSpriteSheet("rsc/nitro/walking.png",
				43, 48, factor), 100);
		jumping = new Animation(loadScaledSpriteSheet("rsc/nitro/flying.png",
				56, 47, factor), 100);
		broken = new Animation(loadScaledSpriteSheet("rsc/nitro/broken.png",
				47, 48, factor), 100);
		shielded = new Animation(loadScaledSpriteSheet(
				"rsc/nitro/shielded.png", 53, 48, factor), 100);
		setCurrentAnimation(walking);
	}

	@Override
	public void onLeftButton() {
		setCurrentAnimation(walking);
		_direction = -1;

	}

	@Override
	public void onRightButton() {
		setCurrentAnimation(walking);
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
		setCurrentAnimation(shielded);
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

	@Override
	public void onTakeDamage(IDamageSource src, int amount) {
		// TODO Auto-generated method stub
	}
}
