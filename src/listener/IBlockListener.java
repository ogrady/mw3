package listener;

import level.Block;

public interface IBlockListener extends IListener {
	void onChangeSolidness(Block b, boolean solid);
}
