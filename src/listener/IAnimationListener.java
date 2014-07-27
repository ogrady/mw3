package listener;

import org.newdawn.slick.Animation;

/**
 *
 * @author Greg
 */
public interface IAnimationListener extends IListener {
	/**
	 * Called, when the last frame of the {@link Animation} has been displayed
	 * (Daniel)
	 *
	 */
	public void onEnded();
}
