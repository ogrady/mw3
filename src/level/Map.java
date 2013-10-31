package level;

import org.newdawn.slick.tiled.TiledMap;

import renderer.IRendereable;
import renderer.slick.MapRenderer;

public class Map implements IRendereable<MapRenderer>{
	private TiledMap map;
	private final Block[][] blocks;
	private MapRenderer renderer;
	
	/**
	 * @return tiled map used to create the map
	 */
	public TiledMap getTiledMap() { return map; }
	
	/**
	 * @return block array (first dim: width, second dim: height)
	 */
	public Block[][] getBlocks() { return blocks; }
	
	/**
	 * @return the width of the map in blocks
	 */
	public int getWidth() { return map.getWidth(); }
	
	/**
	 * @return the height of the map in blocks
	 */
	public int getHeight() { return map.getHeight(); }
	
	/**
	 * @return the width in pixels of one block
	 */
	public int getBlockWidth() { return map.getTileWidth(); }
	
	/**
	 * @return the height in pixels of one block
	 */
	public int getBlockHeight() { return map.getTileHeight(); }
	
	@Override
	public MapRenderer getRenderer() { return renderer; }

	@Override
	public void setRenderer(MapRenderer _renderer) { renderer = _renderer; }
	
	/**
	 * @param x x-coordinate of desired block
	 * @param y y-coordinate of desired block
	 * @return block ad desired position or NULL if the indeces where out of range
	 */
	public Block getBlock(int x, int y) { return 
			x >= 0 && x < getWidth() && y >= 0 && y < getHeight()
			? blocks[x][y]
			: null;
	}
	
	/**
	 * Constructor
	 * @param _widthInBlocks width of the map in blocks
	 * @param _heightInBlocks height of the map in blocks
	 * @param _blockWidth width of one block
	 * @param _blockHeight height of one block
	 */
	public Map(TiledMap _tiledMap) {
		map = _tiledMap;
		blocks = new Block[map.getWidth()][map.getHeight()];
		renderer = new MapRenderer(this);
	}
}
