package controller;

import logger.LogMessageType;

import org.newdawn.slick.Input;

import environment.character.mech.Mech;
import game.MetalWarriors;

public class TestAi implements IController {
	private Mech _controllable;

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
		MetalWarriors.logger.print(
				String.format("%s HP: %d", _controllable.getDescription(),
						_controllable.getCurrentLife()),
				LogMessageType.CONTROLLER_DEBUG);
	}
}
