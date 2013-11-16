package level;

import org.newdawn.slick.geom.Vector2f;

import environment.Positionable;

/**
 * Block of which a map consists of. They hold information such as whether they
 * are destructable or not
 * 
 * @author Daniel
 * 
 */
public class Block extends Positionable {
	private final Map _map;
	private boolean _solid, _destructable;
	private final int _xIndex, _yIndex;

	/**
	 * @return the x-coordinate this block has in the grid of the map (not to
	 *         confuse with the absolute position as received by
	 *         {@link #getPosition()} which is the upper left corner of the
	 *         block in absolute pixels)
	 */
	public int getX() {
		return _xIndex;
	}

	/**
	 * @return the y-coordinate this block has in the grid of the map (not to
	 *         confuse with the absolute position as received by
	 *         {@link #getPosition()} which is the upper left corner of the
	 *         block in absolute pixels)
	 */
	public int getY() {
		return _yIndex;
	}

	/**
	 * @return the map this block resides in
	 */
	public Map getMap() {
		return _map;
	}

	/**
	 * @return whether this block is solid
	 */
	public boolean isSolid() {
		return _solid;
	}

	/**
	 * @param _solid
	 *            set a block solid or not
	 */
	public void setSolid(final boolean solid) {
		_solid = solid;
	}

	/**
	 * @return whether this block is destructable
	 */
	public boolean isDestructable() {
		return _destructable;
	}

	public void setDestructable(final boolean destructable) {
		_destructable = destructable;
	}

	public Block(final int x, final int y, final Map map) {
		super(new Vector2f(x * map.getBlockWidth(), y * map.getBlockHeight()),
				map.getBlockWidth(), map.getBlockHeight());
		_xIndex = x;
		_yIndex = y;
		_map = map;
	}

	@Override
	public void onCollide(final Positionable _collider) {

	}
}
