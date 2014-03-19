package controller.keyboard;

import org.newdawn.slick.Input;

import renderer.slick.mech.NitroRenderer;
import controller.IControllable;
import controller.IController;
import environment.character.mech.Mech;
import environment.character.mech.Nitro;
import game.Configuration;

/**
 * Keyboard controller specific for the Nitro mech
 * 
 * @author Daniel
 * 
 */
public class MechKeyboardController implements IController {
	private Mech _mech;
	private final Configuration _configuration;

	public MechKeyboardController(final Mech mech,
			final Configuration configuration, final NitroRenderer renderer) {
		_mech = mech;
		_configuration = configuration;
	}

	@Override
	public void update(final Input input, final int delta) {
		_mech.tick(delta);

		int deltaX = 0, deltaY = 0;
		if (input.isKeyDown(_configuration.getInteger(Configuration.UP))) {

		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.DOWN))) {

		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.LEFT))) {
			deltaX = -1;
		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.RIGHT))) {
			deltaX = 1;
		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.JUMP))) {
			deltaY = -2;
		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.SPECIAL))) {
			_mech.specialAttack();
		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.ATTACK_1))) {
			_mech.primaryAttack();
		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.ATTACK_2))) {
			_mech.secondaryAttack();
		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.BLOCK))) {
			_mech.block();
		} else {
			_mech.unblock();
		}
		_mech.move(deltaX, deltaY);
	}

	@Override
	public IControllable getControllable() {
		return _mech;
	}

	@Override
	public void setControllable(final IControllable controllable) {
		_mech = (Nitro) controllable;
	}
}
