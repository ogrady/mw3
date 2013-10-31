package renderer;


/**
 * Any class that can be rendered should implement this interface.<br>
 * Different renderers can be applied for different kinds of GUIs.
 * @author Daniel
 *
 * @param <R> type of the renderer
 */
public interface IRendereable<R extends IRenderer> {
	R getRenderer();
	void setRenderer(R _renderer);
}
