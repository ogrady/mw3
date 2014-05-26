package game;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import listener.IConfigListener;
import listener.IListenable;
import listener.ListenerSet;
import listener.notifier.INotifier;
import logger.LogMessageType;

/**
 * Configuration for player-manipulatable values, such as the key-configuration,
 * sound-volume, graphics-resolution and so on.
 *
 * @author Daniel
 *
 */
public class Configuration extends Properties implements
		IListenable<IConfigListener> {
	private static final long serialVersionUID = 1L;
	public static final String
	// the following constants are merely the keys for the stored buttons in the
	// hashmap, NOT the keys (as keys on the keyboard) themselves!
	// move left
	KB_LEFT = "kb_left_key",
	// move right
	KB_RIGHT = "kb_right_key",
	// look up
	KB_UP = "kb_up_key",
	// look down
	KB_DOWN = "kb_down_key",
	// jump
	KB_JUMP = "kb_jump_key",
	// primary attack
	KB_ATTACK_1 = "kb_attack_key_1",
	// secondary attack
	KB_ATTACK_2 = "kb_attack_key_2",
	// special move
	KB_SPECIAL = "kb_special_key",
	// item
	KB_ITEM = "kb_item_key",
	// block
	KB_BLOCK = "kb_block_key",
	// select (eject, enter mech)
	KB_SELECT = "kb_select_key",
	// pause
	KB_START = "kb_start_key",
	// move left
	XB_LEFT = "xb_left_key",
	// move right
	XB_RIGHT = "xb_right_key",
	// look up
	XB_UP = "xb_up_key",
	// look down
	XB_DOWN = "xb_down_key",
	// jump
	XB_JUMP = "xb_jump_key",
	// primary attack
	XB_ATTACK_1 = "xb_attack_key_1",
	// secondary attack
	XB_ATTACK_2 = "xb_attack_key_2",
	// special move
	XB_SPECIAL = "xb_special_key",
	// item
	XB_ITEM = "xb_item_key",
	// block
	XB_BLOCK = "xb_block_key",
	// select (eject, enter mech)
	XB_SELECT = "xb_select_key",
	// pause
	XB_START = "xb_start_key",
	// background music volume
	BGM_VOL = "bgm_vol",
	// sound effects volume
	SFX_VOL = "sfx_vol";

	private final ListenerSet<IConfigListener> _listeners;

	public char getChar(final String key) {
		return getProperty(key).charAt(0);
	}

	public int getInteger(final String key) {
		return Integer.parseInt(getProperty(key));
	}

	public float getFloat(final String key) {
		return Float.parseFloat(getProperty(key));
	}

	public double getDouble(final String key) {
		return Double.parseDouble(getProperty(key));
	}

	public boolean getBoolean(final String key) {
		return Boolean.parseBoolean(getProperty(key));
	}

	/**
	 * Constructor<br>
	 * Attempts to load the configuration from a file. This file has to have the
	 * form of:
	 * <p>
	 * key = value
	 * </p>
	 * With one key/value-pair per line.
	 *
	 * @param path
	 *            the path to the file the configuration should be loaded from
	 */
	public Configuration(final String path) {
		this();
		reload(path);
	}

	public Configuration() {
		_listeners = new ListenerSet<IConfigListener>();
	}

	public void reload(final String from) {
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(from));
			load(in);
			in.close();
		} catch (final IOException ioe) {
			try {
				MetalWarriors.logger.print(String.format(
						"error when attempting to load config from '%s': %s",
						from, ioe.getMessage()), LogMessageType.GENERAL_ERROR);
				if (in != null) {
					in.close();
				}
			} catch (final IOException e) {
				MetalWarriors.logger.print(e.getMessage(),
						LogMessageType.GENERAL_ERROR);
			}
		}
		getListeners().notify(new INotifier<IConfigListener>() {
			@Override
			public void notify(final IConfigListener listener) {
				listener.onLoad(Configuration.this);
			}
		});
	}

	@Override
	public ListenerSet<IConfigListener> getListeners() {
		return _listeners;
	}
}
