package renderer.slick.mech;

import listener.IAnimationListener;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import renderer.slick.ObservableAnimation;
import util.IBitmask;
import environment.IDamageSource;
import environment.MovableState;
import environment.character.mech.Nitro;
import game.Viewport;

public class NitroRenderer extends MechRenderer {
	final float _factor = 2;

	public NitroRenderer(final Nitro pos) {
		super(pos);
		_walking = new ObservableAnimation(loadScaledSpriteSheet(
				"rsc/nitro/walking.png", 36, 45, _factor), 60);
		_jumping = new ObservableAnimation(loadScaledSpriteSheet(
				"rsc/nitro/flying.png", 40, 47, _factor), 60);
		_broken = new ObservableAnimation(loadScaledSpriteSheet(
				"rsc/nitro/broken.png", 47, 48, _factor), 60);
		// this is the wrong sprite. Just to prevent NULL [Daniel]
		_special = new ObservableAnimation(loadScaledSpriteSheet(
				"rsc/nitro/shielded.png", 40, 46, _factor), 60);
		_shielded = new ObservableAnimation(loadScaledSpriteSheet(
				"rsc/nitro/shielded.png", 40, 46, _factor), 60);
		_shielded.setLooping(false);
		_arm = new ObservableAnimation(loadScaledSpriteSheet(
				"rsc/nitro/arm.png", 29, 46, _factor), 60);
		_arm.setCurrentFrame(_arm.getFrameCount() / 2);
		_flyingPrelude = new ObservableAnimation(loadScaledSpriteSheet(
				"rsc/nitro/start_flying.png", 45, 50, _factor), 100);
		_flyingPrelude.setLooping(true);
		this.setIdle();
	}

	protected void setIdle() {
		this.setCurrentAnimation(_walking);
		_renderable.setWidth(_walking.getWidth());
		_renderable.setHeight(_walking.getHeight());
		_walking.stop();
	}

	@Override
	public void onTakeDamage(final IDamageSource src, final int amount) {

	}

	@Override
	public void onDie() {
		setCurrentAnimation(_broken);
	}

	@Override
	public void onSpawn() {
	}

	@Override
	public void onFullHeal() {

	}

	@Override
	public void render(final Graphics g, final Viewport vp) {
		super.render(g, vp);
		final Image frame = _arm.getCurrentFrame();
		final float adjustedCenter = (_renderable.getWidth() - frame.getWidth()) / 2;
		final float adjustedX = adjustedCenter + _renderable.getDirection()
				* 11 * _factor;
		final float adjustedY = 7 * _factor;
		frame.draw(_renderable.getPosition().x + adjustedX,
				_renderable.getPosition().y - adjustedY,
				(_renderable.getDirection() - 1) * -frame.getWidth() / 2, 0,
				(_renderable.getDirection() + 1) * frame.getWidth() / 2,
				frame.getHeight());
	}

	@Override
	public void onChange(final IBitmask<MovableState> mask) {
		/**
		 * The following is utter bullshit and doesn't work at all. :D I tried
		 * to combine your code from the methods I had to delete in here - but
		 * failed. But I didn't remove the code, so that you don't have to start
		 * your work from scratch and can see the idea of the state-mask.
		 * 
		 * @author Daniel
		 */
		if (mask.has(MovableState.BLOCKING)) {
			setCurrentAnimation(_shielded);
		} else if (!mask.has(MovableState.JUMPING)) {
			_flyingPrelude.clearListeners();
			setIdle();
		} else if (!mask.has(MovableState.FLYING)) {
			_flyingPrelude.addListener(new IAnimationListener() {
				@Override
				public void onEnded() {
					_jumping.setLooping(true);
					setCurrentAnimation(_jumping);
				}
			});
			if (_current != _jumping) {
				_flyingPrelude.setCurrentFrame(0);
				setCurrentAnimation(_flyingPrelude);
			}
		} else if (mask.has(MovableState.MOVING)) {
			if (this._current == _walking) {
				_walking.start();
			} else {
				setCurrentAnimation(_walking);
			}
		} else {
			setIdle();
		}
	}

	@Override
	public Vector2f getArmJoint() {
		return _renderable.getPosition().copy()
				.add(new Vector2f(16 * _factor, 13 * _factor));
	}

	@Override
	public int getArmLength() {
		return (int) (28 * _factor);
	}
}
