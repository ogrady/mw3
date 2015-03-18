package listener;

import level.Block;

public interface IBlockListener extends IListener {
	default void onChangeSolidness(final Block b, final boolean solid) {
	}
}
