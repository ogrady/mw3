package controller.keyboard;

import environment.projectile.Bullet;
import game.Configuration;
import game.MetalWarriors;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import renderer.slick.mech.NitroRenderer;
import controller.IControllable;

/**
 * Keyboard controller specific for the Nitro mech
 * 
 * @author Daniel
 * 
 */
public class NitroKeyboardController extends KeyboardController {
	private final static int SMG_DELAY = 100;
	private final NitroRenderer _renderer;
	private int _smgDelayAccu;

	public NitroKeyboardController(final IControllable controllable,
			final Configuration configuration, final NitroRenderer renderer) {
		super(controllable, configuration);
		_renderer = renderer;
	}

	@Override
	public void update(final Input input, final int delta) {
		setInput(input);
		_smgDelayAccu -= delta;
		boolean moving = false;
		int deltaX = 0, deltaY = 0;
		if (isKeyPressed(_configuration.getInteger(Configuration.UP))) {
			// deltaY -= 1;
			_renderer.onUpButton(true);
		}
		if (isKeyPressed(_configuration.getInteger(Configuration.LEFT))) {
			deltaX -= 1;
			_renderer.onLeftButton(true);
			moving = true;
		}
		if (isKeyPressed(_configuration.getInteger(Configuration.DOWN))) {
			deltaY += 1;
			_renderer.onDownButton(true);
		}
		if (isKeyPressed(_configuration.getInteger(Configuration.RIGHT))) {
			deltaX += 1;
			_renderer.onRightButton(true);
			moving = true;
		}
		if (isKeyPressed(_configuration.getInteger(Configuration.JUMP))) {
			deltaY -= 2;
			_renderer.onJumpButton(true);
		}
		if (isKeyPressed(_configuration.getInteger(Configuration.SPECIAL))) {
			deltaX = 0;
			_renderer.onSpecialActionButton(true);
			moving = false;
		}
		if (isKeyPressed(_configuration.getInteger(Configuration.ATTACK_1))) {
			_renderer.onPrimaryAttackButton(true);
			// TODO replace spawning-position with actual bullet-exit-point as
			// soon as available
			if (_smgDelayAccu <= 0) {
				new Bullet(_controllable.getPosition().copy()
						.add(new Vector2f(_controllable.getWidth(), 0)),
						new Vector2f(2.5f * _controllable.getDirection(), 0),
						_controllable);
				_smgDelayAccu = SMG_DELAY;
			}
		}
		if (_controllable.move(deltaX, deltaY)) {
			_renderer.getCurrentAnimation().setAutoUpdate(moving);
			MetalWarriors.instance.getViewPort().getPosition().x -= deltaX
					* _controllable.getXSpeed();
			MetalWarriors.instance.getViewPort().getPosition().y -= deltaY
					* _controllable.getXSpeed();
		}
	}
}
