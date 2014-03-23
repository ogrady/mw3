package listener;

import util.Bitmask;

public interface IEnumBitmaskListener extends IListener {
	void onChange(Bitmask mask);

}
