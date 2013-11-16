package controller;

import org.newdawn.slick.Input;

/**
 * A controller can be anything that can steer an {@link IControllable}, such as keyboards, AIs, joysticks etc.
 * @author Daniel
 *
 * @param <C> type of the {@link IControllable}
 */
public interface IController {
	/**
	 * @return the current {@link IControllable}
	 */
	IControllable getControllable();
	
	/**
	 * @param controllable the new {@link IControllable}
	 */
	void setControllable(IControllable controllable);
	
	/**
	 * Causes the controller to apply its control-rules to the controllable object
	 * @param input input for this tick
	 * @param delta milliseconds passed since the last tick
	 */
	void update(Input input, int delta);
}
