package renderer.slick;

import listener.IInputListener;
import environment.Movable;

/**
 * Renderers for objects that can move should extend this class
 * 
 * @author Daniel
 * 
 */
abstract public class MovableRenderer extends PositionableRenderer implements
		IInputListener {

	public MovableRenderer(final Movable renderable) {
		super(renderable);
	}
}
