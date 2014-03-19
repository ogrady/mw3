package renderer.slick.mech;

import listener.IActorListener;
import listener.IEnumBitmaskListener;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import renderer.slick.IAnimationListener;
import renderer.slick.MovableRenderer;
import renderer.slick.ObservableAnimation;
import util.Bitmask;
import environment.IDamageSource;
import environment.Movable;
import environment.character.mech.Nitro;
import game.Viewport;

public class NitroRenderer extends MovableRenderer<Movable> implements
		IActorListener, IEnumBitmaskListener {
	private final ObservableAnimation walking, jumping, broken, shielded,
			flyingPrelude, arm;
	final float factor = 2;

	public NitroRenderer(final Nitro pos) {
		super(pos);
		walking = new ObservableAnimation(loadScaledSpriteSheet(
				"rsc/nitro/walking.png", 36, 45, factor), 60);
		jumping = new ObservableAnimation(loadScaledSpriteSheet(
				"rsc/nitro/flying.png", 40, 47, factor), 60);
		broken = new ObservableAnimation(loadScaledSpriteSheet(
				"rsc/nitro/broken.png", 47, 48, factor), 60);
		shielded = new ObservableAnimation(loadScaledSpriteSheet(
				"rsc/nitro/shielded.png", 40, 46, factor), 60);
		shielded.setLooping(false);
		arm = new ObservableAnimation(loadScaledSpriteSheet(
				"rsc/nitro/arm.png", 29, 46, factor), 60);
		arm.setCurrentFrame(arm.getFrameCount() / 2);
		flyingPrelude = new ObservableAnimation(loadScaledSpriteSheet(
				"rsc/nitro/start_flying.png", 45, 50, factor), 100);
		flyingPrelude.setLooping(true);
		this.setIdle();
	}

	protected void setIdle() {
		this.setCurrentAnimation(walking);
		_renderable.setWidth(walking.getWidth());
		_renderable.setHeight(walking.getHeight());
		walking.stop();
	}

	@Override
	public void onLeftButton(final boolean down) {
		if (!flying) {
			if (!down) {
				this.setIdle();
			} else {
				if (this._current == walking) {
					walking.start();
				} else {
					setCurrentAnimation(walking);
				}
			}
		}
		_renderable.setDirection(-1);
	}

	@Override
	public void onRightButton(final boolean down) {
		if (!flying) {
			if (!down) {
				this.setIdle();
			} else {
				if (this._current == walking) {
					walking.start();
				} else {
					setCurrentAnimation(walking);
				}
			}
		}
		_renderable.setDirection(1);
	}

	@Override
	public void onUpButton(final boolean down) {
		if (arm.getFrame() < arm.getFrameCount() - 1) {
			arm.setCurrentFrame(arm.getFrame() + 1);
		}
	}

	@Override
	public void onDownButton(final boolean down) {
		if (arm.getFrame() > 0) {
			arm.setCurrentFrame(arm.getFrame() - 1);
		}
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

	private boolean flying = false;

	@Override
	public void onJumpButton(final boolean down) {
		if (!down) {
			flying = false;
			flyingPrelude.clearListeners();
			setIdle();
		} else {
			if (!flying) {
				flyingPrelude.addListener(new IAnimationListener() {
					@Override
					public void onEnded() {
						jumping.setLooping(true);
						setCurrentAnimation(jumping);
					}
				});
				if (_current != jumping) {
					flyingPrelude.setCurrentFrame(0);
					setCurrentAnimation(flyingPrelude);
				}
			}
			flying = true;
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

	@Override
	public void render(final Graphics g, final Viewport vp) {
		super.render(g, vp);
		final Image frame = arm.getCurrentFrame();
		final float adjustedCenter = (_renderable.getWidth() - frame.getWidth()) / 2;
		final float adjustedX = adjustedCenter + _renderable.getDirection()
				* 11 * factor;
		final float adjustedY = 7 * factor;
		frame.draw(_renderable.getPosition().x + adjustedX,
				_renderable.getPosition().y - adjustedY,
				(_renderable.getDirection() - 1) * -frame.getWidth() / 2, 0,
				(_renderable.getDirection() + 1) * frame.getWidth() / 2,
				frame.getHeight());
	}

	@Override
	public void onChange(final Bitmask mask) {
		System.out.println("bitmask changed");
	}
}
