package renderer.slick.mech;

import listener.IAnimationListener;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import renderer.slick.ObservableAnimation;
import util.Const;
import util.IBitmask;
import environment.IDamageSource;
import environment.MovableState;
import environment.character.mech.Nitro;
import game.Viewport;

public class NitroRenderer extends MechRenderer {

	public NitroRenderer(final Nitro pos) {
		super(pos);
		pos.getState().getListeners().registerListener(this);
		_walking = new ObservableAnimation(loadScaledSpriteSheet(
				Const.NITRO_RSC + "walking.png", 36, 45, Const.SCALE_FACTOR),
				60);
		_jumping = new ObservableAnimation(loadScaledSpriteSheet(
				Const.NITRO_RSC + "flying.png", 40, 47, Const.SCALE_FACTOR), 60);
		_broken = new ObservableAnimation(loadScaledSpriteSheet(Const.NITRO_RSC
				+ "broken.png", 47, 48, Const.SCALE_FACTOR), 60);
		// this is the wrong sprite. Just to prevent NULL [Daniel]
		_special = new ObservableAnimation(loadScaledSpriteSheet(
				Const.NITRO_RSC + "shielded.png", 40, 46, Const.SCALE_FACTOR),
				60);
		_shielded = new ObservableAnimation(loadScaledSpriteSheet(
				Const.NITRO_RSC + "shielded.png", 40, 46, Const.SCALE_FACTOR),
				60);
		_shielded.setLooping(false);
		_arm = new ObservableAnimation(loadScaledSpriteSheet(Const.NITRO_RSC
				+ "arm.png", 29, 46, Const.SCALE_FACTOR), 60);
		_arm.setCurrentFrame(_arm.getFrameCount() / 2);
		_flyingPrelude = new ObservableAnimation(loadScaledSpriteSheet(
				Const.NITRO_RSC + "start_flying.png", 45, 50,
				Const.SCALE_FACTOR), 100);
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
		g.setColor(Color.black);
		g.drawString("" + _renderable.getCurrentLife(),
				_renderable.getPosition().x, _renderable.getPosition().y - 20);
		_arm.start();
		_arm.setCurrentFrame(_renderable.getArmPosition());
		final Image frame = _arm.getCurrentFrame();
		final float adjustedCenter = (_renderable.getWidth() - frame.getWidth()) / 2;
		final float adjustedX = adjustedCenter + _renderable.getDirection()
				* 11 * Const.SCALE_FACTOR;
		final float adjustedY = 7 * Const.SCALE_FACTOR;
		frame.draw(_renderable.getPosition().x + adjustedX,
				_renderable.getPosition().y - adjustedY,
				(_renderable.getDirection() - 1) * -frame.getWidth() / 2, 0,
				(_renderable.getDirection() + 1) * frame.getWidth() / 2,
				frame.getHeight());
	}

	/**
	 * This method is just a stub for backwards-compatibility and is called from
	 * both, {@link #onAdd(IBitmask, MovableState)} and
	 * {@link #onRemove(IBitmask, MovableState)} without utilizing their second
	 * parameter. You might want to use that second parameter to probe the
	 * hashmap of behaviours you planned on implementing and remove this method
	 * entirely. So you can differentiate whether a state was added or removed
	 * and which state it was - that's all I can provide you with for now,
	 * sorry. I hope that works out for you.
	 *
	 * @author Daniel
	 */
	public void handleBitmaskChanges(final IBitmask<MovableState> mask,
			final MovableState added, final MovableState removed) {
		/**
		 * The following doesn't work at all. :D I tried to combine your code
		 * from the methods I had to delete in here - but failed. But I didn't
		 * remove the code, so that you don't have to start your work from
		 * scratch and can see the idea of the state-mask.
		 *
		 * @author Daniel
		 */
		if (mask.has(MovableState.BLOCKING)) {
			setCurrentAnimation(_shielded);
		} else if (mask.has(MovableState.SPECIAL)) {
			setCurrentAnimation(_special);
		} else if (mask.has(MovableState.FLYING)) {
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
		} else if (mask.has(MovableState.JUMPING)) {
			_flyingPrelude.clearListeners();
			setIdle();
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
		return _renderable
				.getPosition()
				.copy()
				.add(new Vector2f(16 * Const.SCALE_FACTOR,
						13 * Const.SCALE_FACTOR));
	}

	@Override
	public int getArmLength() {
		return (int) (28 * Const.SCALE_FACTOR);
	}

	@Override
	public void onAdd(final IBitmask<MovableState> mask,
			final MovableState newElement) {
		handleBitmaskChanges(mask, newElement, null);
	}

	@Override
	public void onRemove(final IBitmask<MovableState> mask,
			final MovableState removedElement) {
		handleBitmaskChanges(mask, null, removedElement);

	}
}
