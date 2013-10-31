package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import environment.Positionable;

public class Viewport {
	private final Vector2f position;
	private final GameContainer container;

	public Vector2f getPosition() {
		return position;
	}

	public int getWidth() {
		return container.getWidth();
	}

	public int getHeight() {
		return container.getHeight();
	}

	public Viewport(float _x, float _y, GameContainer _container) {
		container = _container;
		position = new Vector2f(_x, _y);
	}

	public void centerAround(Positionable _center) {
		float x = (_center.getPosition().x + _center.getHitbox().getWidth() / 2)
				- getWidth() / 2;
		float y = (_center.getPosition().y + _center.getHitbox().getHeight() / 2)
				- getHeight() / 2;
		position.set(-x, -y);
	}

	public Rectangle getView() {
		return new Rectangle(position.x, position.y, getWidth(), getHeight());
	}
}
