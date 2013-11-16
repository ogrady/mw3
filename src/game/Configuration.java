package game;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration extends Properties {
	private static final long serialVersionUID = 1L;
	public static final String
		LEFT_KEY = "left_key",
		RIGHT_KEY = "right_key",
		UP_KEY = "up_key",
		DOWN_KEY = "down_key",
		JUMP_KEY = "jump_key",
		ATTACK_KEY_1 = "attack_key_1",
		ATTACK_KEY_2 = "attack_key_2"
	;
	
	public char getChar(String key) {
		return getProperty(key).charAt(0);
	}

	public int getInteger(String key) {
		return Integer.parseInt(getProperty(key));
	}
	
	public double getDouble(String key) {
		return Double.parseDouble(getProperty(key));
	}
	
	public boolean getBoolean(String key) {
		return Boolean.parseBoolean(getProperty(key));
	}
	
	public Configuration(String path) {
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(path));
			load(in);
			in.close();
		} catch (IOException ioe) {
			try {
				System.err.println(String.format("error when attempting to load config from '%s': %s", path, ioe.getMessage()));
				if(in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
