package level;

import listener.IBlockListener;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import renderer.IRendereable;
import renderer.slick.MapRenderer;
import util.QuadTree;

/**
 * Represents the internal structure of a map the game is played on.<br>
 * Each map wraps a TiledMap which holds all the information from the tiled-file
 * and an array of blocks to check for collisions and such.
 *
 * @author Daniel
 *
 */
public class World implements IRendereable<MapRenderer>, IBlockListener {
	private final TiledMap _map;
	private final Block[][] _blocks;
	private MapRenderer _renderer;
	private final QuadTree<Block> _quadtree;
	private Music _bgm;

	public static World last;

	/**
	 * Loads the background-music for this {@link World} from the given path. If
	 * no music can be loaded, the music will be NULLed
	 *
	 * @param path
	 *            path to load the bgm from. Must point to a ogg-file
	 */
	public void loadBGM(final String path) {
		try {
			_bgm = new Music(path);
		} catch (final SlickException e) {
			_bgm = null;
		}
	}

	/**
	 * @return the bgm playing in this level
	 */
	public Music getBGM() {
		return _bgm;
	}

	/**
	 * Attempts to play the bgm if not NULL
	 */
	public void playBGM() {
		if (_bgm != null) {
			_bgm.play();
		}
	}

	/**
	 * Places a block at a certain position in the grid. This method will mostly
	 * be used upon loading a map from file and injecting the blocks one after
	 * one into the map. Solid blocks will be added to the quadtree of solid
	 * blocks.
	 *
	 * @param x
	 *            x index for the block
	 * @param y
	 *            y index for the block
	 * @param b
	 *            block to add
	 */
	public void setBlockAt(final int x, final int y, final Block b) {
		final Block old = _blocks[x][y];
		_blocks[x][y] = b;
		if (b.isSolid()) {
			_quadtree.add(b);
		}
		if (old != null) {
			b.getListeners().unregisterListener(this);
		}
		b.getListeners().registerListener(this);
	}

	public QuadTree<Block> getQuadtree() {
		return _quadtree;
	}

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
	 * @return width of the map in pixels
	 */
	public int getPixelWidth() {
		return getWidth() * getBlockWidth();
	}

	/**
	 * @return height of the map in pixels
	 */
	public int getPixelHeight() {
		return getHeight() * getBlockHeight();
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
	 * Gets the block at the passed coordinate
	 *
	 * @param x
	 *            x-coordinate of desired block
	 * @param y
	 *            y-coordinate of desired block
	 * @return block at desired position or NULL if the indices where out of
	 *         bounds
	 */
	public Block getBlock(final int x, final int y) {
		return x >= 0 && x < getWidth() && y >= 0 && y < getHeight() ? _blocks[x][y]
				: null;
	}

	/**
	 * Gets the block that contains the passed point-coordinate
	 *
	 * @param x
	 *            x-coordinate of the point
	 * @param y
	 *            y-coordinate of the point
	 * @return block at desired position or NULL if the indices where out of
	 *         bounds
	 */
	public Block getBlockAt(final int x, final int y) {
		final int xcoord = x / getBlockWidth();
		final int ycoord = y / getBlockHeight();
		return getBlock(xcoord, ycoord);
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
		_quadtree = new QuadTree<Block>(null, new Rectangle(0, 0,
				getPixelWidth(), getPixelHeight()));
		last = this;
	}

	@Override
	public void onChangeSolidness(final Block b, final boolean solid) {
		if (solid) {
			_quadtree.add(b);
		} else {
			_quadtree.remove(b);
		}
	}
}
