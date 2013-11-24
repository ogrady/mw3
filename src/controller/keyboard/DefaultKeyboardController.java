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
		int deltaX = 0, deltaY = 0;
		if (isKeyPressed(_configuration.getInteger(Configuration.UP))) {
			deltaY -= 1;
		}
		if (isKeyPressed(_configuration.getInteger(Configuration.LEFT))) {
			deltaX -= 1;
		}
		if (isKeyPressed(_configuration.getInteger(Configuration.DOWN))) {
			deltaY += 1;
		}
		if (isKeyPressed(_configuration.getInteger(Configuration.RIGHT))) {
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
