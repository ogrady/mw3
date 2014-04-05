package controller.device;

import java.util.HashSet;

import org.newdawn.slick.ControllerListener;
import org.newdawn.slick.Input;

import controller.IControllable;
import controller.IController;
import environment.character.mech.Mech;

/**
 * Controller to move a mech with a Xbox-360-gamepad<br>
 * <br>
 * <p>
 * <img src=
 * "http://fisnikhasani.com/wp-content/uploads/2012/12/xbox_360_controller.png">
 * </p>
 * <p>
 * Oddly enough, pressing the left and right shoulder-triggers doesn't cause an
 * event. So we apparently can't use them.
 * </p>
 * 
 * @author Daniel
 * 
 */
public class MechXboxPadController implements IController, ControllerListener {
	// the following mapping is taken from the actual events, the
	// controllerButtonPressed-method receives as "button"-parameter, when
	// pressing a button on the pad. Except for LEFT, RIGHT, UP and DOWN. So
	// don't change this mapping!
	public static final int
	/**
	 * A-button (bottom)
	 */
	A = 1,
	/**
	 * B-button (right)
	 */
	B = 2,
	/**
	 * X-button (left)
	 */
	X = 3,
	/**
	 * Y-button (top)
	 */
	Y = 4,
	/**
	 * left bumper (left shoulder)
	 */
	LB = 5,
	/**
	 * right bumper (right shoulder)
	 */
	RB = 6,
	/**
	 * Select-button
	 */
	SELECT = 7,
	/**
	 * Start-button
	 */
	START = 9,
	/**
	 * Pressing the left ministick
	 */
	SL = 9,
	/**
	 * Pressing the right ministick
	 */
	SR = 10,
	/**
	 * Left, either on D-Pad or ministick
	 */
	LEFT = 11,
	/**
	 * Righer, either on D-Pad or ministick
	 */
	RIGHT = 12,
	/**
	 * Up, either on D-Pad or ministick
	 */
	UP = 13,
	/**
	 * Down, either on D-Pad or ministick
	 */
	DOWN = 14;

	public Mech _mech;
	private Input _input;
	private final HashSet<Integer> _pressed;

	/**
	 * Constructor
	 * 
	 * @param _mech
	 *            the mech to controll
	 */
	public MechXboxPadController(final Mech _mech) {
		_pressed = new HashSet<Integer>();
		setControllable(_mech);
	}

	@Override
	public IControllable getControllable() {
		return _mech;
	}

	@Override
	public void setControllable(final IControllable controllable) {
		if (controllable != null && controllable instanceof Mech) {
			_mech = (Mech) controllable;
		}
	}

	@Override
	public void update(final Input input, final int delta) {
		setInput(input);
		if (_pressed.contains(UP)) {
			_mech.armUp();
		}
		if (_pressed.contains(DOWN)) {
			_mech.armDown();
		}
		if (_pressed.contains(RIGHT)) {
			_mech.move(1, 0);
		}
		if (_pressed.contains(LEFT)) {
			_mech.move(-1, 0);
		}
		if (_pressed.contains(A)) {
			_mech.jump();
		}
		if (_pressed.contains(X)) {
			_mech.primaryAttack();
		}
		if (_pressed.contains(Y)) {
			_mech.specialAttack();
		}
		if (_pressed.contains(B)) {
			_mech.secondaryAttack();
		}
		if (_pressed.contains(RB)) {
			_mech.block();
		} else {
			_mech.unblock();
		}
	}

	@Override
	public void inputEnded() {

	}

	@Override
	public void inputStarted() {

	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void setInput(final Input input) {
		if (input != _input) {
			if (_input != null) {
				_input.removeControllerListener(this);
			}
			_input = input;
			_input.addControllerListener(this);
		}
	}

	@Override
	public void controllerButtonPressed(final int controller, final int button) {
		_pressed.add(button);
	}

	@Override
	public void controllerButtonReleased(final int controller, final int button) {
		_pressed.remove(button);
	}

	@Override
	public void controllerDownPressed(final int controller) {
		_pressed.add(DOWN);
	}

	@Override
	public void controllerDownReleased(final int controller) {
		_pressed.remove(DOWN);
	}

	@Override
	public void controllerLeftPressed(final int controller) {
		_pressed.add(LEFT);
	}

	@Override
	public void controllerLeftReleased(final int controller) {
		_pressed.remove(LEFT);
	}

	@Override
	public void controllerRightPressed(final int controller) {
		_pressed.add(RIGHT);
	}

	@Override
	public void controllerRightReleased(final int controller) {
		_pressed.remove(RIGHT);
	}

	@Override
	public void controllerUpPressed(final int controller) {
		_pressed.add(UP);
	}

	@Override
	public void controllerUpReleased(final int controller) {
		_pressed.remove(UP);
	}
}
