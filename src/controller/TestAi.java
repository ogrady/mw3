package controller;

import java.util.Random;

import logger.LogMessageType;

import org.newdawn.slick.Input;

import environment.character.mech.Mech;
import game.MetalWarriors;

public class TestAi implements IController {
	private Mech _controllable;
	private int dir = 1;

	public TestAi(final Mech m) {
		setControllable(m);
	}

	@Override
	public IControllable getControllable() {
		return _controllable;
	}

	@Override
	public void setControllable(final IControllable controllable) {
		_controllable = (Mech) controllable;

	}

	@Override
	public void update(final Input input, final int delta) {
		dir = new Random().nextInt(10) == 0 ? -1 * dir : dir;
		final int action = new Random().nextInt(10);
		switch (action) {
		case 0:
			_controllable.block();
		case 1:
			_controllable.secondaryAttack();
		case 2:
			_controllable.specialAttack();
		default:
		case 3:
			_controllable.primaryAttack();
		}
		// _controllable.move(dir, 0);
		// _controllable.block();
		MetalWarriors.logger.print(
				String.format("%s HP: %d", _controllable.getDescription(),
						_controllable.getCurrentLife()),
				LogMessageType.CONTROLLER_DEBUG);
	}
}
