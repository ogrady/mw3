package controller.keyboard;

import org.newdawn.slick.Input;

import environment.Movable;
import game.Configuration;
import game.MetalWarriors;

public class DefaultKeyboardController extends KeyboardController {

	public DefaultKeyboardController(Movable _controllable,
			Configuration _configuration) {
		super(_controllable, _configuration);
	}

	@Override
	public void update(Input _input, int _delta) {
		setInput(_input);
		_input.addKeyListener(this);
		int deltaX = 0, deltaY = 0;
		if (isKeyPressed(configuration.getInteger(Configuration.UP_KEY))) {
			deltaY -= 1;
		}
		if (isKeyPressed(configuration.getInteger(Configuration.LEFT_KEY))) {
			deltaX -= 1;
		}
		if (isKeyPressed(configuration.getInteger(Configuration.DOWN_KEY))) {
			deltaY += 1;
		}
		if (isKeyPressed(configuration.getInteger(Configuration.RIGHT_KEY))) {
			deltaX += 1;
		}
		if (controllable.move(deltaX, deltaY)) {
			MetalWarriors.instance.getViewPort().getPosition().x -= deltaX
					* controllable.getXSpeed();
			MetalWarriors.instance.getViewPort().getPosition().y -= deltaY
					* controllable.getXSpeed();
		}
	}
}
