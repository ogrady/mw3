package controller.device;

import org.newdawn.slick.Input;

import controller.IControllable;
import controller.IController;
import environment.character.mech.Mech;
import game.Configuration;
import game.MetalWarriors;

/**
 * Keyboard controller specific for mechs.
 *
 * @author Daniel
 *
 */
public class MechKeyboardController implements IController {
	private Mech _mech;
	private final Configuration _configuration;

	public MechKeyboardController(final Mech mech,
			final Configuration configuration) {
		setControllable(mech);
		_configuration = configuration;
	}

	@Override
	public void update(final Input input, final int delta) {
		int deltaX = 0;
		final int deltaY = 0;
		if (input.isKeyDown(_configuration.getInteger(Configuration.KB_START))) {
			MetalWarriors.instance.gotoMenu();
		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.KB_UP))) {
			_mech.armUp();
		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.KB_DOWN))) {
			_mech.armDown();
		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.KB_LEFT))) {
			deltaX = -1;
		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.KB_RIGHT))) {
			deltaX = 1;
		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.KB_JUMP))) {
			_mech.jump();
		}
		if (input
				.isKeyDown(_configuration.getInteger(Configuration.KB_SPECIAL))) {
			_mech.specialAttack();
		}
		if (input.isKeyDown(_configuration
				.getInteger(Configuration.KB_ATTACK_1))) {
			_mech.primaryAttack();
		}
		if (input.isKeyDown(_configuration
				.getInteger(Configuration.KB_ATTACK_2))) {
			_mech.secondaryAttack();
		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.KB_BLOCK))) {
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
		_mech = (Mech) controllable;
	}
}
