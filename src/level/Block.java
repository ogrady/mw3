package level;

import org.newdawn.slick.geom.Vector2f;

import environment.Positionable;

public class Block extends Positionable {
	private final Map map;
	private boolean solid, destructable;
	private final int xIndex, yIndex;

	/**
	 * @return the x-coordinate this block has in the grid of the map (not to
	 *         confuse with the absolute position as received by
	 *         {@link #getPosition()} which is the upper left corner of the
	 *         block in absolute pixels)
	 */
	public int getX() {
		return xIndex;
	}

	/**
	 * @return the y-coordinate this block has in the grid of the map (not to
	 *         confuse with the absolute position as received by
	 *         {@link #getPosition()} which is the upper left corner of the
	 *         block in absolute pixels)
	 */
	public int getY() {
		return yIndex;
	}

	/**
	 * @return the map this block resides in
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * @return whether this block is solid
	 */
	public boolean isSolid() {
		return solid;
	}

	/**
	 * @param _solid
	 *            set a block solid or not
	 */
	public void setSolid(final boolean _solid) {
		solid = _solid;
	}

	/**
	 * @return whether this block is destructable
	 */
	public boolean isDestructable() {
		return destructable;
	}

	public void setDestructable(final boolean _destructable) {
		destructable = _destructable;
	}

	public Block(final int _x, final int _y, final Map _map) {
		super(new Vector2f(_x * _map.getBlockWidth(), _y
				* _map.getBlockHeight()), _map.getBlockWidth(), _map
				.getBlockHeight());
		xIndex = _x;
		yIndex = _y;
		map = _map;
	}

	@Override
	public void onCollide(final Positionable _collider) {

	}
}
