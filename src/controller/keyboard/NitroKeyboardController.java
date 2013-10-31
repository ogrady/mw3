package controller.keyboard;

import game.Configuration;
import game.MetalWarriors;

import org.newdawn.slick.Input;

import renderer.slick.mech.NitroRenderer;
import controller.IControllable;

public class NitroKeyboardController extends KeyboardController {
	private final NitroRenderer renderer;

	public NitroKeyboardController(final IControllable _controllable,
			final Configuration _configuration, final NitroRenderer _renderer) {
		super(_controllable, _configuration);
		renderer = _renderer;
	}

	@Override
	public void update(final Input _input, final int _delta) {
		setInput(_input);
		_input.addKeyListener(this);
		boolean moving = false;
		int deltaX = 0, deltaY = 0;
		if (isKeyPressed(configuration.getInteger(Configuration.UP_KEY))) {
			deltaY -= 1;
		}
		if (isKeyPressed(configuration.getInteger(Configuration.LEFT_KEY))) {
			deltaX -= 1;
			renderer.turnLeft();
			moving = true;
		}
		if (isKeyPressed(configuration.getInteger(Configuration.DOWN_KEY))) {
			deltaY += 1;
		}
		if (isKeyPressed(configuration.getInteger(Configuration.RIGHT_KEY))) {
			deltaX += 1;
			renderer.turnRight();
			moving = true;
		}
		if (controllable.move(deltaX, deltaY)) {
			renderer.getCurrentAnimation().setAutoUpdate(moving);
			MetalWarriors.instance.getViewPort().getPosition().x -= deltaX
					* controllable.getXSpeed();
			MetalWarriors.instance.getViewPort().getPosition().y -= deltaY
					* controllable.getXSpeed();
		}
	}

}
