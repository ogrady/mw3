package controller;

import org.newdawn.slick.Input;

/**
 * {@link NullController}s don't do anything and are used for uncontrolled
 * {@link IControllable}s, like empty mechs, standing on the map but still
 * receiving ticks, which will avoid NullPointerExceptions.
 *
 * @author Daniel
 *
 */
public class NullController implements IController {

	@Override
	public IControllable getControllable() {
		return null;
	}

	@Override
	public void setControllable(final IControllable controllable) {

	}

	@Override
	public void update(final Input input, final int delta) {

	}

}
