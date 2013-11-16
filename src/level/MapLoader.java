package level;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import util.Const;
import exception.MapException;

/**
 * Loads a map from a given tmx-file.<br>
 * Also extracts information for blocks, like whether they are solid,
 * destructable etc.
 * 
 * @author Daniel
 * 
 */
public class MapLoader {

	/**
	 * Loads a map from the given tmx-file
	 * 
	 * @param mapPath
	 *            path to the tmx-file
	 * @return a constructed map
	 * @throws MapException
	 */
	public static Map load(final String mapPath) {
		Map map = null;
		TiledMap tm;
		try {
			tm = new TiledMap(mapPath, true);
			final int destructableIndex = tm
					.getLayerIndex(Const.MAP_LAYER_DESTRUCTABLE);
			final int solidIndex = tm.getLayerIndex(Const.MAP_LAYER_SOLID);
			map = new Map(tm);
			Block b;
			for (int y = 0; y < tm.getHeight(); y++) {
				for (int x = 0; x < tm.getWidth(); x++) {
					b = new Block(x, y, map);
					b.setDestructable(tm.getTileId(x, y, destructableIndex) != 0);
					// destructables are solid by default
					b.setSolid(b.isDestructable()
							|| tm.getTileId(x, y, solidIndex) != 0);
					map.getBlocks()[x][y] = b;
				}
			}
		} catch (final SlickException e) {
			e.printStackTrace();
		}
		return map;
	}
}
