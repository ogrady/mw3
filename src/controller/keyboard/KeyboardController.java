package controller.keyboard;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import controller.IControllable;
import controller.IController;
import environment.Movable;
import game.Configuration;

/**
 * Keyboard input which listens for keypresses
 * 
 * @author Daniel
 * 
 */
abstract public class KeyboardController implements IController, KeyListener {
	protected boolean _accepting;
	protected Movable _controllable;
	protected Input _input;
	protected Configuration _configuration;

	public KeyboardController(final IControllable controllable,
			final Configuration configuration) {
		setControllable(controllable);
		_configuration = configuration;
	}

	@Override
	public IControllable getControllable() {
		return _controllable;
	}

	/**
	 * @throws ClassCastException
	 *             if the controllable is not a {@link Movable}
	 */
	@Override
	public void setControllable(final IControllable controllable) {
		_controllable = (Movable) controllable;
	}

	@Override
	public boolean isAcceptingInput() {
		return _accepting;
	}

	@Override
	public void setInput(final Input input) {
		if (!(_input == input)) {
			if (_input != null) {
				_input.removeKeyListener(this);
			}
			_input = input;
			_input.addKeyListener(this);
		}
	}

	@Override
	public void inputEnded() {
	}

	@Override
	public void inputStarted() {
	}

	@Override
	public void keyPressed(final int key, final char c) {
	}

	@Override
	public void keyReleased(final int key, final char c) {
	}

	public boolean isKeyPressed(final int key) {
		return _input.isKeyDown(key);
	}
}
