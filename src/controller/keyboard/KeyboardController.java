package controller.keyboard;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import controller.IControllable;
import controller.IController;
import environment.Movable;
import game.Configuration;

abstract public class KeyboardController implements IController, KeyListener {
	protected boolean accepting;
	protected Movable controllable;
	protected Input input;
	protected Configuration configuration;
	
	public KeyboardController(IControllable _controllable, Configuration _configuration) {
		setControllable(_controllable);
		configuration = _configuration;
	}
	
	@Override
	public IControllable getControllable() { return controllable; }

	/**
	 * @throws ClassCastException if the controllable is not a {@link Movable}
	 */
	@Override
	public void setControllable(IControllable _controllable) { 
		controllable = (Movable)_controllable;
	}

	@Override
	public boolean isAcceptingInput() { return accepting; }

	@Override
	public void setInput(Input _input) {
		if(!(input == _input)) {
			if(input != null) {
				input.removeKeyListener(this);
			}
			input = _input;
			input.addKeyListener(this);
		}
	}
	
	@Override
	public void inputEnded() {}

	@Override
	public void inputStarted() {}

	@Override
	public void keyPressed(int _key, char _c) {}

	@Override
	public void keyReleased(int _key, char _c) {}
	
	public boolean isKeyPressed(int _key) {
		return input.isKeyDown(_key);
	}
}
