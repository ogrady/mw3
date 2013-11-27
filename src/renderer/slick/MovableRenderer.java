package renderer.slick;

import listener.IInputListener;
import environment.Movable;

/**
 * Renderers for objects that can move should extend this class
 * 
 * @author Daniel
 * 
 */
abstract public class MovableRenderer<M extends Movable> extends
		PositionableRenderer<M> implements IInputListener {

	public MovableRenderer(final M renderable) {
		super(renderable);
	}
}
