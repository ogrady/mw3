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
		float deltaX = 0;
		float deltaY = 0;
		boolean buttonPressed = false;
		if (input.isKeyDown(_configuration.getInteger(Configuration.KB_START))) {
			MetalWarriors.instance.gotoMenu();
			buttonPressed = true;
		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.KB_UP))) {
			_mech.armUp();
			buttonPressed = true;
		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.KB_DOWN))) {
			_mech.armDown();
			buttonPressed = true;
		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.KB_LEFT))) {
			deltaX = -0.5f;
			buttonPressed = true;
		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.KB_RIGHT))) {
			deltaX = 0.5f;
			buttonPressed = true;
		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.KB_JUMP))) {
			_mech.jump();
			buttonPressed = true;
		}
		if (input
				.isKeyDown(_configuration.getInteger(Configuration.KB_SPECIAL))) {
			_mech.specialAttack();
			buttonPressed = true;
		}
		if (input.isKeyDown(_configuration
				.getInteger(Configuration.KB_ATTACK_1))) {
			_mech.primaryAttack();
			buttonPressed = true;
		}
		if (input.isKeyDown(_configuration
				.getInteger(Configuration.KB_ATTACK_2))) {
			_mech.secondaryAttack();
			buttonPressed = true;
		}
		if (input.isKeyDown(_configuration.getInteger(Configuration.KB_BLOCK))) {
			_mech.block();
			buttonPressed = true;
		} else {
			_mech.unblock();
		}
		
		if(buttonPressed) {
			_mech.move(deltaX, deltaY);
		} else {
			_mech.haltMovement(delta);
		}
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
