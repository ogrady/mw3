package environment;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import renderer.IRendereable;
import renderer.slick.Slick2DRenderer;

/**
 * Object that can be positioned somewhere on the playing field and has a certain width and height for collisions
 * @author Daniel
 */
public abstract class Positionable implements IRendereable<Slick2DRenderer> {
	public static final ArrayList<Positionable> instances = new ArrayList<Positionable>();
	
	protected Vector2f position;
	protected float width, height;
	protected Slick2DRenderer renderer;
	
	
	/**
	 * @param _width new width of the object
	 */
	public void setWidth(int _width) { width = _width; }
	
	/**
	 * @param _height new height of the object
	 */
	public void setHeight(int _height) { height = _height; }
	
	/**
	 * @return current position of the object. That is the upper left corner of the object!
	 */
	public Vector2f getPosition() { return position; }
	
	/**
	 * @return hitbox for collisions
	 */
	public Rectangle getHitbox() { return new Rectangle(position.x, position.y, width, height); }
	
	@Override
	public Slick2DRenderer getRenderer() { return renderer; }
	@Override
	public void setRenderer(Slick2DRenderer _renderer) { renderer = _renderer; }
	
	/**
	 * Constructor
	 * @param _position initial position
	 * @param _width width of the hitbox
	 * @param _height height of the hitbox
	 */
	public Positionable(Vector2f _position, float _width, float _height) {
		position = _position;
		width = _width;
		height = _height;
		Positionable.instances.add(this);
		//hitbox =  new Re
				//new Rectangle(_position.x, _position.y, _width, _height);
	}
	
	public void destruct() {
		Positionable.instances.remove(this);
	}
	
	abstract public void onCollide(Positionable _collider);
}
