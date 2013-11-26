package game;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import logger.LogMessageType;

public class Configuration extends Properties {
	private static final long serialVersionUID = 1L;
	public static final String
	// the following constants are merely the keys for the stored buttons in the
	// hashmap, NOT the keys (as keys on the keyboard) themselves!
	// move left
			LEFT = "left_key",
			// move right
			RIGHT = "right_key",
			// look up
			UP = "up_key",
			// look down
			DOWN = "down_key",
			// jump
			JUMP = "jump_key",
			// primary attack
			ATTACK_1 = "attack_key_1",
			// secondary attack
			ATTACK_2 = "attack_key_2",
			// special move
			SPECIAL = "special",
			// item
			ITEM = "item_key",
			// block
			BLOCK = "block_key",
			// select (eject, enter mech)
			SELECT = "select",
			// pause
			START = "start";

	public char getChar(final String key) {
		return getProperty(key).charAt(0);
	}

	public int getInteger(final String key) {
		return Integer.parseInt(getProperty(key));
	}

	public double getDouble(final String key) {
		return Double.parseDouble(getProperty(key));
	}

	public boolean getBoolean(final String key) {
		return Boolean.parseBoolean(getProperty(key));
	}

	public Configuration(final String path) {
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(path));
			load(in);
			in.close();
		} catch (final IOException ioe) {
			try {
				MetalWarriors.logger.print(String.format(
						"error when attempting to load config from '%s': %s",
						path, ioe.getMessage()), LogMessageType.GENERAL_ERROR);
				if (in != null) {
					in.close();
				}
			} catch (final IOException e) {
				MetalWarriors.logger.print(e.getMessage(),
						LogMessageType.GENERAL_ERROR);
			}
		}
	}
}
