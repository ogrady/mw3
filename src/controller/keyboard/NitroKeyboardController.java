package controller.keyboard;

import game.Configuration;
import game.MetalWarriors;

import org.newdawn.slick.Input;

import renderer.slick.mech.NitroRenderer;
import controller.IControllable;

/**
 * Keyboard controller specific for the Nitro mech
 * 
 * @author Daniel
 * 
 */
public class NitroKeyboardController extends KeyboardController {
	private final NitroRenderer _renderer;

	public NitroKeyboardController(final IControllable controllable,
			final Configuration configuration, final NitroRenderer renderer) {
		super(controllable, configuration);
		_renderer = renderer;
	}

	@Override
	public void update(final Input input, final int delta) {
		setInput(input);
		input.addKeyListener(this);
		boolean moving = false;
		int deltaX = 0, deltaY = 0;
		if (isKeyPressed(_configuration.getInteger(Configuration.UP_KEY))) {
			deltaY -= 1;
		}
		if (isKeyPressed(_configuration.getInteger(Configuration.LEFT_KEY))) {
			deltaX -= 1;
			_renderer.onLeftButton();
			moving = true;
		}
		if (isKeyPressed(_configuration.getInteger(Configuration.DOWN_KEY))) {
			deltaY += 1;
		}
		if (isKeyPressed(_configuration.getInteger(Configuration.RIGHT_KEY))) {
			deltaX += 1;
			_renderer.onRightButton();
			moving = true;
		}
		if (isKeyPressed(_configuration.getInteger(Configuration.JUMP_KEY))) {
			deltaY -= 2;
			_renderer.onJumpButton();
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
