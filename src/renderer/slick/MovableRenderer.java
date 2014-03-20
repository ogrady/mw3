package renderer.slick;

import listener.IEnumBitmaskListener;
import environment.Movable;

/**
 * Renderers for objects that can move should extend this class
 * 
 * @author Daniel
 * 
 */
abstract public class MovableRenderer<M extends Movable> extends
		PositionableRenderer<M> implements IEnumBitmaskListener {

	public MovableRenderer(final M renderable) {
		super(renderable);
	}
}
