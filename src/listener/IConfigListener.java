package listener;

import game.Configuration;

public interface IConfigListener extends IListener {
	/**
	 * Fired when the config is (re)loaded. Values from the config (like the
	 * keybinding) could have changed in the meanwhile and listeners might be
	 * interested in this change.
	 *
	 * @param conf
	 *            the newly loaded configuration
	 */
	void onLoad(Configuration config);
}
