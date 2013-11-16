package controller.keyboard;

import org.newdawn.slick.Input;

import environment.Movable;
import game.Configuration;
import game.MetalWarriors;

/**
 * Default keyboard controls. Mainly for testing (up, down, left, right are
 * accepted)
 * 
 * @author Daniel
 * 
 */
public class DefaultKeyboardController extends KeyboardController {

	public DefaultKeyboardController(final Movable controllable,
			final Configuration configuration) {
		super(controllable, configuration);
	}

	@Override
	public void update(final Input input, final int delta) {
		setInput(input);
		input.addKeyListener(this);
		int deltaX = 0, deltaY = 0;
		if (isKeyPressed(_configuration.getInteger(Configuration.UP_KEY))) {
			deltaY -= 1;
		}
		if (isKeyPressed(_configuration.getInteger(Configuration.LEFT_KEY))) {
			deltaX -= 1;
		}
		if (isKeyPressed(_configuration.getInteger(Configuration.DOWN_KEY))) {
			deltaY += 1;
		}
		if (isKeyPressed(_configuration.getInteger(Configuration.RIGHT_KEY))) {
			deltaX += 1;
		}
		if (_controllable.move(deltaX, deltaY)) {
			MetalWarriors.instance.getViewPort().getPosition().x -= deltaX
					* _controllable.getXSpeed();
			MetalWarriors.instance.getViewPort().getPosition().y -= deltaY
					* _controllable.getXSpeed();
		}
	}
}
