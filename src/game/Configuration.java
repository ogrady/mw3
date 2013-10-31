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
	
	public char getChar(String _key) {
		return getProperty(_key).charAt(0);
	}

	public int getInteger(String _key) {
		return Integer.parseInt(getProperty(_key));
	}
	
	public double getDouble(String _key) {
		return Double.parseDouble(getProperty(_key));
	}
	
	public boolean getBoolean(String _key) {
		return Boolean.parseBoolean(getProperty(_key));
	}
	
	public Configuration(String _path) {
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(_path));
			load(in);
			in.close();
		} catch (IOException ioe) {
			try {
				System.err.println(String.format("error when attempting to load config from '%s': %s", _path, ioe.getMessage()));
				if(in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
