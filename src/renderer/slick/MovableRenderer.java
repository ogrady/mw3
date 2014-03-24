package renderer.slick;

import listener.IEnumBitmaskListener;
import environment.Movable;
import environment.MovableState;

/**
 * Renderers for objects that can move should extend this class
 * 
 * @author Daniel
 * 
 */
abstract public class MovableRenderer<M extends Movable> extends
		PositionableRenderer<M> implements IEnumBitmaskListener<MovableState> {

	public MovableRenderer(final M renderable) {
		super(renderable);
	}
}
