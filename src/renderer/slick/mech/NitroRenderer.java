package renderer.slick.mech;

import org.newdawn.slick.Animation;

import renderer.slick.MovableRenderer;
import environment.Movable;

public class NitroRenderer extends MovableRenderer {
	private final Animation walking, jumping;

	public NitroRenderer(final Movable pos) {
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
}
