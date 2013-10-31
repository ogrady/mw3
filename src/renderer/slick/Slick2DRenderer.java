package renderer.slick;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

import renderer.IRenderer;


/**
 * Renderer for slick games on a 2D surface {@link Graphics}. 
 * @author Daniel
 *
 */
abstract public class Slick2DRenderer implements IRenderer {
	protected Animation current;
	
	public Animation getCurrentAnimation() { return current; }
	public int getWidth() {	return current.getWidth(); }
	public int getHeight() { return current.getHeight(); }
	
}
