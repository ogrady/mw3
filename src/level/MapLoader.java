package level;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import util.Const;
import exception.MapException;

public class MapLoader {

	public static Map load(final String _mapPath) throws MapException {
		Map map = null;
		TiledMap tm;
		try {
			tm = new TiledMap(_mapPath, true);
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

					if (b.isDestructable()) {
						System.out.print(1);
					} else if (b.isSolid()) {
						System.out.print(2);
					} else {
						System.out.print(0);
					}
				}
				System.out.println("\r\n");
			}
		} catch (final SlickException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static void main(final String[] args) throws MapException {
		final String _mapPath = "rsc/map/tm3.tmx";

		TiledMap tm;
		try {
			tm = new TiledMap(_mapPath, false);
			tm.getLayerIndex(Const.MAP_LAYER_DESTRUCTABLE);
			tm.getLayerIndex(Const.MAP_LAYER_SOLID);
			for (int y = 0; y < tm.getWidth(); y++) {
				for (int x = 0; x < tm.getHeight(); x++) { //
					// b.setSolid(tm.getTileId(x, y,
					// tm.getLayerIndex(Const.MAP_LAYER_SOLID)) != -1); //
					// b.setDestructable(tm.getTileId(x, y,
					// tm.getLayerIndex(Const.MAP_LAYER_DESTRUCTABLE)) // !=
					// -1);
					final int i = tm.getTileId(x, y,
							tm.getLayerIndex(Const.MAP_LAYER_SOLID));
					// final String s = i < 10 ? "0" + i : "" + i;
					System.out.print(i != 0 ? 1 : 0);
				}
				System.out.println("\r\n");
			}
		} catch (final SlickException e) {
			e.printStackTrace();
		}

		/*
		  final Map m = load(_mapPath); for (int x = 0; x < m.getWidth(); x++)
		  { for (int y = 0; y < m.getHeight(); y++) {
		  System.out.println(m.getBlock(x, y).isSolid() ? 1 : 0); } }
		 */
	}
}
