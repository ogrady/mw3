package controller;

/**
 * A controllable can be any object that is controlled by any controller.<br>
 * Controllers can be linked to anything that control the actions and movements
 * of an object.<br>
 * This includes input-devices, such as mice, keyboards, controllers... as well
 * as AIs or network commands
 *
 * @author Daniel
 *
 * @param <C>
 *            type of the controller
 */
public interface IControllable {
	/**
	 * @param controller
	 *            the controller that is used to controller movements and
	 *            actions
	 */
	void setController(IController controller);

	/**
	 * @return the current controller the controls the objects movements and
	 *         actions
	 */
	IController getController();
}
