package level;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import renderer.DefaultRenderer;
import util.Const;
import environment.Positionable;
import environment.collider.NeverCollider;

/**
 * Block of which a map consists of. They hold information such as whether they
 * are destructable or not
 * 
 * @author Daniel
 * 
 */
public class Block extends Positionable {
	public static final ArrayList<Block> instances = new ArrayList<Block>();
	private final World _map;
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
	public World getMap() {
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
		// block is just an empty space
		if (!solid) {
			_collider = new NeverCollider(this);
		}
	}

	/**
	 * @return whether this block is destructable
	 */
	public boolean isDestructable() {
		return _destructable;
	}

	/**
	 * @param destructable
	 *            set a block destructable or not
	 */
	public void setDestructable(final boolean destructable) {
		_destructable = destructable;
	}

	/**
	 * Constructor
	 * 
	 * @param x
	 *            x-coordinate within the map-grid
	 * @param y
	 *            y-coordinate within the map-grid
	 * @param map
	 *            map in which the block is contained
	 */
	public Block(final int x, final int y, final World map) {
		super(new Vector2f(x * map.getBlockWidth(), y * map.getBlockHeight()),
				map.getBlockWidth(), map.getBlockHeight());
		_xIndex = x;
		_yIndex = y;
		_map = map;
		instances.add(this);
	}

	public void destroy() {
		if (_destructable) {
			setDestructable(false);
			setSolid(false);
			setRenderer(new DefaultRenderer());
			_map.getTiledMap().setTileId(
					_xIndex,
					_yIndex,
					_map.getTiledMap()
							.getLayerIndex(Const.MAP_LAYER_BACKGROUND), 1);
		}
	}

	@Override
	public void destruct() {
		super.destruct();
		instances.remove(this);
	}

	@Override
	public String toString() {
		return String.format("Block at (%d|%d)", _xIndex, _yIndex);
	}
}
