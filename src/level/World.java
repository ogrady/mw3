package level;

import org.newdawn.slick.tiled.TiledMap;

import renderer.IRendereable;
import renderer.slick.MapRenderer;

/**
 * Represents the internal structure of a map the game is played on.<br>
 * Each map wraps a TiledMap which holds all the information from the tiled-file
 * and an array of blocks to check for collisions and such.
 * 
 * @author Daniel
 * 
 */
public class World implements IRendereable<MapRenderer> {
	private final TiledMap _map;
	private final Block[][] _blocks;
	private MapRenderer _renderer;

	/**
	 * @return tiled map used to create the map
	 */
	public TiledMap getTiledMap() {
		return _map;
	}

	/**
	 * @return block array (first dim: width, second dim: height)
	 */
	public Block[][] getBlocks() {
		return _blocks;
	}

	/**
	 * @return the width of the map in blocks
	 */
	public int getWidth() {
		return _map.getWidth();
	}

	/**
	 * @return the height of the map in blocks
	 */
	public int getHeight() {
		return _map.getHeight();
	}

	/**
	 * @return the width in pixels of one block
	 */
	public int getBlockWidth() {
		return _map.getTileWidth();
	}

	/**
	 * @return the height in pixels of one block
	 */
	public int getBlockHeight() {
		return _map.getTileHeight();
	}

	@Override
	public MapRenderer getRenderer() {
		return _renderer;
	}

	@Override
	public void setRenderer(final MapRenderer renderer) {
		_renderer = renderer;
	}

	/**
	 * @param x
	 *            x-coordinate of desired block
	 * @param y
	 *            y-coordinate of desired block
	 * @return block ad desired position or NULL if the indices where out of
	 *         range
	 */
	public Block getBlock(final int x, final int y) {
		return x >= 0 && x < getWidth() && y >= 0 && y < getHeight() ? _blocks[x][y]
				: null;
	}

	/**
	 * Constructor
	 * 
	 * @param tiledMap
	 *            the tiledmap to use
	 */
	public World(final TiledMap tiledMap) {
		_map = tiledMap;
		_blocks = new Block[_map.getWidth()][_map.getHeight()];
		_renderer = new MapRenderer(this);
	}
}
